package saj.startup.pj.model.dao.entity;

import java.sql.Timestamp;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class UniversityData {
	
	public UniversityData(int universityIdPk, String universityName, String type, int coursesOffered,
            String contact, String street, String city, String province,
            String postalCode, Boolean isActive, Timestamp createdAt) {
		this.universityIdPk = universityIdPk;
		this.universityName = universityName;
		this.type = type;
		this.coursesOffered = coursesOffered;
		this.contact = contact;
		this.street = street;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}

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
	
	private String category;
	
	private String founded;
	
	private String students;
	
	private int ranking;
}
