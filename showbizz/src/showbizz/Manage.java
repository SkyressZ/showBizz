package showbizz;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Manage {
    
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static Random rand = new Random();
    
    //Display Admin Details
    public void displayAdminDetails() throws Exception{
        System.out.format("%s%15s%15s\n","Admin Email","Admin Name","Contact");
        ResultSet rs = Model.getAdminDetails();
        while(rs.next()){
            System.out.format("%s%15s%15s\n",rs.getString(1),rs.getString(2),rs.getString(3));
        }
            
    }
    
    //Display User Details
    public void displayUserDetails() throws Exception{
        System.out.format("%s%15s%15s\n","User Email","User Name","Contact");
        ResultSet rs = Model.getUserDetails();
        while(rs.next()){
            System.out.format("%s%15s%15s\n",rs.getString(1),rs.getString(2),rs.getString(3));
        }
            
    }
    
    //Display All Movies
    public void displayAllMovies() throws Exception{
        System.out.format("%s%20s\n","Movie Name","Director");
        ResultSet rs = Model.getMovieDetails();
        while(rs.next()){
            System.out.format("%s%20s\n",rs.getString(1),rs.getString(2));
        }
    }
    
    //Display All Theatres
    public void displayAllTheatres() throws Exception{
        System.out.format("%s%15s%15s%15s\n","Theatre Name","Admin Email","Address","Contact");
        ResultSet rs = Model.getTheatreDetails();
        while(rs.next()){
            System.out.format("%s%15s%15s%15s\n",rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
        }
    }
    
    
    public void addNewAdminDetails(String email) throws Exception{
        
        System.out.println("Enter Name");
        String name = br.readLine();
        System.out.println("Enter Contact Number");
        String contact = br.readLine();
        
        if(Model.addNewAdminDetails(email, name, contact))
            System.out.println("Admin "+email+" Created");
        else
            System.out.println("Admin Already Exists");   
    }
       
    public void addNewUserDetails(String email) throws Exception{
        
        System.out.println("Enter Name");
        String name = br.readLine();
        System.out.println("Enter Contact Number");
        String contact = br.readLine();
        
        if(Model.addNewUserDetails(email, name, contact))
            System.out.println("User "+email+" Created");
        else
            System.out.println("User Already Exists");   
    }
    
    //Check if the admin already exists
    public boolean adminExists(String email) throws Exception{
        
        ResultSet rs = Model.getEmailAndPasswordOfOneAdmin(email);
        
        while(rs.next()){
            if(rs.getString(1).equals(email))
                return true;
            
        }
        return false;
    } 
    
    //Create a new Admin
    public boolean CreateNewAdminAccount(String email,String password) throws Exception{
        return Model.createAdmin(email,password);
    }
    
    
    //Update Admin's Password
    public void UpdatePasswordForAdmin(String email) throws Exception{
        
        String password,newPassword;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Old Password:");
        password = br.readLine();
        ResultSet rs = Model.getEmailAndPasswordOfOneAdmin(email);
        while(rs.next()){
            
            if(rs.getString(2).equals(password)){
                System.out.println("Enter new Password:");
                newPassword = br.readLine();
                Model.UpdateAdminPassword(email, newPassword);
            }
            else{
                System.out.println("Unauthorized Access");
            }
            break;
            
        }
    }
    
    //check if the user exists
    public boolean userExists(String email) throws Exception{
        
        
        ResultSet rs = Model.getEmailAndPasswordOfOneUser(email);
        while(rs.next()){
            if(rs.getString(1).equals(email))
                return true;
            
        }
        return false;
    } 
    
    //Create a new User
    public boolean CreateNewUserAccount(String email,String password) throws Exception{
        
        return Model.createUser(email,password);
        
    }
    
    //Update password for user
    public void UpdatePasswordForUser(String email) throws Exception{
        
        String password,newPassword;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Old Password:");
        password = br.readLine();
        ResultSet rs = Model.getEmailAndPasswordOfOneAdmin(email);
        if(rs.getString(2).equals(password)){
            System.out.println("Enter new Password:");
            newPassword = br.readLine();
            Model.UpdateUserPassword(email, newPassword);
        }
        else{
            System.out.println("Unauthorized Access");
        }
        
    }
    
    //Admin Login
    public boolean adminLogin(String email,String password) throws Exception{
        
        ResultSet rs = Model.getEmailAndPasswordOfOneAdmin(email);
        while(rs.next()){
            if(rs.getString(2).equals(password)){
                return true;
            }
            break;
        }
        return false;
    }
    
    //User Login
    public boolean userLogin(String email,String password) throws Exception{
        
        ResultSet rs = Model.getEmailAndPasswordOfOneUser(email);
        while(rs.next()){
            if(rs.getString(2).equals(password)){
                return true;
            }
            break;
        }
        return false;
    }
    
    //Add new Movie
    public void addNewMovie()throws Exception{
        
        String movieName,director;
        System.out.println("Enter Movie Name");
        movieName = br.readLine();
        System.out.println("Enter Director Name");
        director = br.readLine();
        Model.addNewMovie(movieName,director);
    }
    
    //Add new Timing Directly
    public void addNewTiming()throws Exception{
        
        String theatreName,screenName;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Theatre Name");
        theatreName = br.readLine();
        System.out.println("Enter Screen Name");
        screenName = br.readLine();
        ResultSet rs = Model.getTheatreID(theatreName);
        while(rs.next()){
            ResultSet rs1 = Model.getScreenID(rs.getInt(1),screenName);
            while(rs1.next()){
                addNewTimingR(rs.getInt(1),rs1.getInt(1));
                break;
            }
            break;
        }
        
        
    }
    
    //Add new Timing Reusable
    public void addNewTimingR(Integer theatreid, Integer screenid) throws Exception{
     
        String timing,movieName;
        Integer showtimingid;
        showtimingid = rand.nextInt(10000000);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Show Timing");
        timing = br.readLine();
        System.out.println("Enter Movie Name");
        movieName = br.readLine();
        Model.addNewTiming(showtimingid, screenid, timing, movieName);
    }
    
    //Add new Screen
    public void addNewScreen() throws Exception{
        
        String theatreName;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Theatre Name");
        theatreName = br.readLine();
        ResultSet rs = Model.getTheatreID(theatreName);
        while(rs.next()){
            addNewScreenR(rs.getInt(1));
            break;
        }
    }
    
    //Add new Screen Reusable
    public void addNewScreenR(Integer theatreid) throws Exception{
         
        String screenName;
        Integer screenid = rand.nextInt(100000);
        int box,balcony,gold,silver;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Screen Name");
        screenName = br.readLine();
        System.out.println("Enter Box Class Seats, 0 if not available");
        box = Integer.parseInt(br.readLine());
        System.out.println("Enter Balcony Class Seats, 0 if not available");
        balcony = Integer.parseInt(br.readLine());
        System.out.println("Enter Gold Class Seats, 0 if not available");
        gold = Integer.parseInt(br.readLine());
        System.out.println("Enter Silver Class Seats, 0 if not available");
        silver = Integer.parseInt(br.readLine());

        //Insert into the Database
        if(Model.addNewScreen(screenid,screenName,theatreid,box, balcony,gold, silver)){

            boolean tflag = true;
            do{

                System.out.println("Enter:\n1)Add new Timing For the Screen\nAnyother to Exit");
                if(Integer.parseInt(br.readLine())==1){
                    addNewTimingR(theatreid,screenid);
                }
                else
                    tflag = false;

            }while(tflag);
        }
        
    }
     
    //Add new Theatre
    public void addNewTheatre(String email)throws Exception{
        
        String theatreName,contact, address;
        double box,balc,gold,silver;
        Integer theatreid = rand.nextInt(1000);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Theatre Name");
        theatreName = br.readLine();
        System.out.println("Enter Contact No");
        contact = br.readLine();
        System.out.println("Enter Address");
        address = br.readLine();
        System.out.println("Enter box cost");
        box = Double.parseDouble(br.readLine());
        System.out.println("Enter balcony cost");
        balc = Double.parseDouble(br.readLine());
        System.out.println("Enter gold cost");
        gold = Double.parseDouble(br.readLine());
        System.out.println("Enter silver cost");
        silver = Double.parseDouble(br.readLine());
        
        if(Model.addNewTheatre(theatreid,theatreName, email, contact, address, box, balc, gold, silver)){

            boolean sflag = true;
            do{

                System.out.println("Enter:\n1)Add new Screen For the Theatre\nAnyother to Exit");
                if(Integer.parseInt(br.readLine())==1){
                    addNewScreenR(theatreid);
                }
                else
                    sflag = false;

            }while(sflag);
        }
        
    }
    
    //adda new snack item
    public void addNewSnack() throws Exception{
        
        String theatreName, snackName;
        double cost;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Theatre Name");
        theatreName = br.readLine();
        ResultSet rs = Model.getTheatreID(theatreName);
        System.out.println("Enter Snack Name");
        snackName = br.readLine();
        System.out.println("Enter Cost");
        cost = Double.parseDouble(br.readLine());
        while(rs.next()){
            if(Model.addNewSnack(rs.getInt(1), snackName, cost)){
                System.out.println("Snack Added Successfully to "+theatreName);
            }
            else{
               System.out.println("Snack couldn't be added"); 
            }
            
        }
        
    }
    
    public void displayAllMyTheatres(String adminEmail) throws Exception{
        
        ResultSet rs = Model.getAdminsTheatres(adminEmail);
        System.out.println("Theatres of "+adminEmail);
        while(rs.next()){
            System.out.println(rs.getString(1));
        }
    }
    
    public boolean showInBookings(String date, Integer showtimingid) throws Exception{
        
        ResultSet rs = Model.getAllBookingsOfADate(date, showtimingid);
        while(rs.next()){
            return true;
        }
        return false;
    }
    
    //***display ticketavailability
    public void displayAllAvailableTickets(String theatreName, String date, String screenName) throws Exception{
        
        ResultSet rs = Model.getTheatreID(theatreName);
        while(rs.next()){
            System.out.println("");
            ResultSet rs1 = Model.getScreenID(rs.getInt(1),screenName);
            while(rs1.next()){
                ResultSet tg = Model.getAllShowsOfAScreen(rs1.getInt(1));
                while(tg.next()){
                    System.out.println("Show Timing : "+tg.getString(3));
                    System.out.println("Movie Name : "+tg.getString(4));
                    if(showInBookings(date,tg.getInt(1))){
                        ResultSet bks = Model.getSeatAvailabilityB(date,tg.getInt(1));
                        while(bks.next()){
                            System.out.println("Box : "+bks.getInt(2));
                            System.out.println("Balcony : "+bks.getInt(3));
                            System.out.println("Gold : "+bks.getInt(4));
                            System.out.println("Silver : "+bks.getInt(5));
                            System.out.println("______________________________________");
                            
                        }
                    }
                    else{
                        ResultSet bks = Model.getSeatAvailability(date,tg.getInt(1));
                        while(bks.next()){
                            System.out.println("Box : "+bks.getInt(3));
                            System.out.println("Balcony : "+bks.getInt(4));
                            System.out.println("Gold : "+bks.getInt(5));
                            System.out.println("Silver : "+bks.getInt(6));
                            System.out.println("______________________________________");
                        }
                    }
                }
            }
        }
    }
    
    
    public boolean isSeatAvailable(String date, Integer showtimingid, String seatclass, int seats) throws Exception{
        
        int availBox = 0, availBalc = 0, availGold = 0, availSilver = 0;
        if(showInBookings(date,showtimingid)){
            ResultSet bks = Model.getSeatAvailabilityB(date,showtimingid);
            while(bks.next()){
                availBox = bks.getInt(2);
                availBalc = bks.getInt(3);
                availGold = bks.getInt(4);
                availSilver = bks.getInt(5);
            }
        }
        else{
            ResultSet bks = Model.getSeatAvailability(date,showtimingid);
            while(bks.next()){
                availBox = bks.getInt(3);
                availBalc = bks.getInt(4);
                availGold = bks.getInt(5);
                availSilver = bks.getInt(6);
            }
        }
        
        if(((seatclass.equals("box") && seats <=availBox)||(seatclass.equals("balcony") && seats <=availBalc)||(seatclass.equals("gold") && seats <=availGold)||(seatclass.equals("silver") && seats <=availSilver)) && seats > 0)
            return true;
        
        return false;
        
    }
    

    public void generateBill(String userEmail, Integer theatreId, String theatrename, Integer showtimingid, String date, String Screen, String timing,int box, int balc, int gold, int silver) throws Exception{
        double cost = 0, snackCost = 0;
        ResultSet rs = Model.getCost(theatreId);
        while(rs.next()){
            cost+=box*rs.getDouble(1);
            cost+=balc*rs.getDouble(2);
            cost+=gold*rs.getDouble(3);
            cost+=silver*rs.getDouble(4);          
        }
        
        int ch;
        rs = Model.getSnackDetails(theatreId);
        while(rs.next()){
            System.out.println("Snack Name : "+rs.getString(1)+" Cost : "+rs.getDouble(2));
        }
        do{
            System.out.println("1)Add snack\nAnyother to exit\nEnter:");
            ch = Integer.parseInt(br.readLine());
            if(ch==1){
                System.out.println("Enter Snack Name:");
                String snack = br.readLine();
                System.out.println("Enter Quantity:");
                int qty = Integer.parseInt(br.readLine());
                rs = Model.getDetailsOfASnack(theatreId, snack);
                while(rs.next()){
                    snackCost+=rs.getDouble(1)*qty;
                }
            }
            
            
        }while(ch>0 && ch <2);
        
        System.out.println("BILL : ");
        System.out.println("______________________________________________________");
        System.out.println("Date : "+date);
        System.out.println("Theatre : "+theatrename);
        System.out.println("Screen : "+Screen);
        System.out.println("Timing : "+timing);
        
        if(box > 0)
            System.out.println("Box : "+box);
        if(balc > 0)
            System.out.println("Balcony : "+balc);
        if(gold > 0)
            System.out.println("Gold : "+gold);
        if(silver > 0)
            System.out.println("Silver : "+silver);
        
        System.out.println("Ticket Cost : "+cost);
        System.out.println("Snack Cost : "+snackCost);
        System.out.println("Total Cost : "+(cost+snackCost));
        System.out.println("______________________________________________________");
        
        Integer bookingid = rand.nextInt(10000000);
        Model.addBooking(bookingid, date, showtimingid, box, balc, gold, silver, cost,0, userEmail);
        
        
        
    }
    
    //*****Book tickets
    public void bookTickets(String userEmail) throws Exception{
        String theatreName, date, screenName;
        System.out.println("Enter Theatre Name:");
        theatreName = br.readLine(); 
        ResultSet rs = Model.getTheatreID(theatreName);
        while(rs.next()){
            System.out.println("Enter Date in dd-mm-yyyy format:");
            date = br.readLine();
            int ch;
            do{
                System.out.println("1)Enter Screen Name\nAnyother to Exit\nEnter:");
                ch = Integer.parseInt(br.readLine());
                if(ch==1){

                    System.out.println("Enter Screen Name");
                    screenName = br.readLine();
                    displayAllAvailableTickets(theatreName, date, screenName);
                    ResultSet rs1 = Model.getScreenID(rs.getInt(1),screenName);
                    while(rs1.next()){
                        
                        int ch1;
                        do{
                            System.out.println("1)Enter Show timing\nAnyother to Exit\nEnter:");
                            ch1 = Integer.parseInt(br.readLine());
                            if(ch1==1){
                                System.out.println("Enter Show Timing");
                                String showTiming = br.readLine();
                                ResultSet rs2 = Model.getShowTimingID(rs1.getInt(1), showTiming);
                                while(rs2.next()){
                                    
                                    int ch2;
                                    int box = 0, balc = 0, gold = 0, silver = 0;
                                    boolean bookingFlag = false;
                                    do{
                                        System.out.println("1)Choose Seats\nAnyother to Exit\nEnter:");
                                        ch2 = Integer.parseInt(br.readLine());
                                        if(ch2==1){
                                            System.out.println("Enter Seat Class");
                                            String seatclass = br.readLine();
                                            System.out.println("Enter No Of Seats");
                                            Integer seats = Integer.parseInt(br.readLine());
                                            
                                            if(seatclass.equals("box"))
                                                seats+=box;
                                            else if(seatclass.equals("balcony"))
                                                seats+=balc;
                                            else if(seatclass.equals("gold"))
                                                seats+=gold;
                                            else if(seatclass.equals("silver"))
                                                seats+=silver;
                                            
                                            if(isSeatAvailable(date,rs2.getInt(1),seatclass,seats)){
                                                
                                                if(seatclass.equals("box")){
                                                    box=seats;
                                                    bookingFlag = true;
                                                }
                                                else if(seatclass.equals("balcony")){
                                                    balc=seats;
                                                    bookingFlag = true;
                                                }
                                                else if(seatclass.equals("gold")){
                                                    gold=seats;
                                                    bookingFlag = true;
                                                }
                                                else if(seatclass.equals("silver")){
                                                    silver=seats;
                                                    bookingFlag = true;
                                                }
                                            }
                                            else{
                                                System.out.println("Your required seats are more than the available seats");
                                            }
                                            
                                        }
                                        
                                        
                                    }while(ch2 > 0 && ch2 < 2);
                                    
                                    
                                    if(bookingFlag){
                                        generateBill(userEmail,rs.getInt(1), theatreName, rs2.getInt(1), date, screenName, showTiming, box, balc, gold, silver);
                                        
                                        
                                    }
                                    else{
                                        System.out.println("You haven't booked any tickets");
                                    }
                                    
                                    
                                    break;
                                }
                                
                            }

                        }while(ch1 > 0 && ch1 < 2);
                        break;
                    }
                }

            }while(ch > 0 && ch < 2);
            break;
            
        }
        
    }
    
    //Cancel Tickets
    public void cancelTickets() throws Exception{
        System.out.println("Enter Booking ID : ");
        Integer bookingId = Integer.parseInt(br.readLine());
        Model.cancelBooking(bookingId);
    }
    
    //display my bookings
    public void displayMyBookings(String userEmail) throws Exception{
        ResultSet rs = Model.getMyBookings(userEmail);
        if(rs.next()){
            System.out.println(rs.getString(1));
        }
    }
}
