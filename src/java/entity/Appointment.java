/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Parashar
 */
@Entity
@Table(name = "Appointment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appointment.findAll", query = "SELECT a FROM Appointment a"),
    @NamedQuery(name = "Appointment.findByHealthCard", query = "SELECT a FROM Appointment a WHERE a.appointmentPK.healthCard = :healthCard"),
    @NamedQuery(name = "Appointment.findByDoctorId", query = "SELECT a FROM Appointment a WHERE a.doctorId = :doctorId"),
    @NamedQuery(name = "Appointment.findByDateAndTime", query = "SELECT a FROM Appointment a WHERE a.appointmentPK.dateAndTime = :dateAndTime")})
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AppointmentPK appointmentPK;
    @Basic(optional = false)
    @Column(name = "doctor_id")
    private int doctorId;

    public Appointment() {
    }

    public Appointment(AppointmentPK appointmentPK) {
        this.appointmentPK = appointmentPK;
    }

    public Appointment(AppointmentPK appointmentPK, int doctorId) {
        this.appointmentPK = appointmentPK;
        this.doctorId = doctorId;
    }

    public Appointment(String healthCard, Date dateAndTime) {
        this.appointmentPK = new AppointmentPK(healthCard, dateAndTime);
    }

    public AppointmentPK getAppointmentPK() {
        return appointmentPK;
    }

    public void setAppointmentPK(AppointmentPK appointmentPK) {
        this.appointmentPK = appointmentPK;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appointmentPK != null ? appointmentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.appointmentPK == null && other.appointmentPK != null) || (this.appointmentPK != null && !this.appointmentPK.equals(other.appointmentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Appointment[ appointmentPK=" + appointmentPK + " ]";
    }
    
}
