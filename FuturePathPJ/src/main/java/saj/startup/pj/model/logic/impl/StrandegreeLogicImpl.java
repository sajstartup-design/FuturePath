package saj.startup.pj.model.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.StrandegreeDao;
import saj.startup.pj.model.dao.entity.StrandegreeEntity;
import saj.startup.pj.model.dao.entity.StrandegreeOverviewData;
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
		
		return strandegreeDao.getUserOverview();
	}

}
