// cd Desktop/COM3008/src/com/company/

package com3008.project;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.regex.*;

/*

Create userData Table:
    CREATE TABLE userData (userID varchar(10), displayName varchar(255), title varchar(8), forename varchar(255),
    surname varchar(255), email varchar(255), hashedPassword varchar(255), host BOOLEAN,
    superhost BOOLEAN, hostID varchar(64));
*/

public class COM3008Project {
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

    static void createGuest(int userID, String displayName, String title, String forename,
                            String surname, String email, String password) throws Exception {
        Connection con = null;
        Statement stmt = null;

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk/team015",
                    "team015",
                    "ea4da4e8"
            );

            String hashedPassword = sha256HashingAlgorithm(email + password);

            stmt = con.createStatement();
            String statement = ("INSERT INTO guestData VALUES ('" + userID + "','" + displayName + "','" +
                    title + "','" + forename + "','" + surname + "','" + email + "','" + hashedPassword + "'" +
            ")").toString();
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

    static void createHost(int hostID, String displayName, String title, String forename,
                           String surname, String email, String password) throws Exception {
        Connection con = null;
        Statement stmt = null;

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk/team015",
                    "team015",
                    "ea4da4e8"
            );

            String hashedPassword = sha256HashingAlgorithm(email + password);

            stmt = con.createStatement();
            String statement = ("INSERT INTO hostData VALUES ('" + hostID + "','" + displayName + "','" +
                    title + "','" + forename + "','" + surname + "','" + email + "','" + hashedPassword + "'," +
                    "TRUE" + ")").toString();

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

    static void createPropertyListing(int hostID, int propertyID, String shortName, String longDescript,
                                String generalLoc, String detailedLoc) throws Exception{
        Connection con = null;
        Statement stmt = null;

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk/team015",
                    "team015",
                    "ea4da4e8"
            );

            stmt = con.createStatement();
            String statement = ("INSERT INTO propertyMainData VALUES (" + hostID + "," + propertyID + ",'" +
                    shortName + "','" + longDescript + "','" + generalLoc + "','" + detailedLoc + "')").toString();
            System.out.println(statement);
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

    public static void main(String[] args) throws Exception {
        //createGuest(1, "Guest 1", "Mr.", "Guest 1",
          //      "Guest Surname", "guest@gmail.com", "password");

        //createHost(1, "Host 1", "Mr.", "Host 1",
          //      "Host Surname", "host@gmail.com", "password");

        //createPropertyListing(1, 1, "Country Cottage",
          //      "stone built cottage with off-road parking, fully equipped kitchen and garden to front",
            //    "Sheffield, United Kingdom",
              //  "Block A, Hollis Croft, Sheffield City Centre, South Yorkshire, United Kingdom, S1 4RR");
        
        
        System.out.println("Hello World");
    }
}