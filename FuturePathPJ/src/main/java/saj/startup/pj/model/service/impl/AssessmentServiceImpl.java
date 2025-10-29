package saj.startup.pj.model.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.model.dao.entity.AssessmentCheckerData;
import saj.startup.pj.model.dto.AssessmentDto;
import saj.startup.pj.model.logic.AnswerLogic;
import saj.startup.pj.model.logic.QuestionLogic;
import saj.startup.pj.model.object.RecommendationObj;
import saj.startup.pj.model.service.AssessmentService;

@Service
public class AssessmentServiceImpl implements AssessmentService{
	
	@Autowired
	private AnswerLogic answerLogic;
	
	@Autowired
	private QuestionLogic questionLogic;

	@Override
	public AssessmentDto getAssessmentResult(AssessmentDto inDto) throws Exception {

	    AssessmentDto outDto = new AssessmentDto();

	    ObjectMapper mapper = new ObjectMapper();
	    HashMap<Integer, Integer> answeredMap = mapper.readValue(
	        inDto.getAnsweredJson(),
	        new TypeReference<HashMap<Integer, Integer>>() {}
	    );

	    int totalCorrect = 0;
	    int totalIncorrect = 0;

	    // Main map: code → RecommendationObj
	    Map<String, RecommendationObj> correctCountMap = new HashMap<>();
	    // Helper map: code → totalQuestions under that code
	    Map<String, Integer> totalQuestionPerCode = new HashMap<>();

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
	    }

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
	    percentage = Math.round(percentage * 10.0) / 10.0; // round to 1 decimal
	    String result = percentage >= 75.0 ? "PASSED" : "FAILED";

	    outDto.setTotalCorrect(totalCorrect);
	    outDto.setTotalIncorrect(totalIncorrect);
	    outDto.setTotalQuestion(totalQuestions);
	    outDto.setPercentage(percentage);
	    outDto.setResult(result);
	    outDto.setRecommendationMap(correctCountMap);

	    // ---- Debug Output ----
	    System.out.println("Total Questions: " + totalQuestions);
	    System.out.println("Total Correct: " + totalCorrect);
	    System.out.println("Total Incorrect: " + totalIncorrect);
	    System.out.println("Percentage: " + percentage + "%");
	    System.out.println("Result: " + result);

	    System.out.println("\nCorrect Count Map (code -> RecommendationObj):");
	    for (Map.Entry<String, RecommendationObj> e : correctCountMap.entrySet()) {
	        RecommendationObj rec = e.getValue();
	        System.out.printf(
	            "Code: %s | Correct: %d | %.2f%%\n",
	            rec.getCode(),
	            rec.getCorrectCount(),
	            rec.getPercentage()
	        );
	    }

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

	    String exampleFields = CommonConstant.RIASEC_CODE_MAP.containsValue(topCombo)
	        ? CommonConstant.RIASEC_DETAIL_MAP.entrySet().stream()
	              .filter(e -> CommonConstant.RIASEC_CODE_MAP.get(e.getKey()).equals(topCombo))
	              .map(Map.Entry::getValue)
	              .collect(Collectors.joining(", "))
	        : "Various career paths that align with your skills and interests.";

	    DecimalFormat df = new DecimalFormat("0.00");

	    AssessmentDto dto = new AssessmentDto();
	    dto.setCombination(topCombo);
	    dto.setMessage(message.toString());
	    dto.setExampleFields(exampleFields);

	    System.out.println("COMBINATION: " + topCombo);
	    dto.setRealisticPercentageStr(df.format((double) inDto.getRealistic() / (questionCount.get("R") * 4) * 100));
	    dto.setInvestigativePercentageStr(df.format((double) inDto.getInvestigative() / (questionCount.get("I") * 4) * 100));
	    dto.setArtisticPercentageStr(df.format((double) inDto.getArtistic() / (questionCount.get("A") * 4) * 100));
	    dto.setSocialPercentageStr(df.format((double) inDto.getSocial() / (questionCount.get("S") * 4) * 100));
	    dto.setEnterprisingPercentageStr(df.format((double) inDto.getEnterprising() / (questionCount.get("E") * 4) * 100));
	    dto.setConventionalPercentageStr(df.format((double) inDto.getConventional() / (questionCount.get("C") * 4) * 100));

	    return dto;
	}

}





