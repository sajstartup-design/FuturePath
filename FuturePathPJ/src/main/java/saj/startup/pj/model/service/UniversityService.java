package saj.startup.pj.model.service;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dto.UniversityDto;

@Service
public interface UniversityService {

	public void saveUniversity(UniversityDto inDto) throws Exception;
	
	public UniversityDto getUniversitiesOverview() throws Exception;
	
	public UniversityDto getAllUniversities(UniversityDto inDto) throws Exception;
	
	public UniversityDto getAllUniversitiesNoPageable() throws Exception;
}
