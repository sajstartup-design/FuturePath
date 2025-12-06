package saj.startup.pj.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.common.CommonConstant;
import saj.startup.pj.model.dao.entity.UserEntity;
import saj.startup.pj.model.dao.projection.AssessmentResultData;
import saj.startup.pj.model.dao.projection.RiasecResultData;
import saj.startup.pj.model.dto.HistoryDto;
import saj.startup.pj.model.logic.HistoryLogic;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;
import saj.startup.pj.model.service.HistoryService;
import saj.startup.pj.model.service.UserService;

@Service
public class HistoryServiceImpl implements HistoryService{
	
	@Autowired
	private HistoryLogic historyLogic;
	
	@Autowired
	private UserService userService;

	@Override
	public HistoryDto getAllAssessmentByUser(HistoryDto inDto) throws Exception {
		
		UserEntity user = userService.getUserActive();
		
		HistoryDto outDto = new HistoryDto();
		
		Pageable pageable = PageRequest.of(inDto.getPagination().getPage(), CommonConstant.USER_MAX_DISPLAY);
		
		FilterAndSearchObj filter = inDto.getFilter();
		
		Page<AssessmentResultData> assessments = historyLogic.getAllAssessmentResultByUser(pageable, filter.getSearch(), user.getIdPk());
		
		outDto.setAssessments(assessments.toList());
		
		PaginationObj pagination = new PaginationObj();
		
		pagination.setPage(assessments.getNumber());
		pagination.setTotalPages(assessments.getTotalPages());
		pagination.setTotalElements(assessments.getTotalElements());
		pagination.setHasNext(assessments.hasNext());
		pagination.setHasPrevious(assessments.hasPrevious());
		
		outDto.setPagination(pagination);
		
		return outDto;
	}

	@Override
	public HistoryDto getAllAssessmentResult(HistoryDto inDto) throws Exception {
		
		HistoryDto outDto = new HistoryDto();
		
		Pageable pageable = PageRequest.of(inDto.getPagination().getPage(), CommonConstant.USER_MAX_DISPLAY);
		
		FilterAndSearchObj filter = inDto.getFilter();
		
		Page<AssessmentResultData> assessments = historyLogic.getAllAssessmentResult(pageable, filter.getSearch());
		
		outDto.setAssessments(assessments.toList());
		
		PaginationObj pagination = new PaginationObj();
		
		pagination.setPage(assessments.getNumber());
		pagination.setTotalPages(assessments.getTotalPages());
		pagination.setTotalElements(assessments.getTotalElements());
		pagination.setHasNext(assessments.hasNext());
		pagination.setHasPrevious(assessments.hasPrevious());
		
		outDto.setPagination(pagination);
		
		return outDto;
	}

	@Override
	public HistoryDto getRecentAssessmentResult() throws Exception {
		
		HistoryDto outDto = new HistoryDto();
		
		Pageable pageable = PageRequest.of(0, CommonConstant.RECENT_MAX_DISPLAY);
		
		Page<AssessmentResultData> assessments = historyLogic.getAllAssessmentResult(pageable, CommonConstant.BLANK);
		
		outDto.setAssessments(assessments.toList());

		return outDto;
	}

	@Override
	public HistoryDto getAllRiasecResultByUser(HistoryDto inDto) throws Exception {
		
		UserEntity user = userService.getUserActive();
		
		HistoryDto outDto = new HistoryDto();
		
		Pageable pageable = PageRequest.of(inDto.getPagination().getPage(), CommonConstant.USER_MAX_DISPLAY);
		
		FilterAndSearchObj filter = inDto.getFilter();
		
		Page<RiasecResultData> riasecResults = historyLogic.getAllRiasecResultByUser(pageable, filter.getSearch(), user.getIdPk());
		
		outDto.setRiasecResults(riasecResults.toList());
		
		PaginationObj pagination = new PaginationObj();
		
		pagination.setPage(riasecResults.getNumber());
		pagination.setTotalPages(riasecResults.getTotalPages());
		pagination.setTotalElements(riasecResults.getTotalElements());
		pagination.setHasNext(riasecResults.hasNext());
		pagination.setHasPrevious(riasecResults.hasPrevious());
		
		outDto.setPagination(pagination);
		
		return outDto;
	}

	@Override
	public HistoryDto getAllRiasecResult(HistoryDto inDto) throws Exception {
		
		HistoryDto outDto = new HistoryDto();
		
		Pageable pageable = PageRequest.of(inDto.getPagination().getPage(), CommonConstant.USER_MAX_DISPLAY);
		
		FilterAndSearchObj filter = inDto.getFilter();
		
		Page<RiasecResultData> riasecResults = historyLogic.getAllRiasecResult(pageable, filter.getSearch());
		
		outDto.setRiasecResults(riasecResults.toList());
		
		PaginationObj pagination = new PaginationObj();
		
		pagination.setPage(riasecResults.getNumber());
		pagination.setTotalPages(riasecResults.getTotalPages());
		pagination.setTotalElements(riasecResults.getTotalElements());
		pagination.setHasNext(riasecResults.hasNext());
		pagination.setHasPrevious(riasecResults.hasPrevious());
		
		outDto.setPagination(pagination);
		
		return outDto;
	}


}
