package saj.startup.pj.model.dto;

import java.util.List;

import lombok.Data;
import saj.startup.pj.model.dao.projection.AssessmentResultData;
import saj.startup.pj.model.dao.projection.RiasecResultData;
import saj.startup.pj.model.object.FilterAndSearchObj;
import saj.startup.pj.model.object.PaginationObj;

@Data
public class HistoryDto {
	
	public List<AssessmentResultData> assessments;
	
	public List<RiasecResultData> riasecResults;
	
	private PaginationObj pagination;
	
	private FilterAndSearchObj filter;
}
