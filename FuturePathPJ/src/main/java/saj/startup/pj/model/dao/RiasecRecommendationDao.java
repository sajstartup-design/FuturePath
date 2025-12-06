package saj.startup.pj.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import saj.startup.pj.model.dao.entity.RiasecRecommendationEntity;

public interface RiasecRecommendationDao extends JpaRepository<RiasecRecommendationEntity, Integer>{

	public final String GET_RIASEC_RECOMMENDATION_BY_RIASEC_ID = """
				SELECT e
				FROM RiasecRecommendationEntity e
				WHERE e.riasecIdPk = :riasecIdPk
			""";
	
	@Query(GET_RIASEC_RECOMMENDATION_BY_RIASEC_ID)
	public RiasecRecommendationEntity getRiasecRecommendationByRiasecIdPk(@Param("riasecIdPk") int riasecIdPk) throws DataAccessException;
}
