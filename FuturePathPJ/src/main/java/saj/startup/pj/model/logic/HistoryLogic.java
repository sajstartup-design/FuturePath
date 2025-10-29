package saj.startup.pj.model.logic;

import java.util.List;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.AssessmentResultEntity;
import saj.startup.pj.model.dao.entity.HistoryQuestionEntity;

@Service
public interface HistoryLogic {
	
	public int saveAssessmentResult(AssessmentResultEntity entity);

	public void saveHistoryQuestions(List<HistoryQuestionEntity> histories);
}
