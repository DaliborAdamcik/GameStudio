package sk.tsystems.gamestudio.entity;

import java.util.Date;

import javax.persistence.*;

import sk.tsystems.gamestudio.entity.UserEntity;
import sk.tsystems.gamestudio.entity.GameEntity;


@Entity
@Table(name="COMMENT_JPA")
/*@SecondaryTables({
@SecondaryTable(name="USERS_JPA", pkJoinColumns={@PrimaryKeyJoinColumn(name="USRID")}),
@SecondaryTable(name="GAMES_JPA", pkJoinColumns={@PrimaryKeyJoinColumn(name="GAMID")})
})*/
public class CommentEntity {

	@Id
	@GeneratedValue
	@Column(name = "COMID")
	private int id;
	@Column(name = "COMDATE")
	private Date date;
	@Column(name = "COMTXT")
	private String comment;
	//@Column(table="GAMES_JPA")
	//@Embedded
	//@ManyToOne(fetch=FetchType.LAZY)
	@OneToOne//(mappedBy = "id")
	@JoinColumn(name="GAMEID")
	private GameEntity game;
	//@Column(table="USERS_JPA")
	//@Embedded
	//@ManyToOne(fetch=FetchType.LAZY)
	@OneToOne//(mappedBy = "id")
	@JoinColumn(name="USRID")
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
