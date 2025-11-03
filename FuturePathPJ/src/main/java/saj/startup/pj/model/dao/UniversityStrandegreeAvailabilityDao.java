package saj.startup.pj.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.UniversityStrandegreeAvailabilityEntity;
import saj.startup.pj.model.dao.entity.UniversityStrandegreeAvailabilityId;
import saj.startup.pj.model.dao.projection.StrandegreeAvailabilityProjection;

public interface UniversityStrandegreeAvailabilityDao extends JpaRepository<UniversityStrandegreeAvailabilityEntity, UniversityStrandegreeAvailabilityId>{

	public static final String GET_AVAILABLE_STRANDEGREE = """
			
				SELECT e.id.strandegreeIdPk AS idStrandegreeIdPk, 
				e.availability AS availability
				FROM UniversityStrandegreeAvailabilityEntity e
				WHERE e.id.universityIdPk = :universityIdPk
			""";
	
	@Query(GET_AVAILABLE_STRANDEGREE)
	public List<StrandegreeAvailabilityProjection> getAvailableStrandegree(@Param("universityIdPk") int universityIdPk);
	
	
}
