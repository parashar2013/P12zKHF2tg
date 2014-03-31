/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lib.DB;
import lib.utilities;

/**
 *
 * @author Parashar
 */
public class Visit {
    private String healthCard;
    private Date dateAndTime;
    private int duration;
    private int doctorId;
    private String diagnosis;
    private String prescriptions;
    private Date treatment;
    private String comments;
    private Date lastModified;
    
    public Visit(String health_card,Date date_and_time,int duration, int doctor_id, String diagnosis, String prescriptions,Date treatment,
            String comments,Date last_modified)
    {
        this.healthCard = health_card;
        this.dateAndTime = date_and_time;
        this.duration = duration;
        this.doctorId = doctor_id;
        this.diagnosis = diagnosis;
        this.prescriptions = prescriptions;
        this.treatment = treatment;
        this.comments = comments;
        this.lastModified = last_modified;
    }
    public static List<Map<String, Object>> getVisitByHealthCard(String healthCard) {
        List<Map<String, Object>> visits = new ArrayList<>();
        
        Connection con = DB.getConnection();
        
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT e.name, v.diagnosis, v.prescriptions, v.date_and_time "
                    + "FROM Visit v JOIN Employee e "
                    + "ON v.doctor_id = e.id WHERE v.health_card = ?");
            stmt.setString(1, healthCard);
            
            ResultSet result = stmt.executeQuery();
            visits = utilities.buildListFromResult(result);
            
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return visits;
    }
    
    public static void insertVisit(String duration, String hCard, String doctorId, String diagnosis,
            String prescriptions, String treatment, String comments, String dateAndTime) {
        
        Connection con = DB.getConnection();
        
        try {
            con.setAutoCommit(false);   // Use transaction
            
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Visit VALUES (?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, parseInt(duration));
            stmt.setString(2, hCard);
            stmt.setInt(3, parseInt(doctorId));
            stmt.setString(4, diagnosis);
            stmt.setString(5, prescriptions);
            stmt.setTimestamp(6, new Timestamp(new Date(treatment).getTime()));
            stmt.setString(7, comments);
            stmt.setString(8, dateAndTime);
            stmt.setTimestamp(9, new Timestamp(new Date().getTime()));
            
            stmt.executeUpdate();
            
            // Increment visit count for patient
            PreparedStatement stmt2 = con.prepareStatement("UPDATE Patient "
                    + "SET number_of_visits = number_of_visits + 1 WHERE health_card = ?");
            stmt2.setString(1, hCard);
            
            stmt2.executeUpdate();
            
            con.commit();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
            
            if (con != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch(SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            try {
                con.close();
            }
            catch(SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    public static List<Map<String, Object>> searchVisits(String name, String diagnosis, String prescriptions,
                String comments, String date1, String date2, String date3, String date4, Integer doctorId) {
        List<Map<String, Object>> visits = new ArrayList<>();
        
        Connection con = DB.getConnection();
        
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT p.name, v.diagnosis, v.prescriptions, v.comments, v.treatment, v.date_and_time FROM "
                + "Visit v LEFT JOIN Patient p ON v.health_card = p.health_card "
                + "WHERE p.name LIKE ? AND "
                + "v.diagnosis LIKE ? AND "
                + "v.prescriptions LIKE ? AND "
                + "v.comments LIKE ? AND "
                + "v.treatment BETWEEN ? AND ? AND "
                + "v.date_and_time BETWEEN ? AND ? AND "
                + "v.doctor_id = ?");
            stmt.setString(1, "%"+name+"%");
            stmt.setString(2, "%"+diagnosis+"%");
            stmt.setString(3, "%"+prescriptions+"%");
            stmt.setString(4, "%"+comments+"%");
            stmt.setString(5, date3);
            stmt.setString(6, date4);
            stmt.setString(7, date1);
            stmt.setString(8, date2);
            stmt.setInt(9, doctorId);
            
            ResultSet result = stmt.executeQuery();
            visits = utilities.buildListFromResult(result);

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return visits;
    }

    /**
     * @return the healthCard
     */
    public String getHealthCard() {
        return healthCard;
    }

    /**
     * @param healthCard the healthCard to set
     */
    public void setHealthCard(String healthCard) {
        this.healthCard = healthCard;
    }

    /**
     * @return the dateAndTime
     */
    public Date getDateAndTime() {
        return dateAndTime;
    }

    /**
     * @param dateAndTime the dateAndTime to set
     */
    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the doctorId
     */
    public int getDoctorId() {
        return doctorId;
    }

    /**
     * @param doctorId the doctorId to set
     */
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * @return the diagnosis
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * @param diagnosis the diagnosis to set
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * @return the prescriptions
     */
    public String getPrescriptions() {
        return prescriptions;
    }

    /**
     * @param prescriptions the prescriptions to set
     */
    public void setPrescriptions(String prescriptions) {
        this.prescriptions = prescriptions;
    }

    /**
     * @return the treatment
     */
    public Date getTreatment() {
        return treatment;
    }

    /**
     * @param treatment the treatment to set
     */
    public void setTreatment(Date treatment) {
        this.treatment = treatment;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the lastModified
     */
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * @param lastModified the lastModified to set
     */
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
    
}
