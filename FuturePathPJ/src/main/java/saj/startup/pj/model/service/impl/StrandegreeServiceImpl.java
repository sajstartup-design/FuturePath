package saj.startup.pj.model.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.model.dao.entity.StrandegreeEntity;
import saj.startup.pj.model.dao.entity.StrandegreeOverviewData;
import saj.startup.pj.model.dao.entity.StrandegreeQuestionData;
import saj.startup.pj.model.dto.StrandegreeDto;
import saj.startup.pj.model.logic.StrandegreeLogic;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.object.StrandegreeObj;
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

	@Override
	public StrandegreeDto getStrandegreeOverview() throws Exception {
		
		StrandegreeDto outDto = new StrandegreeDto();
		
		StrandegreeOverviewData overview = strandegreeLogic.getStrandegreeOverview();
		
		outDto.setOverview(overview);
		
		return outDto;
	}

	@Override
	public StrandegreeDto getAllStrandegrees(StrandegreeDto inDto) throws Exception {
		
		StrandegreeDto outDto = new StrandegreeDto();
		
		Pageable pageable = PageRequest.of(inDto.getPagination().getPage(), CommonConstant.STRANDEGREE_MAX_DISPLAY);
		
		FilterAndSearchObj filter = inDto.getFilter();
		
		Page<StrandegreeEntity> allStrandegrees = strandegreeLogic.getAllStrandegrees(pageable, filter.getSearch());
		
		List<StrandegreeObj> strandegrees = new ArrayList<>();
		
		for (StrandegreeEntity strandegree : allStrandegrees) {
			StrandegreeObj obj = new StrandegreeObj();
			
			obj.setIdPk(strandegree.getIdPk());
		    obj.setName(strandegree.getName());
		    obj.setCode(strandegree.getCode());
		    obj.setCategory(strandegree.getCategory());
		    obj.setDetails(strandegree.getDetails());
		    obj.setIsActive(strandegree.getIsActive());
		    obj.setCreatedAt(strandegree.getCreatedAt());
		    
		    strandegrees.add(obj);
		}
		
		PaginationObj pagination = new PaginationObj();
		
		pagination.setPage(allStrandegrees.getNumber());
		pagination.setTotalPages(allStrandegrees.getTotalPages());
		pagination.setTotalElements(allStrandegrees.getTotalElements());
		pagination.setHasNext(allStrandegrees.hasNext());
		pagination.setHasPrevious(allStrandegrees.hasPrevious());
		
		outDto.setStrandegrees(strandegrees);
		outDto.setPagination(pagination);

		return outDto;
	}

	@Override
	public StrandegreeDto getAllStrandegreesNoPageable() throws Exception {
		
		StrandegreeDto outDto = new StrandegreeDto();
		
		List<StrandegreeEntity> allStrandegrees = strandegreeLogic.getAllStrandegreesNoPageable();
		
		List<StrandegreeObj> strandegrees = new ArrayList<>();
		
		for (StrandegreeEntity strandegree : allStrandegrees) {
			StrandegreeObj obj = new StrandegreeObj();
			
			obj.setIdPk(strandegree.getIdPk());
		    obj.setName(strandegree.getName());
		    obj.setCode(strandegree.getCode());
		    obj.setCategory(strandegree.getCategory());
		    obj.setDetails(strandegree.getDetails());
		    obj.setIsActive(strandegree.getIsActive());
		    obj.setCreatedAt(strandegree.getCreatedAt());
		    
		    strandegrees.add(obj);
		}
		
		outDto.setStrandegrees(strandegrees);

		return outDto;
	}

	@Override
	public StrandegreeDto getStrandegreesQuestionsOverview() throws Exception {
		
		StrandegreeDto outDto = new StrandegreeDto();
		
		List<StrandegreeQuestionData> strandegreesQuestions = strandegreeLogic.getStrandegreeQuestionOverview();
		
		System.out.println(strandegreesQuestions);
		
		outDto.setStrandegreesQuestions(strandegreesQuestions);
		
		return outDto;
	}

}
