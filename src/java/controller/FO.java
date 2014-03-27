/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
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
public class FO extends HttpServlet {

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
            case "/checkDoctor":
                doctorInfo(request, response);
                return;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void homePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = EMF.createEntityManager();
        
        Query q = em.createNativeQuery("SELECT id, name FROM Employee WHERE role = 'Doctor'");
        
        List doctors = q.getResultList();
        
        request.setAttribute("doctors", doctors);
        
        request.getRequestDispatcher(utilities.getView("FO/home.jsp")).forward(request, response);
    }
    
    private void doctorInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        String doctorId, date1, date2, health_card, hcstr;
        
        doctorId = request.getParameter("doctorId");
        date1 = request.getParameter("date1");
        date2 = request.getParameter("date2");
        health_card = request.getParameter("health_card");
        
        request.setAttribute("health_card", health_card);
        request.setAttribute("doctorId", doctorId);
        
        hcstr = "";
        
        if (!"NA".equals(health_card))
            hcstr = " AND health_card = '" + health_card + "'";
        
        if ("".equals(date1))
            date1 = "2000-01-01";
        if ("".equals(date2))
            date2 = "2100-01-01";
        
        EntityManager em = EMF.createEntityManager();
        
        Query n = em.createNativeQuery("SELECT name FROM Employee WHERE id = ?").setParameter(1, doctorId);
        String dName = n.getSingleResult().toString();
        
        String qstr1 = "SELECT health_card, duration, diagnosis, prescriptions, treatment, date_and_time FROM Visit "
                + "WHERE doctor_id = " + doctorId + " "
                + "AND date_and_time BETWEEN '" + date1 + "' AND '" + date2 + "'" + hcstr;
        
        String qstr2 = "SELECT DISTINCT health_card FROM Visit WHERE doctor_id = " + doctorId;
        
        Query q1 = em.createNativeQuery(qstr1);
        Query q2 = em.createNativeQuery(qstr2);
        
        List summary = q1.getResultList();
        List patients = q2.getResultList();
        
        request.setAttribute("summary", summary);
        request.setAttribute("patients", patients);
        request.setAttribute("dName", dName);
        
        request.setAttribute("ass", qstr1);
        
        request.setAttribute("date1", request.getParameter("date1"));
        request.setAttribute("date2", request.getParameter("date2"));
        request.setAttribute("count", summary.size());
        
        homePage(request, response);
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
