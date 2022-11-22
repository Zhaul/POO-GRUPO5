/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.edu.utp.controllers.exceptions.NonexistentEntityException;
import pe.edu.utp.entity.Justifications;
import pe.edu.utp.entity.Schedules;

/**
 *
 * @author zhaulvaldera
 */
public class JustificationsJpaController implements Serializable {

    public JustificationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public JustificationsJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProyectoFinalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Justifications justifications) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Schedules idShedule = justifications.getIdShedule();
            if (idShedule != null) {
                idShedule = em.getReference(idShedule.getClass(), idShedule.getId());
                justifications.setIdShedule(idShedule);
            }
            em.persist(justifications);
            if (idShedule != null) {
                idShedule.getJustificationsList().add(justifications);
                idShedule = em.merge(idShedule);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Justifications justifications) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Justifications persistentJustifications = em.find(Justifications.class, justifications.getId());
            Schedules idSheduleOld = persistentJustifications.getIdShedule();
            Schedules idSheduleNew = justifications.getIdShedule();
            if (idSheduleNew != null) {
                idSheduleNew = em.getReference(idSheduleNew.getClass(), idSheduleNew.getId());
                justifications.setIdShedule(idSheduleNew);
            }
            justifications = em.merge(justifications);
            if (idSheduleOld != null && !idSheduleOld.equals(idSheduleNew)) {
                idSheduleOld.getJustificationsList().remove(justifications);
                idSheduleOld = em.merge(idSheduleOld);
            }
            if (idSheduleNew != null && !idSheduleNew.equals(idSheduleOld)) {
                idSheduleNew.getJustificationsList().add(justifications);
                idSheduleNew = em.merge(idSheduleNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = justifications.getId();
                if (findJustifications(id) == null) {
                    throw new NonexistentEntityException("The justifications with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Justifications justifications;
            try {
                justifications = em.getReference(Justifications.class, id);
                justifications.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The justifications with id " + id + " no longer exists.", enfe);
            }
            Schedules idShedule = justifications.getIdShedule();
            if (idShedule != null) {
                idShedule.getJustificationsList().remove(justifications);
                idShedule = em.merge(idShedule);
            }
            em.remove(justifications);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Justifications> findJustificationsEntities() {
        return findJustificationsEntities(true, -1, -1);
    }

    public List<Justifications> findJustificationsEntities(int maxResults, int firstResult) {
        return findJustificationsEntities(false, maxResults, firstResult);
    }

    private List<Justifications> findJustificationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Justifications.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Justifications findJustifications(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Justifications.class, id);
        } finally {
            em.close();
        }
    }

    public int getJustificationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Justifications> rt = cq.from(Justifications.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
