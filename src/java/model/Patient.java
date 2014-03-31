/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import static java.lang.Integer.parseInt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lib.DB;
import lib.utilities;

/**
 *
 * @author Parashar
 */
public class Patient {
    private
            String healthCard;
            private String name;
            private String address;
            private String phoneNumber;
            private int sinNumber;
            private int numberOfVisits;
            private int defaultDoctorId;
            private String currentHealth;
            private String password;
            
        
    public Patient(String health_card,String name, String address, String phone_number,int sin_number,
            int number_of_visits,int default_doctor_id,String current_health,String password)
    {
        this.healthCard = health_card;
        this.name = name;
        this.address = address;
        this.phoneNumber = phone_number;
        this.sinNumber = sin_number;
        this.numberOfVisits = number_of_visits;
        this.defaultDoctorId = default_doctor_id;
        this.currentHealth = current_health;
        this.password = password;        
    }
    public static List<Map<String, Object>> getPatientsByDefaultDoctorId(String idString) {
        List<Map<String, Object>> patients = new ArrayList<>();
        
        Integer id = parseInt(idString);
        
        try (Connection con = DB.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Patient WHERE default_doctor_id = ?");
            stmt.setInt(1, id);
            
            ResultSet result = stmt.executeQuery();
            
            // build list from result
            patients = utilities.buildListFromResult(result);
            
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return patients;
    }
    
    
    public static List<Map<String, Object>> getPatientsByHealthCard(String healthCard) {
        List<Map<String, Object>> patients = new ArrayList<>();
        
        Connection con = DB.getConnection();
        
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT health_card, p.name AS name, address, default_doctor_id, phone_number, current_health, e.name AS default_doctor_name "
                    + "FROM Patient p JOIN Employee e ON p.default_doctor_id = e.id "
                    + "WHERE p.health_card = ?");
            stmt.setString(1, healthCard);
            
            ResultSet result = stmt.executeQuery();
            patients = utilities.buildListFromResult(result);
            
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return patients;
    }
    
    public static List<Map<String, Object>> searchPatients(Integer doctorId, String name, String healthCard, String date) {
        List<Map<String, Object>> patients = new ArrayList<>();
        
        Connection con = DB.getConnection();
        
        try {
            String sql = "SELECT health_card, p.name AS name, address, phone_number, number_of_visits "
                    + "FROM Patient p JOIN Doc_Patient dp ON p.health_card = dp.patient_health_card "
                    + "WHERE dp.doctor_id = ? ";
            
            if (!name.isEmpty()) {
                sql += " AND p.name LIKE '%"+name+"%' ";
            }
            if (!healthCard.isEmpty()) {
                sql += " AND p.health_card = '"+healthCard+"' ";
            }
            if (!date.isEmpty()) {
                //sql += " AND v.date = '"+date+"' ";
            }
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, doctorId);
            
            ResultSet result = stmt.executeQuery();
            patients = utilities.buildListFromResult(result);
            
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return patients;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the sinNumber
     */
    public int getSinNumber() {
        return sinNumber;
    }

    /**
     * @param sinNumber the sinNumber to set
     */
    public void setSinNumber(int sinNumber) {
        this.sinNumber = sinNumber;
    }

    /**
     * @return the numberOfVisits
     */
    public int getNumberOfVisits() {
        return numberOfVisits;
    }

    /**
     * @param numberOfVisits the numberOfVisits to set
     */
    public void setNumberOfVisits(int numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    /**
     * @return the defaultDoctorId
     */
    public int getDefaultDoctorId() {
        return defaultDoctorId;
    }

    /**
     * @param defaultDoctorId the defaultDoctorId to set
     */
    public void setDefaultDoctorId(int defaultDoctorId) {
        this.defaultDoctorId = defaultDoctorId;
    }

    /**
     * @return the currentHealth
     */
    public String getCurrentHealth() {
        return currentHealth;
    }

    /**
     * @param currentHealth the currentHealth to set
     */
    public void setCurrentHealth(String currentHealth) {
        this.currentHealth = currentHealth;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
