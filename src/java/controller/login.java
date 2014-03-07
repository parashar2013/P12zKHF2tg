/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import lib.md5;
import entity.Employee;
import entity.Patient;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
        response.setContentType("text/html;charset=UTF-8");
        String url,login_id,password,login_type;
        PrintWriter out = response.getWriter();
        login_id = request.getParameter("login_id");
        password = md5.md5(request.getParameter("login_password"));
        login_type = request.getParameter("login_type");
 
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("HospitalDBPU");
        
        EntityManager em = emf.createEntityManager();
        
             
        if(!login_id.isEmpty() && !password.isEmpty())
        {
            Query q1 ;
            if(login_type.equals("employee"))
            {
                q1 = em.createQuery("SELECT e  FROM Employee e WHERE e.id=" + login_id + " AND e.password='" + password+"'",Employee.class);
            }
            else
            {
                q1 = em.createQuery("SELECT e  FROM Patient e WHERE e.health_card=" + login_id + " AND e.password='" + password+"'",Patient.class);
                
            }
                
            try {
                
                if(login_type.equals("employee"))
                {
                    Employee emp = (Employee)q1.getSingleResult();
                    if(emp.getRole().equals("Doctor"))
                    {
                        url = "/Doctor_home.jsp";
                    }
                    else if (emp.getRole().equals("Staff"))
                    {
                        url = "/Staff_home.jsp";
                    }
                    else if (emp.getRole().equals("FO"))
                    {
                        url = "/FO_home.jsp";
                    }
                    else
                        url = "/error.jsp";
                }
                else
                    url = "/Patient_home.jsp";
            }
            catch (Exception e) {
            request.setAttribute("exception", e);
                url = "/error.jsp";
            }
        }
        else
        {
            url = "/error.jsp";
        }

        request.getServletContext().getRequestDispatcher(url).forward(request, response);

        
        // Session example
        /*
            UserData u = new UserData();
            u.setFavColour(request.getParameter("name"));
            u.setUserName(request.getParameter("color"));
            
            request.getSession().setAttribute("userData", u);
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
