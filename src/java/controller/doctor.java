/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import model.*;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.DB;
import lib.EMF;
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
            case "/search": 
                searchPage(request, response);
                return;
            case "/search-do": 
                doSearch(request, response);
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
        
        List<Map<String, Object>> patientList = Patient.getPatientsByDefaultDoctorId(me.getId());
        
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
    
    private void insertRecord(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        Employee me = (Employee)request.getSession().getAttribute("user");
        String appInfo[], appDate, hCard, diagnosis, prescriptions, duration, treatment, comments;

        appInfo = request.getParameter("appointment").split(" - ");
        appDate = appInfo[1];
        hCard = appInfo[0];
        diagnosis = request.getParameter("diagnosis");
        prescriptions = request.getParameter("prescriptions");
        duration = request.getParameter("duration");
        treatment = request.getParameter("treatment");
        comments = request.getParameter("comments");

        EntityManager em = EMF.createEntityManager();

        em.getTransaction().begin();
        
        Query q = em.createNativeQuery("INSERT INTO Visit VALUES (?,?,?,?,?,?,?,?,?)")
                .setParameter(1, duration)
                .setParameter(2, hCard)
                .setParameter(3, me.getId())
                .setParameter(4, diagnosis)
                .setParameter(5, prescriptions)
                .setParameter(6, treatment)
                .setParameter(7, comments)
                .setParameter(8, appDate)
                .setParameter(9, new Date());
        
        q.executeUpdate();
        
        em.getTransaction().commit();
        
        request.getRequestDispatcher(utilities.getView("doctor/insert-record-result.jsp")).forward(request, response);
    */
    }
    
    private void doSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*        
        Employee me = (Employee)request.getSession().getAttribute("user");
        String name, diagnosis, prescriptions, date1, date2, date3, date4, treatment, comments;

        name = request.getParameter("name");
        diagnosis = request.getParameter("diagnosis");
        comments = request.getParameter("comments");
        prescriptions = request.getParameter("prescriptions");
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
        
        EntityManager em = EMF.createEntityManager();
        
        Query search = em.createNativeQuery("SELECT p.name, v.diagnosis, v.prescriptions, v.comments, v.treatment, v.date_and_time FROM "
                + "Visit v LEFT JOIN Patient p ON v.health_card = p.health_card "
                + "WHERE p.name LIKE '%?%' AND "
                + "v.diagnosis LIKE '%?%' AND "
                + "v.prescriptions LIKE '%?%' AND "
                + "v.comments LIKE '%?%' AND "
                + "v.treatment BETWEEN '?' AND '?' AND "
                + "v.date_and_time BETWEEN '?' AND '?' AND "
                + "v.doctor_id = ?")
                .setParameter(1, name)
                .setParameter(2, diagnosis)
                .setParameter(3, prescriptions)
                .setParameter(4, comments)
                .setParameter(5, date3)
                .setParameter(6, date4)
                .setParameter(7, date1)
                .setParameter(8, date2)
                .setParameter(9, me.getId());
        
        String qstr = "SELECT p.name, v.diagnosis, v.prescriptions, v.comments, v.treatment, v.date_and_time FROM "
                + "Visit v LEFT JOIN Patient p ON v.health_card = p.health_card "
                + "WHERE p.name LIKE '%" + name + "%' AND "
                + "v.diagnosis LIKE '%" + diagnosis + "%' AND "
                + "v.prescriptions LIKE '%" + prescriptions + "%' AND "
                + "v.comments LIKE '%" + comments + "%' AND "
                + "v.treatment BETWEEN '" + date3 + "' AND '" + date4 + "' AND "
                + "v.date_and_time BETWEEN '" + date1 + "' AND '" + date2 + "' AND "
                + "v.doctor_id = " + me.getId();
        
        search = em.createNativeQuery(qstr);
        
        List vResult = search.getResultList();
        
        request.setAttribute("vList", vResult);
        
        request.setAttribute("name", name);
        request.setAttribute("diagnosis", diagnosis);
        request.setAttribute("comments", comments);
        request.setAttribute("prescriptions", prescriptions);
        request.setAttribute("date1", request.getParameter("date1"));
        request.setAttribute("date2", request.getParameter("date2"));
        request.setAttribute("date3", request.getParameter("date3"));
        request.setAttribute("date4", request.getParameter("date4"));
        
        request.getRequestDispatcher(utilities.getView("doctor/search.jsp")).forward(request, response);
        */
    }
    
    private void searchPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(utilities.getView("doctor/search.jsp")).forward(request, response);
    }
    
    private void givePermission(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        EntityManager em = EMF.createEntityManager();
        
        Employee me = (Employee)request.getSession().getAttribute("user");
        
        String healthCard = (String)request.getParameter("health_card");
        String otherDoctorId = (String)request.getParameter("doctor_id");
        
        em.getTransaction().begin();
        
        Query query = em.createNativeQuery("INSERT INTO Doc_Patient (doctor_id, patient_health_card) VALUES (?,?)")
            .setParameter(1, otherDoctorId)
            .setParameter(2, healthCard);

        query.executeUpdate();
        
        em.getTransaction().commit();
        
        response.sendRedirect(request.getContextPath() + "/patient/info?healthCard=" + healthCard);
        */
    }
    
    // Revoke permission to view patient from another doctor
    private void revokePermission(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        EntityManager em = EMF.createEntityManager();
        
        Employee me = (Employee)request.getSession().getAttribute("user");
        
        String healthCard = (String)request.getParameter("health_card");
        String doctorId = (String)request.getParameter("doctor_id");
        
        em.getTransaction().begin();
        
        Query query = em.createNativeQuery("DELETE FROM Doc_Patient WHERE doctor_id = ? AND patient_health_card = ?")
            .setParameter(1, doctorId)
            .setParameter(2, healthCard);

        query.executeUpdate();
        
        em.getTransaction().commit();
        
        response.sendRedirect(request.getContextPath() + "/patient/info?healthCard=" + healthCard);
        */
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
