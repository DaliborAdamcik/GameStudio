package sk.tsystems.gamestudio.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import sk.tsystems.gamestudio.entity.UserEntity;
import sk.tsystems.gamestudio.entity.GameEntity;


@Entity
public class CommentEntity {

	@Id
	@GeneratedValue
	private int id;
	private Date date;
	private String comment;
	private GameEntity game;
	private UserEntity user;

	public CommentEntity(GameEntity game, UserEntity user, String comment, int id, Date date) {
		super();
		this.id = id;
		this.date = date;
		this.comment = comment;
		this.game = game;
		this.user = user;
	}

	public CommentEntity(GameEntity game, UserEntity user, String comment) {
		this(game, user, comment, -1, new Date());
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getComment() {
		return comment;
	}

	public GameEntity getGame() {
		return game;
	}

	public UserEntity getUser() {
		return user;
	}
	
	public int getUserID()
	{
		return user.getID();
	}
	
	public int getGameID()
	{
		return game.getID();
	}
	
}
