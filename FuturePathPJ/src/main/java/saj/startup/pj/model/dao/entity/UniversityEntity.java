package saj.startup.pj.model.dao.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "universities")
public class UniversityEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPk;
	
	private String universityName;
	
	private String category;
	
	private String contact;
	
	private String street;
	
	private String city;
	
	private String province;
	
	private String postalCode;
	
	private Boolean isActive;
	
	private Boolean isDeleted;
	
	private Timestamp createdAt;
	
	private String founded;
	
	private String students;
	
	private String motto;
	
	private int ranking;
}
