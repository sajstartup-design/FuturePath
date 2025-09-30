package saj.startup.pj.model.logic;

import org.springframework.stereotype.Service;

import saj.startup.pj.model.dao.entity.StrandegreeEntity;

@Service
public interface StrandegreeLogic {

	public void saveStrandegree(StrandegreeEntity entity);
}
