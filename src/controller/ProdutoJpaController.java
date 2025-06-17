package controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import model.Produto;

public class ProdutoJpaController implements Serializable {
   private EntityManagerFactory emf = null;
   
   public ProdutoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
   
   public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
       
    public List<Produto> findProdutoEntities() {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT p FROM Produto p");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
