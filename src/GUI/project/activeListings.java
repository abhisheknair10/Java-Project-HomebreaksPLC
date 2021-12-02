/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 */
package GUI.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author abhisheknair
 */
public class activeListings {
    
    public static String [] queryListedProperties(String email) throws Exception{
        Connection con = null;
        Statement stmt = null;
        ArrayList<String> propertyArrays = new ArrayList<String>();

        try {
            System.out.println("Connection Opened");
            con = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk/team015",
                    "team015",
                    "ea4da4e8"
            );

            stmt = con.createStatement();
            String runQuery = ("SELECT shortName, propertyID FROM propertyMainData WHERE hostID = '" + email + "'").toString();
            System.out.println("Query Processed!");
            ResultSet res = stmt.executeQuery(runQuery);
            
            while(res.next()){
                propertyArrays.add(res.getString(1) + " @ " + res.getInt(2));
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
        String str[] = new String[propertyArrays.size()];
        for (int j = 0; j < propertyArrays.size(); j++) {str[j] = propertyArrays.get(j);}
        return str;
    }
    
    public static void deleteListing(String value, String hostID) throws SQLException{
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
            String [] valueSplit = value.split(" @ ");
            String statement1 = ("DELETE FROM propertyMainData WHERE hostID = '" 
                    + hostID + "' AND propertyID = '" + valueSplit[1] + "'").toString();
            
            String statement2 = ("DELETE FROM bathroomData WHERE hostID = '" 
                    + hostID + "' AND propertyID = '" + valueSplit[1] + "'").toString();
            
            String statement3 = ("DELETE FROM bedData WHERE hostID = '" 
                    + hostID + "' AND propertyID = '" + valueSplit[1] + "'").toString();
            
            String statement4 = ("DELETE FROM facilites WHERE hostID = '" 
                    + hostID + "' AND propertyID = '" + valueSplit[1] + "'").toString();
            
            String statement5 = ("DELETE FROM reviewData WHERE hostID = '" 
                    + hostID + "' AND propertyID = '" + valueSplit[1] + "'").toString();

            stmt.executeUpdate(statement1);
            stmt.executeUpdate(statement2);
            stmt.executeUpdate(statement3);
            stmt.executeUpdate(statement4);
            stmt.executeUpdate(statement5);
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
    
    public static String [] lookUpProperty(String value, String hostID) throws SQLException {
        Connection con = null;
        Statement stmt = null;
        ArrayList<String> propertyData = new ArrayList<String>();

        try {
            System.out.println("Connection Opened");
            con = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk/team015",
                    "team015",
                    "ea4da4e8"
            );

            stmt = con.createStatement();
            String runQuery = ("SELECT * FROM propertyMainData "
                    + "WHERE hostID = '" + hostID + "' AND propertyID = '" + value.split(" @ ")[1] + "'").toString();
            System.out.println("Query Processed!");
            ResultSet res = stmt.executeQuery(runQuery);
            
            if(res.next()){
                propertyData.add(res.getString(3));
                propertyData.add(res.getString(5));
                propertyData.add(String.valueOf(res.getInt(7)));
            }
            else{
                ;
            }
            res.close();
            
            stmt = con.createStatement();
            runQuery = ("SELECT * FROM bedData WHERE hostID = '" + hostID + "' AND "
                    + "propertyID = '" + value.split(" @ ")[1] +"'").toString();
            System.out.println("Query Processed!");
            res = stmt.executeQuery(runQuery);
            int bedrooms = 0;
            int maxGuests = 0;
            
            while(res.next()){
                bedrooms += 1;
                if(res.getString(4).equals("single")){
                    maxGuests += 1;
                }
                else{
                    maxGuests += 2;
                }
                if(res.getString(5).equals("single")){
                    maxGuests += 1;
                }
                else{
                    maxGuests += 2;
                }
            }
            
            res.close();
            
            propertyData.add(String.valueOf(bedrooms));
            propertyData.add(String.valueOf(maxGuests));
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            if(con != null) con.close();System.out.println("Connection Closed");
            if(stmt != null) stmt.close();
        }
        
        String str[] = new String[propertyData.size()];
        for (int j = 0; j < propertyData.size(); j++) {str[j] = propertyData.get(j);}
        return str;
    }
}
