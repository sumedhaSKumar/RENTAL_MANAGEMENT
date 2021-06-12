package controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import model.StandardRoom;
import model.util.DateTime;
import model.ManageData;
import model.Suite;
import model.Room;
import model.RentalRecord;

public class FXMLDocumentController implements Initializable {
    
    ArrayList<Room> roomDataList = new ArrayList<>();
    ArrayList<RentalRecord> hiringRecordDataList = new ArrayList<>(); 
    
    
    private static final String NO_IMAGE = "0.jpg";
    @FXML
    private ListView<ImageView> roomImageList = new ListView<>();
    private ManageData manageData ;
    
    private ArrayList<ImageView> imagesRoom = new ArrayList<>();
   
     String notAvailable = "0.jpg";       
    
    @FXML
    private Pane panel1;
    
    @FXML
    private Pane panel2;
    
    @FXML 
    private TextField idText;
    
    @FXML 
    private TextField roomFeaturesText;
     
    @FXML 
    private TextField roomTypeText;
      
    @FXML 
    private TextField bedroomText;
    
     @FXML 
    private TextField maintenanceDateText;
    
    @FXML
    private Label imageName;
    
    @FXML
    private TextArea textArea;
    
    
    
    @FXML
    private ComboBox<String> comboBoxPropertyType;
    
    @FXML
    private ComboBox<String> comboBoxNumberOfBedroom;
    
    @FXML
    private ComboBox<String> comboBoxPropertyStatus;
    
    @FXML
    private void addProperty(ActionEvent event) {
        panel1.setVisible(true);
        textArea.setText("");
    }
    
    @FXML
    private void rentProperty(ActionEvent event) {
        textArea.setText("");
        panel1.setVisible(false);
        try
        {
            TextInputDialog inputDialog;
            boolean validationCheck = false;
            String pId = "";
            do
            {
                inputDialog = new TextInputDialog("");
                inputDialog.setTitle("Input");
                inputDialog.setHeaderText("");
                inputDialog.setContentText("Please Enter Room Id: ");
                pId = inputDialog.showAndWait().get();
                if(pId.trim().length() > 0)
                    validationCheck = false;
                else
                    validationCheck = true;
            }while(validationCheck);

            boolean isRoomExist = false;
           
            Room room = null;
            for(int b = 0;b< roomDataList.size();b++) 
            {
            	Room rm = roomDataList.get(b);
                if (rm.getRoomId().equals(pId)) {
                	isRoomExist = true;
                    room = rm;
                    break;
                }
            }
            if (isRoomExist == true) 
            {
            	String cId = "";
                do
                {
                inputDialog = new TextInputDialog("");
                inputDialog.setTitle("Input");
                inputDialog.setHeaderText("");
                inputDialog.setContentText("Please Customer ID: ");
                cId = inputDialog.showAndWait().get();
                if(cId.trim().length() > 0)
                    validationCheck = false;
                else
                    validationCheck = true;
                
                }while(validationCheck);
                
                String rDate = "";
                Date date = null;
                do{
                    inputDialog = new TextInputDialog("");
                    inputDialog.setTitle("Input");
                    inputDialog.setHeaderText("");
                    inputDialog.setContentText("Rent Date (dd/mm/yyyy): ");
                    rDate = inputDialog.showAndWait().get();
                    if(rDate.trim().length() == 10)
                    {
                        validationCheck = false;
                        try {
                        	date = new SimpleDateFormat("DD/MM/YYYY").parse(rDate);
                        } catch (ParseException pe) {
                            System.out.println("Entered Rent date format is wrong, please enter correct format as per mention");
                            validationCheck = true;
                        }
                    }
                    else
                        validationCheck = true;
                }while(validationCheck);
                
                String[] dateSplit = rDate.split("/");
                
                int dd = Integer.parseInt(dateSplit[0]);
                int mm = Integer.parseInt(dateSplit[1]);
                int yyyy = Integer.parseInt(dateSplit[2]);
                
                
                int d = 0;
                do{
                    inputDialog = new TextInputDialog("");
                    inputDialog.setTitle("Input");
                    inputDialog.setHeaderText("");
                    inputDialog.setContentText("How Many Days? : ");
                    d = Integer.parseInt(inputDialog.showAndWait().get());
                    try {
                    	if(d<0)
                        {
                            validationCheck = true;
                        }
                        else
                            validationCheck = false;
                    } catch (NumberFormatException nfe) {
                        System.out.println("Number of days cann't take any alphabets");
                        validationCheck = false;
                    }
                    
                }while(validationCheck);
                
                
                
                
                boolean check = room.rent(cId, new DateTime(dd, mm + 1, yyyy), d); 
                if (check == true) {
                   
                    Alert rt = new Alert(Alert.AlertType.INFORMATION);
                    rt.setTitle("Input");
                    rt.setHeaderText(null);
                    rt.setContentText("Room Id: " + pId + " has been rented to customer " + cId );
                    rt.showAndWait();
                    
                    textArea.setText("");
                    textArea.setText(room.getDetails());
                    
                } else {
                    System.out.println();
                    Alert rt = new Alert(Alert.AlertType.INFORMATION);
                    rt.setTitle("Input");
                    rt.setHeaderText(null);
                    rt.setContentText("Room Id: " + pId + " could not be rented to customer " + cId);
                    rt.showAndWait();
                }
               

            }
            else
            {
                 Alert rt = new Alert(Alert.AlertType.INFORMATION);
                    rt.setTitle("Input");
                    rt.setHeaderText(null);
                    rt.setContentText(pId +" not found");
                    rt.showAndWait();

            }

            
        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }
    }
    
    
    @FXML
    private void returnProperty(ActionEvent event) {
        textArea.setText("");
        panel1.setVisible(false);
         try
      {
          TextInputDialog inputDialog;
          boolean validationCheck = false;
          String pId = "";
        do{
          inputDialog = new TextInputDialog("");
        inputDialog.setTitle("Input");
        inputDialog.setHeaderText("");
        inputDialog.setContentText("Enter Room id: ");
        pId = inputDialog.showAndWait().get();
        if(pId.trim().length() > 0)
            validationCheck = false;
        else
            validationCheck = true;
        }while(validationCheck);
        
        
        boolean isRoomExist = false;
        
        Room room = null;
        for(int b = 0;b< roomDataList.size();b++) 
        {
        	Room rm = roomDataList.get(b);
            if (rm.getRoomId().equals(pId)) {
            	isRoomExist = true;
                room = rm;
                break;
            }
        }
        if (isRoomExist == true) {
        	
        	String rDate = "";
            do{
            inputDialog = new TextInputDialog("");
            inputDialog.setTitle("Input");
            inputDialog.setHeaderText("");
            inputDialog.setContentText("Enter Return Date (dd/mm/yyyy): ");
            rDate = inputDialog.showAndWait().get();
            if(rDate.trim().length() == 10)
                validationCheck = false;
            else
                validationCheck = true;
            }while(validationCheck);
            
            String dateSplit[] = rDate.split("/");
            int dd = Integer.parseInt(dateSplit[0]);
            int mm = Integer.parseInt(dateSplit[1]);
            int yyyy = Integer.parseInt(dateSplit[2]);
            boolean check = room.returnRoom(new DateTime(dd, mm, yyyy));
            if (check == true) {
                Alert rt = new Alert(Alert.AlertType.INFORMATION);
                rt.setTitle("Input");
                rt.setHeaderText(null);
                rt.setContentText("Room Id:  " + pId + " has been returned.");
                rt.showAndWait();
                textArea.setText("");
                textArea.setText(room.getDetails());
            } else {
                System.out.println();
                Alert rt = new Alert(Alert.AlertType.INFORMATION);
                rt.setTitle("Input");
                rt.setHeaderText(null);
                rt.setContentText("Room Id:  " + pId + " could not be returned.");
                rt.showAndWait();
            }
        }
        else
        {   Alert rr = new Alert(Alert.AlertType.ERROR);
            rr.setTitle("Error");
            rr.setHeaderText(null);
            rr.setContentText(pId +" not found ");
            rr.showAndWait();
        }
      }catch(Exception ex){
    	  System.out.println("Error==============="+ex);
      }
    }
    
    @FXML
    private void propertyMaintenance(ActionEvent event) {
        textArea.setText("");
        panel1.setVisible(false);
        try
       {
           if(roomDataList.size() >0)
           {
           TextInputDialog inputDialog;
           boolean validationCheck = false;
           String pId = "";
           do{
            inputDialog = new TextInputDialog("");
            inputDialog.setTitle("Input");
            inputDialog.setHeaderText("");
            inputDialog.setContentText("Enter Room id: ");
            pId = inputDialog.showAndWait().get();
            if(pId.trim().length() > 0)
                validationCheck = false;
            else
                validationCheck = true;
           }while(validationCheck);
           
           boolean isRoomExist = false;
           
           Room room = null;
           for(int b = 0;b< roomDataList.size();b++) 
           {
           	Room rm = roomDataList.get(b);
               if (rm.getRoomId().equals(pId)) {
               	isRoomExist = true;
                   room = rm;
                   break;
               }
           }
           if (isRoomExist == true) {
        	   room.performMaintenance();
           }
           else
           {   Alert rr = new Alert(Alert.AlertType.ERROR);
               rr.setTitle("Error");
               rr.setHeaderText(null);
               rr.setContentText(pId +" not found ");
               rr.showAndWait();
           }
          }
           else
           {
               Alert e = new Alert(Alert.AlertType.ERROR);
                    e.setTitle("Error");
                    e.setHeaderText(null);
                    e.setContentText("No Property");
                    e.showAndWait();
           }
       }
       catch(Exception exp)
       {}
    }
    
    @FXML
    private void completeMaintenance(ActionEvent event) {
        textArea.setText("");
        panel1.setVisible(false);
         try
       {
           boolean validationCheck = false;
            TextInputDialog inputDialog;
         String pId = "";
         do{   
            inputDialog = new TextInputDialog("");
           inputDialog.setTitle("Input");
           inputDialog.setHeaderText("");
           inputDialog.setContentText("Enter Room id: ");
           pId = inputDialog.showAndWait().get();
           if(pId.trim().length()>0)
               validationCheck = false;
           else
               validationCheck = true;
         }while(validationCheck);
         
         boolean isRoomExist = false;
         
         Room room = null;
         for(int b = 0;b< roomDataList.size();b++) 
         {
         	Room rm = roomDataList.get(b);
             if (rm.getRoomId().equals(pId)) {
             	isRoomExist = true;
                 room = rm;
                 break;
             }
         }
         if (isRoomExist == true) {
        	 
        	 String com = "";
           do{
              inputDialog = new TextInputDialog("");
              inputDialog.setTitle("Input");
              inputDialog.setHeaderText("");
              inputDialog.setContentText("Maintenance completion date (dd/mm/yyyy): ");
              com = inputDialog.showAndWait().get();
              if(com.trim().length() == 10)
              {
                  validationCheck = false;
                  try {                                         
                  	Date date = new SimpleDateFormat("DD/MM/YYYY").parse(com);
                  } catch (ParseException pe) {
//                      System.out.println();
                      Alert rr = new Alert(Alert.AlertType.ERROR);
                      rr.setTitle("Error");
                      rr.setHeaderText(null);
                      rr.setContentText("Please enter correct date as per mention date format");
                      rr.showAndWait();
                      validationCheck = true;
                  }
              }
              else
                  validationCheck = true;
           }while(validationCheck);
           
           String[] dateSplit = com.split("/");
           int dd = Integer.parseInt(dateSplit[0]);
           int mm = Integer.parseInt(dateSplit[1]);
           int yyyy = Integer.parseInt(dateSplit[2]);
           room.completeMaintenance(new DateTime(dd, mm, yyyy));
           
         }
         else
         {   Alert rr = new Alert(Alert.AlertType.ERROR);
             rr.setTitle("Error");
             rr.setHeaderText(null);
             rr.setContentText(pId +" not found ");
             rr.showAndWait();
         }
       }
       catch(Exception exp)
       {}
    }
    
    @FXML
    private void displayProperties(ActionEvent event) {
        textArea.setText("");
        panel1.setVisible(false);
        textArea.setText("");
        try{
        	for(Room r : roomDataList)
  		  {
        		textArea.appendText(r.getDetails()+"\n=======================\n");
  			 
  		  }
            
        }catch(Exception exp)
        {}
    }
    
    @FXML
    private void exit(ActionEvent event) {
        textArea.setText("");
        panel1.setVisible(false);
         try{
        	 System.out.println("==dddd===");
        databaseData();
        System.exit(0);
         }
         catch(Exception ex){
        	 System.out.println("====="+ex);
        	 System.exit(0);
         }
        
    }
    
    
    
    @FXML
    private void chooseImage(ActionEvent event) {
        textArea.setText("");
       try
       {
        FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);
            if (file != null) {

            	notAvailable = file.getName();
                imageName.setText(notAvailable);
                
                System.out.println("images name: "+notAvailable);
            }
            else
            {
            	notAvailable = "0.jpg";
                imageName.setText("no image");
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("File save cancelled");
                alert.showAndWait();
            }
       }
       catch(Exception ex)
       {}
    }
    
     @FXML
    private void submitProperty(ActionEvent event) {
        textArea.setText("");
        try{
        String id = idText.getText().trim();
        String features = roomFeaturesText.getText().trim();
        int bedrooms = Integer.parseInt(bedroomText.getText().trim());
        String propertyType = roomTypeText.getText().trim();
        String propertyStatus = "Available";
       
        String maintenceDate = maintenanceDateText.getText().trim();        
        
        
        if(id.startsWith("R_") || id.startsWith("S_"))
        {
           if(propertyType.equalsIgnoreCase("StandardRoom") || propertyType.equalsIgnoreCase("Suite"))
           {
               if(propertyStatus.equalsIgnoreCase("Available") || propertyStatus.equalsIgnoreCase("Rented") || propertyStatus.equalsIgnoreCase("Maintenance"))
               {
                    if(bedrooms== 1 || bedrooms==2 || bedrooms == 4)
                    {
                            Room room = null;
                            if(id.startsWith("R"))
                            {
                            	double price = 59.0;
                                double lateFees = 0.0;
                                price = 59.0;
                                if(bedrooms == 2)
                                	price = 99.0;
                                else if(bedrooms == 4)
                                	price = 199;
                                	
                                lateFees = price * 1.35;
                                room = new StandardRoom(id, bedrooms, features,"StandardRoom",propertyStatus,price, lateFees,notAvailable);
                                
                                roomDataList.add(room);
                                ImageView imgView = new ImageView(new Image(new FileInputStream("images/" + notAvailable)));
                                imgView.setFitWidth(500);
                                imgView.setFitHeight(350);
                                
                                imagesRoom.add(imgView);
                                
                                System.out.println(roomDataList.toString());
                                
                                roomImageList.getItems().setAll(FXCollections.observableArrayList(imagesRoom));
                                manageData.addRoomProperty(room, imgView);
                                setUpALLPropertiesImageViews();
                                 addListViewListener();
                                
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Successfull");
                                alert.setHeaderText(null);
                                alert.setContentText("Rental Property with ID: "+id+" added successfully");
                                alert.showAndWait();
                                panel1.setVisible(false);
                            }
                            else 
                            {
                            	Date date = null;
                            	if(maintenceDate.trim().length() == 10)
                                {
                                   try {
                                    	date = new SimpleDateFormat("DD/MM/YYYY").parse(maintenceDate);
                                    	double price = 999.0;
                                        double lateFees = 1099.0;
                                        String[] d = maintenceDate.split("/");
                                    	room = new Suite(id, 6, features,"Suite",propertyStatus,Integer.parseInt(d[0]),Integer.parseInt(d[1]),Integer.parseInt(d[2]),price, lateFees, notAvailable);
                                    
                                    	roomDataList.add(room);
                                        ImageView imgView = new ImageView(new Image(new FileInputStream("images/" + notAvailable)));
                                        imgView.setFitWidth(500);
                                        imgView.setFitHeight(350);
                                        
                                        imagesRoom.add(imgView);
                                        
                                        System.out.println(roomDataList.toString());
                                        
                                        roomImageList.getItems().setAll(FXCollections.observableArrayList(imagesRoom));
                                        manageData.addRoomProperty(room, imgView);
                                        setUpALLPropertiesImageViews();
                                         addListViewListener();
                                        
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Successfull");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Rental Property with ID: "+id+" added successfully");
                                        alert.showAndWait();
                                        panel1.setVisible(false);
                                        
                                   } catch (ParseException pe) {
                                    	Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("ERROR");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Entered date format is wrong, please enter correct format as per mention");
                                        alert.showAndWait();
                                        System.out.println("Entered date format is wrong, please enter correct format as per mention");
                                        
                                    }
                                }
                            	else
                            	{
                            		Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("ERROR");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Entered date format is wrong, please enter correct format as per mention");
                                    alert.showAndWait();
                                    System.out.println("Entered date format is wrong, please enter correct format as per mention");
                            	}
                        	}
                            
                        }
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("Number of Bedroom should be 1, 2 or 3");
                            alert.showAndWait();
                            panel1.setVisible(true);
                        }
                    
                    
               }
               else
               {
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Room Status should be Available, Rented or Maintenance");
                    alert.showAndWait();
                    panel1.setVisible(true);
               }
               
           }
           else
           {
               Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Room Type should be StandardRoom or Premium Suite");
                alert.showAndWait();
                roomTypeText.setText("");
                panel1.setVisible(true);
           }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Property Id starts with R_ or S_");
            alert.showAndWait();  
            idText.setText("");
            panel1.setVisible(true);
        }
         
        }catch(Exception exp){}
    }
    
     @FXML
    private void importDate(ActionEvent event) {
        textArea.setText("");
        panel1.setVisible(false);
        try
        {
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);
            System.out.println(file.getName());
            if (file != null) {

            	hiringRecordDataList.clear();
            	roomDataList.clear();
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String property = bufferedReader.readLine();
                while(property != null)
                {
                    String[] propertyData = property.split(":");
                    if(propertyData.length == 3)
                    {

                    	                     
                        String[] date = propertyData[1].split("/");
                        
                        System.out.println("Integer.parseInt(date[0])"+Integer.parseInt(date[0]));
                        hiringRecordDataList.add(new RentalRecord(propertyData[0],Integer.parseInt(date[0]),Integer.parseInt(date[1]), Integer.parseInt(date[2]),Integer.parseInt(propertyData[2]) ));
                        
                    	property = bufferedReader.readLine();

                    }
                    else
                    {

                    	Room newProperty = null;
                    	ImageView imageView = null;
                    	if( propertyData[0].charAt(0) == 'R' )
                        {
                            double price = 59.0;
                            double lateFees = 0.0;
                            price = 59.0;
                            if(Integer.parseInt(propertyData[1]) == 2)
                            	price = 99.0;
                            else if(Integer.parseInt(propertyData[1]) == 4)
                            	price = 199;
                            	
                            lateFees = price * 1.35;
                            
                            if(propertyData[3].equalsIgnoreCase("Available"))
                                newProperty = new StandardRoom(propertyData[0],Integer.parseInt(propertyData[1]),propertyData[2],"StandardRoom","Available",price,lateFees,propertyData[4]);
                            else if(propertyData[3].equalsIgnoreCase("Rented"))
                                newProperty = new StandardRoom(propertyData[0],Integer.parseInt(propertyData[1]),propertyData[2],"StandardRoom","Rented",price,lateFees,propertyData[4]);
                            else if(propertyData[3].equalsIgnoreCase("Maintenance"))
                                newProperty = new StandardRoom(propertyData[0],Integer.parseInt(propertyData[1]),propertyData[2],"Standard_Room","Maintenance",price,lateFees,propertyData[4]);

                            try
                            {
                                imageView = new ImageView(new Image(new FileInputStream("images/" + propertyData[4] )));
                            }
                            catch(FileNotFoundException e)
                            {
                                Alert couldNotOpenImageFile;
                                couldNotOpenImageFile = new Alert(Alert.AlertType.ERROR, "Cannot open image file : " + propertyData[4], null);
                                couldNotOpenImageFile.showAndWait();
                                try
                                {
                                    imageView = new ImageView(new Image(new FileInputStream("images/" + NO_IMAGE )));
                                }
                                catch(FileNotFoundException e1)
                                {
                                    Alert couldNotOpenDefaultImageFile;
                                    couldNotOpenDefaultImageFile = new Alert(Alert.AlertType.ERROR, "Cannot open NO_PHOTO image file !", null);
                                    couldNotOpenDefaultImageFile.showAndWait();
                                }
                            }
                                    
                        }
                        else
                        {
                            
                            String lM = propertyData[4];
                            double price = 999.0;
                            double lateFees = 1099.0;
                            if(propertyData[3].equalsIgnoreCase("Available"))
                                newProperty = new Suite(propertyData[0],6,propertyData[2],"Suite","Available",Integer.parseInt(lM.split("/")[0]),Integer.parseInt(lM.split("/")[1]), Integer.parseInt(lM.split("/")[2]),price,lateFees,propertyData[5]);
                            else if(propertyData[3].equalsIgnoreCase("Rented"))
                                newProperty = new Suite(propertyData[0],6,propertyData[2],"Suite","Rented",Integer.parseInt(lM.split("/")[0]),Integer.parseInt(lM.split("/")[1]), Integer.parseInt(lM.split("/")[2]),price,lateFees,propertyData[5]);
                            else if(propertyData[3].equalsIgnoreCase("Maintenance"))
                                newProperty = new Suite(propertyData[0],6,propertyData[2],"Suite","Maintenance",Integer.parseInt(lM.split("/")[0]),Integer.parseInt(lM.split("/")[1]), Integer.parseInt(lM.split("/")[2]),price,lateFees,propertyData[5]);
                            
                            try
                            {
                                imageView = new ImageView(new Image(new FileInputStream("images/" + propertyData[5] )));
                            }
                            catch(FileNotFoundException e)
                            {
                                Alert couldNotOpenImageFile;
                                couldNotOpenImageFile = new Alert(Alert.AlertType.ERROR, "Cannot open image file : " + propertyData[5], null);
                                couldNotOpenImageFile.showAndWait();
                                
                                try
                                {
                                    imageView = new ImageView(new Image(new FileInputStream("images/" + NO_IMAGE )));
                                }
                                catch(FileNotFoundException e1)
                                {
                                    Alert couldNotOpenDefaultImageFile;
                                    couldNotOpenDefaultImageFile = new Alert(Alert.AlertType.ERROR, "Cannot open NO_PHOTO image file!", null);
                                    couldNotOpenDefaultImageFile.showAndWait();
                                                                       
                                }
                            }
                        }
                    	
                    	imageView.setFitWidth(500);
                    	imageView.setFitHeight(350);
                    	imagesRoom.add(imageView);
                    roomImageList.getItems().setAll(FXCollections.observableArrayList(imagesRoom));
                    manageData.addRoomProperty(newProperty, imageView);
                    
                    
                    property = bufferedReader.readLine();
                    }
                }
                setUpALLPropertiesImageViews();
                for(Room rp : roomDataList)
                {
                    for(RentalRecord rr : hiringRecordDataList)
                    {
                        String id = rr.getRecordId();
                        String[] parts = id.split("_");
                        
                        String pid = parts[0]+"_"+parts[1];
                        
                        if(rp.getRoomId().equals(pid))
                        {
                            
                            rp.getHiringRecords().add(rr);
                            System.out.println("pidpid: "+pid);
                        }
                    }
                }
                addListViewListener();
                
                	bufferedReader.close();
                	
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Import Data");
                alert.showAndWait();

                System.out.println(roomDataList.toString());
            }
            
            
        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }
    }
    
     @FXML
    private void exportData(ActionEvent event) {
        textArea.setText("");
        panel1.setVisible(false);
        try
        {
            System.out.println(roomDataList.size());
            if(roomDataList.size()>0 )
            {
                
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("data.txt")));
                for(int i=0;i<roomDataList.size();i++)
                    {
                        bufferedWriter.write(roomDataList.get(i).toString());
                         bufferedWriter.newLine();
                        for(RentalRecord rr : roomDataList.get(i).getHiringRecords())
                        {

                            String id = rr.getRecordId();
                            String[] parts = id.split("_");

                            String pid = parts[0]+"_"+parts[1];
                            if(roomDataList.get(i).getRoomId().equals(pid))
                            {
                            bufferedWriter.write(rr.toString());
                            bufferedWriter.newLine();
                            }
                        }
                       
                    }
                bufferedWriter.flush();
                bufferedWriter.close();
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Export");
                    alert.setHeaderText(null);
                    alert.setContentText("Export Data Successfully");
                    alert.showAndWait();

            }
            else
            {
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No Data Found");
            alert.showAndWait();
            }
                
        }
        
       catch(Exception exp)
       {}
    }
    
    public void databaseData()
    {
        try
        { 
              Connection conn = DriverManager.getConnection("jdbc:sqlite:room.db"); 
              Statement statement = conn.createStatement();
           
              statement.execute("DELETE from Rooms;");
              for(Room rp : roomDataList)
              {
              	System.out.println(rp.getHiringRecords());
              }
              for(Room rental : roomDataList)
              {
            	  if(rental instanceof StandardRoom)
            	  {
            		  statement.executeUpdate("INSERT INTO Rooms VALUES('"+rental.getRoomId()+"',"+rental.getBeds()+",'"+rental.getFeatureSummary()+"','"+rental.getRoomType()+"','"+rental.getRoomStatus()+"',"+0+","+0+","+0+","+rental.getFee()+","+rental.getLateFee()+",'"+rental.getRoomImage()+"');");
                  }
                  else 
                  {
                	  DateTime dt = rental.getLastMaintenance();
                	  String[] dd = dt.getFormattedDate().split("/");
                	  statement.executeUpdate("INSERT INTO Rooms VALUES('"+rental.getRoomId()+"',"+rental.getBeds()+",'"+rental.getFeatureSummary()+"','"+rental.getRoomType()+"','"+rental.getRoomStatus()+"',"+Integer.parseInt(dd[0])+","+Integer.parseInt(dd[1])+","+Integer.parseInt(dd[2])+","+rental.getFee()+","+rental.getLateFee()+",'"+rental.getRoomImage()+"');");
                  }
            	  
            	  Statement statement1 = conn.createStatement(); 
                  statement1.execute("DELETE from HiringRecord");
                  for(RentalRecord record : rental.getHiringRecords())
                  {
                	  DateTime rt = record.getRentDate();
                	  DateTime et = record.getEstimatedReturnDate();
                	  int days = DateTime.diffDays(et, rt);
                	  String[] p = rt.getFormattedDate().split("/");
                    statement.executeUpdate("INSERT INTO HiringRecord VALUES('"+record.getRecordId()+"',"+Integer.parseInt(p[0])+","+Integer.parseInt(p[1])+","+Integer.parseInt(p[2])+","+days+""+");");
                    
                  }
              }
             
           conn.commit();
             
        }
        catch(Exception ex){
        	System.out.println("exception"+ex);
        }
    }
    
     public Collection<Room> data1()
    {
    	 Collection<Room> properties = new ArrayList<>();
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:room.db"); 
           Statement stmt = conn.createStatement();
           String query = "SELECT * FROM Rooms";
            ResultSet resultSet = stmt.executeQuery(query);
           
            while(resultSet.next()) 
            {
        	String roomId = resultSet.getString("roomId");
                int beds = resultSet.getInt("beds");
                String featureSummary = resultSet.getString("featureSummary");
                String roomType = resultSet.getString("roomType");
                String roomStatus = resultSet.getString("roomStatus");
                int dd = resultSet.getInt("dd");
                int mm = resultSet.getInt("mm");
                int yyyy = resultSet.getInt("yyyy");
                
                double fee = resultSet.getDouble("fee");
                double lateFee = resultSet.getDouble("lateFee");
                
                String roomImage = resultSet.getString("roomImage");
                
                Room room;
                
                if(roomId.startsWith("R"))
                    room = new StandardRoom(roomId, beds, featureSummary, roomType, roomStatus, fee, lateFee, roomImage);
                else
                    room = new Suite(roomId, beds, featureSummary, roomType, roomStatus, dd, mm, yyyy, fee, lateFee, roomImage);
                    
                properties.add(room);
                System.out.println(properties);
                ImageView imgView = new ImageView(new Image(new FileInputStream("images/" + roomImage)));
                imgView.setFitWidth(500);
                imgView.setFitHeight(350);
                imagesRoom.add(imgView);
                roomImageList.getItems().setAll(FXCollections.observableArrayList(imagesRoom));
                manageData.addRoomProperty(room, imgView);
//                setUpALLPropertiesImageViews();
                addListViewListener();
                
               
            }
           
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        return manageData.getAllProperties();
    }
    
    public ArrayList<RentalRecord> data2()
    {
        ArrayList<RentalRecord> rental = new ArrayList<>();
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:room.db"); 
            Statement stmt = conn.createStatement();
          
           String query = "SELECT * FROM HiringRecord";
            
           ResultSet resultSet = stmt.executeQuery(query);
            
            while(resultSet.next()) 
            {
            	String recordId = resultSet.getString("recordId");
                int dd = resultSet.getInt("dd");
                int mm = resultSet.getInt("mm");
                int yyyy = resultSet.getInt("yyyy");
                int days = resultSet.getInt("days");
                rental.add(new RentalRecord(recordId, dd, mm, yyyy, days));
            
            }

        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        return rental;
    	
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panel1.setVisible(false);
        textArea.setText("");
        comboBoxPropertyType.getItems().add("StandardRoom");
        comboBoxPropertyType.getItems().add("Suite");
        comboBoxPropertyType.getItems().add("All");
        comboBoxPropertyType.setValue("All");
        
        comboBoxNumberOfBedroom.getItems().add("1");
        comboBoxNumberOfBedroom.getItems().add("2");
        comboBoxNumberOfBedroom.getItems().add("4");
        comboBoxNumberOfBedroom.getItems().add("6");
        comboBoxNumberOfBedroom.getItems().add("All");
        comboBoxNumberOfBedroom.setValue("All");
        
        comboBoxPropertyStatus.getItems().add("Available");
        comboBoxPropertyStatus.getItems().add("Rented");
        comboBoxPropertyStatus.getItems().add("Maintenance");
        comboBoxPropertyStatus.getItems().add("All");
        comboBoxPropertyStatus.setValue("All");
//        manageData = new ManageData("input.txt");
        manageData = new ManageData();
        data1();
        
        
        for(RentalRecord rr : data2())
        {
            hiringRecordDataList.add(rr);
        }
        System.out.println(hiringRecordDataList.size());
        
        setUpALLPropertiesImageViews();
        
        System.out.println(roomDataList.size());
        
        for(Room rp : roomDataList)
        {
            for(RentalRecord rr : hiringRecordDataList)
            {
                String id = rr.getRecordId();
                String[] parts = id.split("_");
                
                String pid = parts[0]+"_"+parts[1];
                
                if(rp.getRoomId().equals(pid))
                {
                    
                    rp.getHiringRecords().add(rr);
                    System.out.println("pidpid: "+pid);
                }
            }
        }
        
        setComboBoxListeners();
        addListViewListener();

        for(Room rp : roomDataList)
        {
        	System.out.println(rp.getHiringRecords());
        }
        
    }    
    
    
    public void setUpALLPropertiesImageViews()
    {
        clearImageList();
        
        for( Room room : manageData.getAllProperties() )
        {
        	imagesRoom.add( manageData.getRoomImageView(room.getRoomId()));
           
            roomDataList.add(room);
            
            System.out.println(imagesRoom.toString());
        }
        updateImageList();
    }
     
    private void updateImageList()
    {
        for(ImageView imageView : imagesRoom )
        {
            imageView.setFitWidth(500);
            imageView.setFitHeight(350);
        }
        
        roomImageList.getItems().setAll(FXCollections.observableArrayList(imagesRoom));
    }
     
     public void updateImageViews(String roomType,String roomStatus, String roomBeds)
    {
        ArrayList<Room> room1 = new ArrayList<>();
        
        if( roomType.equals("All"))
        {
            for( Room rm : manageData.getAllProperties() )
            {
                room1.add(rm);
            }
        }
        else if( roomType.equals("StandardRoom"))
        {
            for( Room rm : manageData.getAllProperties() )
            {
                if( rm instanceof StandardRoom)
                    room1.add(rm);
            }
        }
        else if( roomType.equals("Suite"))
        {
            for( Room rm : manageData.getAllProperties() )
            {
                if( rm.getRoomType().equals("Suite"))
                    room1.add(rm);
            } 
        }
        
        ArrayList<Room> room2 = new ArrayList<>();
        for(Room p : room1)
            room2.add(p);
        room1.clear();
        
        if( roomStatus.equals("Rented"))
        {
            for( Room rm : room2)
            {
                if( rm.getRoomStatus().equals("Rented"))
                    room1.add(rm);
            }
        }
        else if( roomStatus.equals("Available"))
        {
            for( Room rm : room2)
            {
                if( rm.getRoomStatus().equals("Available"))
                    room1.add(rm);
            }
        }
        else if( roomStatus.equals("Maintenance"))
        {
            for( Room rm : room2)
            {
                if( rm.getRoomStatus().equals("Maintenance"))
                    room1.add(rm);
            }
        }
        else if( roomStatus.equals("All"))
        {
            for( Room rm : room2)
                room1.add(rm);
        }
        room2.clear();
        for(Room p : room1)
            room2.add(p);
        room1.clear();
        if( roomBeds.equals("1"))
        {
            for( Room rm : room2)
            {
                if( rm.getBeds()== 1 )
                    room1.add(rm);
            }
        }
        else if( roomBeds.equals("2"))
        {
            for( Room rm : room2)
            {
                if( rm.getBeds() == 2 )
                    room1.add(rm);
            }
        }
        else if( roomBeds.equals("4"))
        {
            for( Room rm : room2)
            {
                if( rm.getBeds()== 4 )
                    room1.add(rm);
            }
        }
        else if( roomBeds.equals("6"))
        {
            for( Room rm : room2)
            {
                if( rm.getBeds()== 6 )
                    room1.add(rm);
            }
        }
        else if( roomBeds.equals("All"))
        {
            for( Room rm : room2)
                room1.add(rm);
        }
        for(Room property : room1)
        {
        	imagesRoom.add( manageData.getRoomImageView(property.getRoomId()));
        }
    }
     
    private void clearImageList()
    {
    	imagesRoom.clear();
        
        roomImageList.getItems().clear();
    }
     
    
    private void addListViewListener()
    {
        
    	roomImageList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ImageView>() 
        {
            
            @Override
            public void changed(ObservableValue<? extends ImageView> observable, ImageView oldValue, ImageView newValue) 
            {
             
                try
                {
                	Room roomDetails = manageData.getPropertyForImageView(newValue);
		            textArea.setText("");
		            textArea.appendText("Room ID: "+roomDetails.getRoomId()+"");
		            textArea.appendText("\nType: "+roomDetails.getRoomType());
		            textArea.appendText("\nStatus: "+roomDetails.getRoomStatus());
		            for(RentalRecord hiringRecord : roomDetails.getHiringRecords())
		            {
		                
		                String id = hiringRecord.getRecordId();
		                String[] parts = id.split("_");
		                
		                String pid = parts[0]+"_"+parts[1];
		                if(roomDetails.getRoomId().equals(pid))
		                {
		                textArea.appendText("\n======================");
		                textArea.appendText("\nRENTAL RECORD");
		                textArea.appendText("\nRecord ID: "+hiringRecord.getRecordId());
		                textArea.appendText("\nRent Date: "+hiringRecord.getRentDate());
		                textArea.appendText("\nEstimated Return Date: "+hiringRecord.getEstimatedReturnDate());
		                textArea.appendText("\nActual Return Date: "+hiringRecord.getActualReturnDate());
		                textArea.appendText("\nRental Fee: "+hiringRecord.getRentalFee());
		                textArea.appendText("\nLate Fee: "+hiringRecord.getLateFee());
		                textArea.appendText("\n");
		                }
		            }
                }
                catch(Exception ex){}
            }
        });
    }
    
    private void setComboBoxListeners()
    {
       try{
        comboBoxPropertyType.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
        {
            clearImageList();
            updateImageViews( newValue, comboBoxPropertyStatus.getSelectionModel().getSelectedItem(), 
                    comboBoxNumberOfBedroom.getSelectionModel().getSelectedItem());
            
            updateImageList();
                  
        });
        
        comboBoxNumberOfBedroom.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            clearImageList();
            updateImageViews(comboBoxPropertyType.getSelectionModel().getSelectedItem(),
                    comboBoxPropertyStatus.getSelectionModel().getSelectedItem(), newValue);
            
            updateImageList();
           
        });
        comboBoxPropertyStatus.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            clearImageList();
            
            updateImageViews(comboBoxPropertyType.getSelectionModel().getSelectedItem(),
                    newValue,comboBoxNumberOfBedroom.getSelectionModel().getSelectedItem() );
            
            updateImageList();
            
            
        });
       }catch(Exception ex){}
    }
    
}
