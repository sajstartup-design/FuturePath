package saj.startup.pj.model.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.QuestionDao;
import saj.startup.pj.model.dao.entity.QuestionEntity;
import saj.startup.pj.model.logic.QuestionLogic;

@Service
public class QuestionLogicImpl implements QuestionLogic{
	
	@Autowired
	private QuestionDao questionDao;

	@Override
	public int saveQuestion(QuestionEntity entity) {
		
		return questionDao.save(entity).getIdPk();
	}

}
