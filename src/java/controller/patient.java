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
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.EMF;
import static lib.utilities.getView;

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
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void homePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = EMF.createEntityManager();
        
        Patient me = (Patient)request.getSession().getAttribute("user");
        
        TypedQuery<Appointment> query = em.createQuery("SELECT a FROM Appointment a WHERE a.appointmentPK.healthCard = :id", Appointment.class)
                                        .setParameter("id", me.getHealthCard());
        
        List<Appointment> appointmentList = query.getResultList();
        
        request.setAttribute("appointmentList", appointmentList);
        
        request.getRequestDispatcher(getView("patient/home.jsp")).forward(request, response);
    }
    
    private void profile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = EMF.createEntityManager();
        
        Patient me = (Patient)request.getSession().getAttribute("user");
        
        request.setAttribute("patientProfile", me);
        
        request.getRequestDispatcher(getView("patient/profile.jsp")).forward(request, response);
    }
    
    private void updateProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name, hCard, address, phone, SIN, numVisits, defaultDoctorId, curHealth, pw;

        name = request.getParameter("name");
        hCard = request.getParameter("hCard");
        address = request.getParameter("address");
        phone = request.getParameter("phone");
        SIN = request.getParameter("SIN");
        numVisits = request.getParameter("numVisits");
        defaultDoctorId = request.getParameter("defaultDoctorId");
        curHealth = request.getParameter("curHealth");
        pw = request.getParameter("pw");

        EntityManager em = EMF.createEntityManager();

        em.getTransaction().begin();
        
        TypedQuery update = em.createQuery("UPDATE Patient p SET p.address = :address, "
                + "p.currentHealth = :curHealth, "
                + "p.defaultDoctorId = :defaultDoctorId, "
                + "p.name = :name, "
                + "p.numberOfVisits = :numVisits, "
                + "p.password = :pw, "
                + "p.phoneNumber = :phone, "
                + "p.sinNumber = :SIN "
                + "WHERE p.healthCard = :hCard", Patient.class)
                .setParameter("name", name)
                .setParameter("hCard", hCard)
                .setParameter("address", address)
                .setParameter("phone", phone)
                .setParameter("SIN", parseInt(SIN))
                .setParameter("numVisits", parseInt(numVisits))
                .setParameter("defaultDoctorId", parseInt(defaultDoctorId))
                .setParameter("curHealth", curHealth)
                .setParameter("pw", pw);
        
        int rowcount = update.executeUpdate();
        
        if (rowcount == 1) {
            Patient me = (Patient)request.getSession().getAttribute("user");
            me.setAddress(address);
            me.setCurrentHealth(curHealth);
            me.setDefaultDoctorId(parseInt(defaultDoctorId));
            me.setHealthCard(hCard);
            me.setName(name);
            me.setNumberOfVisits(parseInt(numVisits));
            me.setPassword(pw);
            me.setPhoneNumber(phone);
            me.setSinNumber(parseInt(SIN));
        }
        
        String qy = update.toString();
        request.setAttribute("sentquery", qy);
        em.getTransaction().commit();
        
        profile(request, response);
    }
    
    private void info(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = EMF.createEntityManager();
        
        Object user = request.getSession().getAttribute("user");
        String healthCard = request.getParameter("healthCard");
        
        if (user.getClass() == Employee.class) {
            // check if patients doctor id == employees id
            // forward to doctors version of patient info page
            
            Employee emp = (Employee)user;

            TypedQuery<Patient> query =
                    em.createQuery("SELECT p FROM Patient p WHERE p.healthCard = :healthCard", Patient.class)
                      .setParameter("healthCard", healthCard);

            List<Patient> patientList = query.getResultList();
            
            if (patientList.isEmpty()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            } else {
                request.setAttribute("patient", patientList.get(0));

                request.getRequestDispatcher(getView("doctor/patient-info.jsp")).forward(request, response);
            }
        }
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
