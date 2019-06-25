package showbizz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Model {
    
     public static Connection conn;
    
    //Connection variable
    static{
        
        try{
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/showbizz", "postgres", "admin");
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    //Get Admin Details
    public static ResultSet getAdminDetails() throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select * from admindetails"); 
        return stmt.executeQuery();
    }
    
    //Get Admin Details
    public static ResultSet getUserDetails() throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select * from userdetails"); 
        return stmt.executeQuery();
    }
    
    //Get Theatre Details
    public static ResultSet getTheatreDetails() throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select * from theatres"); 
        return stmt.executeQuery();
    }
    
    //Get Cost values of Theatre
    public static ResultSet getCost(Integer theatreid) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select boxcost,balconycost,goldcost,silvercost from theatres where theatreid = ?"); 
        stmt.setInt(1,theatreid);
        return stmt.executeQuery();
    }
    
    
    //Get Theatre ID
    public static ResultSet getTheatreID(String theatreName) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select theatreid from theatres where theatrename = ?");
        try{
            stmt.setString(1,theatreName);  
        }
        catch(Exception e){}
        return stmt.executeQuery();
    }
    
    //Get Screen ID
    public static ResultSet getScreenID(Integer tid, String screenName) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select screenid from screens where theatreid = ? and screenname = ?");
        try{
            stmt.setInt(1,tid);
            stmt.setString(2,screenName);
        }
        catch(Exception e){}
        return stmt.executeQuery();
    }
    
    //Get ShowTiming ID
    public static ResultSet getShowTimingID(Integer screenID, String showtiming) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select showtimingid from timing where screenid = ? and showtiming = ?");
        try{
            stmt.setInt(1,screenID);
            stmt.setString(2,showtiming);
        }
        catch(Exception e){}
        return stmt.executeQuery();
    }
    
    //Get bookings based on showid
    public static ResultSet getBookingsBasedOnShowId(Integer showtimingid) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select (c.box - p.box)as box,(c.balcony - p.balcony)as balcony,(c.gold - p.gold)as gold,(c.silver - p.silver)as silver\n" +
        "from(select sum(boxseats) as box,sum(balconyseats) as balcony, sum(goldseats) as gold, sum(silverseats) as silver\n" +
        "from bookings\n" +
        "where showtimingid = ?) as p, screens as c, timing as t\n" +
        "where t.showtimingid = ? and t.screenid = c.screenid");
        try{
            stmt.setInt(1,showtimingid);
            stmt.setInt(2,showtimingid);
        }
        catch(Exception e){}
        return stmt.executeQuery();
    }
    
    
    public static ResultSet getAdminsTheatres(String email) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select theatrename from theatres where adminemail = ?");
        try{
            stmt.setString(1,email);  
        }
        catch(Exception e){}
        return stmt.executeQuery();
    }
    
    //Get Screen Details
    public static ResultSet getScreenDetails() throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select * from screens");
        return stmt.executeQuery();
    }
    
    //Get Screen Details
    public static ResultSet getScreenDetailsOfGivenScreenID(Integer screenid) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select box,balcony,gold,silver from screens where screenid = ?");
        stmt.setInt(1,screenid);
        return stmt.executeQuery();
    }
    
    //Get Timing Details
    public static ResultSet getTimingDetails() throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select * from timing");
        return stmt.executeQuery();
    }
    
    //Get Cost Details
    public static ResultSet getCostDetails() throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select boxcost,balcony,goldCost,silverCost from cost");
        return stmt.executeQuery();
    }
    
    //Get Cost Details
    public static ResultSet getMovieDetails() throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select * from movies");
        return stmt.executeQuery();
    }
    
    //Get Show Details
    public static ResultSet getShowDetails() throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select * from shows");
        return stmt.executeQuery();
    }
    
    //Get snack details
     public static ResultSet getSnackDetails(Integer theatreid) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select snackname,cost from snacks where theatreid = ?");
        stmt.setInt(1,theatreid);
        return stmt.executeQuery();
    }
    
    //Get Individual snack details
     public static ResultSet getDetailsOfASnack(Integer theatreid,String snackName) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select cost from snacks where theatreid = ? and snackname = ?");
        stmt.setInt(1,theatreid);
        stmt.setString(2,snackName);
        return stmt.executeQuery();
    }
     
    //Add A Movie
    public static boolean addNewMovie(String movieName, String directorName) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("insert into movies(\"moviename\",\"director\") values(?,?)");
        try{
            stmt.setString(1,movieName);
            stmt.setString(2,directorName);
            int aff = stmt.executeUpdate();
            if(aff>0){
                System.out.println("Movie "+movieName+" Added Successfully");
                return true;
            }
        }
        catch(Exception e){
            System.out.println("Movie "+movieName+" Already Exists");
        }
        return false;
    }
    
    //Add A Theatre
    public static boolean addNewTheatre(Integer theatreid, String theatreName, String adminEmail, String contact, String address, double boxcost,  double balonycost, double goldcost, double silvercost) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("insert into theatres(\"theatreid\",\"theatrename\",\"adminemail\",\"address\",\"contact\",\"boxcost\",\"balconycost\",\"goldcost\",\"silvercost\") values(?,?,?,?,?,?,?,?,?)");
        try{
            stmt.setInt(1,theatreid);
            stmt.setString(2,theatreName);
            stmt.setString(3,adminEmail);
            stmt.setString(4,address);
            stmt.setString(5,contact);
            stmt.setDouble(6,boxcost);
            stmt.setDouble(7,balonycost);
            stmt.setDouble(8,goldcost);
            stmt.setDouble(9,silvercost);
            int aff = stmt.executeUpdate();
            if(aff>0){
                System.out.println("Theatre "+theatreName+" Added Successfully");
                return true;
            }
        }
        catch(Exception e){
            System.out.println("Theatre "+theatreName+" Already Exists");
        }
        return false;
    }
    
    //Add a new Screen
    public static boolean addNewScreen(Integer screenid, String screenName,Integer theatreid, int box, int balcony,int gold, int silver) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("insert into screens(\"screenid\",\"screenname\",\"theatreid\",\"box\",\"balcony\",\"gold\",\"silver\")values(?,?,?,?,?,?,?)");
        try{
            stmt.setInt(1,screenid);
            stmt.setString(2,screenName);
            stmt.setInt(3,theatreid);
            stmt.setInt(4,box);
            stmt.setInt(5,balcony);
            stmt.setInt(6,gold);
            stmt.setInt(7,silver);
            int aff = stmt.executeUpdate();
            if(aff>0){
                System.out.println("Screen "+screenName+" Added Successfully");
                return true;
            }
        }
        catch(Exception e){
            System.out.println("Screen "+screenName+" Already Exists");
        }
        return false;
    }
    
    //Add new Timing
    public static boolean addNewTiming(Integer Showtimingid, Integer screenid,String showtiming, String movieName) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("insert into timing(\"showtimingid\",\"screenid\",\"showtiming\",\"moviename\") values(?,?,?,?)");
        try{
            stmt.setInt(1,Showtimingid);
            stmt.setInt(2,screenid);
            stmt.setString(3,showtiming);
            stmt.setString(4,movieName);
           
            int aff = stmt.executeUpdate();
            if(aff>0){
                System.out.println("Timing Added Successfully");
                return true;
            }
        }
        catch(Exception e){
            System.out.println("Timing Already Exists");
        }
        return false;
    }
    
    
    
    //Add new Snack
    public static boolean addNewSnack(Integer tid, String snackName, double cost) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("insert into snacks(\"theatreid\",\"snackname\",\"cost\") values(?,?,?)");
        try{
            stmt.setInt(1,tid);
            stmt.setString(2,snackName);
            stmt.setDouble(3,cost);
           
            int aff = stmt.executeUpdate();
            if(aff>0){
                return true;
            }
        }
        catch(Exception e){}
        return false;
    }
    
    
    //Get Email of a Single Admin
    public static ResultSet getEmailOfOneAdmin(String email) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select email from adminaccounts where email = ?");
        stmt.setString(1,email);
        return stmt.executeQuery();
    }
    
    //Get Email and Password of One Admin
    public static ResultSet getEmailAndPasswordOfOneAdmin(String email) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select email,password from adminaccounts where email = ?");
        stmt.setString(1,email);
        return stmt.executeQuery();
    }
    
    //Create a new Admin
    public static boolean createAdmin(String email, String password) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("insert into public.adminaccounts(\"email\",\"password\") values(?,?)");
        try{
            stmt.setString(1,email);
            stmt.setString(2,password);
            int aff = stmt.executeUpdate();
            if(aff>0){
                System.out.println("Account Created Successfully");
                return true;
            }
        }
        catch(Exception e){
            System.out.println("Email Already Exists");
        }
        return false;
    }
    
    //Get all shows of a screen
    public static ResultSet getAllShowsOfAScreen(Integer screenid) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select * from timing where screenid = ?");
        stmt.setInt(1,screenid);
        return stmt.executeQuery();
    }
    
    
    //Get bookings
    public static ResultSet getAllBookingsOfADate(String date, Integer showtimingid) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select  from bookings where date = ? and showtimingid = ? and status = ?");
        stmt.setString(1,date);
        stmt.setInt(2,showtimingid);
        stmt.setInt(3,0);
       return stmt.executeQuery();
    }
    
    //Get seat availability for in bookings
    public static ResultSet getSeatAvailabilityB(String date, Integer showtimingid) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select c.screenname, (c.box - p.box), (c.balcony - p.balcony), (c.gold - p.gold), (c.silver - p.silver)\n" +
        "from (select sum(boxseats) as box,sum(balconyseats) as balcony,sum(goldseats) as gold,sum(silverseats) as silver\n" +
        "from bookings\n" +
        "where date = ? and showtimingid = ? and status = ?) as p,\n" +
        "timing as t, screens as c\n" +
        "where t.showtimingid = ? and t.screenid = c.screenid");
        stmt.setString(1, date);
        stmt.setInt(2,showtimingid);
        stmt.setInt(3,0);
        stmt.setInt(4,showtimingid);
        
        return stmt.executeQuery();
    
    }
    
    public static ResultSet getSeatAvailability(String date, Integer showtimingid) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select c.screenname, t.showtiming, c.box, c.balcony, c.gold, c.silver\n" +
        "from timing as t, screens as c\n" +
        "where t.showtimingid = ? and t.screenid = c.screenid\n" +
        "");
        stmt.setInt(1,showtimingid);
        
        return stmt.executeQuery();
    
    }
    
    
    
    //Update Admin's Password
    public static void UpdateAdminPassword(String email, String newPassword) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("UPDATE adminaccounts SET password = ? WHERE email = ?");
        
        try{
            stmt.setString(1,newPassword);
            stmt.setString(2,email);
            int aff = stmt.executeUpdate();
            if(aff>0)
                System.out.println("Password Updated Successfully");
        }
        catch(Exception e){
            System.out.println("Password Update Failed");
        }
            
    }
    
    //Get Email of a Single User
    public static ResultSet getEmailOfOneUser(String email) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select email from useraccounts where email = ?");
        stmt.setString(1,email);
        return stmt.executeQuery();
    }
    
    //Get Email and Password of One User
    public static ResultSet getEmailAndPasswordOfOneUser(String email) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select email,password from useraccounts where email = ?");
        stmt.setString(1,email);
        return stmt.executeQuery();
    }
    
    //Create a new user
    public static boolean createUser(String email, String password) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("insert into useraccounts(\"email\",\"password\") values(?,?)");
        try{
            stmt.setString(1,email);
            stmt.setString(2,password);
            int aff = stmt.executeUpdate();
            if(aff>0){
                System.out.println("Account Created Successfully");
                return true;
            }
        }
        catch(Exception e){
            System.out.println("Email Already Exists");
        }
        return false;
    }
    
    //Update User's Password
    public static void UpdateUserPassword(String email, String newPassword) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("UPDATE useraccounts SET password = ? WHERE email = ?");
        try{
            stmt.setString(1,newPassword);
            stmt.setString(2,email);
            int aff = stmt.executeUpdate();
            if(aff>0)
                System.out.println("Password Updated Successfully");
        }
        catch(Exception e){
            System.out.println("Password Update Failed");
        }
    }
    
    //Add new Admin details
    public static boolean addNewAdminDetails(String email,String name, String contact) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("insert into admindetails(\"email\",\"name\",\"contact\") values(?,?,?)");
        try{
            stmt.setString(1,email);
            stmt.setString(2,name);
            stmt.setString(3,contact);
            int aff = stmt.executeUpdate();
            if(aff>0){
                System.out.println("Admin Details Added Successfully Successfully");
                return true;
            }
        }
        catch(Exception e){
            System.out.println("Admin Details Already Exists");
        }
        return false;
    }
    
    
    //Add new User Details
    public static boolean addNewUserDetails(String email,String name, String contact) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("insert into userdetails(\"email\",\"name\",\"contact\") values(?,?,?)");
        try{
            stmt.setString(1,email);
            stmt.setString(2,name);
            stmt.setString(3,contact);
            int aff = stmt.executeUpdate();
            if(aff>0){
                System.out.println("User Details Added Successfully Successfully");
                return true;
            }
        }
        catch(Exception e){
            System.out.println("User Details Already Exists");
        }
        return false;
    }
    
    //Add Booking
    public static boolean addBooking(Integer bookingid, String date, Integer showtimingid, int box, int balc, int gold, int silver, double cost,int status, String useremail) throws Exception{
        
        PreparedStatement stmt = conn.prepareStatement("insert into bookings(\"bookingid\",\"date\",\"showtimingid\",\"boxseats\",\"balconyseats\",\"goldseats\",\"silverseats\",\"totalcost\",\"status\",\"useremail\") values(?,?,?,?,?,?,?,?,?,?)");
        
        try{
            stmt.setInt(1,bookingid);
            stmt.setString(2,date);
            stmt.setInt(3,showtimingid);
            stmt.setInt(4,box);
            stmt.setInt(5,balc);
            stmt.setInt(6,gold);
            stmt.setInt(7,silver);
            stmt.setDouble(8,cost);
            stmt.setInt(9,status);
            stmt.setString(10,useremail);
            
            
            int aff = stmt.executeUpdate();
            if(aff>0){
                System.out.println("Booking Successful");
                return true;
            }
        }
        catch(Exception e){
            System.out.println("Booking Failed");
        }
        return false;
        
        
    }
    
    public static ResultSet getMyBookings(String userEmail) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("select * from bookings where useremail = ?");
        stmt.setString(1,userEmail);
        return stmt.executeQuery();
    }
    
    //Cancelling a ticket
    public static void cancelBooking(Integer bookingid) throws Exception{
        PreparedStatement stmt = conn.prepareStatement("UPDATE bookings SET status = ? WHERE bookingid = ? and status = ?");
        try{
            stmt.setInt(1,2);
            stmt.setInt(2,bookingid);
            stmt.setInt(3,0);
            int aff = stmt.executeUpdate();
            if(aff>0)
                System.out.println("Cancelled Successfully");
        }
        catch(Exception e){
            System.out.println("Cancellation Failed");
        }
    }
    
}
