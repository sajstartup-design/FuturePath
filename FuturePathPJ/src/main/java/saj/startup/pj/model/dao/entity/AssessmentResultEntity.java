package saj.startup.pj.model.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "assessment_result")
public class AssessmentResultEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPk;
	
	private int userIdPk;
	
	private int correct;
	
	private int incorrect;
	
	private int totalQuestion;
	
	private double score;
}
