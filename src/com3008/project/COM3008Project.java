// cd Desktop/COM3008/src/com/company/

package com3008.project;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
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
    
    static void databaseQuery () throws Exception {
        Connection con = null;
        Statement stmt = null;

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk/team015",
                    "team015",
                    "ea4da4e8"
            );

            stmt = con.createStatement();
            String statement = "";

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

    public static void main(String[] args) throws Exception {
        Date now = new Date();
        String pattern = "2022-04-10";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String mysqlDateString = formatter.format(now);
        System.out.println(now);
        System.out.println(formatter);
        System.out.println(mysqlDateString);
    }
}