/*
 * @author H.O.V.A.
 */

package Source;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    public int booking_id;
    public String room_id, applicant_id, dept, authority, security, av_cell ;
    public String applicant_name, applicant_phone, applicant_email, building ;
    public int floor;  
    
    public Booking(int booking_id, String room_id   , String applicant_id, String dept, 
                                   String authority , String security    , String av_cell,
                   String applicant_name, String applicant_phone, String applicant_email,
                   String building, int floor){
        
        this.booking_id         = booking_id;
        this.room_id            = room_id;
        this.applicant_id       = applicant_id;
        this.dept               = dept;
        this.authority          = authority;
        this.security           = security;
        this.av_cell            = av_cell;
        this.applicant_name     = applicant_name;
        this.applicant_phone    = applicant_phone;
        this.applicant_email    = applicant_email;
        this.building           = building;
        this.floor              = floor;        
    }
    
    public static boolean createBooking(String room_id, String user_id, String date, String time_slots, int role, int av_cell_req){
        try{
            Connection conn =   Connect.returnConnection();
            
            String dept     =   "PENDING";
            if(role == 1)
              dept = "NA ";
            
            String av_cell  =   "PENDING";
            if(av_cell_req==0)
              av_cell="NA";
            
            String Query = "INSERT INTO Booking" + "(room_id,applicant_id,dept,av_cell)" + "VALUES(?,?,?,?) ;" ; 
            PreparedStatement stmt  =   conn.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,room_id);
            stmt.setString(2,user_id);
            stmt.setString(3,dept);
            stmt.setString(4,av_cell);
            stmt.executeUpdate();
            
            ResultSet rs    =   stmt.getGeneratedKeys();
            int booking_id  = 0 ;
            if(rs.next()){
                booking_id  = rs.getInt(1);
            }
            String slots[]  = time_slots.split(",");
         
            for(String slot : slots ){
                stmt  =   conn.prepareStatement("INSERT INTO Booking_slot " + "VALUES(?,?,?,?,'ACCEPTED') ");
                stmt.setInt(1,booking_id);
                stmt.setString(2,room_id);
                stmt.setString(3, date);
                stmt.setString(4,slot);
                stmt.executeUpdate();
            }
          return true;
       }
       catch(Exception e){
           System.out.println(e);
           return false;
       }
    
    }
    
        
    public static List<Booking> getPendingForAuthority(){
        try{
            Connection conn = Connect.returnConnection();
            String Query    = "SELECT * FROM (Booking NATURAL JOIN Room) JOIN Applicant USING(applicant_id) " + 
                              "WHERE authority = 'PENDING' AND Booking.dept != 'PENDING' AND Booking.dept != 'REJECTED' " ; 
            
            //Statement stmt  = conn.createStatement(); ResultSet rs    = stmt.executeQuery(Query);
            PreparedStatement stmt = conn.prepareStatement(Query); ResultSet rs = stmt.executeQuery() ;  
             
            List<Booking> res = new ArrayList<>();            
            while(rs.next()){
                Booking b = new Booking( rs.getInt("booking_id"), rs.getString("room_id")   , rs.getString("applicant_id"),
                                         rs.getString("dept")   , rs.getString("authority") , rs.getString("security"),
                                         rs.getString("av_cell"), rs.getString("name")      , rs.getString("phone"), 
                                         rs.getString("email")  , rs.getString("building")  , rs.getInt("floor")
                                        ) ;  
                res.add( b );
            }
            return res;
        }
        catch(Exception e){
            System.out.println(e);
            return new ArrayList<>();
        }        
    }        
    public static List<Booking> getApprovedForAuthority(){
        try{
            Connection conn = Connect.returnConnection();
            String Query    = "SELECT * FROM (Booking NATURAL JOIN Room) JOIN Applicant USING(applicant_id) " + 
                              "WHERE authority != 'PENDING' AND authority != 'REJECTED' " ; 
            
            //Statement stmt  = conn.createStatement(); ResultSet rs    = stmt.executeQuery(Query);
            PreparedStatement stmt = conn.prepareStatement(Query); ResultSet rs = stmt.executeQuery() ;  
            
            List<Booking> res = new ArrayList<>();            
            while(rs.next()){
                Booking b = new Booking( rs.getInt("booking_id"), rs.getString("room_id")   , rs.getString("applicant_id"),
                                         rs.getString("dept")   , rs.getString("authority") , rs.getString("security"),
                                         rs.getString("av_cell"), rs.getString("name")      , rs.getString("phone"), 
                                         rs.getString("email")  , rs.getString("building")  , rs.getInt("floor")
                                        ) ; 
                res.add( b );
            }
            return res;
        }
        catch(Exception e){
            System.out.println(e);
            return new ArrayList<>();
        }        
    }    
    
    public static List<Booking> getPendingForSecurity(){
        try{
            Connection conn =   Connect.returnConnection();
            String Query    = "SELECT * FROM (Booking NATURAL JOIN Room) JOIN Applicant USING(applicant_id) " + 
                              "WHERE security = 'PENDING' AND authority != 'PENDING' AND authority != 'REJECTED' " ; 
            
            //Statement stmt  = conn.createStatement(); ResultSet rs    = stmt.executeQuery(Query);
            PreparedStatement stmt = conn.prepareStatement(Query); ResultSet rs = stmt.executeQuery() ; 
            System.out.println(rs);
            
            List<Booking> res = new ArrayList<>();            
            while(rs.next()){
                Booking b = new Booking( rs.getInt("booking_id"), rs.getString("room_id")   , rs.getString("applicant_id"),
                                         rs.getString("dept")   , rs.getString("authority") , rs.getString("security"),
                                         rs.getString("av_cell"), rs.getString("name")      , rs.getString("phone"), 
                                         rs.getString("email")  , rs.getString("building")  , rs.getInt("floor")
                                        ) ; 
                res.add( b );
            }
            return res;
        }
        catch(Exception e){
            System.out.println(e);
            return new ArrayList<>();
        }        
    }    
    public static List<Booking> getApprovedForSecurity(){
        try{
            Connection conn = Connect.returnConnection();
            String Query    = "SELECT * FROM (Booking NATURAL JOIN Room) JOIN Applicant USING(applicant_id) " + 
                              "WHERE security != 'PENDING' AND security != 'REJECTED' " ; 
            
            //Statement stmt  = conn.createStatement(); ResultSet rs    = stmt.executeQuery(Query);
            PreparedStatement stmt = conn.prepareStatement(Query); ResultSet rs = stmt.executeQuery() ;  
            
            List<Booking> res = new ArrayList<>();            
            while(rs.next()){
                Booking b = new Booking( rs.getInt("booking_id"), rs.getString("room_id")   , rs.getString("applicant_id"),
                                         rs.getString("dept")   , rs.getString("authority") , rs.getString("security"),
                                         rs.getString("av_cell"), rs.getString("name")      , rs.getString("phone"), 
                                         rs.getString("email")  , rs.getString("building")  , rs.getInt("floor")
                                        ) ; 
                res.add( b );
            }
            return res;
        }
        catch(Exception e){
            System.out.println(e);
            return new ArrayList<>();
        } 
    }    
    
    public static List<Booking> getPendingForAVCell(){
        try{
            Connection conn =   Connect.returnConnection();
            String Query    = "SELECT * FROM (Booking NATURAL JOIN Room) JOIN Applicant USING(applicant_id) " + 
                              "WHERE av_cell = 'PENDING' AND authority != 'PENDING' AND authority != 'REJECTED' " ; 
            
            //Statement stmt  = conn.createStatement(); ResultSet rs    = stmt.executeQuery(Query);
            PreparedStatement stmt = conn.prepareStatement(Query); ResultSet rs = stmt.executeQuery() ; 
            System.out.println(rs);
            
            List<Booking> res = new ArrayList<>();            
            while(rs.next()){
                Booking b = new Booking( rs.getInt("booking_id"), rs.getString("room_id")   , rs.getString("applicant_id"),
                                         rs.getString("dept")   , rs.getString("authority") , rs.getString("security"),
                                         rs.getString("av_cell"), rs.getString("name")      , rs.getString("phone"), 
                                         rs.getString("email")  , rs.getString("building")  , rs.getInt("floor")
                                        ) ; 
                res.add( b );
            }
            return res;
        }
        catch(Exception e){
            System.out.println(e);
            return new ArrayList<>();
        }
    }    
    public static List<Booking> getApprovedForAVCell(){
        try{
            Connection conn = Connect.returnConnection();
            String Query    = "SELECT * FROM (Booking NATURAL JOIN Room) JOIN Applicant USING(applicant_id) " + 
                              "WHERE av_cell != 'PENDING' AND av_cell != 'NA' AND av_cell != 'REJECTED' " ; 
            
            //Statement stmt  = conn.createStatement(); ResultSet rs    = stmt.executeQuery(Query);
            PreparedStatement stmt = conn.prepareStatement(Query); ResultSet rs = stmt.executeQuery() ;  
            
            List<Booking> res = new ArrayList<>();            
            while(rs.next()){
                Booking b = new Booking( rs.getInt("booking_id"), rs.getString("room_id")   , rs.getString("applicant_id"),
                                         rs.getString("dept")   , rs.getString("authority") , rs.getString("security"),
                                         rs.getString("av_cell"), rs.getString("name")      , rs.getString("phone"), 
                                         rs.getString("email")  , rs.getString("building")  , rs.getInt("floor")
                                        ) ; 
                res.add( b );
            }
            return res;
        }
        catch(Exception e){
            System.out.println(e);
            return new ArrayList<>();
        }
    }
      
    public static List<Booking> getPendingForDept(String dept){
        try{
            Connection conn = Connect.returnConnection();
            String Query = "SELECT * FROM (Booking NATURAL JOIN Room) JOIN Applicant USING(applicant_id) " + 
                           "WHERE Applicant.dept = ? AND Booking.dept = 'PENDING' " ;
            
            PreparedStatement stmt  = conn.prepareStatement(Query);
            stmt.setString(1, dept);            
            ResultSet rs            = stmt.executeQuery();
            
            List<Booking> res = new ArrayList<>();            
            while(rs.next()){
                Booking b = new Booking( rs.getInt("booking_id"), rs.getString("room_id")   , rs.getString("applicant_id"),
                                         rs.getString("dept")   , rs.getString("authority") , rs.getString("security"),
                                         rs.getString("av_cell"), rs.getString("name")      , rs.getString("phone"), 
                                         rs.getString("email")  , rs.getString("building")  , rs.getInt("floor")
                                        ) ; 
                res.add( b );
            }
            return res;
        }
        catch(Exception e){
            System.out.println(e);
            return new ArrayList<>();
        }
    }    
    public static List<Booking> getApprovedForDept(String dept){
        try{
            Connection conn = Connect.returnConnection();
            String Query = "SELECT * FROM (Booking NATURAL JOIN Room) JOIN Applicant USING(applicant_id) " + 
                           "WHERE Applicant.dept = ? AND Booking.dept != 'PENDING' AND Booking.dept != 'REJECTED' " ;
            
            PreparedStatement stmt  = conn.prepareStatement(Query);
            stmt.setString(1, dept);            
            ResultSet rs            = stmt.executeQuery();
            
            List<Booking> res = new ArrayList<>();            
            while(rs.next()){
                Booking b = new Booking( rs.getInt("booking_id"), rs.getString("room_id")   , rs.getString("applicant_id"),
                                         rs.getString("dept")   , rs.getString("authority") , rs.getString("security"),
                                         rs.getString("av_cell"), rs.getString("name")      , rs.getString("phone"), 
                                         rs.getString("email")  , rs.getString("building")  , rs.getInt("floor")
                                        ) ; 
                res.add( b );
            }
            return res;
        }
        catch(Exception e){
            System.out.println(e);
            return new ArrayList<>();
        }
    }
         
    
    public static boolean actionByAuthority(int booking_id, String user_id, int action){
        try{
            Connection conn  =   Connect.returnConnection();
            String Query     =   "UPDATE Booking " + "SET authority = ? " + "WHERE booking_id = ? " ;
            String authority = "" ; 
            if ( action == 0 ){
                authority = "REJECTED" ; 
            }
            else if ( action == 1 ){
                authority = user_id ; 
            }
            
            PreparedStatement stmt  =   conn.prepareStatement(Query);                        
            stmt.setString(1,authority);
            stmt.setInt(2,booking_id);
            stmt.executeUpdate();
            
            if ( action == 0 ){
                String Query_Rej = "UPDATE Booking_slot " + "SET status = 'REJECTED' " + "WHERE booking_id = ? "; 
                stmt    =   conn.prepareStatement(Query_Rej);
                stmt.setInt(1,booking_id);
                stmt.executeUpdate();
            }
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
        
    public static boolean actionByDept(int booking_id, String user_id, int action){
        try{
            Connection conn  =   Connect.returnConnection();
            String Query     =   "UPDATE Booking " + "SET dept = ? " + "WHERE booking_id = ? " ;
            String dept = "" ; 
            if ( action == 0 ){
                dept = "REJECTED" ; 
            }
            else if ( action == 1 ){
                dept = user_id ; 
            }
            
            PreparedStatement stmt  =   conn.prepareStatement(Query);                        
            stmt.setString(1,dept);
            stmt.setInt(2,booking_id);
            stmt.executeUpdate();
            
            if ( action == 0 ){
                String Query_Rej = "UPDATE Booking_slot " + "SET status = 'REJECTED' " + "WHERE booking_id = ? "; 
                stmt    =   conn.prepareStatement(Query_Rej);
                stmt.setInt(1,booking_id);
                stmt.executeUpdate();
            }
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean actionByAVCell(int booking_id,String user_id,int action){
        try{
            Connection conn  =   Connect.returnConnection();
            String Query     =   "UPDATE Booking " + "SET av_cell = ? " + "WHERE booking_id = ? " ; 
            String dept = "" ; 
            if ( action == 0 ){
                dept = "REJECTED" ; 
            }
            else if ( action == 1 ){
                dept = user_id ; 
            }
            
            PreparedStatement stmt  =   conn.prepareStatement(Query);                        
            stmt.setString(1,dept);
            stmt.setInt(2,booking_id);
            stmt.executeUpdate();
            
            if ( action == 0 ){
                String Query_Rej = "UPDATE Booking_slot " + "SET status = 'REJECTED' " + "WHERE booking_id = ? "; 
                stmt    =   conn.prepareStatement(Query_Rej);
                stmt.setInt(1,booking_id);
                stmt.executeUpdate();
            }
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }       
    }
    
    public static boolean actionBySecurity(int booking_id,String user_id,int action){
        try{
            Connection conn  =   Connect.returnConnection();
            String Query     =   "UPDATE Booking " + "SET security = ? " + "WHERE booking_id = ? " ; 
            String dept = "" ; 
            if ( action == 0 ){
                dept = "REJECTED" ; 
            }
            else if ( action == 1 ){
                dept = user_id ; 
            }
            
            PreparedStatement stmt  =   conn.prepareStatement(Query);                        
            stmt.setString(1,dept);
            stmt.setInt(2,booking_id);
            stmt.executeUpdate();
            
            if ( action == 0 ){
                String Query_Rej = "UPDATE Booking_slot " + "SET status = 'REJECTED' " + "WHERE booking_id = ? "; 
                stmt    =   conn.prepareStatement(Query_Rej);
                stmt.setInt(1,booking_id);
                stmt.executeUpdate();
            }
            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        } 
    }
    
    
    public static String getSlot(int booking_id){
        try{
            Connection conn =   Connect.returnConnection();
            String Query    =   "SELECT * FROM Booking_slot" + " WHERE booking_id = ? " ; 
            
            PreparedStatement stmt  =   conn.prepareStatement(Query);
            stmt.setInt(1, booking_id);
            ResultSet rs            =   stmt.executeQuery();
            
            String slot = "" ;
            while(rs.next()){
                slot += rs.getString("date") + " " + rs.getString("time_slot") + "<br/><br/>" ;
            }
            return slot;
        }
        catch(Exception e){
            return null;
        }
    }
        
    public static List<String> getRooms(){
        try{
            Connection conn  =   Connect.returnConnection();
            String Query     =   "SELECT * FROM Room" ; 
            
            Statement stmt   =   conn.createStatement(); ResultSet rs    =   stmt.executeQuery(Query);

            List<String> res =   new ArrayList<>();
            while(rs.next()){
                res.add( rs.getString("room_id") );
            }
            return res;
        }
        catch(Exception e){
            System.out.println(e);
            return new ArrayList<>();
        }        
    }
        
    public static List<Booking> getBookingsForApplicant(String id){
        try{
            Connection conn =   Connect.returnConnection();
            String Query    =   "SELECT * FROM Booking NATURAL JOIN Room" + " WHERE applicant_id = ?" ; 
            
            PreparedStatement stmt  =   conn.prepareStatement(Query);
            stmt.setString(1,id);
            ResultSet rs            =   stmt.executeQuery();
            
            List<Booking> res = new ArrayList<>();
            while(rs.next()){
                Booking b = new Booking( rs.getInt("booking_id"), rs.getString("room_id")   , id ,
                                         rs.getString("dept")   , rs.getString("authority") , rs.getString("security"),
                                         rs.getString("av_cell"), "", "", "" , rs.getString("building")  , rs.getInt("floor")
                                        ) ; 
                res.add( b );                
            }
            return res;
        }
        catch(Exception e){
            System.out.println(e);
            return new ArrayList<>();
        }        
    }    

    public static List<String> getAvailableSlots(String room_id, String date){
        try{
            Connection conn =   Connect.returnConnection();
            String Query = "SELECT * FROM Booking_slot" + " WHERE room_id = ? AND date = ? AND status != 'REJECTED' " ; 
            
            PreparedStatement stmt  =   conn.prepareStatement(Query);                    
            stmt.setString(1,room_id);
            stmt.setString(2,date);
            ResultSet rs            =   stmt.executeQuery();
            
            List<String> filled = new ArrayList<>();
            while(rs.next()){
                filled.add(rs.getString("time_slot"));
            }
            
            String[] total = {"6:00-7:00", "7:00-8:00", "8:00-9:00", "9:00-10:00", "10:00-11:00", "11:00-12:00"};
            
            List<String> available = new ArrayList<>();
            for( int i=0 ; i<total.length ; i++ ){
                boolean avail = true;
                for(String j : filled ){
                    if( total[i].equals(j) )
                        avail = false;
                }
                if(avail)
                    available.add(total[i]);
            }
            return available;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return new ArrayList<>();
    }
    
    
    
    public static boolean cancelBooking(int booking_id){
        try{
            Connection conn =   Connect.returnConnection();
            
            String Query1    =   "DELETE FROM Booking_slot " + "WHERE booking_id = ? " ;
            PreparedStatement stmt1 =   conn.prepareStatement(Query1);
            stmt1.setInt(1,booking_id);
            stmt1.executeUpdate();
            
            String Query2    =   "DELETE FROM Booking " + "WHERE booking_id = ? " ; 
            PreparedStatement stmt2 =   conn.prepareStatement(Query2);
            stmt2.setInt(1,booking_id);
            stmt2.executeUpdate();
            
            return true;
        }
        catch(Exception e){
           System.out.println(e);
           return false;
       }
    }
    
    
}
