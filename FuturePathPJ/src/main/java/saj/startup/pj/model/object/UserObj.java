package saj.startup.pj.model.object;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserObj {

	private int idPk;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone;
	
	private String gender;
	
	private String username;
	
	private Boolean isActive;
	
	private Timestamp createdAt;
}
