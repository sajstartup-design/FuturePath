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
@Table(name = "strandegrees")
public class StrandegreeEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPk;
	
	private String name;
	
	private String code;
	
	private String category; 
	
	private String details;
	
	private int duration;
	
	private Boolean isActive;
	
	private Boolean isDeleted;
	
	private Timestamp createdAt;
}
