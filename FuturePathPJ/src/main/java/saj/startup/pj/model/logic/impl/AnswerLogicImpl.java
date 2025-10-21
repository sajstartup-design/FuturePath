package saj.startup.pj.model.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.AnswerDao;
import saj.startup.pj.model.logic.AnswerLogic;

@Service
public class AnswerLogicImpl implements AnswerLogic {
	
	@Autowired
	private AnswerDao answerDao;

	@Override
	public Boolean isAnswerCorrect(int answerIdPk) {
	
		return answerDao.isAnswerCorrect(answerIdPk);
	}

}
