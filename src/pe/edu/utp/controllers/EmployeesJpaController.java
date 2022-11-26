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
import pe.edu.utp.entity.CargoPersonal;
import pe.edu.utp.entity.Rol;
import pe.edu.utp.entity.Schedule;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pe.edu.utp.controllers.exceptions.IllegalOrphanException;
import pe.edu.utp.controllers.exceptions.NonexistentEntityException;
import pe.edu.utp.entity.Employe;

/**
 *
 * @author zhaulvaldera
 */
public class EmployeesJpaController implements Serializable {

    public EmployeesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public EmployeesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProyectoFinalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employe employees) {
        if (employees.getSchedulesList() == null) {
            employees.setSchedulesList(new ArrayList<Schedule>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CargoPersonal idCargo = employees.getIdCargo();
            if (idCargo != null) {
                idCargo = em.getReference(idCargo.getClass(), idCargo.getId());
                employees.setIdCargo(idCargo);
            }
            Rol idRol = employees.getIdRol();
            if (idRol != null) {
                idRol = em.getReference(idRol.getClass(), idRol.getId());
                employees.setIdRol(idRol);
            }
            List<Schedule> attachedSchedulesList = new ArrayList<Schedule>();
            for (Schedule schedulesListSchedulesToAttach : employees.getSchedulesList()) {
                schedulesListSchedulesToAttach = em.getReference(schedulesListSchedulesToAttach.getClass(), schedulesListSchedulesToAttach.getId());
                attachedSchedulesList.add(schedulesListSchedulesToAttach);
            }
            employees.setSchedulesList(attachedSchedulesList);
            em.persist(employees);
            if (idCargo != null) {
                idCargo.getEmployeesList().add(employees);
                idCargo = em.merge(idCargo);
            }
            if (idRol != null) {
                idRol.getEmployeesList().add(employees);
                idRol = em.merge(idRol);
            }
            for (Schedule schedulesListSchedules : employees.getSchedulesList()) {
                Employe oldIdEmployeOfSchedulesListSchedules = schedulesListSchedules.getIdEmploye();
                schedulesListSchedules.setIdEmploye(employees);
                schedulesListSchedules = em.merge(schedulesListSchedules);
                if (oldIdEmployeOfSchedulesListSchedules != null) {
                    oldIdEmployeOfSchedulesListSchedules.getSchedulesList().remove(schedulesListSchedules);
                    oldIdEmployeOfSchedulesListSchedules = em.merge(oldIdEmployeOfSchedulesListSchedules);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employe employees) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employe persistentEmployees = em.find(Employe.class, employees.getId());
            CargoPersonal idCargoOld = persistentEmployees.getIdCargo();
            CargoPersonal idCargoNew = employees.getIdCargo();
            Rol idRolOld = persistentEmployees.getIdRol();
            Rol idRolNew = employees.getIdRol();
            List<Schedule> schedulesListOld = persistentEmployees.getSchedulesList();
            List<Schedule> schedulesListNew = employees.getSchedulesList();
            List<String> illegalOrphanMessages = null;
            for (Schedule schedulesListOldSchedules : schedulesListOld) {
                if (!schedulesListNew.contains(schedulesListOldSchedules)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Schedules " + schedulesListOldSchedules + " since its idEmploye field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCargoNew != null) {
                idCargoNew = em.getReference(idCargoNew.getClass(), idCargoNew.getId());
                employees.setIdCargo(idCargoNew);
            }
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getId());
                employees.setIdRol(idRolNew);
            }
            List<Schedule> attachedSchedulesListNew = new ArrayList<Schedule>();
            for (Schedule schedulesListNewSchedulesToAttach : schedulesListNew) {
                schedulesListNewSchedulesToAttach = em.getReference(schedulesListNewSchedulesToAttach.getClass(), schedulesListNewSchedulesToAttach.getId());
                attachedSchedulesListNew.add(schedulesListNewSchedulesToAttach);
            }
            schedulesListNew = attachedSchedulesListNew;
            employees.setSchedulesList(schedulesListNew);
            employees = em.merge(employees);
            if (idCargoOld != null && !idCargoOld.equals(idCargoNew)) {
                idCargoOld.getEmployeesList().remove(employees);
                idCargoOld = em.merge(idCargoOld);
            }
            if (idCargoNew != null && !idCargoNew.equals(idCargoOld)) {
                idCargoNew.getEmployeesList().add(employees);
                idCargoNew = em.merge(idCargoNew);
            }
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getEmployeesList().remove(employees);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getEmployeesList().add(employees);
                idRolNew = em.merge(idRolNew);
            }
            for (Schedule schedulesListNewSchedules : schedulesListNew) {
                if (!schedulesListOld.contains(schedulesListNewSchedules)) {
                    Employe oldIdEmployeOfSchedulesListNewSchedules = schedulesListNewSchedules.getIdEmploye();
                    schedulesListNewSchedules.setIdEmploye(employees);
                    schedulesListNewSchedules = em.merge(schedulesListNewSchedules);
                    if (oldIdEmployeOfSchedulesListNewSchedules != null && !oldIdEmployeOfSchedulesListNewSchedules.equals(employees)) {
                        oldIdEmployeOfSchedulesListNewSchedules.getSchedulesList().remove(schedulesListNewSchedules);
                        oldIdEmployeOfSchedulesListNewSchedules = em.merge(oldIdEmployeOfSchedulesListNewSchedules);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = employees.getId();
                if (findEmployees(id) == null) {
                    throw new NonexistentEntityException("The employees with id " + id + " no longer exists.");
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
            Employe employees;
            try {
                employees = em.getReference(Employe.class, id);
                employees.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employees with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Schedule> schedulesListOrphanCheck = employees.getSchedulesList();
            for (Schedule schedulesListOrphanCheckSchedules : schedulesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Employees (" + employees + ") cannot be destroyed since the Schedules " + schedulesListOrphanCheckSchedules + " in its schedulesList field has a non-nullable idEmploye field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CargoPersonal idCargo = employees.getIdCargo();
            if (idCargo != null) {
                idCargo.getEmployeesList().remove(employees);
                idCargo = em.merge(idCargo);
            }
            Rol idRol = employees.getIdRol();
            if (idRol != null) {
                idRol.getEmployeesList().remove(employees);
                idRol = em.merge(idRol);
            }
            em.remove(employees);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employe> findEmployeesEntities() {
        return findEmployeesEntities(true, -1, -1);
    }

    public List<Employe> findEmployeesEntities(int maxResults, int firstResult) {
        return findEmployeesEntities(false, maxResults, firstResult);
    }

    private List<Employe> findEmployeesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employe.class));
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

    public Employe findEmployees(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employe.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmployeesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employe> rt = cq.from(Employe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
