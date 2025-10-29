package saj.startup.pj.model.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.AnswerDao;
import saj.startup.pj.model.dao.QuestionDao;
import saj.startup.pj.model.dao.entity.AnswerData;
import saj.startup.pj.model.dao.entity.AnswerEntity;
import saj.startup.pj.model.dao.entity.AssessmentCheckerData;
import saj.startup.pj.model.dao.entity.QuestionData;
import saj.startup.pj.model.dao.entity.QuestionEntity;
import saj.startup.pj.model.dao.entity.QuestionOverviewData;
import saj.startup.pj.model.logic.QuestionLogic;

@Service
public class QuestionLogicImpl implements QuestionLogic{
	
	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private AnswerDao answerDao;

	@Override
	public int saveQuestion(QuestionEntity entity) {
		
		return questionDao.save(entity).getIdPk();
	}

	@Override
	public void saveAnswers(List<AnswerEntity> entities) {
		
		answerDao.saveAll(entities);
		
	}

	@Override
	public QuestionOverviewData getQuestionOverview() {
		
		return questionDao.getQuestionOverview();
	}

	@Override
	public Page<QuestionData> getAllQuestions(Pageable pageable, String search) {
		
		return questionDao.getAllQuestions(pageable, search);
	}

	@Override
	public List<QuestionData> getDegreesQuestionsForAssessment(List<String> degrees) {
		
		/*
		 * This right here can still be improved
		 */
		
		List<QuestionData> questions = questionDao.getQuestionsForAssessment(degrees);
		
		for(QuestionData question : questions) {
			
			List<AnswerData> answers = answerDao.getAnswersByQuestionIdPk(question.getQuestionIdPk());
			
			question.setAnswers(answers);
		}
		
		return questions;
	}

	@Override
	public AssessmentCheckerData getQuestionAssessmentChecker(int questionIdPk,
			int answerIdPk) {
		
		return questionDao.getQuestionAssessmentChecker(questionIdPk, answerIdPk);
	}

	@Override
	public QuestionEntity getQuestionByIdPk(int idPk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AnswerData> getAnswersByQuestionIdPk(int questionIdPk) {
		// TODO Auto-generated method stub
		return null;
	}

}
