/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.utp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author zhaulvaldera
 */
@Entity
@Table(name = "schedules")
@NamedQueries({
    @NamedQuery(name = "Schedules.findAll", query = "SELECT s FROM Schedules s"),
    @NamedQuery(name = "Schedules.findById", query = "SELECT s FROM Schedules s WHERE s.id = :id"),
    @NamedQuery(name = "Schedules.findByHourStart", query = "SELECT s FROM Schedules s WHERE s.hourStart = :hourStart"),
    @NamedQuery(name = "Schedules.findByDateStart", query = "SELECT s FROM Schedules s WHERE s.dateStart = :dateStart"),
    @NamedQuery(name = "Schedules.findByHourEntry", query = "SELECT s FROM Schedules s WHERE s.hourEntry = :hourEntry"),
    @NamedQuery(name = "Schedules.findByStatus", query = "SELECT s FROM Schedules s WHERE s.status = :status")})
public class Schedules implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "hourStart")
    @Temporal(TemporalType.TIME)
    private Date hourStart;
    @Column(name = "dateStart")
    @Temporal(TemporalType.DATE)
    private Date dateStart;
    @Column(name = "hourEntry")
    @Temporal(TemporalType.TIME)
    private Date hourEntry;
    @Column(name = "status")
    private Character status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idShedule")
    private List<Justifications> justificationsList;
    @JoinColumn(name = "idEmploye", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Employees idEmploye;

    public Schedules() {
    }

    public Schedules(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getHourStart() {
        return hourStart;
    }

    public void setHourStart(Date hourStart) {
        this.hourStart = hourStart;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getHourEntry() {
        return hourEntry;
    }

    public void setHourEntry(Date hourEntry) {
        this.hourEntry = hourEntry;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public List<Justifications> getJustificationsList() {
        return justificationsList;
    }

    public void setJustificationsList(List<Justifications> justificationsList) {
        this.justificationsList = justificationsList;
    }

    public Employees getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Employees idEmploye) {
        this.idEmploye = idEmploye;
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
        if (!(object instanceof Schedules)) {
            return false;
        }
        Schedules other = (Schedules) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.edu.utp.entity.Schedules[ id=" + id + " ]";
    }
    
}
