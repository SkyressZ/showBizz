package showbizz;

public class Admin {
     
    //insert into theatres("theatreId","theatreName","adminEmail","address","contact","boxCost","balconyCost","goldCost","silverCost")
//values('202','baba cinemas','admin1@g.com','Chennai','044-2345678','200','180','150','120')
    
    
    //insert into screens("screenId","screenName","theatreId","box","balcony","gold","silver")
//values('20101','screen 1','202','0','40','80','40')
    
//    insert into bookings("bookingId","date","showTimingId","boxSeats","balconySeats","goldSeats","silverSeats","totalCost","status")
//values('9876473','26-06-2019','1010109','7','0','0','0','1750','0')
    
    
    
//    select c.screenName, t.showTiming, m.moviename, (c.box - k.boxseats)
//from screens c, timing t, movies m, bookings k
//where c.theatreid = '101' and c.screenid = t.screenid and t.movieid = m.movieid and k.date = '26-06-2019' 
//and k.showtimingid = t.showtimingid
//order by screenname ASC

    
    
    
    
    //All Shows of a theatre at a particular day
    
//((select c.screenName, t.showTiming, m.moviename, c.box,c.balcony,c.gold,c.silver
//from screens c, timing t, movies m, bookings k
//where c.theatreid = '101' and c.screenid = t.screenid and t.movieid = m.movieid and k.showtimingid!=t.showtimingid)
//union
//(select c.screenName, t.showTiming, m.moviename, (c.box - k.boxseats),(c.balcony - k.balconyseats),(c.gold - k.goldseats),(c.silver - k.silverseats)
//from screens c, timing t, movies m, bookings k
//where c.theatreid = '101' and c.screenid = t.screenid and t.movieid = m.movieid and k.showtimingid = t.showtimingid))
//order by screenname

//((select c.screenName, t.showTiming, m.moviename, c.box,c.balcony,c.gold,c.silver
//from screens c, timing t, movies m, bookings k
//where c.theatreid = '101' and c.screenid = t.screenid and t.movieid = m.movieid and k.showtimingid!=t.showtimingid)
//union
//(select c.screenName, t.showTiming, m.moviename, (c.box - k.boxseats),(c.balcony - k.balconyseats),(c.gold - k.goldseats),(c.silver - k.silverseats)
//from screens c, timing t, movies m, bookings k
//where c.theatreid = '101' and c.screenid = t.screenid and t.movieid = m.movieid and k.showtimingid = t.showtimingid and k.date = '26-06-2019'))
//order by screenname



//****** date, theatrename, if inside bookings
//select c.screenname, (c.box - p.box), (c.balcony - p.balcony), (c.gold - p.gold), (c.silver - p.silver)
//from (select sum(boxseats) as box,sum(balconyseats) as balcony,sum(goldseats) as gold,sum(silverseats) as silver
//from bookings
//where date = '26-06-2019' and showtimingid = '1010109') as p,
//timing as t, screens as c
//where t.showtimingid = '1010109' and t.screenid = c.screenid

//******** if not in bookings give only showtimingid
//select c.screenname, t.showtiming, c.box, c.balcony, c.gold, c.silver
//from timing as t, screens as c
//where t.showtimingid = '1010118' and t.screenid = c.screenid
    
    
    
    
    
    
    
    
    
    
//    //Add new Show
//     public static boolean addNewShow(String date, String theatreName, String screenName, String movieName, String timing, String seatClass, Integer seats) throws Exception{
//        PreparedStatement stmt = conn.prepareStatement("insert into shows(\"date\",\"theatrename\",\"screenname\",\"moviename\",\"timing\",\"seatclass\",\"availabletickets\") values(?,?,?,?,?,?,?)");
//        try{
//            stmt.setString(1,date);
//            stmt.setString(2,theatreName);
//            stmt.setString(3,screenName);
//            stmt.setString(4,movieName);
//            stmt.setString(5,timing);
//            stmt.setString(6,seatClass);
//            stmt.setInt(7,seats);
//            
//            int aff = stmt.executeUpdate();
//            if(aff>0){
//                System.out.println("Show Added Successfully @"+timing+" in "+screenName+" of "+theatreName+" on "+date);
//                return true;
//            }
//        }
//        catch(Exception e){
//            System.out.println("Show was not added");
//        }
//        return false;
//    }
    
    
    
}
