package saj.startup.pj.model.dto;

import java.util.List;

import lombok.Data;
import saj.startup.pj.model.dao.entity.FeedbackData;
import saj.startup.pj.model.dao.entity.FeedbackOverviewData;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;

@Data
public class FeedbackDto {

	private int resultIdPk;
	
	private int ratings;
	
	private String feedback;
	
	private List<FeedbackData> feedbacks;
	
	private PaginationObj pagination;
	
	private FilterAndSearchObj filter;
	
	private FeedbackOverviewData overview;
}
