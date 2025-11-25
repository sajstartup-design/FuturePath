package saj.startup.pj.model.logic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.FeedbackData;
import saj.startup.pj.model.dao.entity.FeedbackEntity;
import saj.startup.pj.model.dao.entity.FeedbackOverviewData;

@Service
public interface FeedbackLogic {

	public void saveFeedback(FeedbackEntity entity);
	
	public FeedbackEntity getFeedbackByResultId(int resultIdPk);
	
	public Page<FeedbackData> getAllFeedbacks(Pageable pageable, String search);
	
	public FeedbackOverviewData getFeedbackOverview();
}
