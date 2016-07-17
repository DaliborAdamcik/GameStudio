package sk.tsystems.gamestudio.services.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sk.tsystems.gamestudio.entity.CommentEntity;
import sk.tsystems.gamestudio.entity.GameEntity;
import sk.tsystems.gamestudio.services.GameService;

public class GameSvc extends JpaConnector implements GameService {

	public GameSvc() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public GameEntity getGame(int id) {
		try
		{
			EntityManager em = JpaConnector.getEntityManager();
		JpaConnector.beginTransaction();

		Query que = em.createQuery("SELECT c FROM CommentEntity c WHERE c.Game = ").setParameter("Game", game).setMaxResults(10);
		
			return (List<CommentEntity>) que.getResultList();
		}
		finally
		{
			JpaConnector.commitTransaction();
		}
	}

	@Override
	public List<GameEntity> listGames() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void addgame(GameEntity game)
	{
		EntityManager em = JpaConnector.getEntityManager();
		JpaConnector.beginTransaction();
		em.persist(game);
		JpaConnector.commitTransaction();
	}

}
