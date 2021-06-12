package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    
   
   public static void main(String[] args)
   {
       getConnection();
   }
    
    public static void getConnection(){
   	
        try
        {
    		String url = "jdbc:sqlite:room.db";
            Connection con = DriverManager.getConnection(url);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Rooms (roomId text NOT NULL, beds integer NOT NULL, featureSummary text NOT NULL,"
                + "roomType text NOT NULL, roomStatus text NOT NULL, dd integer NOT NULL, mm integer NOT NULL, yyyy integer NOT NULL, fee real NOT NULL, lateFee real NOT NULL, roomImage text NOT NULL);");
        
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS HiringRecord (recordId text NOT NULL, dd integer NOT NULL, mm integer NOT NULL, yyyy integer NOT NULL, days integer NOT NULL) ;");
            System.out.println(con);
        
         }
        catch(Exception exp)
        {
            System.out.println("ERROR: "+exp.getMessage());
        }
    }
}

