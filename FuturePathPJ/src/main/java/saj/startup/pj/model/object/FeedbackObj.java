package saj.startup.pj.model.object;

import lombok.Data;

@Data
public class FeedbackObj {

	private String resultIdPk;

	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private int ratings;
	
	private String feedback;
}
