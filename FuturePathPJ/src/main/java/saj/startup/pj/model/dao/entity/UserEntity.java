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
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPk;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone;
	
	private String gender;
	
	private String username;
	
	private String password;
	
	private Boolean isActive;
	
	private Boolean isDeleted;
	
	private Timestamp createdAt;
}
