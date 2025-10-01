package saj.startup.pj.model.dao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "university_strandegree_availability")
public class UniversityStrandegreeAvailabilityEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPk;
	
	private int universityIdPk;
	
	private int strandegreeIdPk;
	
	private Boolean availability;
}
