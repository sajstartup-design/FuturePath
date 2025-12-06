package saj.startup.pj.model.dao.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "riasec_result")
public class RiasecResultEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPk;
	
	private int userIdPk;
	
    private int realistic;
    private int investigative;
    private int artistic;
    private int social;
    private int enterprising;
    private int conventional;
    
    private Timestamp dateTaken;
    private String category;
}
