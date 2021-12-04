/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class createListing {
    static void createNewListing() throws Exception {
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
            String runQuery = ("SELECT shortName, propertyID FROM propertyMainData WHERE hostID =").toString();
            System.out.println("Query Processed!");
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            if(con != null) con.close();System.out.println("Connection Closed");
            if(stmt != null) stmt.close();
        }
    }
}
