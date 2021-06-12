
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ManageData 
{
    
    private HashMap<String, Room> properties = new HashMap<>();
    private HashMap<String, ImageView> propertyImages = new HashMap<>();
    
    private ArrayList<RentalRecord> recordList = new ArrayList<>();
    
    private static final String IMAGE_NOT_FOUND_FILE_NAME = "0.jpg";
    
    public ManageData(){}
    public ManageData(String fileName)
    {
         
        readFile(fileName);
        
        System.out.println(fileName);
    }
    
    public boolean addRoomProperty(Room room, ImageView imageView )
    {
        if( properties.containsKey(room.roomId))
            return false;
            
        properties.put(room.roomId, room);
        propertyImages.put(room.roomId, imageView);
        return true;
    }
    
   
    public Room getRoomProperty(String propertyID)
    {
        return properties.get(propertyID);
    }
    
    public ImageView getRoomImageView(String propertyID)
    {
        return propertyImages.get(propertyID);
    }
    
    public boolean addRoomProperty(String inputLine)
    {
       String tokens[] = inputLine.split(":");       
       Room newProperty = null;
       ImageView imageView = null;
       System.out.println("input line: "+inputLine); 
       
        if( tokens[0].charAt(0) == 'R' )
        {
            double price = 59.0;
            double lateFees = 0.0;
            price = 59.0;
            if(Integer.parseInt(tokens[1]) == 2)
            	price = 99.0;
            else if(Integer.parseInt(tokens[1]) == 4)
            	price = 199;
            	
            lateFees = price * 1.35;
            
            if(tokens[3].equalsIgnoreCase("Available"))
                newProperty = new StandardRoom(tokens[0],Integer.parseInt(tokens[1]),tokens[2],"StandardRoom","Available",price,lateFees,tokens[4]);
            else if(tokens[3].equalsIgnoreCase("Rented"))
                newProperty = new StandardRoom(tokens[0],Integer.parseInt(tokens[1]),tokens[2],"StandardRoom","Rented",price,lateFees,tokens[4]);
            else if(tokens[3].equalsIgnoreCase("Maintenance"))
                newProperty = new StandardRoom(tokens[0],Integer.parseInt(tokens[1]),tokens[2],"StandardRoom","Maintenance",price,lateFees,tokens[4]);

            try
            {
                imageView = new ImageView(new Image(new FileInputStream("images/" + tokens[4] )));
            }
            catch(FileNotFoundException e)
            {
                Alert couldNotOpenImageFile;
                couldNotOpenImageFile = new Alert(Alert.AlertType.ERROR, "Cannot open image file : " + tokens[4], null);
                couldNotOpenImageFile.showAndWait();
                
                
                // load no image file
                try
                {
                    imageView = new ImageView(new Image(new FileInputStream("images/" + IMAGE_NOT_FOUND_FILE_NAME )));
                }
                catch(FileNotFoundException e1)
                {
                    Alert couldNotOpenDefaultImageFile;
                    couldNotOpenDefaultImageFile = new Alert(Alert.AlertType.ERROR, "Cannot open NO_PHOTO image file !", null);
                    couldNotOpenDefaultImageFile.showAndWait();
                    
                    return false;
                    
                }
            }
                    
        }
        else
        {
            
            String lM = tokens[4];
            double price = 999.0;
            double lateFees = 1099.0;
            if(tokens[3].equalsIgnoreCase("Available"))
                newProperty = new Suite(tokens[0],6,tokens[2],"Suite","Available",Integer.parseInt(lM.split("/")[0]),Integer.parseInt(lM.split("/")[1]), Integer.parseInt(lM.split("/")[2]),price,lateFees,tokens[5]);
            else if(tokens[3].equalsIgnoreCase("Rented"))
                newProperty = new Suite(tokens[0],6,tokens[2],"Suite","Rented",Integer.parseInt(lM.split("/")[0]),Integer.parseInt(lM.split("/")[1]), Integer.parseInt(lM.split("/")[2]),price,lateFees,tokens[5]);
            else if(tokens[3].equalsIgnoreCase("Maintenance"))
                newProperty = new Suite(tokens[0],6,tokens[2],"Suite","Maintenance",Integer.parseInt(lM.split("/")[0]),Integer.parseInt(lM.split("/")[1]), Integer.parseInt(lM.split("/")[2]),price,lateFees,tokens[5]);
            
            try
            {
                imageView = new ImageView(new Image(new FileInputStream("images/" + tokens[5] )));
            }
            catch(FileNotFoundException e)
            {
                Alert couldNotOpenImageFile;
                couldNotOpenImageFile = new Alert(Alert.AlertType.ERROR, "Cannot open image file : " + tokens[5], null);
                couldNotOpenImageFile.showAndWait();
                
                try
                {
                    imageView = new ImageView(new Image(new FileInputStream("images/" + IMAGE_NOT_FOUND_FILE_NAME )));
                }
                catch(FileNotFoundException e1)
                {
                    Alert couldNotOpenDefaultImageFile;
                    couldNotOpenDefaultImageFile = new Alert(Alert.AlertType.ERROR, "Cannot open NO_PHOTO image file!", null);
                    couldNotOpenDefaultImageFile.showAndWait();
                    return false;
                    
                }
            }
        }
        addRoomProperty(newProperty, imageView);
        
        return true;
    }
    
    public boolean addRoomRentalRecord(String inputLine)
    {
        String tokens[] = inputLine.split(":");
        
        String[] date = tokens[1].split("/");
        
        System.out.println("Integer.parseInt(date[0])"+Integer.parseInt(date[0]));
        recordList.add(new RentalRecord(tokens[0],Integer.parseInt(date[0]),Integer.parseInt(date[1]), Integer.parseInt(date[2]),Integer.parseInt(tokens[2]) ));
        return true;
    }
    
    public boolean readFile(String fileName)
    {
        
        
        try
        {
            BufferedReader br = new BufferedReader( new FileReader(new File("input.txt")));
            String line = br.readLine();
        
            while( line != null )
            {

                String tokens[] = line.split(":");
                if( tokens.length == 3 )
                {
                    addRoomRentalRecord(line);
                }
                else
                {
                    addRoomProperty(line);
                }
                line = br.readLine();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            Alert couldNotOpenFileToLoadDefaultData = new Alert(Alert.AlertType.ERROR, "Cannot open file to load default data!", null);
            couldNotOpenFileToLoadDefaultData.showAndWait();
            return false;
        } 
       
        return true;
    }
    
    public Collection<Room> getAllProperties()
    {
        return properties.values();
    }
    
     public Collection<RentalRecord> getRecordList()
    {
        return recordList;
    }
    
    public Room getPropertyForImageView(ImageView imageView)
    {
        for( String propertyID : propertyImages.keySet() )
        {
            if( propertyImages.get(propertyID) == imageView )
                return properties.get( propertyID );
        }
        return null;
    }        
}

