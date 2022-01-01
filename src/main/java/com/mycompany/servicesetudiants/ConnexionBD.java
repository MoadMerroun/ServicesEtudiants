/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servicesetudiants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Surface Pro
 */
public class ConnexionBD {
    public static Connection connecterbd(){
        try {
            Connection conn=null;
            conn=DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11462413","sql11462413","hY75MSn6KJ");
            return conn;
        } catch (SQLException ex) {
            Logger.getLogger(ConnexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
