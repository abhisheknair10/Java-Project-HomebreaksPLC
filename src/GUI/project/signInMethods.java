package GUI.project;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.regex.Pattern;
import javax.swing.plaf.nimbus.State;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abhisheknair
 */
public class signInMethods {
    
    static String sha256HashingAlgorithm(String rawString) throws Exception{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(rawString.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xFF & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    static boolean verifySignIn(String email, String hashedPassword) throws Exception {
        Connection con = null;
        Statement stmt = null;
        boolean correctCredentials = false;

        try {
            System.out.println("Connection Opened");
            con = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk/team015",
                    "team015",
                    "ea4da4e8"
            );

            stmt = con.createStatement();
            String runQuery = ("SELECT hashedPassword FROM userData WHERE userID = '" + email + "'").toString();
            System.out.println("Query Processed!");
            ResultSet res = stmt.executeQuery(runQuery);
            
            if(res.next()){
                String dbpassword = res.getString(1);
                if(dbpassword.equals(hashedPassword)){
                    correctCredentials = true;
                }
            }
            else{
                 correctCredentials = false;
            }
            res.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            if(con != null) con.close();System.out.println("Connection Closed");
            if(stmt != null) stmt.close();
        }
        return correctCredentials;
    }
    
    static void setSignInType(String EMAIL, String setValue) throws Exception {
        Connection con = null;
        Statement stmt = null;

        try {
            System.out.println("Connection Opened");
            con = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk/team015",
                    "team015",
                    "ea4da4e8"
            );

            stmt = con.createStatement();
            String statement = ("UPDATE userData SET signInAsHost = " + setValue + " WHERE userID = '" + EMAIL + "'").toString();

            stmt.executeUpdate(statement);
            System.out.println("Query Processed!");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if(con != null) con.close();
            System.out.println("Connection Closed");
        }
    }
    
    static boolean emailValidator(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) return false;
        return pat.matcher(email).matches();
    }
}
