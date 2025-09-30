package saj.startup.pj.model.service;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dto.StrandegreeDto;

@Service
public interface StrandegreeService {

	public void saveStrandegree(StrandegreeDto inDto) throws Exception;
	
	public StrandegreeDto getStrandegreeOverview() throws Exception;
}
