package saj.startup.pj.model.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import saj.startup.pj.model.dao.entity.AssessmentCheckerData;
import saj.startup.pj.model.dto.AssessmentDto;
import saj.startup.pj.model.logic.AnswerLogic;
import saj.startup.pj.model.logic.QuestionLogic;
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
	        new TypeReference<>() {}
	    );

	    List<Integer> keysList = new ArrayList<>(answeredMap.keySet());
	    List<Integer> valuesList = new ArrayList<>(answeredMap.values());

	    int totalCorrect = 0;
	    int totalIncorrect = 0;
	    List<String> recommended = new ArrayList<>();

	    List<AssessmentCheckerData> checkers = questionLogic.getQuestionAssessmentChecker(keysList, valuesList);

	    // New map: key = code, value = number of correct answers
	    HashMap<String, Integer> correctCountMap = new HashMap<>();

	    for(AssessmentCheckerData checker : checkers) {
	        String code = checker.getCode(); // assuming getCode() gives the question code
	        boolean isCorrect = Boolean.TRUE.equals(checker.getIsCorrect());

	        if (isCorrect) {
	            totalCorrect++;
	            correctCountMap.put(code, correctCountMap.getOrDefault(code, 0) + 1);
	        } else {
	            totalIncorrect++;
	        }
	    }

	    int totalQuestions = answeredMap.size();
	    double percentage = totalQuestions > 0 ? ((double) totalCorrect / totalQuestions) * 100 : 0.0;
	    String result = percentage >= 75.0 ? "PASSED" : "FAILED";

	    outDto.setTotalCorrect(totalCorrect);
	    outDto.setTotalIncorrect(totalIncorrect);
	    outDto.setTotalQuestion(totalQuestions);
	    outDto.setPercentage(percentage);
	    outDto.setResult(result);
	    
	 // ---- PRINT RESULTS ----
	    System.out.println("Total Questions: " + totalQuestions);
	    System.out.println("Total Correct: " + totalCorrect);
	    System.out.println("Total Incorrect: " + totalIncorrect);
	    System.out.println("Percentage: " + percentage + "%");
	    System.out.println("Result: " + result);

	    System.out.println("Correct Count Map (code -> correct count):");
	    for (Map.Entry<String, Integer> entry : correctCountMap.entrySet()) {
	        String code = entry.getKey();
	        int correctCount = entry.getValue();
	        double codePercentage = ((double) correctCount / 1) * 100; // if only 1 attempt per code
	        System.out.println("Code: " + code + ", Correct Count: " + correctCount + ", Percentage: " + codePercentage + "%");
	    }
	    


	    return outDto;
	}



}
