package saj.startup.pj.model.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.FeedbackDao;
import saj.startup.pj.model.dao.entity.FeedbackEntity;
import saj.startup.pj.model.logic.FeedbackLogic;

@Service
public class FeedbackLogicImpl implements FeedbackLogic{
	
	@Autowired
	private FeedbackDao feedbackDao;

	@Override
	public void saveFeedback(FeedbackEntity entity) {
		
		feedbackDao.save(entity);
	}

	@Override
	public FeedbackEntity getFeedbackByResultId(int resultIdPk) {
	
		return feedbackDao.getFeedbackByResultId(resultIdPk);
	}

}
