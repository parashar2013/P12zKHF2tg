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
            PreparedStatement stmt = con.prepareStatement("SELECT health_card, p.name AS name, address, phone_number, current_health, e.name AS default_doctor_name "
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
}
