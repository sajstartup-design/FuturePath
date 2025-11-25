package saj.startup.pj.model.logic;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.FeedbackEntity;

@Service
public interface FeedbackLogic {

	public void saveFeedback(FeedbackEntity entity);
	
	public FeedbackEntity getFeedbackByResultId(int resultIdPk);
}
