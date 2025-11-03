package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.model.dao.entity.UniversityData;
import saj.startup.pj.model.dao.entity.UniversityEntity;
import saj.startup.pj.model.dao.entity.UniversityOverviewData;
import saj.startup.pj.model.dao.entity.UniversityStrandegreeAvailabilityEntity;
import saj.startup.pj.model.dao.entity.UniversityStrandegreeAvailabilityId;
import saj.startup.pj.model.dto.UniversityDto;
import saj.startup.pj.model.logic.UniversityLogic;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.object.UniversityObj;
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
		newUniversity.setFounded(inDto.getFounded());
		newUniversity.setStudents(inDto.getStudents());
		newUniversity.setMotto(inDto.getMotto());
		
		int universityIdPk = universityLogic.saveUniversity(newUniversity);
		
		List<UniversityStrandegreeAvailabilityEntity> strandegreeAvailability = new ArrayList<>();
		
		if (inDto.getStrandegreesAvailability() != null) {
		    inDto.getStrandegreesAvailability().forEach((id, available) -> {
		        UniversityStrandegreeAvailabilityId key = new UniversityStrandegreeAvailabilityId();
		        key.setUniversityIdPk(universityIdPk);
		        key.setStrandegreeIdPk(id);

		        UniversityStrandegreeAvailabilityEntity entity = new UniversityStrandegreeAvailabilityEntity();
		        entity.setId(key);
		        entity.setAvailability(available);
		        
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

	@Override
	public UniversityDto getAllUniversities(UniversityDto inDto) throws Exception {
		
		UniversityDto outDto = new UniversityDto();
		
		Pageable pageable = PageRequest.of(inDto.getPagination().getPage(), CommonConstant.UNIVERSITY_MAX_DISPLAY);
		
		FilterAndSearchObj filter = inDto.getFilter();
		
		Page<UniversityData> allUniversities = universityLogic.getAllUniversities(pageable, filter.getSearch());
		
		List<UniversityData> universities = allUniversities.getContent();
		
		PaginationObj pagination = new PaginationObj();
		
		pagination.setPage(allUniversities.getNumber());
		pagination.setTotalPages(allUniversities.getTotalPages());
		pagination.setTotalElements(allUniversities.getTotalElements());
		pagination.setHasNext(allUniversities.hasNext());
		pagination.setHasPrevious(allUniversities.hasPrevious());
		
		outDto.setUniversities(universities);
		outDto.setPagination(pagination);

		return outDto;
	}

	@Override
	public UniversityDto getAllUniversitiesNoPageable() throws Exception {
		
		UniversityDto outDto = new UniversityDto();
		
		List<UniversityData> allUniversities = universityLogic.getAllUniversitiesNoPageable();
		
		List<UniversityObj> universities = new ArrayList<>();
		
		for(UniversityData university : allUniversities) {
			
			UniversityObj obj = new UniversityObj();
			
			obj.setUniversityIdPk(university.getUniversityIdPk());
			obj.setUniversityName(university.getUniversityName());
			obj.setCategory(university.getCategory());
			obj.setContact(university.getContact());
			obj.setStreet(university.getStreet());
			obj.setCity(university.getCity());
			obj.setProvince(university.getProvince());
			obj.setPostalCode(university.getPostalCode());		
			obj.setFounded(university.getFounded());
			obj.setStudents(university.getStudents());
			obj.setCourses(university.getCoursesOffered());		
			
			
			
			universities.add(obj);
			
		}
		
		outDto.setAllUniversities(universities);
		
		return outDto;
	}

	@Override
	public UniversityDto getUniversity(UniversityDto inDto) throws Exception {
		
		UniversityDto outDto = new UniversityDto();
		
		UniversityEntity entity = universityLogic.getUniversityIdPk(inDto.getIdPk());
		
		UniversityObj obj = new UniversityObj();
		
		obj.setUniversityName(entity.getUniversityName());
		obj.setCategory(entity.getCategory());
		obj.setContact(entity.getContact());
		obj.setFounded(entity.getFounded());
		obj.setStudents(entity.getStudents());
		obj.setStreet(entity.getStreet());
		obj.setCity(entity.getCity());
		obj.setProvince(entity.getProvince());
		obj.setPostalCode(entity.getPostalCode());
		obj.setMotto(entity.getMotto());
		
		outDto.setUniversity(obj);
		
		HashMap<Integer, Boolean> strandegreesAvailability = universityLogic.getAvailableStrandegree(inDto.getIdPk());
		
		outDto.setStrandegreesAvailability(strandegreesAvailability);
		
		return outDto;
	}
	
	
	@Override
	public void updateUniversity(UniversityDto inDto) throws Exception {

	    // 🔹 Get existing university
	    UniversityEntity existingUniversity = universityLogic.getUniversityIdPk(inDto.getIdPk());
	    if (existingUniversity == null) {
	        throw new Exception("University not found with ID: " + inDto.getIdPk());
	    }

	    // 🔹 Update fields
	    existingUniversity.setUniversityName(inDto.getUniversityName());
	    existingUniversity.setCategory(inDto.getCategory());
	    existingUniversity.setContact(inDto.getContact());
	    existingUniversity.setStreet(inDto.getStreet());
	    existingUniversity.setCity(inDto.getCity());
	    existingUniversity.setProvince(inDto.getProvince());
	    existingUniversity.setPostalCode(inDto.getPostalCode());
	    existingUniversity.setFounded(inDto.getFounded());
	    existingUniversity.setStudents(inDto.getStudents());
	    existingUniversity.setMotto(inDto.getMotto());
	    existingUniversity.setIsActive(true);
	    existingUniversity.setIsDeleted(false);

	    universityLogic.saveUniversity(existingUniversity);

	    // 🔹 Get current availability map
	    Map<Integer, Boolean> existingMap = universityLogic.getAvailableStrandegree(inDto.getIdPk());

	    // 🔹 Build list of updates (only changes)
	    List<UniversityStrandegreeAvailabilityEntity> updates = new ArrayList<>();

	    if (inDto.getStrandegreesAvailability() != null) {
	        for (Map.Entry<Integer, Boolean> entry : inDto.getStrandegreesAvailability().entrySet()) {
	            Integer strandegreeIdPk = entry.getKey();
	            Boolean newAvailability = entry.getValue();
	            Boolean currentAvailability = existingMap.get(strandegreeIdPk);

	            // Only process if value changed or doesn’t exist yet
	            if (currentAvailability == null || !currentAvailability.equals(newAvailability)) {
	                UniversityStrandegreeAvailabilityId id = new UniversityStrandegreeAvailabilityId();
	                id.setUniversityIdPk(inDto.getIdPk());
	                id.setStrandegreeIdPk(strandegreeIdPk);

	                UniversityStrandegreeAvailabilityEntity entity = new UniversityStrandegreeAvailabilityEntity();
	                entity.setId(id);
	                entity.setAvailability(newAvailability);

	                updates.add(entity);
	            }
	        }

	        if (!updates.isEmpty()) {
	            universityLogic.saveAllStrandegreeAvailability(updates);
	        }
	    }
	}



}
