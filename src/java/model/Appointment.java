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
    public static List<Map<String, Object>> getAppointmentsByDoctorId(String doctorId) {
        List<Map<String, Object>> appointments = new ArrayList<>();
        
        Connection con = DB.getConnection();
        
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT p.name, p.health_card, a.date_and_time "
                + "FROM Doc_Patient dp "
                + "JOIN Patient p ON (p.health_card = dp.patient_health_card)"
                + "NATURAL JOIN Appointment a "
                + "WHERE dp.doctor_id = ?");
            stmt.setInt(1, parseInt(doctorId));
            
            ResultSet result = stmt.executeQuery();
            appointments = utilities.buildListFromResult(result);
            
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return appointments;
    }
}
