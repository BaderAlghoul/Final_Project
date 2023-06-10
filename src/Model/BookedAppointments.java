/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bader M
 */
@Entity
@Table(name = "booked_appointments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BookedAppointments.findAll", query = "SELECT b FROM BookedAppointments b JOIN FETCH b.appointmentId"),
    @NamedQuery(name = "BookedAppointments.findAllPatient", query = "SELECT b FROM BookedAppointments b"),
    @NamedQuery(name = "BookedAppointments.findById", query = "SELECT b FROM BookedAppointments b WHERE b.id = :id"),
    @NamedQuery(name = "BookedAppointments.findByBookedStatus", query = "SELECT b FROM BookedAppointments b WHERE b.bookedStatus = :bookedStatus"),
    @NamedQuery(name = "BookedAppointments.findByDoctorCommit", query = "SELECT b FROM BookedAppointments b WHERE b.doctorCommit = :doctorCommit")})
public class BookedAppointments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "bookedStatus")
    private String bookedStatus;
    @Column(name = "doctorCommit")
    private String doctorCommit;
    @JoinColumn(name = "appointmentId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Appointments appointmentId;
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public BookedAppointments() {
    }

    public BookedAppointments(Integer id) {
        this.id = id;
    }

    public BookedAppointments( String bookedStatus, String doctorCommit, Appointments appointmentId) {
        
        this.bookedStatus = bookedStatus;
        this.doctorCommit = doctorCommit;
        this.appointmentId = appointmentId;
        
    }

    public BookedAppointments(String bookedStatus, String doctorCommit) {
        this.bookedStatus = bookedStatus;
        this.doctorCommit = doctorCommit;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookedStatus() {
        return bookedStatus;
    }

    public void setBookedStatus(String bookedStatus) {
        this.bookedStatus = bookedStatus;
    }

    public String getDoctorCommit() {
        return doctorCommit;
    }

    public void setDoctorCommit(String doctorCommit) {
        this.doctorCommit = doctorCommit;
    }

    public Appointments getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Appointments appointmentId) {
        this.appointmentId = appointmentId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof BookedAppointments)) {
            return false;
        }
        BookedAppointments other = (BookedAppointments) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Model.BookedAppointments[ id=" + id + " ]";
    }
    
}
