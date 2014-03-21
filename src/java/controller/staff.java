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
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.*;
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
            case "/patient_info.jsp":
                 patientsinfoPage(request,response);
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
        Query query3 = em.createQuery("SELECT p.name, a.appointmentPK.dateAndTime FROM"
                + " Patient p, Appointment a, Employee e WHERE p.healthCard=a.appointmentPK.healthCard AND e.id=a.doctorId AND e.id=:id").
                setParameter("id",doc_id);
        
        List<Object[]> appointmentList = query3.getResultList();
        
        request.setAttribute("appointmentList", appointmentList);
        request.setAttribute("doctor", query1.getSingleResult().getName());
        request.getRequestDispatcher("/WEB-INF/view/staff/appointments.jsp").forward(request, response);
    }  
         private void patientsinfoPage(HttpServletRequest request, HttpServletResponse response)
                 throws ServletException, IOException{
             EntityManager em = EMF.createEntityManager();
             Patient patientProfile;
             String health_card,phone_number,address,name,current_health,password;
             int default_doctor, number_of_visits, sin_number;
             boolean has_health_card = request.getParameter("health_card")==null?false:request.getParameter("health_card").isEmpty()?false:true;
             TypedQuery<Patient> query1;
             boolean from_patient_info = request.getParameter("namePage")==null?false:request.getParameter("namePage").equals("patient_info");
             String page;
             
             if(has_health_card)
             {
                health_card=request.getParameter("health_card").isEmpty()?null:request.getParameter("health_card");
                query1 = em.createNamedQuery("Patient.findByHealthCard",Patient.class)
                    .setParameter("healthCard", health_card);   
                
                phone_number = request.getParameter("phone_number")==null?null:request.getParameter("phone_number").isEmpty()?null:request.getParameter("phone_number");
                address = request.getParameter("address")==null?null:request.getParameter("address").isEmpty()?null:request.getParameter("address");
                name = request.getParameter("name")==null?null:request.getParameter("name").isEmpty()?null:request.getParameter("name");
                default_doctor = request.getParameter("default_doctor")==null?0:request.getParameter("default_doctor").isEmpty()?null:parseInt(request.getParameter("default_doctor"));
                current_health = request.getParameter("current_health")==null?null:request.getParameter("current_health").isEmpty()?null:request.getParameter("current_health");
                password = request.getParameter("password")==null?null:request.getParameter("password").isEmpty()?null:request.getParameter("password");
                number_of_visits = request.getParameter("number_of_visits")==null?0:request.getParameter("number_of_visits").isEmpty()?null:parseInt(request.getParameter("number_of_visits"));
                sin_number = request.getParameter("sin_number")==null?0:request.getParameter("sin_number").isEmpty()?null:parseInt(request.getParameter("sin_number"));                

                if(query1.getResultList().isEmpty())
                {
                    patientProfile = new Patient(health_card,name,address,phone_number,sin_number,number_of_visits,default_doctor,current_health,password);
                    em.getTransaction().begin();
                    em.persist(patientProfile);
                    em.getTransaction().commit();
                }
                else
                {
                    patientProfile = query1.getSingleResult();
                
                    em.getTransaction().begin();
                      if(phone_number!=null&&!phone_number.isEmpty())
                          patientProfile.setPhoneNumber(phone_number);
                      if(address!=null && !address.isEmpty())
                          patientProfile.setAddress(address);
                       if(name!=null &&!name.isEmpty())
                          patientProfile.setName(name);
                       if(default_doctor>0)
                          patientProfile.setDefaultDoctorId(default_doctor);
                       if(current_health!=null&&!current_health.isEmpty())
                          patientProfile.setCurrentHealth(current_health);
                       if(password!=null&&!password.isEmpty())
                          patientProfile.setPassword(password);
                       if(number_of_visits>0)
                          patientProfile.setNumberOfVisits(number_of_visits);
                       if(sin_number>0)
                          patientProfile.setNumberOfVisits(sin_number);     
                    em.getTransaction().commit();    
                }
             }
             else
                 patientProfile = new Patient();
             
             if(from_patient_info)
                 page="/WEB-INF/view/staff/home.jsp";
             else
                 page="/WEB-INF/view/staff/patient_info.jsp";
             
             request.setAttribute("patientProfile", patientProfile);
             request.getRequestDispatcher(page).forward(request, response);
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
