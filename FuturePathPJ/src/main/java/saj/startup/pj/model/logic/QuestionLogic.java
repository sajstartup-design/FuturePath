package saj.startup.pj.model.logic;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.AnswerData;
import saj.startup.pj.model.dao.entity.AnswerEntity;
import saj.startup.pj.model.dao.entity.AssessmentCheckerData;
import saj.startup.pj.model.dao.entity.QuestionData;
import saj.startup.pj.model.dao.entity.QuestionEntity;
import saj.startup.pj.model.dao.entity.QuestionOverviewData;

@Service
public interface QuestionLogic {

	public int saveQuestion(QuestionEntity entity);
	
	public void saveAnswers(List<AnswerEntity> entities);
	
	public QuestionOverviewData getQuestionOverview();
	
	public Page<QuestionData> getAllQuestions(Pageable pageable, String search);
	
	public List<QuestionData> getDegreesQuestionsForAssessment(List<String> degrees);
	
	public AssessmentCheckerData getQuestionAssessmentChecker(int questionIdPk,
			int answerIdPk);
	
	public QuestionEntity getQuestionByIdPk(int idPk);
	
	public List<AnswerData> getAnswersByQuestionIdPk(int questionIdPk);
	
	public List<AnswerEntity> getAnswersEntityByQuestionIdPk(int questionIdPk);
	
	public void deleteQuestion(int idPk);
	
}
