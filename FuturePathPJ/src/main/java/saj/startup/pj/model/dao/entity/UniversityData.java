package saj.startup.pj.model.dao.entity;

import java.sql.Timestamp;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class UniversityData {

	private int universityIdPk;
	
	private String universityName;
	
	private String type;
	
	private int coursesOffered;
	
	private String contact;
		
	private String street;
	
	private String city;
	
	private String province;
	
	private String postalCode;
	
	private Boolean isActive;
	
	private Timestamp createdAt;
}
