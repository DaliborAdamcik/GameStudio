package sk.tsystems.gamestudio.services.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import sk.tsystems.gamestudio.entity.UserEntity;
import sk.tsystems.gamestudio.services.UserService;

public class UserSvc extends JpaConnector implements UserService {
	private UserEntity current = null;

	public UserSvc() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean auth(String name) {
		UserEntity usr = getUser(name);
		if(usr==null)
			usr = new UserEntity(0,name);
		
		current = usr;
		
		EntityManager em = getEntityManager();
		beginTransaction();
		em.persist(usr);
		commitTransaction();
		return true; // TODO
	}

	@Override
	public UserEntity getUser(int id) {
		try
		{
			EntityManager em = getEntityManager();
			beginTransaction();

			Query que = em.createQuery("SELECT u FROM UserEntity u WHERE u.id = :id").setParameter("id", id);
			return (UserEntity) que.getSingleResult();
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
	public UserEntity getUser(String name) {
		try
		{
			EntityManager em = getEntityManager();
			beginTransaction();
			
			Query que = em.createQuery("SELECT u FROM UserEntity u WHERE u.name = :name").setParameter("name", name);
			return (UserEntity) que.getSingleResult();
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
	public UserEntity me() {
		return current;
	}

	@Override
	public UserEntity addUser(String name) {
		UserEntity usr = getUser(name);
		if(usr==null)
			usr = new UserEntity(0,name);
		
		EntityManager em = getEntityManager();
		beginTransaction();
		em.persist(usr);
		commitTransaction();
		return usr;
	}

}
