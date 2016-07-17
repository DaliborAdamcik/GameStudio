package sk.tsystems.gamestudio.services.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import sk.tsystems.gamestudio.entity.CommentEntity;
import sk.tsystems.gamestudio.entity.GameEntity;
import sk.tsystems.gamestudio.entity.UserEntity;
import sk.tsystems.gamestudio.services.CommentService;
import sk.tsystems.gamestudio.services.GameService;
import sk.tsystems.gamestudio.services.UserService;

public class CommentSvc extends JpaConnector implements CommentService  {

	public CommentSvc(UserService us, GameService ga) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addComment(CommentEntity comment) {
		EntityManager em = JpaConnector.getEntityManager();
		JpaConnector.beginTransaction();
		em.persist(comment);
		JpaConnector.commitTransaction();

		return true;
	}

	@Override
	public List<CommentEntity> commentsFor(GameEntity game) {
		try
		{
			EntityManager em = JpaConnector.getEntityManager();
		JpaConnector.beginTransaction();

		Query que = em.createQuery("SELECT c FROM CommentEntity c WHERE c.GameEntity = :game").setParameter("Game", game).setMaxResults(10);
		
			return (List<CommentEntity>) que.getResultList();
		}
		finally
		{
			JpaConnector.commitTransaction();
		}
	}

	@Override
	public List<CommentEntity> commentsFor(UserEntity user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLimit(int limit) {
		// TODO Auto-generated method stub

	}
}
