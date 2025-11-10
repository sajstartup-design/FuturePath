package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import saj.startup.pj.model.dao.entity.HistoryQuestionData;
import saj.startup.pj.model.dao.entity.HistoryQuestionEntity;
import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.dao.projection.AssessmentStatisticsData;
import saj.startup.pj.model.dao.projection.UniversityRecommendationData;
import saj.startup.pj.model.dto.AssessmentDto;
import saj.startup.pj.model.logic.AnswerLogic;
import saj.startup.pj.model.logic.HistoryLogic;
import saj.startup.pj.model.logic.QuestionLogic;
import saj.startup.pj.model.logic.UniversityLogic;
import saj.startup.pj.model.object.RecommendationObj;
import saj.startup.pj.model.service.AssessmentService;
import saj.startup.pj.model.service.UserService;

@Service
public class AssessmentServiceImpl implements AssessmentService{
		
	@Autowired
	private QuestionLogic questionLogic;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HistoryLogic historyLogic;
	
	@Autowired
	private UniversityLogic universityLogic;
	
	@Override
	public AssessmentDto saveAssessmentResult(AssessmentDto inDto) throws Exception {

	    AssessmentDto outDto = new AssessmentDto();
	    
	    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	    UserEntity user = userService.getUserActive();

	    ObjectMapper mapper = new ObjectMapper();
	    HashMap<Integer, Integer> answeredMap = mapper.readValue(
	        inDto.getAnsweredJson(),
	        new TypeReference<HashMap<Integer, Integer>>() {}
	    );

	    int totalCorrect = 0;
	    int totalIncorrect = 0;

	    List<HistoryQuestionEntity> historyQuestions = new ArrayList<>();

	    AssessmentResultEntity resultEntity = new AssessmentResultEntity();
	    resultEntity.setUserIdPk(user.getIdPk());
	    resultEntity.setDateTaken(timestamp);
	    resultEntity.setCategory(
    	    inDto.getMode() != null && inDto.getMode().contains("DEGREE") ? "DEGREE" : "STRAND"
    	);
	    int resultIdPk = historyLogic.saveAssessmentResult(resultEntity);

	    for (Map.Entry<Integer, Integer> entry : answeredMap.entrySet()) {
	        Integer questionId = entry.getKey();
	        Integer answerId = entry.getValue();

	        AssessmentCheckerData checker = questionLogic.getQuestionAssessmentChecker(questionId, answerId);
	        boolean isCorrect = Boolean.TRUE.equals(checker.getIsCorrect());

	        if (isCorrect) {
	            totalCorrect++;
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

	    int totalQuestions = answeredMap.size();
	    double percentage = totalQuestions > 0 ? ((double) totalCorrect / totalQuestions) * 100 : 0.0;
	    percentage = Math.round(percentage * 10.0) / 10.0;

	    resultEntity.setCorrect(totalCorrect);
	    resultEntity.setIncorrect(totalIncorrect);
	    resultEntity.setScore(percentage);
	    resultEntity.setTotalQuestion(totalQuestions);

	    historyLogic.saveAssessmentResult(resultEntity);

	    outDto.setResultIdPk(resultIdPk);

	    return outDto;
	}



	@Override
	public AssessmentDto getAssessmentResult(AssessmentDto inDto) throws Exception {

	    AssessmentDto outDto = new AssessmentDto();

	    System.out.println("Result Id: " + inDto.getResultIdPk());

	    Map<String, RecommendationObj> correctCountMap = new HashMap<>();
	    Map<String, Integer> totalQuestionPerCode = new HashMap<>();

	    AssessmentResultEntity result = historyLogic.getAssessmentResult(inDto.getResultIdPk());
	    List<HistoryQuestionData> questions = historyLogic.getHistoryQuestionsByResultIdPk(inDto.getResultIdPk());
	    outDto.setQuestions(questions);

	    for (HistoryQuestionData q : questions) {
	        String code = q.getCode();
	        String name = q.getName();
	        boolean isCorrect = Boolean.TRUE.equals(q.getIsCorrect());

	        correctCountMap.putIfAbsent(code, new RecommendationObj(code, name, 0, 0.0));
	        totalQuestionPerCode.put(code, totalQuestionPerCode.getOrDefault(code, 0) + 1);

	        if (isCorrect) {
	            correctCountMap.get(code).setCorrectCount(
	                correctCountMap.get(code).getCorrectCount() + 1
	            );
	        }
	    }

	    for (Map.Entry<String, RecommendationObj> e : correctCountMap.entrySet()) {
	        String code = e.getKey();
	        RecommendationObj rec = e.getValue();
	        int totalPerCode = totalQuestionPerCode.getOrDefault(code, 0);
	        double percentagePerCode = totalPerCode > 0
	            ? ((double) rec.getCorrectCount() / totalPerCode) * 100
	            : 0.0;
	        rec.setPercentage(percentagePerCode);
	    }

	    List<RecommendationObj> top3 = correctCountMap.values().stream()
	        .filter(rec -> rec.getPercentage() > 0)
	        .sorted((a, b) -> Double.compare(b.getPercentage(), a.getPercentage()))
	        .limit(3)
	        .collect(Collectors.toList());

	    List<String> top3Codes = top3.stream()
	        .map(RecommendationObj::getCode)
	        .collect(Collectors.toList());

	    List<UniversityRecommendationData> universities = universityLogic.getUniversityRecommendation(top3Codes);
	    
	    outDto.setUniversities(universities);

	    outDto.setResultIdPk(inDto.getResultIdPk());
	    outDto.setTotalCorrect(result.getCorrect());
	    outDto.setTotalIncorrect(result.getIncorrect());
	    outDto.setTotalQuestion(result.getTotalQuestion());
	    outDto.setPercentage(result.getScore());
	    outDto.setRecommendationMap(correctCountMap);
	    outDto.setTop3Recommendations(top3);

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
	    System.out.println("TOP COMBO: " + topCombo);
	    dto.setExampleFields(exampleFields);
	    dto.setRiasecCodes(getTopMatches(topCombo));
	    dto.setRealisticPercentageStr(df.format((double) inDto.getRealistic() / (questionCount.get("R") * 4) * 100));
	    dto.setInvestigativePercentageStr(df.format((double) inDto.getInvestigative() / (questionCount.get("I") * 4) * 100));
	    dto.setArtisticPercentageStr(df.format((double) inDto.getArtistic() / (questionCount.get("A") * 4) * 100));
	    dto.setSocialPercentageStr(df.format((double) inDto.getSocial() / (questionCount.get("S") * 4) * 100));
	    dto.setEnterprisingPercentageStr(df.format((double) inDto.getEnterprising() / (questionCount.get("E") * 4) * 100));
	    dto.setConventionalPercentageStr(df.format((double) inDto.getConventional() / (questionCount.get("C") * 4) * 100));

	    return dto;
	}
	
	public static List<Integer> getTopMatches(String inputCode) {
	    List<Map.Entry<Integer, String>> allEntries = new ArrayList<>(CommonConstant.RIASEC_CODE_MAP.entrySet());

	    // Sort by descending score
	    allEntries.sort((a, b) -> Integer.compare(
	            calculateScore(inputCode, b.getValue()),
	            calculateScore(inputCode, a.getValue())
	    ));

	    // Return only the keys of the top 5 matches
	    return allEntries.stream()
	            .limit(5)
	            .map(Map.Entry::getKey)
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



	@Override
	public AssessmentDto getAssessmentStatistics() throws Exception {
		
		AssessmentDto outDto = new AssessmentDto();
	
		AssessmentStatisticsData data = historyLogic.getAssessmentStatistics();
		
		outDto.setAssessmentStatistics(data);
		
		return outDto;
	}
}





