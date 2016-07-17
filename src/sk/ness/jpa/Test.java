package sk.ness.jpa;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import sk.ness.jpa.Student;

public class Test {
	
	// jpql

	public static void main(String[] args) {

		EntityManager em = JpaHelper.getEntityManager();
		
		JpaHelper.beginTransaction();

		Student stud = new Student("Janko", "hrasko", 10);
		em.persist(stud);

		stud = new Student("Puki", "hrasko", 15);
		em.persist(stud);

		stud = new Student("ruki", "hrasko", 17);
		em.persist(stud);

		
		JpaHelper.commitTransaction();
		JpaHelper.beginTransaction();

		Query que = em.createQuery("SELECT s FROM Student s");
		que.setMaxResults(3);
		
		System.out.println(que.getResultList());
		
		que = em.createQuery("SELECT s FROM Student s WHERE s.meno =:meno");
		que.setParameter("meno", "ruki");
		
		System.out.println(que.getResultList());
		
		stud = em.find(Student.class, 2);
		System.out.println(stud);
		//em.remove(stud);
		
		stud.setVek(20);
		JpaHelper.commitTransaction();
	}
}
