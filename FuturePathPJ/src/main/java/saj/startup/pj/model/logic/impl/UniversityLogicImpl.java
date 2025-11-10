package saj.startup.pj.model.logic.impl;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.UniversityDao;
import saj.startup.pj.model.dao.UniversityStrandegreeAvailabilityDao;
import saj.startup.pj.model.dao.entity.UniversityData;
import saj.startup.pj.model.dao.entity.UniversityEntity;
import saj.startup.pj.model.dao.entity.UniversityOverviewData;
import saj.startup.pj.model.dao.entity.UniversityStrandegreeAvailabilityEntity;
import saj.startup.pj.model.dao.projection.StrandegreeAvailabilityProjection;
import saj.startup.pj.model.dao.projection.UniversityRecommendationData;
import saj.startup.pj.model.logic.UniversityLogic;

@Service
public class UniversityLogicImpl implements UniversityLogic{
	
	@Autowired
	private UniversityDao universityDao;
	
	@Autowired
	private UniversityStrandegreeAvailabilityDao universityStrandegreeAvailabilityDao;

	@Override
	public int saveUniversity(UniversityEntity entity) {
		
		return universityDao.save(entity).getIdPk();
	}

	@Override
	public void saveAllStrandegreeAvailability(List<UniversityStrandegreeAvailabilityEntity> entities) {

		universityStrandegreeAvailabilityDao.saveAll(entities);
		
	}

	@Override
	public UniversityOverviewData getUniversitiesOverview() {
		
		return universityDao.getUniversitiesOverview();
	}

	@Override
	public Page<UniversityData> getAllUniversities(Pageable pageable, String search) {
		
		return universityDao.getAllUniversities(pageable, search);
	}

	@Override
	public List<UniversityData> getAllUniversitiesNoPageable() {
	
		return universityDao.getAllUniversitiesNoPageable();
	}

	@Override
	public UniversityEntity getUniversityIdPk(int idPk) {
		
		return universityDao.getUniversityByIdPk(idPk);
	}

	@Override
	public HashMap<Integer, Boolean> getAvailableStrandegree(int universityIdPk) {
	    List<StrandegreeAvailabilityProjection> results =
	        universityStrandegreeAvailabilityDao.getAvailableStrandegree(universityIdPk);
	    
	    System.out.println(results);
	    
	    return results.stream()
	        .filter(r -> r.getIdStrandegreeIdPk() != null && r.getAvailability() != null) // prevent NPE
	        .collect(Collectors.toMap(
	            StrandegreeAvailabilityProjection::getIdStrandegreeIdPk,
	            StrandegreeAvailabilityProjection::getAvailability,
	            (a, b) -> b, 
	            HashMap::new
	        ));
	}

	@Override
	public Boolean findStrandegreeAvailability(int strandegreeIdPk, int unviversityIdPk) {

		return null;
	}

	@Override
	public List<UniversityRecommendationData> getUniversityRecommendation(List<String> programs) {
		
		return universityDao.getUniversityRecommendation(programs);
	}

	@Override
	public void deleteUniversity(int idPk) {
		
		universityDao.deleteUniversity(idPk);
	}



}
