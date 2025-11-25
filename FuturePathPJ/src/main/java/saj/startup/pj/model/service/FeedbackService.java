package saj.startup.pj.model.service;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dto.FeedbackDto;

@Service
public interface FeedbackService {
	
	public void saveFeedback(FeedbackDto inDto) throws Exception;
	
	public FeedbackDto getAllFeedbacks(FeedbackDto inDto) throws Exception;
	
	public FeedbackDto getFeedbackOverview() throws Exception;
}
