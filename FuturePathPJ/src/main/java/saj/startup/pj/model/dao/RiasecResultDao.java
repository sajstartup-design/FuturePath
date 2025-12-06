package saj.startup.pj.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.RiasecResultEntity;
import saj.startup.pj.model.dao.projection.RiasecResultData;

public interface RiasecResultDao extends JpaRepository<RiasecResultEntity, Integer>{

	public static final String GET_RIASEC_RESULT_BY_ID = """
				SELECT e
				FROM RiasecResultEntity e
				WHERE e.idPk = :riasecIdPk
			""";
	
	@Query(GET_RIASEC_RESULT_BY_ID)
	public RiasecResultEntity getRiasecResultById(@Param("riasecIdPk") int riasecIdPk) throws DataAccessException;
	
	public final String GET_ALL_RIASEC_RESULT_BY_USER = """
			SELECT 
		       e.id_pk AS riasec_id_pk,
		       '' AS full_name,
		       e.date_taken,
		       e.category
		    FROM riasec_result e
		    WHERE user_id_pk = :userIdPk
		      AND (
		          :search IS NULL OR :search = '' OR 
		          CAST(e.id_pk AS TEXT) ILIKE CONCAT('%', :search, '%') OR
		          TO_CHAR(e.date_taken, 'YYYY-MM-DD HH24:MI') ILIKE CONCAT('%', :search, '%')
		      )
		    ORDER BY date_taken ASC
		""";

	@Query(value=GET_ALL_RIASEC_RESULT_BY_USER, nativeQuery=true)
	public Page<RiasecResultData> getAllRiasecResultByUser(Pageable pageable,
			@Param("search") String search,
			@Param("userIdPk") int userIdPk) throws DataAccessException;
	
	public final String GET_ALL_RIASEC_RESULT = """
			SELECT 
		       e.id_pk AS riasec_id_pk,
		       CONCAT(u.first_name, ' ', u.last_name) AS full_name,
		       e.date_taken,
		       e.category
		    FROM riasec_result e
		    LEFT JOIN users u ON u.id_pk = e.user_id_pk
		    WHERE (
		          :search IS NULL OR :search = '' OR 
		          CAST(e.id_pk AS TEXT) ILIKE CONCAT('%', :search, '%') OR
		          TO_CHAR(e.date_taken, 'YYYY-MM-DD HH24:MI') ILIKE CONCAT('%', :search, '%')
		      )
		    ORDER BY date_taken ASC
		""";

	@Query(value=GET_ALL_RIASEC_RESULT, nativeQuery=true)
	public Page<RiasecResultData> getAllRiasecResult(Pageable pageable,
			@Param("search") String search) throws DataAccessException;
}
