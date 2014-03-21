/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Parashar
 */
@Entity
@Table(name = "Patient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Patient.findAll", query = "SELECT p FROM Patient p"),
    @NamedQuery(name = "Patient.findByName", query = "SELECT p FROM Patient p WHERE p.name = :name"),
    @NamedQuery(name = "Patient.findByPhoneNumber", query = "SELECT p FROM Patient p WHERE p.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Patient.findByHealthCard", query = "SELECT p FROM Patient p WHERE p.healthCard = :healthCard"),
    @NamedQuery(name = "Patient.findBySinNumber", query = "SELECT p FROM Patient p WHERE p.sinNumber = :sinNumber"),
    @NamedQuery(name = "Patient.findByNumberOfVisits", query = "SELECT p FROM Patient p WHERE p.numberOfVisits = :numberOfVisits"),
    @NamedQuery(name = "Patient.findByDefaultDoctorId", query = "SELECT p FROM Patient p WHERE p.defaultDoctorId = :defaultDoctorId"),
    @NamedQuery(name = "Patient.findByPassword", query = "SELECT p FROM Patient p WHERE p.password = :password")})
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Lob
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Id
    @Basic(optional = false)
    @Column(name = "health_card")
    private String healthCard;
    @Basic(optional = false)
    @Column(name = "sin_number")
    private int sinNumber;
    @Basic(optional = false)
    @Column(name = "number_of_visits")
    private int numberOfVisits;
    @Basic(optional = false)
    @Column(name = "default_doctor_id")
    private int defaultDoctorId;
    @Basic(optional = false)
    @Lob
    @Column(name = "current_health")
    private String currentHealth;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name="default_doctor_id", referencedColumnName="id")
    private Employee defaultDoctor;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy="patient")
    private List<Appointment> appointments;

    public Patient() {
    }

    public Patient(String healthCard) {
        this.healthCard = healthCard;
    }

    public Patient(String healthCard, String name, String address, String phoneNumber, int sinNumber, int numberOfVisits, int defaultDoctorId, String currentHealth, String password) {
        this.healthCard = healthCard;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.sinNumber = sinNumber;
        this.numberOfVisits = numberOfVisits;
        this.defaultDoctorId = defaultDoctorId;
        this.currentHealth = currentHealth;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHealthCard() {
        return healthCard;
    }

    public void setHealthCard(String healthCard) {
        this.healthCard = healthCard;
    }

    public int getSinNumber() {
        return sinNumber;
    }

    public void setSinNumber(int sinNumber) {
        this.sinNumber = sinNumber;
    }

    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(int numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public int getDefaultDoctorId() {
        return defaultDoctorId;
    }

    public void setDefaultDoctorId(int defaultDoctorId) {
        this.defaultDoctorId = defaultDoctorId;
    }
    
    public String getDefaultDoctorName() {
        return defaultDoctor.getName();
    }

    public String getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(String currentHealth) {
        this.currentHealth = currentHealth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (healthCard != null ? healthCard.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patient)) {
            return false;
        }
        Patient other = (Patient) object;
        if ((this.healthCard == null && other.healthCard != null) || (this.healthCard != null && !this.healthCard.equals(other.healthCard))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Patient[ healthCard=" + healthCard + " ]";
    }
    
}
