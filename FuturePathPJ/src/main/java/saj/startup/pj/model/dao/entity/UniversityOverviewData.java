package saj.startup.pj.model.dao.entity;

import org.springframework.context.annotation.Scope;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Scope("prototype")
public class UniversityOverviewData {

	private int totalUniversity;
	private int totalActiveUniversity;
	private int totalInactiveUniversity;
	private int totalPrivateUniversity;
	private int totalPublicUniversity;
	
}
