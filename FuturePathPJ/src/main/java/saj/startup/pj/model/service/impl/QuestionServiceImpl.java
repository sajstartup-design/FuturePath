package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.QuestionEntity;
import saj.startup.pj.model.dto.QuestionDto;
import saj.startup.pj.model.logic.QuestionLogic;
import saj.startup.pj.model.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionLogic questionLogic;

	@Override
	public void saveQuestion(QuestionDto inDto) throws Exception {
		
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		
		QuestionEntity newQuestion = new QuestionEntity();
		
		newQuestion.setQuestion(inDto.getQuestion());
		newQuestion.setStrandegreeIdPk(inDto.getStrandegreeIdPk());
		newQuestion.setIsActive(true);
		newQuestion.setIsDeleted(false);
		newQuestion.setCreatedAt(timeNow);
		
		int questionIdPk = questionLogic.saveQuestion(newQuestion);
		

	}



}
