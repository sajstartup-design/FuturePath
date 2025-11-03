package saj.startup.pj.model.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.AssessmentResultDao;
import saj.startup.pj.model.dao.HistoryQuestionDao;
import saj.startup.pj.model.dao.entity.AssessmentResultEntity;
import saj.startup.pj.model.dao.entity.HistoryQuestionData;
import saj.startup.pj.model.dao.entity.HistoryQuestionEntity;
import saj.startup.pj.model.logic.HistoryLogic;

@Service
public class HistoryLogicImpl implements HistoryLogic {
	
	@Autowired
	private HistoryQuestionDao historyQuestionDao;
	
	@Autowired
	private AssessmentResultDao assessmentResultDao;
	
	@Override
	public int saveAssessmentResult(AssessmentResultEntity entity) {
		
		return assessmentResultDao.save(entity).getIdPk();
	}

	@Override
	public void saveHistoryQuestions(List<HistoryQuestionEntity> histories) {
		
		historyQuestionDao.saveAll(histories);
	}

	@Override
	public AssessmentResultEntity getAssessmentResult(int resultIdPk) {
		
		return assessmentResultDao.getAssessmentResult(resultIdPk);
	}

	@Override
	public List<HistoryQuestionData> getHistoryQuestionsByResultIdPk(int resultIdPk) {
		
		return historyQuestionDao.getHistoryQuestionsByResultIdPk(resultIdPk);
	}

}
