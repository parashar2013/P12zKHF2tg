/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import entity.AppointmentPK;
import entity.Employee;
import entity.Patient;
import entity.Visit;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.DB;
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
            throws ServletException, IOException  {
        String page = request.getPathInfo();
        if (page == null || page.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/staff/");
            return;
        }
        try{
        switch (page) {
            case "/": 
                doctorhomePage(request, response);
                return;
            case "/home": 
                doctorhomePage(request, response);
                return;
            case "/doctor_home.jsp":
                 doctorhomePage(request,response);
            case "/patients.jsp":
                 patientsPage(request,response);
            case "/patient_home.jsp":
                 patientsHomePage(request,response);
            case "/appointments.jsp":
                 appointmentsPage(request,response);
            case "/patient_info.jsp":
                 patientsinfoPage(request,response);
            case "/visit_records.jsp":
                 visitRecordsPage(request,response);
            case "/delete_appointment":
                 deleteAppointment(request,response);
            case "/insert_appointment.jsp":
                 insertAppointment(request,response); 
            case "/new_appointment":
                 insertAppointment(request,response);
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
    
        private void doctorhomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException,SQLException {        
        Employee me = (Employee)request.getSession().getAttribute("user");
        String page = request.getParameter("pg");
        
        if(page != null)
        {
            if(page.equals("appointments"))
                appointmentsPage(request,response);
            else
                patientsPage(request,response);
        }
        
       // List<Patient> patientList=DB.getPatients();
        List<Employee> doctorList = DB.getDoctorsByStaffId(me.getId());
        //Employee staff = (Employee)query.getSingleResult();
        //List<Employee> doctorList = query.getResultList();
        
        request.setAttribute("doctorList", doctorList);
        
        request.getRequestDispatcher("/WEB-INF/view/staff/doctor_home.jsp").forward(request, response);
    }
          private void patientsHomePage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException,ClassNotFoundException,SQLException  {
               ArrayList<Patient> patientList = DB.getPatients();
        request.setAttribute("patientList", patientList);
        request.getRequestDispatcher("/WEB-INF/view/staff/patient_home.jsp").forward(request, response);
    }        
         private void patientsPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException,ClassNotFoundException,SQLException  {
        int doc_id = request.getParameter("doctor_id").isEmpty()?null:parseInt(request.getParameter("doctor_id"));

        ArrayList<Patient> patientList = DB.getPatientsByDoctorId(doc_id);
        Employee doc = DB.getEmployeeById(doc_id);
        request.setAttribute("patientList", patientList);
        request.setAttribute("doctor", doc.getName());
        request.getRequestDispatcher("/WEB-INF/view/staff/patients.jsp").forward(request, response);
    }
        
         private void appointmentsPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException,ClassNotFoundException,SQLException {
        int doc_id = request.getParameter("doctor_id").isEmpty()?null:parseInt(request.getParameter("doctor_id"));
       
        Employee doc = DB.getEmployeeById(doc_id);
        
        List<Object[]> appointmentList = DB.getAppointments(doc_id);
        
        request.setAttribute("appointmentList", appointmentList);
        request.setAttribute("doctor", doc);
        request.getRequestDispatcher("/WEB-INF/view/staff/appointments.jsp").forward(request, response);
    }  
         private void patientsinfoPage(HttpServletRequest request, HttpServletResponse response)
                 throws ServletException, IOException,ClassNotFoundException,SQLException{
             Patient patientProfile;
             String health_card,phone_number,address,name,current_health,password;
             int default_doctor, number_of_visits, sin_number;
             boolean has_health_card = request.getParameter("health_card")==null?false:request.getParameter("health_card").isEmpty()?false:true;
             boolean from_patient_info = request.getParameter("namePage")==null?false:request.getParameter("namePage").equals("patient_info");
             String page;
             
             if(has_health_card)
             {
                health_card=request.getParameter("health_card").isEmpty()?null:request.getParameter("health_card");
                Patient p = DB.getPatientByHealthCard(health_card);
                
                phone_number = request.getParameter("phone_number")==null?null:request.getParameter("phone_number").isEmpty()?null:request.getParameter("phone_number");
                address = request.getParameter("address")==null?null:request.getParameter("address").isEmpty()?null:request.getParameter("address");
                name = request.getParameter("name")==null?null:request.getParameter("name").isEmpty()?null:request.getParameter("name");
                default_doctor = request.getParameter("default_doctor")==null?0:request.getParameter("default_doctor").isEmpty()?null:parseInt(request.getParameter("default_doctor"));
                current_health = request.getParameter("current_health")==null?null:request.getParameter("current_health").isEmpty()?null:request.getParameter("current_health");
                password = request.getParameter("password")==null?null:request.getParameter("password").isEmpty()?null:request.getParameter("password");
                number_of_visits = request.getParameter("number_of_visits")==null?0:request.getParameter("number_of_visits").isEmpty()?null:parseInt(request.getParameter("number_of_visits"));
                sin_number = request.getParameter("sin_number")==null?0:request.getParameter("sin_number").isEmpty()?null:parseInt(request.getParameter("sin_number"));                

                if(p==null)
                {
                        if(
                                (address!=null && !address.isEmpty())
                                && (name!=null &&!name.isEmpty())
                                && (default_doctor>0)
                                && (current_health!=null&&!current_health.isEmpty())
                                && (password!=null&&!password.isEmpty())
                                && (number_of_visits>0)
                                && (sin_number>0)
                                )
                            DB.InsertPatientInfo(health_card, name, address, phone_number, sin_number, number_of_visits, default_doctor, current_health, password);
                    patientProfile = new Patient(health_card,name,address,phone_number,sin_number,number_of_visits,default_doctor,current_health,password);
;
                }
                else
                {
                    patientProfile = DB.getPatientByHealthCard(health_card);
                
                      if(phone_number!=null&&!phone_number.isEmpty())

                      if(
                              (address!=null && !address.isEmpty())
                              && (name!=null &&!name.isEmpty())
                              && (default_doctor>0)
                              && (current_health!=null&&!current_health.isEmpty())
                              && (password!=null&&!password.isEmpty())
                              && (number_of_visits>0)
                              && (sin_number>0)
                              )
                          DB.UpdatePatientInfo(health_card, name, address, phone_number, sin_number, number_of_visits, default_doctor, current_health, password);
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
         
          private void visitRecordsPage(HttpServletRequest request, HttpServletResponse response)
                 throws ServletException, IOException,ClassNotFoundException,SQLException{
              String health_card=request.getParameter("health_card").isEmpty()?null:request.getParameter("health_card");
              String page="/WEB-INF/view/staff/visit_records.jsp";
              List<Visit> visitList = DB.getVisits(health_card);
              request.setAttribute("visitList", visitList);
              request.setAttribute("patient", DB.getPatientByHealthCard(health_card).getName());
              request.getRequestDispatcher(page).forward(request, response);
          }        
          
//          private void insertAppointment(HttpServletRequest request, HttpServletResponse response)
//                 throws ServletException, IOException,ClassNotFoundException,SQLException{
//              String health_card=request.getParameter("health_card")==null?null:request.getParameter("health_card").isEmpty()?null:request.getParameter("health_card");
//              String date_and_time_string=request.getParameter("date_and_time")==null?null:request.getParameter("date_and_time").isEmpty()?null:request.getParameter("date_and_time");
//              int doc_id = request.getParameter("doctor_id")==null?null:request.getParameter("doctor_id").isEmpty()?null:parseInt(request.getParameter("doctor_id"));
//              String page = "/WEB-INF/view/staff/insert_appointment.jsp";
//              try{
//                  if(health_card != null)
//                  {
//                      Date date_and_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH).parse(date_and_time_string);
//                      AppointmentPK app_pk = new AppointmentPK(health_card,date_and_time);
//                      DB.insertAppointment(app_pk,doc_id);
//                      appointmentsPage(request,response);
//                  }
//                  else
//                  {
//                    request.getRequestDispatcher(page).forward(request, response);
//                  }
//              }
//              catch(ParseException e)
//              {
//                  response.sendError(HttpServletResponse.SC_NOT_FOUND,"ParseException thrown" + e.getMessage());
//              }
//          }
          
           private void insertAppointment(HttpServletRequest request, HttpServletResponse response)
                 throws ServletException, IOException,ClassNotFoundException,SQLException{
              String health_card=request.getParameter("health_card")==null?null:request.getParameter("health_card").isEmpty()?null:request.getParameter("health_card");           
              int doc_id = request.getParameter("doctor_id")==null?null:request.getParameter("doctor_id").isEmpty()?null:parseInt(request.getParameter("doctor_id"));
              String page = "/WEB-INF/view/staff/insert_appointment.jsp";
              request.setAttribute("doctor_id",doc_id);
              try{
                  if(health_card != null)
                  {
                      int year = request.getParameter("year")==null?null:request.getParameter("year").isEmpty()?null:parseInt(request.getParameter("year"));
                      int month = request.getParameter("month")==null?null:request.getParameter("month").isEmpty()?null:parseInt(request.getParameter("month"));
                      int day = request.getParameter("day")==null?null:request.getParameter("day").isEmpty()?null:parseInt(request.getParameter("day"));
                      int hour = request.getParameter("hour")==null?null:request.getParameter("hour").isEmpty()?null:parseInt(request.getParameter("hour"));
                      int minute = request.getParameter("minute")==null?null:request.getParameter("minute").isEmpty()?null:parseInt(request.getParameter("minute"));
                      String date_and_time_string = String.format("%4d-%2d-%2d %2d:%2d:00.000",year,month,day,hour,minute);
                      
                      DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH);
                      FORMATTER.setLenient(false);
                      Date date_and_time = FORMATTER.parse(date_and_time_string);
                      Date today = new Date();
                      AppointmentPK app_pk = new AppointmentPK(health_card,new Date(today.getTime()));
                      DB.insertAppointment(app_pk,doc_id);
                      appointmentsPage(request,response);
                  }
                  else
                  {
                    
                    request.getRequestDispatcher(page).forward(request, response);
                  }
              }
              catch(ParseException e)
              {
                  response.sendError(HttpServletResponse.SC_NOT_FOUND,"ParseException thrown" + e.getMessage());
              }
          }         
          
          private void deleteAppointment(HttpServletRequest request, HttpServletResponse response)
                 throws ServletException, IOException,ClassNotFoundException,SQLException{
              String health_card=request.getParameter("health_card").isEmpty()?null:request.getParameter("health_card");
              String date_and_time_string=request.getParameter("date_and_time").isEmpty()?null:request.getParameter("date_and_time");
              try{
                  Date date_and_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.ENGLISH).parse(date_and_time_string);
                  AppointmentPK app_pk = new AppointmentPK(health_card,date_and_time);
                  DB.deleteAppointment(app_pk);
                  appointmentsPage(request,response);
              }
              catch(ParseException e)
              {
                  response.sendError(HttpServletResponse.SC_NOT_FOUND,"ParseException thrown" + e.getMessage());
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
