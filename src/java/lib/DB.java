/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package lib;

import model.Employee;
import model.Patient;
import model.Visit;
import java.io.IOException;
import java.sql.*;
import java.util.*;


/**
 *
 * @author Babanani
 */
public class DB {
    
    public static void testConnection()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            con = getConnection();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static Connection getConnection() {
        Connection con = null;
        
        try {
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
            
            final String url = properties.getProperty("url");
            final String user = properties.getProperty("user");
            final String pwd = properties.getProperty("password");
            
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return con;
    }
    
    public static ArrayList<Patient> getPatients()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Patient> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(
                    
                    
                    "SELECT * FROM Patient;");
            ret = new ArrayList<>();
            while (resultSet.next()) {
                Patient p = new Patient(
                        resultSet.getString("health_card"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("sin_number"),
                        resultSet.getInt("number_of_visits"),
                        resultSet.getInt("default_doctor_id"),
                        resultSet.getString("current_health"),
                        resultSet.getString("password")
                );
                ret.add(p);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static Patient getPatientByHealthCard(String health_card)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        Patient ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    "SELECT * FROM Patient WHERE health_card=%s;",health_card
            ));
            while (resultSet.next()) {
                Patient p = new Patient(
                        resultSet.getString("health_card"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("sin_number"),
                        resultSet.getInt("number_of_visits"),
                        resultSet.getInt("default_doctor_id"),
                        resultSet.getString("current_health"),
                        resultSet.getString("password")
                );
                ret=p;
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static void InsertPatientInfo(String health_card,
            String name,
            String address,
            String phone_number,
            int sin_number,
            int number_of_visits,
            int default_doctor_id,
            String current_health,
            String password)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        Patient ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement( "INSERT INTO Patient VALUES( "
                    + String.format("'%s',",name)
                    + String.format("'%s',",address)
                    + String.format("'%s',",phone_number)
                    + String.format("'%s',",health_card)
                    + String.format("%d,",sin_number)
                    + String.format("%d,",number_of_visits)
                    + String.format("%d,",default_doctor_id)
                    + String.format("'%s',",current_health)
                    + String.format("'%s'",password)
                    + ")"
            );
            System.out.println(pstmt);
            pstmt.executeUpdate();
            
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static void UpdatePatientInfo(String health_card,
            String name,
            String address,
            String phone_number,
            int sin_number,
            int number_of_visits,
            int default_doctor_id,
            String current_health,
            String password)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        Patient ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement( "UPDATE Patient SET "
                    + String.format("name='%s',",name)
                    + String.format("address='%s',",address)
                    + String.format("phone_number='%s',",phone_number)
                    + String.format("health_card='%s',",health_card)
                    + String.format("sin_number=%d,",sin_number)
                    + String.format("number_of_visits=%d,",number_of_visits)
                    + String.format("default_doctor_id=%d,",default_doctor_id)
                    + String.format("current_health='%s',",current_health)
                    + String.format("password='%s'",password)
                    + String.format("WHERE health_card='%s';",health_card)
            );
            System.out.println(pstmt);
            pstmt.executeUpdate();
            
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static void Reschedule(String health_card, int doctor_id, String date_and_time, String new_date_and_time)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        Patient ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement( "UPDATE Appointment SET "
                    + String.format("health_card='%s',",health_card)
                    + String.format("doctor_id=%d,",doctor_id)
                    + String.format("date_and_time='%s'",new_date_and_time)
                    + String.format("WHERE health_card='%s' AND date_and_time = '%s'",health_card, date_and_time)
            );
            System.out.println(pstmt);
            pstmt.executeUpdate();
            
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static ArrayList<Employee> getDoctorsByStaffId(int staff_id)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Employee> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    "SELECT * FROM Employee WHERE id IN (SELECT doctor_id FROM Doc_Staff WHERE staff_id=%d)",staff_id
            ));
            ret = new ArrayList<>();
            while (resultSet.next()) {
                Employee p = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("role"),
                        resultSet.getString("password")
                );
                ret.add(p);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static Employee getEmployeeById(int id)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        Employee ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet;
            resultSet = stmt.executeQuery(String.format(
                    "SELECT * FROM Employee WHERE id=%d",id
            ));
            while(resultSet.next())
            {
                Employee e = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("role"),
                        resultSet.getString("password")
                );
                ret = e;
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static ArrayList<Employee> getEmployeesByRole(String role)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Employee> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet;
            resultSet = stmt.executeQuery(String.format(
                    "SELECT * FROM Employee WHERE role='%s'",role
            ));
            ret = new ArrayList<>();
            while(resultSet.next())
            {
                Employee e = new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("role"),
                        resultSet.getString("password")
                );
                ret.add(e);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static ArrayList<Patient> getPatientsByDoctorId(int doctor_id)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Patient> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    
                    
                    "SELECT * FROM Patient WHERE health_card IN (SELECT patient_health_card FROM Doc_Patient WHERE doctor_id=%d);",doctor_id
            ));
            ret = new ArrayList<>();
            while (resultSet.next()) {
                Patient p = new Patient(
                        resultSet.getString("health_card"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("sin_number"),
                        resultSet.getInt("number_of_visits"),
                        resultSet.getInt("default_doctor_id"),
                        resultSet.getString("current_health"),
                        resultSet.getString("password")
                );
                ret.add(p);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    
    public static boolean findAppointment(int doctor_id, String health_card, String date_and_time)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        boolean found = false;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    "SELECT * FROM Appointment WHERE doctor_id = %d AND health_card = '%s' AND date_and_time = '%s'", doctor_id, health_card, date_and_time
            ));
            while (resultSet.next()) {
                found = true;
            }
            return found;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static ArrayList<Object[]> getAppointments(int doctor_id)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Object[]> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    
                    
                    "SELECT p.health_card,p.name, a.date_and_time FROM Patient p, Appointment a, Employee e WHERE p.health_card=a.health_card AND e.id=a.doctor_id AND e.id=%d",doctor_id
            ));
            ret = new ArrayList<>();
            while (resultSet.next()) {
                Object[] list = {
                    resultSet.getString("health_card"),
                    resultSet.getString("name"),
                    resultSet.getString("date_and_time")
                };
                ret.add(list);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static ArrayList<Object[]> getPatientAppointments(String health_card)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Object[]> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    
                    
                    "SELECT p.health_card,e.name, a.date_and_time FROM Patient p, Appointment a, Employee e WHERE p.health_card=a.health_card AND e.id=a.doctor_id AND a.health_card=%s",health_card
            ));
            ret = new ArrayList<>();
            while (resultSet.next()) {
                Object[] list = {
                    resultSet.getString("health_card"),
                    resultSet.getString("name"),
                    resultSet.getString("date_and_time")
                };
                ret.add(list);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static ArrayList<Visit> getVisits(String doctor_id)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Visit> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    
                    
                    "SELECT * FROM Visit v WHERE v.doctor_id=%s "
                            + "AND v.last_modified IN "
                            + "(SELECT max(last_modified) FROM Visit "
                            + "WHERE health_card = v.health_card AND date_and_time = v.date_and_time "
                            + "GROUP BY health_card, date_and_time)",doctor_id
            ));
            ret = new ArrayList<>();
            while (resultSet.next()) {
                Visit v = new Visit(
                        resultSet.getString("health_card"),
                        resultSet.getDate("date_and_time"),
                        resultSet.getInt("duration"),
                        resultSet.getInt("doctor_id"),
                        resultSet.getString("diagnosis"),
                        resultSet.getString("prescriptions"),
                        resultSet.getDate("treatment"),
                        resultSet.getString("comments"),
                        resultSet.getDate("last_modified")
                );
                ret.add(v);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static ArrayList<Object[]> getPatientVisits(String health_card)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Object[]> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    "SELECT e.name, v.diagnosis, v.prescriptions, v.date_and_time "
                            + "FROM Visit v LEFT JOIN Employee e ON v.doctor_id = e.id "
                            + "WHERE v.health_card=%s "
                            + "AND v.last_modified IN "
                            + "(SELECT max(last_modified) FROM Visit "
                            + "WHERE health_card = v.health_card AND date_and_time = v.date_and_time "
                            + "GROUP BY health_card, date_and_time)",health_card
            ));
            ret = new ArrayList<>();
            while (resultSet.next()) {
                Object[] list = {
                        resultSet.getString("name"),
                        resultSet.getString("diagnosis"),
                        resultSet.getString("prescriptions"),
                        resultSet.getDate("date_and_time")
                };
                ret.add(list);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static ArrayList<Visit> getDocVisits(String doctor_id)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Visit> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    
                    
                    "SELECT * FROM Visit v WHERE v.doctor_id=%s "
                            + "AND v.last_modified IN "
                            + "(SELECT max(last_modified) FROM Visit "
                            + "WHERE health_card = v.health_card AND date_and_time = v.date_and_time "
                            + "GROUP BY health_card, date_and_time)",doctor_id
            ));
            ret = new ArrayList<>();
            while (resultSet.next()) {
                Visit v = new Visit(
                        resultSet.getString("health_card"),
                        resultSet.getDate("date_and_time"),
                        resultSet.getInt("duration"),
                        resultSet.getInt("doctor_id"),
                        resultSet.getString("diagnosis"),
                        resultSet.getString("prescriptions"),
                        resultSet.getDate("treatment"),
                        resultSet.getString("comments"),
                        resultSet.getDate("last_modified")
                );
                ret.add(v);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static ArrayList<String> getDistinctVisits(String doctor_id)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<String> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    
                    
                    "SELECT DISTINCT v.health_card FROM Visit v WHERE v.doctor_id =%s "
                            + "AND v.last_modified IN "
                            + "(SELECT max(last_modified) FROM Visit "
                            + "WHERE health_card = v.health_card AND date_and_time = v.date_and_time "
                            + "GROUP BY health_card, date_and_time)", doctor_id
            ));
            ret = new ArrayList<>();
            while (resultSet.next()) {
                String v = resultSet.getString("health_card");
                ret.add(v);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static ArrayList<Visit> getFOVisits(String doctor_id, String date1, String date2, String hcstr)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Visit> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    "SELECT * FROM Visit v "
                            + "WHERE v.doctor_id = " + doctor_id + " "
                            + "AND v.last_modified IN "
                            + "(SELECT max(last_modified) FROM Visit "
                            + "WHERE health_card = v.health_card AND date_and_time = v.date_and_time "
                            + "GROUP BY health_card, date_and_time) "
                            + "AND v.date_and_time BETWEEN '" + date1 + "' AND '" + date2 + "'" + hcstr
            ));
            ret = new ArrayList<>();
            while (resultSet.next()) {
                Visit v = new Visit(
                        resultSet.getString("health_card"),
                        resultSet.getDate("date_and_time"),
                        resultSet.getInt("duration"),
                        resultSet.getInt("doctor_id"),
                        resultSet.getString("diagnosis"),
                        resultSet.getString("prescriptions"),
                        resultSet.getDate("treatment"),
                        resultSet.getString("comments"),
                        resultSet.getDate("last_modified")
                );
                ret.add(v);
            }
            return ret;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static void insertAppointment(String health_card, String date_and_time, int doctor_id)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement(
                    String.format("INSERT INTO Appointment VALUES('%s',%d,'%s');",health_card,doctor_id, date_and_time)
            );
            System.out.println(pstmt);
            pstmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static void insertDocPatient(String health_card, int doctor_id)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement(
                    String.format("INSERT INTO Doc_Patient VALUES(%d,'%s');",doctor_id, health_card)
            );
            System.out.println(pstmt);
            pstmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static boolean findDocPatient(String health_card, int doctor_id)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        boolean found = false;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    "SELECT * FROM Doc_Patient WHERE doctor_id = %d AND patient_health_card = '%s'", doctor_id, health_card
            ));
            while (resultSet.next()) {
                found = true;
            }
            return found;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public static void deleteAppointment(String health_card,java.util.Date date)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement(
                    String.format("DELETE FROM Appointment WHERE health_card='%s' AND date_and_time=?;",health_card)
            );
            
            pstmt.setTimestamp(1, new Timestamp(date.getTime()));
            System.out.println(pstmt);
            pstmt.executeUpdate();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
