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
			EntityManager em = getEntityManager();
			beginTransaction();

			Query que = em.createQuery("SELECT g FROM GameEntity g WHERE g.id = :id").setParameter("id", id);
			return (GameEntity) que.getSingleResult();
		}
		finally
		{
			commitTransaction();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GameEntity> listGames() {
		try
		{
			EntityManager em = getEntityManager();
			beginTransaction();

			Query que = em.createQuery("SELECT g FROM GameEntity g");
		
			return que.getResultList();
		}
		finally
		{
			commitTransaction();
		}
	}

	@Override
	public boolean addGame(GameEntity game) {
		// TODO we need to set game.setID(0) there
		EntityManager em = getEntityManager();
		beginTransaction();
		em.persist(game);
		commitTransaction();
		return true; // TODO
	}

}
