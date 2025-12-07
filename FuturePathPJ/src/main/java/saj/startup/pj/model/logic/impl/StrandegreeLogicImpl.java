package saj.startup.pj.model.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.StrandegreeDao;
import saj.startup.pj.model.dao.entity.StrandegreeEntity;
import saj.startup.pj.model.dao.entity.StrandegreeOverviewData;
import saj.startup.pj.model.dao.entity.StrandegreeQuestionData;
import saj.startup.pj.model.logic.StrandegreeLogic;

@Service
public class StrandegreeLogicImpl implements StrandegreeLogic{
	
	@Autowired
	private StrandegreeDao strandegreeDao;

	@Override
	public void saveStrandegree(StrandegreeEntity entity) {
		
		strandegreeDao.save(entity);
	}

	@Override
	public StrandegreeOverviewData getStrandegreeOverview() {
		
		return strandegreeDao.getStrandegreeOverview();
	}

	@Override
	public Page<StrandegreeEntity> getAllStrandegrees(Pageable pageable, String search) {
	
		return strandegreeDao.getAllStrandegrees(pageable, search);
	}

	@Override
	public List<StrandegreeEntity> getAllStrandegreesNoPageable() {
		
		return strandegreeDao.getAllStrandegreesNoPageable();
	}

	@Override
	public List<StrandegreeQuestionData> getStrandegreeQuestionOverview() {
		
		return strandegreeDao.getAllStrandegreeQuestion();
	}

	@Override
	public StrandegreeEntity getStrandegree(int idPk) {
		
		return strandegreeDao.getStrandegreeByIdPk(idPk);
	}

	@Override
	public void deleteStrandegree(int idPk) {
		
		strandegreeDao.deleteStrandegree(idPk);
	}

	@Override
	public int countRecommendedStrandegree(List<Integer> codes) {

		return strandegreeDao.countRecommendedStrandegree(codes);
	}

	@Override
	public int countAllStrandegree() {
		
		return strandegreeDao.countAllDStrandegree();
	}

	@Override
	public List<StrandegreeEntity> getRandomRecommendedStrandegree(List<Integer> codes) {
		
		return strandegreeDao.getRandomRecommendedStrandegree(codes);
	}

	@Override
	public List<StrandegreeEntity> getStrandegreeByIdPks(List<Integer> idPks) {
		
		return strandegreeDao.getStrandegreesByIdPks(idPks);
	}

	@Override
	public List<StrandegreeEntity> getStrandegreeByRiasecCodesAndNotInIdPks(List<Integer> codes, List<Integer> idPks) {
	
		return strandegreeDao.getStrandegreeByRiasecCodesAndNotInIdPks(codes, idPks);
	}

}
