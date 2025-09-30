package saj.startup.pj.model.object;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class StrandegreeObj {
	
	private int idPk;

	private String name;
	
	private String code;
	
	private String category;
	
	private String details;
	
	private Boolean isActive;
	
	private Timestamp createdAt;
}
