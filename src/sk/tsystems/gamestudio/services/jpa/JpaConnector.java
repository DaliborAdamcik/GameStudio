package sk.tsystems.gamestudio.services.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

abstract class JpaConnector implements AutoCloseable {
	/*
	 * postarat sa o ziskanie factory umoznit ziskanie entity manager zacatie
	 * transakcie, ukoncenie transakcie uzatvorit faktory aj entity manager
	 */

	private static EntityManagerFactory factory;
	private static EntityManager entityManager;

	private static EntityManagerFactory getFactory() {
		if (factory == null || !factory.isOpen()) {
			factory = Persistence.createEntityManagerFactory("hibernatePersistenceUnit");
		}
		return factory;
	}

	static EntityManager getEntityManager() {
		if (entityManager == null || !entityManager.isOpen()) {
			entityManager = getFactory().createEntityManager();
		}
		return entityManager;
	}

	static void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	static void commitTransaction() {
		getEntityManager().getTransaction().commit();
	}

	private static void closeEntityManager() {
		if (entityManager != null && entityManager.isOpen()) {
			entityManager.close();
		}
	}

	private static void closeEntityManagerFactory() {
		if (factory != null && factory.isOpen()) {
			factory.close();
		}
	}

/*	public static void closeAll() {
		closeEntityManager();
		closeEntityManagerFactory();
	}*/
	
	@Override
	public void close() throws Exception {
		closeEntityManager();
		closeEntityManagerFactory();
	}
	
}
