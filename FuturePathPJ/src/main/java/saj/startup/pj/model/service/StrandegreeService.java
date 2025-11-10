package saj.startup.pj.model.service;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dto.StrandegreeDto;

@Service
public interface StrandegreeService {

	public void saveStrandegree(StrandegreeDto inDto) throws Exception;
	
	public StrandegreeDto getStrandegreeOverview() throws Exception;
	
	public StrandegreeDto getStrandegree(StrandegreeDto inDto) throws Exception;
	
	public StrandegreeDto getAllStrandegrees(StrandegreeDto inDto) throws Exception;
	
	public StrandegreeDto getAllStrandegreesNoPageable() throws Exception;
	
	public StrandegreeDto getStrandegreesQuestionsOverview() throws Exception;
	
	public void updateStrandegree(StrandegreeDto inDto) throws Exception;
	
	public void deleteStrandegree(StrandegreeDto inDto) throws Exception;
}
