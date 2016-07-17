package sk.tsystems.gamestudio.services.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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

			Query que = em.createQuery("SELECT g FROM GameEntity g WHERE g.id = :id").setParameter("id", id);
			return (GameEntity) que.getSingleResult();
		}
		finally
		{
			JpaConnector.commitTransaction();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GameEntity> listGames() {
		try
		{
			EntityManager em = JpaConnector.getEntityManager();
			JpaConnector.beginTransaction();

			Query que = em.createQuery("SELECT g FROM GameEntity g");
		
			return  que.getResultList();
		}
		finally
		{
			JpaConnector.commitTransaction();
		}
	}

	@Override
	public boolean addGame(GameEntity game) {
		EntityManager em = JpaConnector.getEntityManager();
		JpaConnector.beginTransaction();
		em.persist(game);
		JpaConnector.commitTransaction();
		return true; // TODO
	}

}
