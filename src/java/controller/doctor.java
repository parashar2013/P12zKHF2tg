/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import model.*;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.DB;
import lib.utilities;

/**
 *
 * @author Parashar
 */
public class doctor extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = request.getPathInfo();
        
        if (page == null || page.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/doctor/");
            return;
        }
        
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }
        
        switch (page) {
            case "/": 
                homePage(request, response);
                return;
            case "/home": 
                homePage(request, response);
                return;
            case "/insert-record": 
                insertRecordPage(request, response);
                return;
            case "/edit-record": 
                editRecordPage(request, response);
                return;
            case "/edit-record-do": 
                editRecord(request, response);
                return;
            case "/search": 
                searchPage(request, response);
                return;
            case "/search-do": 
                doSearch(request, response);
                return;
            case "/search-patients": 
                searchPatientsPage(request, response);
                return;
            case "/search-patients-do": 
                doPatientsSearch(request, response);
                return;
            case "/insert": 
                insertRecord(request, response);
                return;
            case "/give-permission": 
                givePermission(request, response);
                return;
            case "/revoke-permission": 
                revokePermission(request, response);
                return;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void homePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User me = (User)request.getSession().getAttribute("user");
        
        List<Map<String, Object>> patientList = Employee.getCurrentPatients(me.getId());
        
        request.setAttribute("patientList", patientList);
        request.getRequestDispatcher(utilities.getView("doctor/home.jsp")).forward(request, response);
    }
    
    private void insertRecordPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User me = (User)request.getSession().getAttribute("user");
        
        List<Map<String, Object>> appointments = Appointment.getAppointmentsByDoctorId(me.getId());
        
        request.setAttribute("appointments", appointments);
        
        request.getRequestDispatcher(utilities.getView("doctor/insert-record.jsp")).forward(request, response);
    }
    
    private void editRecordPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User me = (User)request.getSession().getAttribute("user");
        String healthCard = request.getParameter("health_card");
        String dateAndTime = request.getParameter("date_and_time");
        
        Map<String, Object> visit = Visit.getVisit(healthCard, dateAndTime);
        
        visit.put("treatment_date", ((Date)visit.get("treatment_date")).toString().replace('-', '/'));
        
        request.setAttribute("visit", visit);
        
        request.getRequestDispatcher(utilities.getView("doctor/edit-record.jsp")).forward(request, response);
    }
    
    private void searchPatientsPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher(utilities.getView("doctor/search-patients.jsp")).forward(request, response);
    }
    
    private void editRecord(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User me = (User)request.getSession().getAttribute("user");
        String appDate, hCard, diagnosis, prescriptions, duration, treatment, comments;

        appDate = request.getParameter("date_and_time");
        hCard = request.getParameter("health_card");
        diagnosis = request.getParameter("diagnosis");
        prescriptions = request.getParameter("prescriptions");
        duration = request.getParameter("duration");
        treatment = request.getParameter("treatment");
        comments = request.getParameter("comments");
        
        Visit.updateVisit(duration, hCard, me.getId(), diagnosis, prescriptions, treatment, comments, appDate);
        
        Map<String, Object> visit = Visit.getVisit(hCard, appDate);
        
        visit.put("treatment_date", ((Date)visit.get("treatment_date")).toString().replace('-', '/'));
        
        request.setAttribute("visit", visit);
        request.setAttribute("result", "Record updated successfully");
        request.getRequestDispatcher(utilities.getView("doctor/edit-record.jsp")).forward(request, response);
    }
    
    private void insertRecord(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User me = (User)request.getSession().getAttribute("user");
        String appInfo[], appDate, hCard, diagnosis, prescriptions, duration, treatment, comments;

        appInfo = request.getParameter("appointment").split(" - ");
        appDate = appInfo[1];
        hCard = appInfo[0];
        diagnosis = request.getParameter("diagnosis");
        prescriptions = request.getParameter("prescriptions");
        duration = request.getParameter("duration");
        treatment = request.getParameter("treatment");
        comments = request.getParameter("comments");
        
        Visit.insertVisit(duration, hCard, me.getId(), diagnosis, prescriptions, treatment, comments, appDate);
        
        request.getRequestDispatcher(utilities.getView("doctor/insert-record-result.jsp")).forward(request, response);
    }
    
    private void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
              
        User me = (User)request.getSession().getAttribute("user");
        String name, diagnosis, prescriptions, date1, date2, date3, date4, treatment, comments;

        name = request.getParameter("name") == null ? "" : request.getParameter("name");
        diagnosis = request.getParameter("diagnosis") == null ? "" : request.getParameter("diagnosis");
        comments = request.getParameter("comments") == null ? "" : request.getParameter("comments");
        prescriptions = request.getParameter("prescriptions") == null ? "" : request.getParameter("prescriptions");
        date1 = request.getParameter("date1");
        date2 = request.getParameter("date2");
        date3 = request.getParameter("date3");
        date4 = request.getParameter("date4");
        
        if ("".equals(date1))
            date1 = "2000-01-01";
        if ("".equals(date2))
            date2 = "2100-01-01";
        if ("".equals(date3))
            date3 = "2000-01-01";
        if ("".equals(date4))
            date4 = "2100-01-01";
        
        List<Map<String, Object>> visitList =
                Visit.searchVisits(name, diagnosis, prescriptions, comments, date1, date2, date3, date4, parseInt(me.getId()));
        
        request.setAttribute("vList", visitList);
        
        request.setAttribute("name", name);
        request.setAttribute("diagnosis", diagnosis);
        request.setAttribute("comments", comments);
        request.setAttribute("prescriptions", prescriptions);
        request.setAttribute("date1", request.getParameter("date1"));
        request.setAttribute("date2", request.getParameter("date2"));
        request.setAttribute("date3", request.getParameter("date3"));
        request.setAttribute("date4", request.getParameter("date4"));
        
        request.getRequestDispatcher(utilities.getView("doctor/search.jsp")).forward(request, response);
    }
    
        private void doPatientsSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
              
        User me = (User)request.getSession().getAttribute("user");
        String name, health_card, last_visit_date;

        name = request.getParameter("name") == null ? "" : request.getParameter("name");
        health_card = request.getParameter("health_card") == null ? "" : request.getParameter("health_card");
        last_visit_date = request.getParameter("date") == null ? "" : request.getParameter("date");

        List<Map<String, Object>> patientList = Patient.searchPatients(parseInt(me.getId()), name, health_card, last_visit_date);
        
        request.setAttribute("patientList", patientList);
        
        request.setAttribute("name", name);
        request.setAttribute("health_card", health_card);
        request.setAttribute("date", last_visit_date);
        
        request.getRequestDispatcher(utilities.getView("doctor/search-patients.jsp")).forward(request, response);
    }
    
    private void searchPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(utilities.getView("doctor/search.jsp")).forward(request, response);
    }
    
    private void givePermission(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String healthCard = (String)request.getParameter("health_card");
        String otherDoctorId = (String)request.getParameter("doctor_id");
        
        try (Connection con = DB.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Doc_Patient (doctor_id, patient_health_card) "
                    + "VALUES (?,?)");
            stmt.setInt(1, parseInt(otherDoctorId));
            stmt.setString(2, healthCard);

            stmt.executeUpdate();
       } catch (SQLException e) {
            e.printStackTrace();
       }
        
        response.sendRedirect(request.getContextPath() + "/patient/info?healthCard=" + healthCard);
    }
    
    // Revoke permission to view patient from another doctor
    private void revokePermission(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String healthCard = (String)request.getParameter("health_card");
        String doctorId = (String)request.getParameter("doctor_id");
        
        try (Connection con = DB.getConnection()) {
        
            PreparedStatement stmt = con.prepareStatement("DELETE FROM Doc_Patient "
                    + "WHERE doctor_id = ? AND patient_health_card = ?");
            stmt.setInt(1, parseInt(doctorId));
            stmt.setString(2, healthCard);

            stmt.executeUpdate();
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/patient/info?healthCard=" + healthCard);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
