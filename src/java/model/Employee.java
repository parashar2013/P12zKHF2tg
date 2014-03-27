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
import java.util.List;
import java.util.Map;
import lib.DB;
import lib.utilities;

/**
 *
 * @author Parashar
 */
public class Employee {
    
    private int id;
    private String name;
    private String role;
    private String password;
    
    public Employee(int id,String name,String role,String password)
    {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
    }
    public static List<Map<String, Object>> getDoctorsWithPermissionToPatient(String doctorId, String healthCard) {
        List<Map<String, Object>> doctors = new ArrayList<>();
        
        Connection con = DB.getConnection();
        
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT name, id FROM Employee e "
                    + "WHERE role = 'doctor' AND id != ? "
                    + "AND e.id IN (SELECT doctor_id FROM Doc_Patient dp "
                    + "WHERE dp.patient_health_card = ?)");
            stmt.setInt(1, parseInt(doctorId));
            stmt.setString(2, healthCard);
            
            ResultSet result = stmt.executeQuery();
            doctors = utilities.buildListFromResult(result);
            
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return doctors;
    }
    
    public static List<Map<String, Object>> getDoctorsWithoutPermissionToPatient(String doctorId, String healthCard) {
        List<Map<String, Object>> doctors = new ArrayList<>();
        
        Connection con = DB.getConnection();
        
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT name, id FROM Employee e "
                    + "WHERE role = 'doctor' AND id != ? "
                    + "AND e.id NOT IN (SELECT doctor_id FROM Doc_Patient dp "
                    + "WHERE dp.patient_health_card = ?)");
            stmt.setInt(1, parseInt(doctorId));
            stmt.setString(2, healthCard);
            
            ResultSet result = stmt.executeQuery();
            doctors = utilities.buildListFromResult(result);
            
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return doctors;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
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
