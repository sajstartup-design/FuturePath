package saj.startup.pj.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.FeedbackEntity;
import saj.startup.pj.model.dto.FeedbackDto;
import saj.startup.pj.model.logic.FeedbackLogic;
import saj.startup.pj.model.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService{
	
	@Autowired
	private FeedbackLogic feedbackLogic;

	@Override
	public void saveFeedback(FeedbackDto inDto) throws Exception {
		
		FeedbackEntity newFeedback = new FeedbackEntity();
		
		newFeedback.setResultIdPk(inDto.getResultIdPk());
		newFeedback.setRatings(inDto.getRatings());
		newFeedback.setFeedback(inDto.getFeedback());
		
		feedbackLogic.saveFeedback(newFeedback);
	}
}
