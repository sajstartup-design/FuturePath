package saj.startup.pj.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import saj.startup.pj.model.dao.entity.UniversityEntity;
import saj.startup.pj.model.dao.entity.UniversityOverviewData;

public interface UniversityDao extends JpaRepository<UniversityEntity, Integer>{

	public final String GET_UNIVERSITIES_OVERVIEW =
		    "SELECT new saj.startup.pj.model.dao.entity.UniversityOverviewData( " +
		    "CAST(COUNT(e) AS INTEGER), " +
		    "CAST(SUM(CASE WHEN e.isActive = true THEN 1 ELSE 0 END) AS INTEGER), " +
		    "CAST(SUM(CASE WHEN e.isActive = false THEN 1 ELSE 0 END) AS INTEGER), " +
		    "CAST(SUM(CASE WHEN e.category = 'PRIVATE' THEN 1 ELSE 0 END) AS INTEGER), " +
		    "CAST(SUM(CASE WHEN e.category = 'PUBLIC' THEN 1 ELSE 0 END) AS INTEGER) " +
		    ") " +
		    "FROM UniversityEntity e "
		    + "WHERE e.isDeleted = false ";
	
	@Query(GET_UNIVERSITIES_OVERVIEW)
	public UniversityOverviewData getUniversitiesOverview() throws DataAccessException;
}
