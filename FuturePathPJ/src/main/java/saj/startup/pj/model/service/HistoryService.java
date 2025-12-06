package saj.startup.pj.model.service;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dto.HistoryDto;

@Service
public interface HistoryService {

	public HistoryDto getAllAssessmentByUser(HistoryDto inDto) throws Exception; 
	
	public HistoryDto getAllAssessmentResult(HistoryDto inDto) throws Exception; 
	
	public HistoryDto getRecentAssessmentResult() throws Exception; 
	
	public HistoryDto getAllRiasecResultByUser(HistoryDto inDto) throws Exception;
	
	public HistoryDto getAllRiasecResult(HistoryDto inDto) throws Exception;
}
