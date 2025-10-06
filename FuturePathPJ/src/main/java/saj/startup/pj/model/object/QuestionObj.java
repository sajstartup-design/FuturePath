package saj.startup.pj.model.object;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class QuestionObj {

	private int idPk;

	private String category;
	
	private String question;
	
	private String strandegree;
	
	private Boolean isActive;
	
	private Timestamp createdAt;
}
