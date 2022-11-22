/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import pe.edu.utp.entity.CargosPersonal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pe.edu.utp.controllers.exceptions.IllegalOrphanException;
import pe.edu.utp.controllers.exceptions.NonexistentEntityException;
import pe.edu.utp.entity.Areas;

/**
 *
 * @author zhaulvaldera
 */
public class AreasJpaController implements Serializable {

    public AreasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public AreasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProyectoFinalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Areas areas) {
        if (areas.getCargosPersonalList() == null) {
            areas.setCargosPersonalList(new ArrayList<CargosPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CargosPersonal> attachedCargosPersonalList = new ArrayList<CargosPersonal>();
            for (CargosPersonal cargosPersonalListCargosPersonalToAttach : areas.getCargosPersonalList()) {
                cargosPersonalListCargosPersonalToAttach = em.getReference(cargosPersonalListCargosPersonalToAttach.getClass(), cargosPersonalListCargosPersonalToAttach.getId());
                attachedCargosPersonalList.add(cargosPersonalListCargosPersonalToAttach);
            }
            areas.setCargosPersonalList(attachedCargosPersonalList);
            em.persist(areas);
            for (CargosPersonal cargosPersonalListCargosPersonal : areas.getCargosPersonalList()) {
                Areas oldIdAreaOfCargosPersonalListCargosPersonal = cargosPersonalListCargosPersonal.getIdArea();
                cargosPersonalListCargosPersonal.setIdArea(areas);
                cargosPersonalListCargosPersonal = em.merge(cargosPersonalListCargosPersonal);
                if (oldIdAreaOfCargosPersonalListCargosPersonal != null) {
                    oldIdAreaOfCargosPersonalListCargosPersonal.getCargosPersonalList().remove(cargosPersonalListCargosPersonal);
                    oldIdAreaOfCargosPersonalListCargosPersonal = em.merge(oldIdAreaOfCargosPersonalListCargosPersonal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Areas areas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Areas persistentAreas = em.find(Areas.class, areas.getId());
            List<CargosPersonal> cargosPersonalListOld = persistentAreas.getCargosPersonalList();
            List<CargosPersonal> cargosPersonalListNew = areas.getCargosPersonalList();
            List<String> illegalOrphanMessages = null;
            for (CargosPersonal cargosPersonalListOldCargosPersonal : cargosPersonalListOld) {
                if (!cargosPersonalListNew.contains(cargosPersonalListOldCargosPersonal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CargosPersonal " + cargosPersonalListOldCargosPersonal + " since its idArea field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CargosPersonal> attachedCargosPersonalListNew = new ArrayList<CargosPersonal>();
            for (CargosPersonal cargosPersonalListNewCargosPersonalToAttach : cargosPersonalListNew) {
                cargosPersonalListNewCargosPersonalToAttach = em.getReference(cargosPersonalListNewCargosPersonalToAttach.getClass(), cargosPersonalListNewCargosPersonalToAttach.getId());
                attachedCargosPersonalListNew.add(cargosPersonalListNewCargosPersonalToAttach);
            }
            cargosPersonalListNew = attachedCargosPersonalListNew;
            areas.setCargosPersonalList(cargosPersonalListNew);
            areas = em.merge(areas);
            for (CargosPersonal cargosPersonalListNewCargosPersonal : cargosPersonalListNew) {
                if (!cargosPersonalListOld.contains(cargosPersonalListNewCargosPersonal)) {
                    Areas oldIdAreaOfCargosPersonalListNewCargosPersonal = cargosPersonalListNewCargosPersonal.getIdArea();
                    cargosPersonalListNewCargosPersonal.setIdArea(areas);
                    cargosPersonalListNewCargosPersonal = em.merge(cargosPersonalListNewCargosPersonal);
                    if (oldIdAreaOfCargosPersonalListNewCargosPersonal != null && !oldIdAreaOfCargosPersonalListNewCargosPersonal.equals(areas)) {
                        oldIdAreaOfCargosPersonalListNewCargosPersonal.getCargosPersonalList().remove(cargosPersonalListNewCargosPersonal);
                        oldIdAreaOfCargosPersonalListNewCargosPersonal = em.merge(oldIdAreaOfCargosPersonalListNewCargosPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = areas.getId();
                if (findAreas(id) == null) {
                    throw new NonexistentEntityException("The areas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Areas areas;
            try {
                areas = em.getReference(Areas.class, id);
                areas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The areas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CargosPersonal> cargosPersonalListOrphanCheck = areas.getCargosPersonalList();
            for (CargosPersonal cargosPersonalListOrphanCheckCargosPersonal : cargosPersonalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Areas (" + areas + ") cannot be destroyed since the CargosPersonal " + cargosPersonalListOrphanCheckCargosPersonal + " in its cargosPersonalList field has a non-nullable idArea field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(areas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Areas> findAreasEntities() {
        return findAreasEntities(true, -1, -1);
    }

    public List<Areas> findAreasEntities(int maxResults, int firstResult) {
        return findAreasEntities(false, maxResults, firstResult);
    }

    private List<Areas> findAreasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Areas.class));
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

    public Areas findAreas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Areas.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Areas> rt = cq.from(Areas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
