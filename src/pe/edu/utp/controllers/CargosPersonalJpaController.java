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
import pe.edu.utp.entity.Area;
import pe.edu.utp.entity.Employe;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pe.edu.utp.controllers.exceptions.IllegalOrphanException;
import pe.edu.utp.controllers.exceptions.NonexistentEntityException;
import pe.edu.utp.entity.CargoPersonal;

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

    public void create(CargoPersonal cargosPersonal) {
        if (cargosPersonal.getEmployeesList() == null) {
            cargosPersonal.setEmployeesList(new ArrayList<Employe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Area idArea = cargosPersonal.getIdArea();
            if (idArea != null) {
                idArea = em.getReference(idArea.getClass(), idArea.getId());
                cargosPersonal.setIdArea(idArea);
            }
            List<Employe> attachedEmployeesList = new ArrayList<Employe>();
            for (Employe employeesListEmployeesToAttach : cargosPersonal.getEmployeesList()) {
                employeesListEmployeesToAttach = em.getReference(employeesListEmployeesToAttach.getClass(), employeesListEmployeesToAttach.getId());
                attachedEmployeesList.add(employeesListEmployeesToAttach);
            }
            cargosPersonal.setEmployeesList(attachedEmployeesList);
            em.persist(cargosPersonal);
            if (idArea != null) {
                idArea.getCargosPersonalList().add(cargosPersonal);
                idArea = em.merge(idArea);
            }
            for (Employe employeesListEmployees : cargosPersonal.getEmployeesList()) {
                CargoPersonal oldIdCargoOfEmployeesListEmployees = employeesListEmployees.getIdCargo();
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

    public void edit(CargoPersonal cargosPersonal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CargoPersonal persistentCargosPersonal = em.find(CargoPersonal.class, cargosPersonal.getId());
            Area idAreaOld = persistentCargosPersonal.getIdArea();
            Area idAreaNew = cargosPersonal.getIdArea();
            List<Employe> employeesListOld = persistentCargosPersonal.getEmployeesList();
            List<Employe> employeesListNew = cargosPersonal.getEmployeesList();
            List<String> illegalOrphanMessages = null;
            for (Employe employeesListOldEmployees : employeesListOld) {
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
            List<Employe> attachedEmployeesListNew = new ArrayList<Employe>();
            for (Employe employeesListNewEmployeesToAttach : employeesListNew) {
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
            for (Employe employeesListNewEmployees : employeesListNew) {
                if (!employeesListOld.contains(employeesListNewEmployees)) {
                    CargoPersonal oldIdCargoOfEmployeesListNewEmployees = employeesListNewEmployees.getIdCargo();
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
            CargoPersonal cargosPersonal;
            try {
                cargosPersonal = em.getReference(CargoPersonal.class, id);
                cargosPersonal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargosPersonal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Employe> employeesListOrphanCheck = cargosPersonal.getEmployeesList();
            for (Employe employeesListOrphanCheckEmployees : employeesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CargosPersonal (" + cargosPersonal + ") cannot be destroyed since the Employees " + employeesListOrphanCheckEmployees + " in its employeesList field has a non-nullable idCargo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Area idArea = cargosPersonal.getIdArea();
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

    public List<CargoPersonal> findCargosPersonalEntities() {
        return findCargosPersonalEntities(true, -1, -1);
    }

    public List<CargoPersonal> findCargosPersonalEntities(int maxResults, int firstResult) {
        return findCargosPersonalEntities(false, maxResults, firstResult);
    }

    private List<CargoPersonal> findCargosPersonalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CargoPersonal.class));
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

    public CargoPersonal findCargosPersonal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CargoPersonal.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargosPersonalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CargoPersonal> rt = cq.from(CargoPersonal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
