package saj.startup.pj.model.dao.entity;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class FeedbackData {
	
	private int resultIdPk;

	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private int ratings;
	
	private String feedback;
}
