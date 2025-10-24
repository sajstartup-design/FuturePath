package saj.startup.pj.model.logic;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.UniversityData;
import saj.startup.pj.model.dao.entity.UniversityEntity;
import saj.startup.pj.model.dao.entity.UniversityOverviewData;
import saj.startup.pj.model.dao.entity.UniversityStrandegreeAvailabilityEntity;

@Service
public interface UniversityLogic {

	public int saveUniversity(UniversityEntity entity);
	
	public void saveAllStrandegreeAvailability(List<UniversityStrandegreeAvailabilityEntity> entities);
	
	public UniversityOverviewData getUniversitiesOverview();
	
	public Page<UniversityData> getAllUniversities(Pageable pageable, String search);
	
	public List<UniversityData> getAllUniversitiesNoPageable();
}
