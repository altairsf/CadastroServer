package controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
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

    public Produto findProduto(int idProduto) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produto.class, idProduto);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    public void edit(Produto produto) throws Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Produto p = em.find(Produto.class, produto.getIdProduto());
            if (p == null) {
                throw new EntityNotFoundException("Produto com id " + produto.getIdProduto() + " não existe.");
            }
            
            p.setNome(produto.getNome());
            p.setQuantidade(produto.getQuantidade());
            em.merge(p);
            em.getTransaction().commit();       
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}

