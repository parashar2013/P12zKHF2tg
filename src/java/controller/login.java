/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import lib.utilities.*;
import model.*;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.sql.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lib.DB;

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
        String login_id;
        String hCard;
        User user = null;
        
        login_id = request.getParameter("login_id").isEmpty() ? null : request.getParameter("login_id");
        //password = utilities.md5(request.getParameter("login_password"));
        password = request.getParameter("login_password");
        login_type = request.getParameter("login_type");
        
        Connection con = DB.getConnection();
             
        if (login_id == null || password.isEmpty()) {
            sendBackToLoginPage(request, response);
            return;
        } else {
            if (login_type.equals("employee"))
            {
                try {
                    PreparedStatement stmt = con.prepareStatement("SELECT name, id, role FROM Employee WHERE id = ? AND password = ?");
                    stmt.setInt(1, parseInt(login_id));
                    stmt.setString(2, password);
                    ResultSet result = stmt.executeQuery();
                    
                    if (result.next()) {
                        String name = result.getString("name");
                        String role = result.getString("role");
                        String id = result.getString("id");
                        
                        user = new User(name, role, id);
                    } else {
                        sendBackToLoginPage(request, response);
                        return;
                    }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                try {
                    PreparedStatement stmt = con.prepareStatement("SELECT name, health_card FROM Patient WHERE health_card = ? AND password = ?");
                    stmt.setString(1, login_id);
                    stmt.setString(2, password);
                    ResultSet result = stmt.executeQuery();
                    
                    if (result.next()) {
                        String name = result.getString("name");
                        String role = "Patient";
                        String id = result.getString("health_card");
                        
                        user = new User(name, role, id);
                    } else {
                        sendBackToLoginPage(request, response);
                        return;
                    }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
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
