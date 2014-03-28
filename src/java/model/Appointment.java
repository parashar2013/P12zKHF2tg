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
public class Appointment {
    
    private String healthCard;
    private int doctorId;
    private Date date;
    
    public Appointment(String health_card,int doctor_id,Date date)
    {
        this.healthCard = health_card;
        this.doctorId = doctor_id;
        this.date = date;
    }
    public static List<Map<String, Object>> getAppointmentsByDoctorId(String doctorId) {
        List<Map<String, Object>> appointments = new ArrayList<>();
        
        Connection con = DB.getConnection();
        
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT p.name, p.health_card, a.date_and_time "
                + "FROM Doc_Patient dp "
                + "JOIN Patient p ON (p.health_card = dp.patient_health_card)"
                + "NATURAL JOIN Appointment a "
                + "WHERE dp.doctor_id = ? "
                + "AND (p.health_card, dp.doctor_id, a.date_and_time) "
                    + "NOT IN (SELECT health_card, doctor_id, date_and_time FROM Visit)");
            stmt.setInt(1, parseInt(doctorId));
            
            ResultSet result = stmt.executeQuery();
            appointments = utilities.buildListFromResult(result);
            
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return appointments;
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
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
