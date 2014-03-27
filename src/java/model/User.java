/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Parashar
 */
public class User {
    private String name;
    private String role;
    private String id;
    
    public User(String name, String role, String id) {
        this.name = name;
        this.role = role;
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getRole() {
        return role;
    }
    
    public String getId() {
        return id;
    }
    
}
