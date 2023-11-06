package ro.uaic.info.l3.models;


import org.junit.jupiter.api.Test;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class TestTest {

    @Test
    public void testJPA() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("abcdef");;
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        ro.uaic.info.l3.entities.Test test = new ro.uaic.info.l3.entities.Test();
        test.setName("test test test 1234");
        em.persist(test);
        em.getTransaction().commit();;

        em.close();
        entityManagerFactory.close();
    }
}