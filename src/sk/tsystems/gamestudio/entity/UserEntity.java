package sk.tsystems.gamestudio.entity;

public class UserEntity {
	private int id;
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
}
