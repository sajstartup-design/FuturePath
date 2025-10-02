package saj.startup.pj.model.logic;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.QuestionEntity;

@Service
public interface QuestionLogic {

	public int saveQuestion(QuestionEntity entity);
}
