/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.DB;
import lib.utilities;
import model.Employee;
import model.Visit;

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
        
        try {
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
        catch(ClassNotFoundException e){
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"ClassNotFoundException thrown" + e.getMessage());
        }
        catch(SQLException e)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,"SQLException thrown" + e.getMessage());
        }
    }
    
    private void homePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        
        List<Employee> doctors = DB.getEmployeesByRole("Doctor");
        
        request.setAttribute("doctors", doctors);
        
        request.getRequestDispatcher(utilities.getView("FO/home.jsp")).forward(request, response);
    }
    
    private void doctorInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
    
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
        
        Employee e = DB.getEmployeeById(parseInt(doctorId));
        String dName = e.getName();
        
        List<Visit> summary = DB.getFOVisits(doctorId, date1, date2, hcstr);
        List<String> patients = DB.getDistinctVisits(doctorId);
        
        request.setAttribute("summary", summary);
        request.setAttribute("patients", patients);
        request.setAttribute("dName", dName);
        
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
