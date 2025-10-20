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
@Table(name = "answer")
public class AnalyticEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int totalDegreeAssessments;
	private int totalStrandAssessments;

	private int totalPassedDegreeAssessments;
	private int totalFailedDegreeAssessments;

	private int totalPassedStrandAssessments;
	private int totalFailedStrandAssessments;

	
	
}
