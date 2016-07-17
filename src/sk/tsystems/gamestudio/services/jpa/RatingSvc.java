package sk.tsystems.gamestudio.services.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import sk.tsystems.gamestudio.entity.GameEntity;
import sk.tsystems.gamestudio.entity.RatingEntity;
import sk.tsystems.gamestudio.entity.UserEntity;
import sk.tsystems.gamestudio.services.RatingService;

public class RatingSvc extends JpaConnector implements RatingService {

	public RatingSvc() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addRating(RatingEntity rating) {
		RatingEntity rat = myRating(rating.getGame(), rating.getUser());
		if(rat!= null)
		{
			rat.setRating(rating.getRating());
		}
		else
		{
			rat = rating;
			rat.setID(0); // Autoset ID of rating for JPA
		}
		
		EntityManager em = getEntityManager();
		beginTransaction();
		em.persist(rat);
		commitTransaction();
	}

	@Override
	public RatingEntity myRating(GameEntity game, UserEntity user) {
		try
		{
			EntityManager em = getEntityManager();
			beginTransaction();
			Query que = em.createQuery("SELECT r FROM RatingEntity r WHERE r.game = :game  AND r.user = :user ").setParameter("game", game).setParameter("user", user);
			return (RatingEntity) que.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
		finally
		{
			commitTransaction();
		}
	}

	@Override
	public double gameRating(GameEntity game) { // TODO is select statement ok? 
		try
		{
			Query q = getEntityManager().createQuery("SELECT SUM(r.rating), COUNT(*) FROM RatingEntity r WHERE r.game = :game").setParameter("game", game);
			Object[] res = (Object[]) q.getSingleResult();
			if ((long) res[1] == 0)
				return 0;
			
			return (long) res[0] / (long) res[1];
		}
		catch (NoResultException e) {
			return 0;
		}
	}

	@Override
	public List<RatingEntity> ratings() {
		/*try
		{
			EntityManager em = getEntityManager();
			beginTransaction();
			Query que = em.createQuery("SELECT s FROM ScoreEntity s WHERE s.game = :game ORDER BY s.score").setParameter("game", game).setMaxResults(10);
			return que.getResultList();
		}
		catch (NoResultException e) {
			return new ArrayList<ScoreEntity>();
		}
		finally
		{
			commitTransaction();
		}*/
		return null;
	}

}
