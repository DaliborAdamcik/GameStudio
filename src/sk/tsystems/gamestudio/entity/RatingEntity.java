package sk.tsystems.gamestudio.entity;
import sk.tsystems.gamestudio.entity.UserEntity;
import sk.tsystems.gamestudio.entity.GameEntity;

public class RatingEntity {
	private int id;
	private int rating;
	private GameEntity game;
	private UserEntity user;
	
	public RatingEntity(GameEntity game, UserEntity user, int rating, int id) {
		super();
		this.game = game;
		this.user = user;
		this.rating = rating;
		this.id = id;
	}
	
	public RatingEntity(GameEntity game, UserEntity user, int rating) {
		this(game, user, rating, -1);
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public GameEntity getGame() {
		return game;
	}

	public UserEntity getUser() {
		return user;
	}
	
	
	
}


