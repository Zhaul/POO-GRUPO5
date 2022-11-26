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
import pe.edu.utp.entity.Rol;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import pe.edu.utp.controllers.exceptions.NonexistentEntityException;
import pe.edu.utp.controllers.exceptions.PreexistingEntityException;
import pe.edu.utp.entity.Permiso;

/**
 *
 * @author zhaulvaldera
 */
public class PermisosJpaController implements Serializable {

    public PermisosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public PermisosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProyectoFinalPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permiso permisos) throws PreexistingEntityException, Exception {
        if (permisos.getRolesList() == null) {
            permisos.setRolesList(new ArrayList<Rol>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Rol> attachedRolesList = new ArrayList<Rol>();
            for (Rol rolesListRolesToAttach : permisos.getRolesList()) {
                rolesListRolesToAttach = em.getReference(rolesListRolesToAttach.getClass(), rolesListRolesToAttach.getId());
                attachedRolesList.add(rolesListRolesToAttach);
            }
            permisos.setRolesList(attachedRolesList);
            em.persist(permisos);
            for (Rol rolesListRoles : permisos.getRolesList()) {
                rolesListRoles.getPermisosList().add(permisos);
                rolesListRoles = em.merge(rolesListRoles);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPermisos(permisos.getId()) != null) {
                throw new PreexistingEntityException("Permisos " + permisos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permiso permisos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permiso persistentPermisos = em.find(Permiso.class, permisos.getId());
            List<Rol> rolesListOld = persistentPermisos.getRolesList();
            List<Rol> rolesListNew = permisos.getRolesList();
            List<Rol> attachedRolesListNew = new ArrayList<Rol>();
            for (Rol rolesListNewRolesToAttach : rolesListNew) {
                rolesListNewRolesToAttach = em.getReference(rolesListNewRolesToAttach.getClass(), rolesListNewRolesToAttach.getId());
                attachedRolesListNew.add(rolesListNewRolesToAttach);
            }
            rolesListNew = attachedRolesListNew;
            permisos.setRolesList(rolesListNew);
            permisos = em.merge(permisos);
            for (Rol rolesListOldRoles : rolesListOld) {
                if (!rolesListNew.contains(rolesListOldRoles)) {
                    rolesListOldRoles.getPermisosList().remove(permisos);
                    rolesListOldRoles = em.merge(rolesListOldRoles);
                }
            }
            for (Rol rolesListNewRoles : rolesListNew) {
                if (!rolesListOld.contains(rolesListNewRoles)) {
                    rolesListNewRoles.getPermisosList().add(permisos);
                    rolesListNewRoles = em.merge(rolesListNewRoles);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = permisos.getId();
                if (findPermisos(id) == null) {
                    throw new NonexistentEntityException("The permisos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permiso permisos;
            try {
                permisos = em.getReference(Permiso.class, id);
                permisos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permisos with id " + id + " no longer exists.", enfe);
            }
            List<Rol> rolesList = permisos.getRolesList();
            for (Rol rolesListRoles : rolesList) {
                rolesListRoles.getPermisosList().remove(permisos);
                rolesListRoles = em.merge(rolesListRoles);
            }
            em.remove(permisos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Permiso> findPermisosEntities() {
        return findPermisosEntities(true, -1, -1);
    }

    public List<Permiso> findPermisosEntities(int maxResults, int firstResult) {
        return findPermisosEntities(false, maxResults, firstResult);
    }

    private List<Permiso> findPermisosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permiso.class));
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

    public Permiso findPermisos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permiso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permiso> rt = cq.from(Permiso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
