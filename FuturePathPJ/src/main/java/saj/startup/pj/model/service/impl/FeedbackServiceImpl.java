package saj.startup.pj.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.model.dao.entity.FeedbackData;
import saj.startup.pj.model.dao.entity.FeedbackEntity;
import saj.startup.pj.model.dao.entity.FeedbackOverviewData;
import saj.startup.pj.model.dto.FeedbackDto;
import saj.startup.pj.model.logic.FeedbackLogic;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
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

	@Override
	public FeedbackDto getAllFeedbacks(FeedbackDto inDto) throws Exception {
		
		FeedbackDto outDto = new FeedbackDto();
		
		Pageable pageable = PageRequest.of(inDto.getPagination().getPage(), CommonConstant.UNIVERSITY_MAX_DISPLAY);
		
		FilterAndSearchObj filter = inDto.getFilter();
		
		Page<FeedbackData> allFeedbacks = feedbackLogic.getAllFeedbacks(pageable, filter.getSearch());
		
		List<FeedbackData> feedbacks = allFeedbacks.getContent();
		
		PaginationObj pagination = new PaginationObj();
		
		pagination.setPage(allFeedbacks.getNumber());
		pagination.setTotalPages(allFeedbacks.getTotalPages());
		pagination.setTotalElements(allFeedbacks.getTotalElements());
		pagination.setHasNext(allFeedbacks.hasNext());
		pagination.setHasPrevious(allFeedbacks.hasPrevious());
		
		outDto.setFeedbacks(feedbacks);
		outDto.setPagination(pagination);

		return outDto;
	}

	@Override
	public FeedbackDto getFeedbackOverview() throws Exception {
		
		FeedbackDto outDto = new FeedbackDto();
		
		FeedbackOverviewData overview = feedbackLogic.getFeedbackOverview();
		
		outDto.setOverview(overview);
		
		return outDto;
	}
}
