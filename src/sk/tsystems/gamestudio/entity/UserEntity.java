package sk.tsystems.gamestudio.entity;

import javax.persistence.*;

@Entity
@Table(name="JPA_USERS")
public class UserEntity {
	@Id
	@Column(name = "USRID")
	@GeneratedValue
	private int id;
	
	@Column(name = "USRNAME")	
	private String name;
	
	public UserEntity(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", name=" + name + "]";
	}
	
}
