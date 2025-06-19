package controller;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Pessoa;


public class PessoaJpaController implements Serializable {
    private EntityManagerFactory emf = null;
    
    public PessoaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public Pessoa findPessoa(int idPessoa) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pessoa.class, idPessoa);
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
