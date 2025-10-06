package saj.startup.pj.model.logic;

import java.util.List;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.AnswerEntity;
import saj.startup.pj.model.dao.entity.QuestionEntity;

@Service
public interface QuestionLogic {

	public int saveQuestion(QuestionEntity entity);
	
	public void saveAnswers(List<AnswerEntity> entities);
}
