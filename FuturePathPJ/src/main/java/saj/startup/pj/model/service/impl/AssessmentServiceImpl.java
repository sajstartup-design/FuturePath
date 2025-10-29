package saj.startup.pj.model.service.impl;

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

	    final String topCombo = (inDto.getCombination() == null || inDto.getCombination().isEmpty())
	    	    ? "R-I-C"
	    	    : inDto.getCombination();


	    String[] letters = topCombo.split("-");

	    StringBuilder message = new StringBuilder("Based on your interests and strengths:<br><br>");
	    for (String letter : letters) {
	        message.append("• ").append(CommonConstant.TRAIT_DESCRIPTIONS.getOrDefault(letter, "")).append(".<br>");
	    }

	    String summary = CommonConstant.RIASEC_SUMMARY_MAP.getOrDefault(
	        letters[0],
	        "You have a mix of interests that can lead to many exciting paths."
	    );
	    message.append("<br>").append(summary);

	    // Example fields from RIASEC_CODE_MAP and RIASEC_DETAIL_MAP
	    String exampleFields = CommonConstant.RIASEC_CODE_MAP.containsValue(topCombo)
	        ? CommonConstant.RIASEC_DETAIL_MAP.entrySet().stream()
	              .filter(e -> CommonConstant.RIASEC_CODE_MAP.get(e.getKey()).equals(topCombo))
	              .map(Map.Entry::getValue)
	              .collect(Collectors.joining(", "))
	        : "Various career paths that align with your skills and interests.";

	    // Build DTO
	    AssessmentDto dto = new AssessmentDto();
	    dto.setCombination(topCombo);
	    dto.setMessage(message.toString());
	    dto.setExampleFields(exampleFields);

	    return dto;
	}







}
