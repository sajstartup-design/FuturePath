package saj.startup.pj.model.logic;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.StrandegreeEntity;
import saj.startup.pj.model.dao.entity.StrandegreeOverviewData;
import saj.startup.pj.model.dao.entity.StrandegreeQuestionData;

@Service
public interface StrandegreeLogic {

	public void saveStrandegree(StrandegreeEntity entity);
	
	public StrandegreeOverviewData getStrandegreeOverview();
	
	public Page<StrandegreeEntity> getAllStrandegrees(Pageable pageable, String search);
	
	public List<StrandegreeEntity> getAllStrandegreesNoPageable();
	
	public List<StrandegreeQuestionData> getStrandegreeQuestionOverview();
	
	public StrandegreeEntity getStrandegree(int idPk);
	
	public void deleteStrandegree(int idPk);
}
