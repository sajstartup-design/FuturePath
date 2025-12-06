package saj.startup.pj.model.dao.projection;

import java.sql.Timestamp;

public interface RiasecResultData {
	Integer getRiasecIdPk();
	String getFullName();
	Timestamp getDateTaken();
	String getCategory();
}
