/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Bader M
 */
@Entity
@Table(name = "appointments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appointments.findAll", query = "SELECT a FROM Appointments a"),
    @NamedQuery(name = "Appointments.findById", query = "SELECT a FROM Appointments a WHERE a.id = :id"),
    @NamedQuery(name = "Appointments.findByDay", query = "SELECT a FROM Appointments a WHERE a.day = :day"),
    @NamedQuery(name = "Appointments.findByDate", query = "SELECT a FROM Appointments a WHERE a.date = :date"),
    @NamedQuery(name = "Appointments.findByDay", query = "SELECT a FROM Appointments a WHERE a.day = :day"),
    @NamedQuery(name = "Appointments.findByTime", query = "SELECT a FROM Appointments a WHERE a.time = :time"),
    @NamedQuery(name = "Appointments.findByAppoStatus", query = "SELECT a FROM Appointments a WHERE a.appoStatus = :appoStatus")})
public class Appointments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "date")
    private LocalDate date;
    @Basic(optional = false)
    @Column(name = "day")
    private String day;
    @Basic(optional = false)
    @Column(name = "time")
    private LocalTime time;
    @Basic(optional = false)
    @Column(name = "appoStatus")
    private String appoStatus;
    
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appointmentId")
    private Collection<BookedAppointments> bookedAppointmentsCollection;

    public Appointments() {
    }

    public Appointments(Integer id) {
        this.id = id;
    }

    public Appointments(Integer id, LocalDate date, String day, LocalTime time, String appoStatus) {
        this.id = id;
        this.date = date;
        this.day = day;
        this.time = time;
        this.appoStatus = appoStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getAppoStatus() {
        return appoStatus;
    }

    public void setAppoStatus(String appoStatus) {
        this.appoStatus = appoStatus;
    }

    @XmlTransient
    public Collection<BookedAppointments> getBookedAppointmentsCollection() {
        return bookedAppointmentsCollection;
    }

    public void setBookedAppointmentsCollection(Collection<BookedAppointments> bookedAppointmentsCollection) {
        this.bookedAppointmentsCollection = bookedAppointmentsCollection;
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
        if (!(object instanceof Appointments)) {
            return false;
        }
        Appointments other = (Appointments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.Appointments[ id=" + id + " ]";
    }
    
}
