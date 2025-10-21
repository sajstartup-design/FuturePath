package saj.startup.pj.model.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import saj.startup.pj.model.dto.AssessmentDto;
import saj.startup.pj.model.logic.AnswerLogic;
import saj.startup.pj.model.service.AssessmentService;

@Service
public class AssessmentServiceImpl implements AssessmentService{
	
	@Autowired
	private AnswerLogic answerLogic;

	@Override
	public AssessmentDto getAssessmentResult(AssessmentDto inDto) throws Exception {

	    AssessmentDto outDto = new AssessmentDto();

	    ObjectMapper mapper = new ObjectMapper();
	    HashMap<Integer, Integer> answeredMap = mapper.readValue(inDto.getAnsweredJson(), new TypeReference<>() {});

	    int totalCorrect = 0;
	    int totalIncorrect = 0;

	    for (Integer answerIdPk : answeredMap.values()) {
	        Boolean isCorrect = answerLogic.isAnswerCorrect(answerIdPk);

	        if (Boolean.TRUE.equals(isCorrect)) {
	            totalCorrect++;
	        } else {
	            totalIncorrect++;
	        }
	    }

	    int totalQuestions = answeredMap.size();
	    double percentage = totalQuestions > 0 ? ((double) totalCorrect / totalQuestions) * 100 : 0.0;

	    // Decide pass/fail — for example, pass if >= 75%
	    String result = percentage >= 75.0 ? "PASSED" : "FAILED";

	    outDto.setTotalCorrect(totalCorrect);
	    outDto.setTotalIncorrect(totalIncorrect);
	    outDto.setTotalQuestion(totalQuestions);
	    outDto.setPercentage(percentage);
	    outDto.setResult(result);

	    return outDto;
	}


}
