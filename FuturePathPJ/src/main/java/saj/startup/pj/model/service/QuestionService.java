package saj.startup.pj.model.service;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dto.QuestionDto;


@Service
public interface QuestionService {
	
	public QuestionDto getQuestionOverview() throws Exception;

	public void saveQuestion(QuestionDto inDto) throws Exception;
	
	public QuestionDto getAllQuestions(QuestionDto inDto) throws Exception;
}
