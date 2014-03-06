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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Parashar
 */
@Embeddable
public class VisitPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "health_card")
    private String healthCard;
    @Basic(optional = false)
    @Column(name = "date_and_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAndTime;

    public VisitPK() {
    }

    public VisitPK(String healthCard, Date dateAndTime) {
        this.healthCard = healthCard;
        this.dateAndTime = dateAndTime;
    }

    public String getHealthCard() {
        return healthCard;
    }

    public void setHealthCard(String healthCard) {
        this.healthCard = healthCard;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (healthCard != null ? healthCard.hashCode() : 0);
        hash += (dateAndTime != null ? dateAndTime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitPK)) {
            return false;
        }
        VisitPK other = (VisitPK) object;
        if ((this.healthCard == null && other.healthCard != null) || (this.healthCard != null && !this.healthCard.equals(other.healthCard))) {
            return false;
        }
        if ((this.dateAndTime == null && other.dateAndTime != null) || (this.dateAndTime != null && !this.dateAndTime.equals(other.dateAndTime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.VisitPK[ healthCard=" + healthCard + ", dateAndTime=" + dateAndTime + " ]";
    }
    
}
