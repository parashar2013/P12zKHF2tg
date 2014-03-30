/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import model.*;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.DB;
import static lib.utilities.getView;
import model.User;

/**
 *
 * @author Parashar
 */
public class patient extends HttpServlet {

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
            response.sendRedirect(request.getContextPath() + "/patient/");
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
            case "/profile": 
                profile(request, response);
                return;
            case "/updateProfile":
                updateProfile(request, response);
                return;
            case "/info":
                info(request, response);
                return;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void homePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User me = (User)request.getSession().getAttribute("user");
        
        List<Object[]> appointmentList = null;
        List<Object[]> visitList = null;
        
        try {
            appointmentList = DB.getPatientAppointments(me.getId());
            visitList = DB.getPatientVisits(me.getId());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(patient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("appointmentList", appointmentList);
        request.setAttribute("visitList", visitList);
        
        request.getRequestDispatcher(getView("patient/home.jsp")).forward(request, response);
    }
    
    private void profile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User me = (User)request.getSession().getAttribute("user");
        String hCard = me.getId();
        
        Patient patientProfile = null;
        
        try {
            patientProfile = DB.getPatientByHealthCard(hCard);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(patient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        request.setAttribute("patientProfile", patientProfile);
        
        request.getRequestDispatcher(getView("patient/profile.jsp")).forward(request, response);
    }
    
    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name, hCard, address, phone, curHealth, pw;
        int SIN, numVisits, defaultDoctorId;

        name = request.getParameter("name");
        hCard = request.getParameter("hCard");
        address = request.getParameter("address");
        phone = request.getParameter("phone");
        SIN = parseInt(request.getParameter("SIN"));
        numVisits = parseInt(request.getParameter("numVisits"));
        defaultDoctorId = parseInt(request.getParameter("defaultDoctorId"));
        curHealth = request.getParameter("curHealth");
        pw = request.getParameter("pw");
        
        if (!pw.equals("") && !name.equals("") && !address.equals("") && !phone.equals("")) {
            try {
                DB.UpdatePatientInfo(hCard, name, address, phone, SIN, numVisits, defaultDoctorId, curHealth, pw);
                request.setAttribute("congrats", "Your profile has been updated!<br><br>");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(patient.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            request.setAttribute("congrats", "<font color='red'>Please fill in all the fields.</font><br><br>");
        }
        profile(request, response);
    }
    
    private void info(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = (User)request.getSession().getAttribute("user");
        String healthCard = request.getParameter("healthCard");
        
        //if (user.getRole() == "Doctor") {
            // check if patients doctor id == employees id
            // forward to doctors version of patient info page
            
            List patientList = Patient.getPatientsByHealthCard(healthCard);
            
            if (patientList.isEmpty()){
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            Map<String, Object> patient = (Map)patientList.get(0);
            
            boolean isDefaultDoctor = patient.get("default_doctor_id").equals(parseInt(user.getId()));
            
            List<Map<String, Object>> visitList = Visit.getVisitByHealthCard(healthCard);
            
            List<Map<String, Object>> doctorsWithPermissionList = Employee.getDoctorsWithPermissionToPatient(user.getId(), healthCard);
            
            List<Map<String, Object>> doctorsWithoutPermissionList = Employee.getDoctorsWithoutPermissionToPatient(user.getId(), healthCard);
            
            request.setAttribute("visitList", visitList);
            request.setAttribute("doctorsWithPermissionList", doctorsWithPermissionList);
            request.setAttribute("doctorsWithoutPermissionList", doctorsWithoutPermissionList);
            request.setAttribute("patient", patient);
            request.setAttribute("isDefaultDoctor", isDefaultDoctor);
            request.getRequestDispatcher(getView("doctor/patient-info.jsp")).forward(request, response);
        //}
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
