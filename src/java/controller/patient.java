/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.Appointment;
import entity.Employee;
import entity.Patient;
import entity.Visit;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.EMF;
import static lib.utilities.getView;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;

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
            case "/info":
                info(request, response);
                return;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void homePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = EMF.createEntityManager();
        
        Patient me = (Patient)request.getSession().getAttribute("user");
        
        Query apps = em.createNativeQuery("SELECT e.name, a.date_and_time FROM Appointment a LEFT JOIN Employee e "
                + "ON a.doctor_id = e.id WHERE a.health_card = " + me.getHealthCard());
        Query visits = em.createNativeQuery("SELECT e.name, v.diagnosis, v.prescriptions, v.date_and_time FROM Visit v LEFT JOIN Employee e "
                + "ON v.doctor_id = e.id WHERE v.health_card = " + me.getHealthCard());
        
        List appointmentList = apps.getResultList();
        List visitList = visits.getResultList();
        
        request.setAttribute("appointmentList", appointmentList);
        request.setAttribute("visitList", visitList);
        
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
        
        Query update = em.createQuery("UPDATE Patient p SET p.address = :address, "
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
        
        request.setAttribute("updateMsg", "Your profile has been updated!<br><br>");
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

            Query query = em.createNativeQuery("SELECT health_card, p.name AS name, address, phone_number, current_health, e.name AS default_doctor_name "
                    + "FROM Patient p JOIN Employee e ON p.default_doctor_id = e.id "
                    + "WHERE p.health_card = ?")
                .setParameter(1, healthCard);

            query.setHint(QueryHints.RESULT_TYPE, ResultType.Map); 
            List patientList = query.getResultList();
            
            if (patientList.isEmpty()){
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            
            Map patient = (Map)patientList.get(0);
            
            Query visits = em.createNativeQuery("SELECT e.name, v.diagnosis, v.prescriptions, v.date_and_time "
                    + "FROM Visit v JOIN Employee e "
                    + "ON v.doctor_id = e.id WHERE v.health_card = ?")
                .setParameter(1, healthCard);

            List visitList = visits.getResultList();
            
            
            //doctors who have permission
            Query doctorsWithPermission = em.createNativeQuery("SELECT name, id FROM Employee e "
                    + "WHERE role = 'doctor' AND id != ? "
                    + "AND e.id IN (SELECT doctor_id FROM Doc_Patient dp "
                        + "WHERE dp.patient_health_card = ?)")
                    .setParameter(1, emp.getId())
                    .setParameter(2, healthCard);
            
            doctorsWithPermission.setHint(QueryHints.RESULT_TYPE, ResultType.Map);            
            List doctorsWithPermissionList = doctorsWithPermission.getResultList();
            
            // doctors not already granted permission to this patient
            Query doctorsWithoutPermission = em.createNativeQuery("SELECT name, id FROM Employee e "
                + "WHERE role = 'doctor' AND id != ? "
                + "AND e.id NOT IN (SELECT doctor_id FROM Doc_Patient dp "
                    + "WHERE dp.patient_health_card = ?)")
                .setParameter(1, emp.getId())
                .setParameter(2, healthCard);
            
            doctorsWithoutPermission.setHint(QueryHints.RESULT_TYPE, ResultType.Map);            
            List doctorsWithoutPermissionList = doctorsWithoutPermission.getResultList();
            
            request.setAttribute("visitList", visitList);
            request.setAttribute("doctorsWithPermissionList", doctorsWithPermissionList);
            request.setAttribute("doctorsWithoutPermissionList", doctorsWithoutPermissionList);
            request.setAttribute("patient", patient);
            request.getRequestDispatcher(getView("doctor/patient-info.jsp")).forward(request, response);
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
