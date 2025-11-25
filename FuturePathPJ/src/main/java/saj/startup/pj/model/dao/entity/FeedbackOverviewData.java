package saj.startup.pj.model.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedbackOverviewData {

	private int totalFeedback;
	
	private int total5StarRating;
	
	private int total4StarRating;
	
	private int total3StarRating;
	
	private int total2StarRating;
	
	private int total1StarRating;
}
