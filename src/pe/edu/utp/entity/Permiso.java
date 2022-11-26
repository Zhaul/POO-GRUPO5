/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author zhaulvaldera
 */
@Entity
@Table(name = "permisos")
@NamedQueries({
    @NamedQuery(name = "Permisos.findAll", query = "SELECT p FROM Permisos p"),
    @NamedQuery(name = "Permisos.findById", query = "SELECT p FROM Permisos p WHERE p.id = :id"),
    @NamedQuery(name = "Permisos.findByName", query = "SELECT p FROM Permisos p WHERE p.name = :name"),
    @NamedQuery(name = "Permisos.findByPosition", query = "SELECT p FROM Permisos p WHERE p.position = :position"),
    @NamedQuery(name = "Permisos.findByStatus", query = "SELECT p FROM Permisos p WHERE p.status = :status")})
public class Permiso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "position")
    private Integer position;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Character status;
    @JoinTable(name = "permisoRol", joinColumns = {
        @JoinColumn(name = "idPermiso", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "idRol", referencedColumnName = "id")})
    @ManyToMany
    private List<Rol> rolesList;

    public Permiso() {
    }

    public Permiso(String id) {
        this.id = id;
    }

    public Permiso(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public List<Rol> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Rol> rolesList) {
        this.rolesList = rolesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permiso)) {
            return false;
        }
        Permiso other = (Permiso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.edu.utp.entity.Permisos[ id=" + id + " ]";
    }
    
}
