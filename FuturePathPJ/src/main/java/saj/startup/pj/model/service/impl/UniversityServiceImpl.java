package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.UniversityEntity;
import saj.startup.pj.model.dao.entity.UniversityOverviewData;
import saj.startup.pj.model.dao.entity.UniversityStrandegreeAvailabilityEntity;
import saj.startup.pj.model.dao.entity.UniversityStrandegreeAvailabilityId;
import saj.startup.pj.model.dto.UniversityDto;
import saj.startup.pj.model.logic.UniversityLogic;
import saj.startup.pj.model.service.UniversityService;

@Service
public class UniversityServiceImpl implements UniversityService {
	
	@Autowired
	private UniversityLogic universityLogic;

	@Override
	public void saveUniversity(UniversityDto inDto) throws Exception {
		
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		
		UniversityEntity newUniversity = new UniversityEntity();
		
		newUniversity.setUniversityName(inDto.getUniversityName());
		newUniversity.setCategory(inDto.getCategory());
		newUniversity.setContact(inDto.getContact());
		newUniversity.setStreet(inDto.getStreet());
		newUniversity.setCity(inDto.getCity());
		newUniversity.setProvince(inDto.getProvince());
		newUniversity.setPostalCode(inDto.getPostalCode());
		newUniversity.setIsActive(true);
		newUniversity.setIsDeleted(false);
		newUniversity.setCreatedAt(timeNow);
		
		int universityIdPk = universityLogic.saveUniversity(newUniversity);
		
		List<UniversityStrandegreeAvailabilityEntity> strandegreeAvailability = new ArrayList<>();
		
		if (inDto.getStrandegreesAvailability() != null) {
		    inDto.getStrandegreesAvailability().forEach((id, available) -> {
		        UniversityStrandegreeAvailabilityId key = new UniversityStrandegreeAvailabilityId();
		        key.setUniversityIdPk(universityIdPk);
		        key.setStrandegreeIdPk(id);

		        UniversityStrandegreeAvailabilityEntity entity = new UniversityStrandegreeAvailabilityEntity();
		        entity.setId(key);
		        entity.setAvailability(true);
		        
		        strandegreeAvailability.add(entity);
		    });
		}

		universityLogic.saveAllStrandegreeAvailability(strandegreeAvailability);
	}

	@Override
	public UniversityDto getUniversitiesOverview() throws Exception {
		
		UniversityDto outDto = new UniversityDto();
		
		UniversityOverviewData overview = universityLogic.getUniversitiesOverview();
		
		outDto.setOverview(overview);
		
		return outDto;
	}

}
