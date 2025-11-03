package saj.startup.pj.model.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.UniversityData;
import saj.startup.pj.model.dao.entity.UniversityEntity;
import saj.startup.pj.model.dao.entity.UniversityOverviewData;
import saj.startup.pj.model.dao.projection.UniversityRecommendationData;

public interface UniversityDao extends JpaRepository<UniversityEntity, Integer>{

	public final String GET_UNIVERSITIES_OVERVIEW =
		    "SELECT new saj.startup.pj.model.dao.entity.UniversityOverviewData( " +
		    "CAST(COALESCE(COUNT(e), 0) AS INTEGER), " +
		    "CAST(COALESCE(SUM(CASE WHEN e.isActive = true THEN 1 ELSE 0 END), 0) AS INTEGER), " +
		    "CAST(COALESCE(SUM(CASE WHEN e.isActive = false THEN 1 ELSE 0 END), 0) AS INTEGER), " +
		    "CAST(COALESCE(SUM(CASE WHEN e.category = 'PRIVATE' THEN 1 ELSE 0 END), 0) AS INTEGER), " +
		    "CAST(COALESCE(SUM(CASE WHEN e.category = 'PUBLIC' THEN 1 ELSE 0 END), 0) AS INTEGER) " +
		    ") " +
		    "FROM UniversityEntity e " +
		    "WHERE e.isDeleted = false";

	
	@Query(GET_UNIVERSITIES_OVERVIEW)
	public UniversityOverviewData getUniversitiesOverview() throws DataAccessException;
	
	public final String GET_ALL_UNIVERSITIES = 
		    "SELECT new saj.startup.pj.model.dao.entity.UniversityData( "
		  + " e.idPk, "
		  + " e.universityName, "
		  + " e.category, "  // mapped to 'type'
		  + " CAST(SUM(CASE WHEN s.availability = true THEN 1 ELSE 0 END) AS INTEGER), " // coursesOffered
		  + " e.contact, "
		  + " e.street, "
		  + " e.city, "
		  + " e.province, "
		  + " e.postalCode, "
		  + " e.isActive, "
		  + " e.createdAt "
		  + ") "
		  + "FROM UniversityEntity e "
		  + "LEFT JOIN UniversityStrandegreeAvailabilityEntity s "
		  + "ON s.id.universityIdPk = e.idPk "
		  + "WHERE e.isDeleted = false "
		  + "GROUP BY e.idPk, e.universityName, e.category, e.contact, "
		  + " e.street, e.city, e.province, e.postalCode, e.isActive, e.createdAt " 
		  + "HAVING ( "
		  + "   (:search IS NOT NULL AND :search <> '' AND ( " 
		  + "       LOWER(e.universityName) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		  + "       LOWER(e.category) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		  + "       LOWER(e.contact) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		  + "       LOWER(e.street) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		  + "       LOWER(e.city) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		  + "       LOWER(e.province) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		  + "       LOWER(e.postalCode) LIKE LOWER(CONCAT('%', :search, '%')) OR " 
		  + "       LOWER(CAST(e.isActive AS string)) LIKE LOWER(CONCAT('%', :search, '%')) OR "
		  + "       CAST(SUM(CASE WHEN s.availability = true THEN 1 ELSE 0 END) AS string) LIKE CONCAT('%', :search, '%') "
		  + "   )) " 
		  + "   OR (:search IS NULL OR :search = '') "
		  + ") "
		  + "ORDER BY e.idPk ASC ";



	
	@Query(GET_ALL_UNIVERSITIES)
	public Page<UniversityData> getAllUniversities(Pageable pageable,
			@Param("search") String search) throws DataAccessException;
	
	public final String GET_ALL_UNIVERSITIES_NO_PAGEABLE = 
		    "SELECT new saj.startup.pj.model.dao.entity.UniversityData( "
		  + " e.idPk, "
		  + " e.universityName, "
		  + " e.category, "  // mapped to 'type'
		  + " CAST(SUM(CASE WHEN s.availability = true THEN 1 ELSE 0 END) AS INTEGER), " // coursesOffered
		  + " e.contact, "
		  + " e.street, "
		  + " e.city, "
		  + " e.province, "
		  + " e.postalCode, "
		  + " e.isActive, "
		  + " e.createdAt, "
		  + " e.category, "
		  + " e.founded, "
		  + " e.students "
		  + ") "
		  + "FROM UniversityEntity e "
		  + "LEFT JOIN UniversityStrandegreeAvailabilityEntity s "
		  + "ON s.id.universityIdPk = e.idPk "
		  + "WHERE e.isDeleted = false "
		  + "GROUP BY e.idPk, e.universityName, e.category, e.contact, "
		  + " e.street, e.city, e.province, e.postalCode, e.isActive, e.createdAt " 
		  + "ORDER BY e.idPk ASC ";

	@Query(GET_ALL_UNIVERSITIES_NO_PAGEABLE)
	public List<UniversityData> getAllUniversitiesNoPageable() throws DataAccessException;
	
	public final String GET_UNIVERSITY_BY_ID_PK = """
				SELECT u FROM UniversityEntity u WHERE u.idPk = :idPk
			""";
	
	@Query()
	public UniversityEntity getUniversityByIdPk(@Param("idPk") int idPk) throws DataAccessException;
	
	public final String GET_UNIVERSITY_RECOMMENDATION = """
				SELECT 
				    u.id_pk AS university_id_pk,
				    u.university_name,
				    ARRAY_AGG(DISTINCT s.name ORDER BY s.name) AS offered_programs
				FROM universities u
				JOIN university_strandegree_availability usa 
				    ON usa.university_id_pk = u.id_pk
				JOIN strandegrees s 
				    ON s.id_pk = usa.strandegree_id_pk
				WHERE s.code IN (:programs) 
				  AND usa.availability = TRUE  
				GROUP BY u.id_pk, u.university_name
				ORDER BY COUNT(s.id_pk) DESC;
			""";
	
	@Query(value=GET_UNIVERSITY_RECOMMENDATION, nativeQuery=true)
	public List<UniversityRecommendationData> getUniversityRecommendation(@Param("programs") List<String> programs) throws DataAccessException;
}
