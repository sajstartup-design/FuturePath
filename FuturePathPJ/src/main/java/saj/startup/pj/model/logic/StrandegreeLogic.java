package saj.startup.pj.model.logic;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.StrandegreeEntity;
import saj.startup.pj.model.dao.entity.StrandegreeOverviewData;

@Service
public interface StrandegreeLogic {

	public void saveStrandegree(StrandegreeEntity entity);
	
	public StrandegreeOverviewData getStrandegreeOverview();
}
