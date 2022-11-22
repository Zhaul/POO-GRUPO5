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
import pe.edu.utp.entity.Areas;
import pe.edu.utp.entity.Employees;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pe.edu.utp.controllers.exceptions.IllegalOrphanException;
import pe.edu.utp.controllers.exceptions.NonexistentEntityException;
import pe.edu.utp.entity.CargosPersonal;

/**
 *
 * @author zhaulvaldera
 */
public class CargosPersonalJpaController implements Serializable {

    public CargosPersonalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public CargosPersonalJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProyectoFinalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CargosPersonal cargosPersonal) {
        if (cargosPersonal.getEmployeesList() == null) {
            cargosPersonal.setEmployeesList(new ArrayList<Employees>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Areas idArea = cargosPersonal.getIdArea();
            if (idArea != null) {
                idArea = em.getReference(idArea.getClass(), idArea.getId());
                cargosPersonal.setIdArea(idArea);
            }
            List<Employees> attachedEmployeesList = new ArrayList<Employees>();
            for (Employees employeesListEmployeesToAttach : cargosPersonal.getEmployeesList()) {
                employeesListEmployeesToAttach = em.getReference(employeesListEmployeesToAttach.getClass(), employeesListEmployeesToAttach.getId());
                attachedEmployeesList.add(employeesListEmployeesToAttach);
            }
            cargosPersonal.setEmployeesList(attachedEmployeesList);
            em.persist(cargosPersonal);
            if (idArea != null) {
                idArea.getCargosPersonalList().add(cargosPersonal);
                idArea = em.merge(idArea);
            }
            for (Employees employeesListEmployees : cargosPersonal.getEmployeesList()) {
                CargosPersonal oldIdCargoOfEmployeesListEmployees = employeesListEmployees.getIdCargo();
                employeesListEmployees.setIdCargo(cargosPersonal);
                employeesListEmployees = em.merge(employeesListEmployees);
                if (oldIdCargoOfEmployeesListEmployees != null) {
                    oldIdCargoOfEmployeesListEmployees.getEmployeesList().remove(employeesListEmployees);
                    oldIdCargoOfEmployeesListEmployees = em.merge(oldIdCargoOfEmployeesListEmployees);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CargosPersonal cargosPersonal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CargosPersonal persistentCargosPersonal = em.find(CargosPersonal.class, cargosPersonal.getId());
            Areas idAreaOld = persistentCargosPersonal.getIdArea();
            Areas idAreaNew = cargosPersonal.getIdArea();
            List<Employees> employeesListOld = persistentCargosPersonal.getEmployeesList();
            List<Employees> employeesListNew = cargosPersonal.getEmployeesList();
            List<String> illegalOrphanMessages = null;
            for (Employees employeesListOldEmployees : employeesListOld) {
                if (!employeesListNew.contains(employeesListOldEmployees)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employees " + employeesListOldEmployees + " since its idCargo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idAreaNew != null) {
                idAreaNew = em.getReference(idAreaNew.getClass(), idAreaNew.getId());
                cargosPersonal.setIdArea(idAreaNew);
            }
            List<Employees> attachedEmployeesListNew = new ArrayList<Employees>();
            for (Employees employeesListNewEmployeesToAttach : employeesListNew) {
                employeesListNewEmployeesToAttach = em.getReference(employeesListNewEmployeesToAttach.getClass(), employeesListNewEmployeesToAttach.getId());
                attachedEmployeesListNew.add(employeesListNewEmployeesToAttach);
            }
            employeesListNew = attachedEmployeesListNew;
            cargosPersonal.setEmployeesList(employeesListNew);
            cargosPersonal = em.merge(cargosPersonal);
            if (idAreaOld != null && !idAreaOld.equals(idAreaNew)) {
                idAreaOld.getCargosPersonalList().remove(cargosPersonal);
                idAreaOld = em.merge(idAreaOld);
            }
            if (idAreaNew != null && !idAreaNew.equals(idAreaOld)) {
                idAreaNew.getCargosPersonalList().add(cargosPersonal);
                idAreaNew = em.merge(idAreaNew);
            }
            for (Employees employeesListNewEmployees : employeesListNew) {
                if (!employeesListOld.contains(employeesListNewEmployees)) {
                    CargosPersonal oldIdCargoOfEmployeesListNewEmployees = employeesListNewEmployees.getIdCargo();
                    employeesListNewEmployees.setIdCargo(cargosPersonal);
                    employeesListNewEmployees = em.merge(employeesListNewEmployees);
                    if (oldIdCargoOfEmployeesListNewEmployees != null && !oldIdCargoOfEmployeesListNewEmployees.equals(cargosPersonal)) {
                        oldIdCargoOfEmployeesListNewEmployees.getEmployeesList().remove(employeesListNewEmployees);
                        oldIdCargoOfEmployeesListNewEmployees = em.merge(oldIdCargoOfEmployeesListNewEmployees);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cargosPersonal.getId();
                if (findCargosPersonal(id) == null) {
                    throw new NonexistentEntityException("The cargosPersonal with id " + id + " no longer exists.");
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
            CargosPersonal cargosPersonal;
            try {
                cargosPersonal = em.getReference(CargosPersonal.class, id);
                cargosPersonal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargosPersonal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Employees> employeesListOrphanCheck = cargosPersonal.getEmployeesList();
            for (Employees employeesListOrphanCheckEmployees : employeesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CargosPersonal (" + cargosPersonal + ") cannot be destroyed since the Employees " + employeesListOrphanCheckEmployees + " in its employeesList field has a non-nullable idCargo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Areas idArea = cargosPersonal.getIdArea();
            if (idArea != null) {
                idArea.getCargosPersonalList().remove(cargosPersonal);
                idArea = em.merge(idArea);
            }
            em.remove(cargosPersonal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CargosPersonal> findCargosPersonalEntities() {
        return findCargosPersonalEntities(true, -1, -1);
    }

    public List<CargosPersonal> findCargosPersonalEntities(int maxResults, int firstResult) {
        return findCargosPersonalEntities(false, maxResults, firstResult);
    }

    private List<CargosPersonal> findCargosPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CargosPersonal.class));
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

    public CargosPersonal findCargosPersonal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CargosPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargosPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CargosPersonal> rt = cq.from(CargosPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
