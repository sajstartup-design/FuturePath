package saj.startup.pj.model.dao.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UniversityStrandegreeAvailabilityId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer universityIdPk;
    private Integer strandegreeIdPk;
}
