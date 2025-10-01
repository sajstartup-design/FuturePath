package saj.startup.pj.model.dao.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "university_strandegree_availability")
public class UniversityStrandegreeAvailabilityEntity {

	@EmbeddedId
    private UniversityStrandegreeAvailabilityId id;
	
	private Boolean availability;
}
