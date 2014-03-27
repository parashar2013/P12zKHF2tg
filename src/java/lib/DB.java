/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import entity.AppointmentPK;
import entity.Employee;
import entity.Patient;
import entity.Visit;
import entity.VisitPK;
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
            int executeUpdate = stmt.executeUpdate( "INSERT INTO Patient VALUES( "
                    + String.format("'%s',",name)
                    + String.format("'%s',",address)
                    + String.format("'%s',",phone_number)
                    + String.format("%s",health_card)
                    + String.format("%d",sin_number)
                    + String.format("%d",number_of_visits)
                    + String.format("%d",default_doctor_id)
                    + String.format("'%s'",current_health)
                    + String.format("'%s'",password) 
                    + ")"
            );
            
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
            int executeUpdate = stmt.executeUpdate( "UPDATE Patient SET "
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
      public static ArrayList<Visit> getVisits(String health_card)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<Visit> ret = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(String.format(
                    
                    
                    "SELECT * FROM Visit WHERE health_card=%s",health_card
            ));
            ret = new ArrayList<>();
            while (resultSet.next()) {
                Visit v = new Visit( 
                        new VisitPK(resultSet.getString("health_card"),resultSet.getDate("date_and_time")),
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
      public static void insertAppointment(AppointmentPK app_pk, int doctor_id)
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            System.out.println(app_pk.getDateAndTime().getTime());
            PreparedStatement pstmt = con.prepareStatement(
                    String.format("INSERT INTO Appointment VALUES('%s',%d,?);",app_pk.getHealthCard(),doctor_id)
            );
            pstmt.setTimestamp(1, new Timestamp(app_pk.getDateAndTime().getTime()));
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
      public static void deleteAppointment(AppointmentPK app_pk)             
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        Statement stmt = null;
        try {
            con = getConnection();
            stmt = con.createStatement();
            PreparedStatement pstmt = con.prepareStatement(
                    String.format("DELETE FROM Appointment WHERE health_card='%s' AND date_and_time=?;",app_pk.getHealthCard())
            );
            
            pstmt.setTimestamp(1, new Timestamp(app_pk.getDateAndTime().getTime()));
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
