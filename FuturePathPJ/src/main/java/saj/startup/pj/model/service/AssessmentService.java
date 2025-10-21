package saj.startup.pj.model.service;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dto.AssessmentDto;

@Service
public interface AssessmentService {

	public AssessmentDto getAssessmentResult(AssessmentDto inDto) throws Exception;
}
