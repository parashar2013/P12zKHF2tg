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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Parashar
 */
@Entity
@Table(name = "Visit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visit.findAll", query = "SELECT v FROM Visit v"),
    @NamedQuery(name = "Visit.findByDuration", query = "SELECT v FROM Visit v WHERE v.duration = :duration"),
    @NamedQuery(name = "Visit.findByHealthCard", query = "SELECT v FROM Visit v WHERE v.visitPK.healthCard = :healthCard"),
    @NamedQuery(name = "Visit.findByDoctorId", query = "SELECT v FROM Visit v WHERE v.doctorId = :doctorId"),
    @NamedQuery(name = "Visit.findByTreatment", query = "SELECT v FROM Visit v WHERE v.treatment = :treatment"),
    @NamedQuery(name = "Visit.findByDateAndTime", query = "SELECT v FROM Visit v WHERE v.visitPK.dateAndTime = :dateAndTime"),
    @NamedQuery(name = "Visit.findByLastModified", query = "SELECT v FROM Visit v WHERE v.lastModified = :lastModified")})
public class Visit implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VisitPK visitPK;
    @Basic(optional = false)
    @Column(name = "duration")
    private int duration;
    @Basic(optional = false)
    @Column(name = "doctor_id")
    private int doctorId;
    @Basic(optional = false)
    @Lob
    @Column(name = "diagnosis")
    private String diagnosis;
    @Basic(optional = false)
    @Lob
    @Column(name = "prescriptions")
    private String prescriptions;
    @Basic(optional = false)
    @Column(name = "treatment")
    @Temporal(TemporalType.TIMESTAMP)
    private Date treatment;
    @Basic(optional = false)
    @Lob
    @Column(name = "comments")
    private String comments;
    @Basic(optional = false)
    @Column(name = "last_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    public Visit() {
    }

    public Visit(VisitPK visitPK) {
        this.visitPK = visitPK;
    }

    public Visit(VisitPK visitPK, int duration, int doctorId, String diagnosis, String prescriptions, Date treatment, String comments, Date lastModified) {
        this.visitPK = visitPK;
        this.duration = duration;
        this.doctorId = doctorId;
        this.diagnosis = diagnosis;
        this.prescriptions = prescriptions;
        this.treatment = treatment;
        this.comments = comments;
        this.lastModified = lastModified;
    }

    public Visit(String healthCard, Date dateAndTime) {
        this.visitPK = new VisitPK(healthCard, dateAndTime);
    }

    public VisitPK getVisitPK() {
        return visitPK;
    }

    public void setVisitPK(VisitPK visitPK) {
        this.visitPK = visitPK;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(String prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Date getTreatment() {
        return treatment;
    }

    public void setTreatment(Date treatment) {
        this.treatment = treatment;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitPK != null ? visitPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visit)) {
            return false;
        }
        Visit other = (Visit) object;
        if ((this.visitPK == null && other.visitPK != null) || (this.visitPK != null && !this.visitPK.equals(other.visitPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Visit[ visitPK=" + visitPK + " ]";
    }
    
}
