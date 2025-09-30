package saj.startup.pj.model.dao.entity;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class StrandegreeOverviewData {

	private int totalStrand;
	private int totalActiveStrand;
	private int totalInactiveStrand;
	
	private int totalDegree;
	private int totalActiveDegree;
	private int totalInactiveDegree;
}
