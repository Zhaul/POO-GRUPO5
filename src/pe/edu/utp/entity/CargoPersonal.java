/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author zhaulvaldera
 */
@Entity
@Table(name = "cargosPersonal")
@NamedQueries({
    @NamedQuery(name = "CargosPersonal.findAll", query = "SELECT c FROM CargosPersonal c"),
    @NamedQuery(name = "CargosPersonal.findById", query = "SELECT c FROM CargosPersonal c WHERE c.id = :id"),
    @NamedQuery(name = "CargosPersonal.findByName", query = "SELECT c FROM CargosPersonal c WHERE c.name = :name"),
    @NamedQuery(name = "CargosPersonal.findByStatus", query = "SELECT c FROM CargosPersonal c WHERE c.status = :status")})
public class CargoPersonal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Character status;
    @JoinColumn(name = "idArea", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Area idArea;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCargo")
    private List<Employe> employeesList;

    public CargoPersonal() {
    }

    public CargoPersonal(Integer id) {
        this.id = id;
    }

    public CargoPersonal(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    public List<Employe> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employe> employeesList) {
        this.employeesList = employeesList;
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
        if (!(object instanceof CargoPersonal)) {
            return false;
        }
        CargoPersonal other = (CargoPersonal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.edu.utp.entity.CargosPersonal[ id=" + id + " ]";
    }
    
}
