/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

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
public class Visit {
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
    
}
