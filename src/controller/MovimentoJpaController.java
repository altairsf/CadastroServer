package controller;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Movimento;

public class MovimentoJpaController implements Serializable {
    private EntityManagerFactory emf = null;
    
    public MovimentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void create(Movimento mov) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(mov);
            em.getTransaction().commit();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
