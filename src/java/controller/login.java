/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import lib.utilities.*;
import lib.EMF;
import entity.Employee;
import entity.Patient;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Parashar
 */
public class login extends HttpServlet {

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
        String password, login_type;
        Integer login_id;
        String hCard;
        Object user = null;
        
        login_id = request.getParameter("login_id").isEmpty() ? null : parseInt(request.getParameter("login_id"));
        //password = utilities.md5(request.getParameter("login_password"));
        password = request.getParameter("login_password");
        login_type = request.getParameter("login_type");
        
        EntityManager em = EMF.createEntityManager();
             
        if (login_id == null || password.isEmpty()) {
            sendBackToLoginPage(request, response);
        } else {
            if (login_type.equals("employee"))
            {
                TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e WHERE e.id = :login_id AND e.password = :password", Employee.class)
                        .setParameter("login_id", login_id)
                        .setParameter("password", password);
                
                List<Employee> empList = query.getResultList();
                
                if (empList.isEmpty()) {
                    sendBackToLoginPage(request, response);
                } else {
                    user = empList.get(0);
                }
            }
            else
            {
                TypedQuery<Patient> query = em.createQuery("SELECT e FROM Patient e WHERE e.healthCard = :login_id AND e.password = :password", Patient.class)
                        .setParameter("login_id", request.getParameter("login_id"))
                        .setParameter("password", password);
                
                List<Patient> patientList = query.getResultList();
                
                if (patientList.isEmpty()) {
                    sendBackToLoginPage(request, response);
                } else {
                    user = patientList.get(0);
                }
            }
        }

        request.getSession().setAttribute("user", user);
        
        response.sendRedirect(request.getContextPath());    // redirect back to index page
    }
    
    private void sendBackToLoginPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.sendRedirect(request.getContextPath());
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
