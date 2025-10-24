package saj.startup.pj.model.logic.impl;

import java.util.List;

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

}
