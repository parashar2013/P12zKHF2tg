/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            case "/insert": 
                insertRecord(request, response);
                return;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void homePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = EMF.createEntityManager();
        
        Employee me = (Employee)request.getSession().getAttribute("user");
        
        TypedQuery<Patient> query = em.createQuery("SELECT p FROM Patient p WHERE p.defaultDoctorId = :id", Patient.class)
                                        .setParameter("id", me.getId());
        
        List<Patient> patientList = me.getPatients();
        List<Patient> patientListDefault = query.getResultList();
        
        request.setAttribute("patientList", patientListDefault);
        
        request.getRequestDispatcher(utilities.getView("doctor/home.jsp")).forward(request, response);
    }
    
    private void insertRecordPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Employee me = (Employee)request.getSession().getAttribute("user");
        
        EntityManager em = EMF.createEntityManager();
        
        Query query = em.createNativeQuery("SELECT p.name, p.health_card, a.date_and_time "
                + "FROM Doc_Patient dp "
                + "JOIN Patient p ON (p.health_card = dp.patient_health_card)"
                + "NATURAL JOIN Appointment a "
                + "WHERE dp.doctor_id = ?")
                .setParameter(1, me.getId())
                .setParameter(2, me.getId());
        
        List results = query.getResultList();
        
        request.setAttribute("results", results);
        
        request.getRequestDispatcher(utilities.getView("doctor/insert-record.jsp")).forward(request, response);
    }
    
    private void insertRecord(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
    }
    
    private void searchPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(utilities.getView("doctor/search.jsp")).forward(request, response);
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
