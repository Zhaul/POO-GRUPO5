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
import pe.edu.utp.entity.Employe;
import pe.edu.utp.entity.Justification;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pe.edu.utp.controllers.exceptions.IllegalOrphanException;
import pe.edu.utp.controllers.exceptions.NonexistentEntityException;
import pe.edu.utp.entity.Schedule;

/**
 *
 * @author zhaulvaldera
 */
public class SchedulesJpaController implements Serializable {

    public SchedulesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public SchedulesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProyectoFinalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Schedule schedules) {
        if (schedules.getJustificationsList() == null) {
            schedules.setJustificationsList(new ArrayList<Justification>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employe idEmploye = schedules.getIdEmploye();
            if (idEmploye != null) {
                idEmploye = em.getReference(idEmploye.getClass(), idEmploye.getId());
                schedules.setIdEmploye(idEmploye);
            }
            List<Justification> attachedJustificationsList = new ArrayList<Justification>();
            for (Justification justificationsListJustificationsToAttach : schedules.getJustificationsList()) {
                justificationsListJustificationsToAttach = em.getReference(justificationsListJustificationsToAttach.getClass(), justificationsListJustificationsToAttach.getId());
                attachedJustificationsList.add(justificationsListJustificationsToAttach);
            }
            schedules.setJustificationsList(attachedJustificationsList);
            em.persist(schedules);
            if (idEmploye != null) {
                idEmploye.getSchedulesList().add(schedules);
                idEmploye = em.merge(idEmploye);
            }
            for (Justification justificationsListJustifications : schedules.getJustificationsList()) {
                Schedule oldIdSheduleOfJustificationsListJustifications = justificationsListJustifications.getIdShedule();
                justificationsListJustifications.setIdShedule(schedules);
                justificationsListJustifications = em.merge(justificationsListJustifications);
                if (oldIdSheduleOfJustificationsListJustifications != null) {
                    oldIdSheduleOfJustificationsListJustifications.getJustificationsList().remove(justificationsListJustifications);
                    oldIdSheduleOfJustificationsListJustifications = em.merge(oldIdSheduleOfJustificationsListJustifications);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Schedule schedules) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Schedule persistentSchedules = em.find(Schedule.class, schedules.getId());
            Employe idEmployeOld = persistentSchedules.getIdEmploye();
            Employe idEmployeNew = schedules.getIdEmploye();
            List<Justification> justificationsListOld = persistentSchedules.getJustificationsList();
            List<Justification> justificationsListNew = schedules.getJustificationsList();
            List<String> illegalOrphanMessages = null;
            for (Justification justificationsListOldJustifications : justificationsListOld) {
                if (!justificationsListNew.contains(justificationsListOldJustifications)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Justifications " + justificationsListOldJustifications + " since its idShedule field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idEmployeNew != null) {
                idEmployeNew = em.getReference(idEmployeNew.getClass(), idEmployeNew.getId());
                schedules.setIdEmploye(idEmployeNew);
            }
            List<Justification> attachedJustificationsListNew = new ArrayList<Justification>();
            for (Justification justificationsListNewJustificationsToAttach : justificationsListNew) {
                justificationsListNewJustificationsToAttach = em.getReference(justificationsListNewJustificationsToAttach.getClass(), justificationsListNewJustificationsToAttach.getId());
                attachedJustificationsListNew.add(justificationsListNewJustificationsToAttach);
            }
            justificationsListNew = attachedJustificationsListNew;
            schedules.setJustificationsList(justificationsListNew);
            schedules = em.merge(schedules);
            if (idEmployeOld != null && !idEmployeOld.equals(idEmployeNew)) {
                idEmployeOld.getSchedulesList().remove(schedules);
                idEmployeOld = em.merge(idEmployeOld);
            }
            if (idEmployeNew != null && !idEmployeNew.equals(idEmployeOld)) {
                idEmployeNew.getSchedulesList().add(schedules);
                idEmployeNew = em.merge(idEmployeNew);
            }
            for (Justification justificationsListNewJustifications : justificationsListNew) {
                if (!justificationsListOld.contains(justificationsListNewJustifications)) {
                    Schedule oldIdSheduleOfJustificationsListNewJustifications = justificationsListNewJustifications.getIdShedule();
                    justificationsListNewJustifications.setIdShedule(schedules);
                    justificationsListNewJustifications = em.merge(justificationsListNewJustifications);
                    if (oldIdSheduleOfJustificationsListNewJustifications != null && !oldIdSheduleOfJustificationsListNewJustifications.equals(schedules)) {
                        oldIdSheduleOfJustificationsListNewJustifications.getJustificationsList().remove(justificationsListNewJustifications);
                        oldIdSheduleOfJustificationsListNewJustifications = em.merge(oldIdSheduleOfJustificationsListNewJustifications);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = schedules.getId();
                if (findSchedules(id) == null) {
                    throw new NonexistentEntityException("The schedules with id " + id + " no longer exists.");
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
            Schedule schedules;
            try {
                schedules = em.getReference(Schedule.class, id);
                schedules.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The schedules with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Justification> justificationsListOrphanCheck = schedules.getJustificationsList();
            for (Justification justificationsListOrphanCheckJustifications : justificationsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Schedules (" + schedules + ") cannot be destroyed since the Justifications " + justificationsListOrphanCheckJustifications + " in its justificationsList field has a non-nullable idShedule field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Employe idEmploye = schedules.getIdEmploye();
            if (idEmploye != null) {
                idEmploye.getSchedulesList().remove(schedules);
                idEmploye = em.merge(idEmploye);
            }
            em.remove(schedules);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Schedule> findSchedulesEntities() {
        return findSchedulesEntities(true, -1, -1);
    }

    public List<Schedule> findSchedulesEntities(int maxResults, int firstResult) {
        return findSchedulesEntities(false, maxResults, firstResult);
    }

    private List<Schedule> findSchedulesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Schedule.class));
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

    public Schedule findSchedules(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Schedule.class, id);
        } finally {
            em.close();
        }
    }

    public int getSchedulesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Schedule> rt = cq.from(Schedule.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
