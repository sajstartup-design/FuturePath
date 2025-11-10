package saj.startup.pj.model.dao.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "assessment_config")
public class AssessmentConfigEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPk;
	
	private int totalQuestion;
	
	private boolean defaultDegreeAvailability;
	
	private boolean customDegreeAvailability;
	
	private boolean defaultStrandAvailability;
	
	private boolean customStrandAvailability;
}
