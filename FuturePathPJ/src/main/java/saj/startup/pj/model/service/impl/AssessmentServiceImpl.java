package saj.startup.pj.model.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.model.dao.entity.AssessmentCheckerData;
import saj.startup.pj.model.dao.entity.AssessmentResultEntity;
import saj.startup.pj.model.dao.entity.HistoryQuestionEntity;
import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.dto.AssessmentDto;
import saj.startup.pj.model.logic.AnswerLogic;
import saj.startup.pj.model.logic.HistoryLogic;
import saj.startup.pj.model.logic.QuestionLogic;
import saj.startup.pj.model.object.RecommendationObj;
import saj.startup.pj.model.service.AssessmentService;
import saj.startup.pj.model.service.UserService;

@Service
public class AssessmentServiceImpl implements AssessmentService{
	
	@Autowired
	private AnswerLogic answerLogic;
	
	@Autowired
	private QuestionLogic questionLogic;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HistoryLogic historyLogic;
	
	@Override
	public void saveAssessmentResult(AssessmentDto inDto) throws Exception {
		
		
	}


	@Override
	public AssessmentDto getAssessmentResult(AssessmentDto inDto) throws Exception {

	    AssessmentDto outDto = new AssessmentDto();
	    
	    UserEntity user = userService.getUserActive();

	    ObjectMapper mapper = new ObjectMapper();
	    HashMap<Integer, Integer> answeredMap = mapper.readValue(
	        inDto.getAnsweredJson(),
	        new TypeReference<HashMap<Integer, Integer>>() {}
	    );
	    
	    System.out.println(getTopMatches("E-I-S"));

	    int totalCorrect = 0;
	    int totalIncorrect = 0;

	    Map<String, RecommendationObj> correctCountMap = new HashMap<>();

	    Map<String, Integer> totalQuestionPerCode = new HashMap<>();
	    
	    List<HistoryQuestionEntity> historyQuestions = new ArrayList<>();
	    
	    AssessmentResultEntity resultEntity = new AssessmentResultEntity();
	    resultEntity.setUserIdPk(user.getIdPk());
	    
	    int resultIdPk = historyLogic.saveAssessmentResult(resultEntity);

	    for (Map.Entry<Integer, Integer> entry : answeredMap.entrySet()) {
	        Integer questionId = entry.getKey();
	        Integer answerId = entry.getValue();

	        AssessmentCheckerData checker = questionLogic.getQuestionAssessmentChecker(
	            questionId, answerId
	        );

	        String code = checker.getCode();
	        String name = checker.getName();
	        boolean isCorrect = Boolean.TRUE.equals(checker.getIsCorrect());

	        // Initialize if absent
	        correctCountMap.putIfAbsent(code, new RecommendationObj(code, name, 0, 0.0));
	        totalQuestionPerCode.put(code, totalQuestionPerCode.getOrDefault(code, 0) + 1);

	        RecommendationObj rec = correctCountMap.get(code);

	        if (isCorrect) {
	            totalCorrect++;
	            rec.setCorrectCount(rec.getCorrectCount() + 1);
	        } else {
	            totalIncorrect++;
	        }
	        
	        HistoryQuestionEntity history = new HistoryQuestionEntity();
	        history.setResultIdPk(resultIdPk);
	        history.setUserIdPk(user.getIdPk());
	        history.setQuestionIdPk(questionId);	     
	        history.setAnswerIdPk(answerId);
	        history.setIsCorrect(isCorrect);
	         
	        historyQuestions.add(history);
	        
	    }
	    
	    historyLogic.saveHistoryQuestions(historyQuestions);

	    // ✅ Compute per-code percentages only once
	    for (Map.Entry<String, RecommendationObj> e : correctCountMap.entrySet()) {
	        String code = e.getKey();
	        RecommendationObj rec = e.getValue();
	        int totalPerCode = totalQuestionPerCode.getOrDefault(code, 0);
	        double percentagePerCode = totalPerCode > 0
	            ? (double) rec.getCorrectCount() / totalPerCode * 100
	            : 0.0;
	        rec.setPercentage(percentagePerCode);
	    }

	    int totalQuestions = answeredMap.size();
	    double percentage = totalQuestions > 0 ? ((double) totalCorrect / totalQuestions) * 100 : 0.0;
	    percentage = Math.round(percentage * 10.0) / 10.0;
	    
	    resultEntity.setCorrect(totalCorrect);
	    resultEntity.setIncorrect(totalIncorrect);
	    resultEntity.setScore(percentage);
	    resultEntity.setTotalQuestion(totalQuestions);

	    outDto.setResultIdPk(resultIdPk);
	    outDto.setTotalCorrect(totalCorrect);
	    outDto.setTotalIncorrect(totalIncorrect);
	    outDto.setTotalQuestion(totalQuestions);
	    outDto.setPercentage(percentage);
	    outDto.setRecommendationMap(correctCountMap);

	    // ---- Debug Output ----
//	    System.out.println("Total Questions: " + totalQuestions);
//	    System.out.println("Total Correct: " + totalCorrect);
//	    System.out.println("Total Incorrect: " + totalIncorrect);
//	    System.out.println("Percentage: " + percentage + "%");
//	    System.out.println("Result: " + result);
//
//	    System.out.println("\nCorrect Count Map (code -> RecommendationObj):");
//	    for (Map.Entry<String, RecommendationObj> e : correctCountMap.entrySet()) {
//	        RecommendationObj rec = e.getValue();
//	        System.out.printf(
//	            "Code: %s | Correct: %d | %.2f%%\n",
//	            rec.getCode(),
//	            rec.getCorrectCount(),
//	            rec.getPercentage()
//	        );
//	    }

	    return outDto;
	}

	@Override
	public AssessmentDto getAssessmentRIASECResult(AssessmentDto inDto) throws Exception {

	    Map<String, Integer> questionCount = Map.of(
	        "R", 6,
	        "I", 8,
	        "A", 7,
	        "S", 6,
	        "E", 7,
	        "C", 8
	    );

	    Map<String, Integer> totals = Map.of(
	        "R", inDto.getRealistic(),
	        "I", inDto.getInvestigative(),
	        "A", inDto.getArtistic(),
	        "S", inDto.getSocial(),
	        "E", inDto.getEnterprising(),
	        "C", inDto.getConventional()
	    );
	    
	    List<String> top3Letters = totals.entrySet().stream()
	    	    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()) // highest first
	    	    .limit(3)
	    	    .map(Map.Entry::getKey)
	    	    .collect(Collectors.toList());


	    String topCombo = String.join("-", top3Letters);

	    StringBuilder message = new StringBuilder("Based on your interests and strengths:<br><br>");
	    for (String letter : top3Letters) {
	        message.append("• ")
	               .append(CommonConstant.TRAIT_DESCRIPTIONS.getOrDefault(letter, ""))
	               .append(".<br>");
	    }
	    String summary = CommonConstant.RIASEC_SUMMARY_MAP.getOrDefault(
	        top3Letters.get(0),
	        "You have a mix of interests that can lead to many exciting paths."
	    );
	    message.append("<br>").append(summary);

	
	    Set<String> exampleFieldsSet = new LinkedHashSet<>(); 

	    for (String letter : top3Letters) {
	
	        Set<Integer> keys = CommonConstant.RIASEC_CODE_MAP.entrySet().stream()
	                .filter(e -> e.getValue().contains(letter))
	                .map(Map.Entry::getKey)
	                .collect(Collectors.toSet());


	        keys.stream()
	            .map(CommonConstant.RIASEC_DETAIL_MAP::get)
	            .filter(Objects::nonNull)
	            .forEach(exampleFieldsSet::add); 
	    }

	    String exampleFields = String.join(", ", exampleFieldsSet);

	    DecimalFormat df = new DecimalFormat("0.00");

	    AssessmentDto dto = new AssessmentDto();
	    dto.setCombination(topCombo);
	    dto.setMessage(message.toString());
	    dto.setExampleFields(exampleFields);

	    dto.setRealisticPercentageStr(df.format((double) inDto.getRealistic() / (questionCount.get("R") * 4) * 100));
	    dto.setInvestigativePercentageStr(df.format((double) inDto.getInvestigative() / (questionCount.get("I") * 4) * 100));
	    dto.setArtisticPercentageStr(df.format((double) inDto.getArtistic() / (questionCount.get("A") * 4) * 100));
	    dto.setSocialPercentageStr(df.format((double) inDto.getSocial() / (questionCount.get("S") * 4) * 100));
	    dto.setEnterprisingPercentageStr(df.format((double) inDto.getEnterprising() / (questionCount.get("E") * 4) * 100));
	    dto.setConventionalPercentageStr(df.format((double) inDto.getConventional() / (questionCount.get("C") * 4) * 100));

	    return dto;
	}
	
	public static List<String> getTopMatches(String inputCode) {
	    List<Map.Entry<Integer, String>> allEntries = new ArrayList<>(CommonConstant.RIASEC_CODE_MAP.entrySet());

	    allEntries.sort((a, b) -> Integer.compare(
	            calculateScore(inputCode, b.getValue()),
	            calculateScore(inputCode, a.getValue())
	    ));

	    return allEntries.stream()
	            .map(e -> CommonConstant.RIASEC_DETAIL_MAP.get(e.getKey()))
	            .distinct()
	            .limit(5)
	            .collect(Collectors.toList());
	}

	private static int calculateScore(String inputCode, String testCode) {
	    String[] inp = inputCode.split("-");
	    String[] tst = testCode.split("-");

	    int score = 0;

	    if (inputCode.equals(testCode)) score += 100;

	    // Rule 2: Contains all letters bonus
	    for (String c : inp) {
	        if (testCode.contains(c)) score += 10;
	    }

	    // Rule 3: Position match bonus
	    for (int i = 0; i < 3; i++) {
	        if (inp[i].equals(tst[i])) score += 5;
	    }

	    return score;
	}




}





