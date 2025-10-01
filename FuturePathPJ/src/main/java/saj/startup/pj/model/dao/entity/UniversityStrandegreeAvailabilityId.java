package saj.startup.pj.model.dao.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UniversityStrandegreeAvailabilityId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "university_id_pk")
	private Integer universityIdPk;
	
	@Column(name = "strandegree_id_pk")
    private Integer strandegreeIdPk;
}
