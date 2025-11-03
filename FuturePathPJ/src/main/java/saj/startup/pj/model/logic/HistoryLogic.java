package saj.startup.pj.model.logic;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.AssessmentResultEntity;
import saj.startup.pj.model.dao.entity.HistoryQuestionData;
import saj.startup.pj.model.dao.entity.HistoryQuestionEntity;
import saj.startup.pj.model.dao.projection.AssessmentResultData;

@Service
public interface HistoryLogic {
	
	public int saveAssessmentResult(AssessmentResultEntity entity);

	public void saveHistoryQuestions(List<HistoryQuestionEntity> histories);
	
	public AssessmentResultEntity getAssessmentResult(int resultIdPk);
	
	public List<HistoryQuestionData> getHistoryQuestionsByResultIdPk(int resultIdPk);
	
	public Page<AssessmentResultData> getAllAssessmentResultByUser(Pageable pageable, String search, int userIdPk);
}
