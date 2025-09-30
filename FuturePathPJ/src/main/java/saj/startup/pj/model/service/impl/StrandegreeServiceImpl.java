package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.StrandegreeEntity;
import saj.startup.pj.model.dto.StrandegreeDto;
import saj.startup.pj.model.logic.StrandegreeLogic;
import saj.startup.pj.model.service.StrandegreeService;

@Service
public class StrandegreeServiceImpl implements StrandegreeService {
	
	@Autowired
	private StrandegreeLogic strandegreeLogic;

	@Override
	public void saveStrandegree(StrandegreeDto inDto) throws Exception {
		
		Timestamp timeNow = new Timestamp(System.currentTimeMillis());
		
		StrandegreeEntity newStrandegree = new StrandegreeEntity();
		
		newStrandegree.setName(inDto.getName());
		newStrandegree.setCode(inDto.getCode());
		newStrandegree.setCategory(inDto.getCategory());
		newStrandegree.setDetails(inDto.getDetails());
		newStrandegree.setIsActive(true);
		newStrandegree.setIsDeleted(false);
		newStrandegree.setCreatedAt(timeNow);
		
		strandegreeLogic.saveStrandegree(newStrandegree);
	}

}
