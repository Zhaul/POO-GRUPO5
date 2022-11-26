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
import pe.edu.utp.entity.Permiso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pe.edu.utp.controllers.exceptions.IllegalOrphanException;
import pe.edu.utp.controllers.exceptions.NonexistentEntityException;
import pe.edu.utp.entity.Employe;
import pe.edu.utp.entity.Rol;

/**
 *
 * @author zhaulvaldera
 */
public class RolesJpaController implements Serializable {

    public RolesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public RolesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProyectoFinalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol roles) {
        if (roles.getPermisosList() == null) {
            roles.setPermisosList(new ArrayList<Permiso>());
        }
        if (roles.getEmployeesList() == null) {
            roles.setEmployeesList(new ArrayList<Employe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Permiso> attachedPermisosList = new ArrayList<Permiso>();
            for (Permiso permisosListPermisosToAttach : roles.getPermisosList()) {
                permisosListPermisosToAttach = em.getReference(permisosListPermisosToAttach.getClass(), permisosListPermisosToAttach.getId());
                attachedPermisosList.add(permisosListPermisosToAttach);
            }
            roles.setPermisosList(attachedPermisosList);
            List<Employe> attachedEmployeesList = new ArrayList<Employe>();
            for (Employe employeesListEmployeesToAttach : roles.getEmployeesList()) {
                employeesListEmployeesToAttach = em.getReference(employeesListEmployeesToAttach.getClass(), employeesListEmployeesToAttach.getId());
                attachedEmployeesList.add(employeesListEmployeesToAttach);
            }
            roles.setEmployeesList(attachedEmployeesList);
            em.persist(roles);
            for (Permiso permisosListPermisos : roles.getPermisosList()) {
                permisosListPermisos.getRolesList().add(roles);
                permisosListPermisos = em.merge(permisosListPermisos);
            }
            for (Employe employeesListEmployees : roles.getEmployeesList()) {
                Rol oldIdRolOfEmployeesListEmployees = employeesListEmployees.getIdRol();
                employeesListEmployees.setIdRol(roles);
                employeesListEmployees = em.merge(employeesListEmployees);
                if (oldIdRolOfEmployeesListEmployees != null) {
                    oldIdRolOfEmployeesListEmployees.getEmployeesList().remove(employeesListEmployees);
                    oldIdRolOfEmployeesListEmployees = em.merge(oldIdRolOfEmployeesListEmployees);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol roles) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRoles = em.find(Rol.class, roles.getId());
            List<Permiso> permisosListOld = persistentRoles.getPermisosList();
            List<Permiso> permisosListNew = roles.getPermisosList();
            List<Employe> employeesListOld = persistentRoles.getEmployeesList();
            List<Employe> employeesListNew = roles.getEmployeesList();
            List<String> illegalOrphanMessages = null;
            for (Employe employeesListOldEmployees : employeesListOld) {
                if (!employeesListNew.contains(employeesListOldEmployees)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Employees " + employeesListOldEmployees + " since its idRol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Permiso> attachedPermisosListNew = new ArrayList<Permiso>();
            for (Permiso permisosListNewPermisosToAttach : permisosListNew) {
                permisosListNewPermisosToAttach = em.getReference(permisosListNewPermisosToAttach.getClass(), permisosListNewPermisosToAttach.getId());
                attachedPermisosListNew.add(permisosListNewPermisosToAttach);
            }
            permisosListNew = attachedPermisosListNew;
            roles.setPermisosList(permisosListNew);
            List<Employe> attachedEmployeesListNew = new ArrayList<Employe>();
            for (Employe employeesListNewEmployeesToAttach : employeesListNew) {
                employeesListNewEmployeesToAttach = em.getReference(employeesListNewEmployeesToAttach.getClass(), employeesListNewEmployeesToAttach.getId());
                attachedEmployeesListNew.add(employeesListNewEmployeesToAttach);
            }
            employeesListNew = attachedEmployeesListNew;
            roles.setEmployeesList(employeesListNew);
            roles = em.merge(roles);
            for (Permiso permisosListOldPermisos : permisosListOld) {
                if (!permisosListNew.contains(permisosListOldPermisos)) {
                    permisosListOldPermisos.getRolesList().remove(roles);
                    permisosListOldPermisos = em.merge(permisosListOldPermisos);
                }
            }
            for (Permiso permisosListNewPermisos : permisosListNew) {
                if (!permisosListOld.contains(permisosListNewPermisos)) {
                    permisosListNewPermisos.getRolesList().add(roles);
                    permisosListNewPermisos = em.merge(permisosListNewPermisos);
                }
            }
            for (Employe employeesListNewEmployees : employeesListNew) {
                if (!employeesListOld.contains(employeesListNewEmployees)) {
                    Rol oldIdRolOfEmployeesListNewEmployees = employeesListNewEmployees.getIdRol();
                    employeesListNewEmployees.setIdRol(roles);
                    employeesListNewEmployees = em.merge(employeesListNewEmployees);
                    if (oldIdRolOfEmployeesListNewEmployees != null && !oldIdRolOfEmployeesListNewEmployees.equals(roles)) {
                        oldIdRolOfEmployeesListNewEmployees.getEmployeesList().remove(employeesListNewEmployees);
                        oldIdRolOfEmployeesListNewEmployees = em.merge(oldIdRolOfEmployeesListNewEmployees);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = roles.getId();
                if (findRoles(id) == null) {
                    throw new NonexistentEntityException("The roles with id " + id + " no longer exists.");
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
            Rol roles;
            try {
                roles = em.getReference(Rol.class, id);
                roles.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The roles with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Employe> employeesListOrphanCheck = roles.getEmployeesList();
            for (Employe employeesListOrphanCheckEmployees : employeesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Roles (" + roles + ") cannot be destroyed since the Employees " + employeesListOrphanCheckEmployees + " in its employeesList field has a non-nullable idRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Permiso> permisosList = roles.getPermisosList();
            for (Permiso permisosListPermisos : permisosList) {
                permisosListPermisos.getRolesList().remove(roles);
                permisosListPermisos = em.merge(permisosListPermisos);
            }
            em.remove(roles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolesEntities() {
        return findRolesEntities(true, -1, -1);
    }

    public List<Rol> findRolesEntities(int maxResults, int firstResult) {
        return findRolesEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
