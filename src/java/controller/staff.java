/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.Appointment;
import entity.Employee;
import entity.Patient;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.EMF;

/**
 *
 * @author Parashar
 */
public class staff extends HttpServlet {

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
            response.sendRedirect(request.getContextPath() + "/staff/");
            return;
        }

        switch (page) {
            case "/": 
                homePage(request, response);
                return;
            case "/home": 
                homePage(request, response);
                return;
            case "/home.jsp":
                 homePage(request,response);
            case "/patients.jsp":
                 patientsPage(request,response);
            case "/appointments.jsp":
                 appointmentsPage(request,response);
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
    
        private void homePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = EMF.createEntityManager();
        
        Employee me = (Employee)request.getSession().getAttribute("user");
        String page = request.getParameter("pg");
        
        if(page != null)
        {
            if(page.equals("appointments"))
                appointmentsPage(request,response);
            else
                patientsPage(request,response);
        }
        
        TypedQuery<Employee> query = em.createNamedQuery("Employee.findById",Employee.class)
                                        .setParameter("id", me.getId());
        
        Employee staff = query.getSingleResult();
        List<Employee> doctorList = staff.getDoctors();
        
        request.setAttribute("doctorList", doctorList);
        
        request.getRequestDispatcher("/WEB-INF/view/staff/home.jsp").forward(request, response);
    }
         
         private void patientsPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = EMF.createEntityManager();
        
        //Employee me = (Employee)request.getSession().getAttribute("user");
        int doc_id = request.getParameter("doctor_id").isEmpty()?null:parseInt(request.getParameter("doctor_id"));
        
        TypedQuery<Employee> query = em.createNamedQuery("Employee.findById",Employee.class)
                                        .setParameter("id", doc_id);
        
        Employee doc = query.getSingleResult();
        List<Patient> patientList = doc.getPatients();
        
        request.setAttribute("patientList", patientList);
        request.setAttribute("doctor", doc.getName());
        request.getRequestDispatcher("/WEB-INF/view/staff/patients.jsp").forward(request, response);
    }
        
         private void appointmentsPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = EMF.createEntityManager();
        
        //Employee me = (Employee)request.getSession().getAttribute("user");
        int doc_id = request.getParameter("doctor_id").isEmpty()?null:parseInt(request.getParameter("doctor_id"));
  
        TypedQuery<Employee> query1 = em.createNamedQuery("Employee.findById",Employee.class)
                                        .setParameter("id", doc_id);       
        TypedQuery<Appointment> query2 = em.createNamedQuery("Appointment.findByDoctorId",Appointment.class)
                                        .setParameter("doctorId", doc_id);
        
        List<Appointment> appointmentList = query2.getResultList();
        
        request.setAttribute("appointmentList", appointmentList);
        request.setAttribute("doctor", query1.getSingleResult().getName());
        request.getRequestDispatcher("/WEB-INF/view/staff/appointments.jsp").forward(request, response);
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
