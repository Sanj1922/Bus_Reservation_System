import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
public class TicketCounter {
    public static void main(String args[]){
        String name,fromplace,toplace,date,phone,mailID;
        int seats,choice,fare;
        Scanner read = new Scanner(System.in);
        Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String userName = "root";
            String password = "Shri@sql2410";
            String url = "jdbc:mysql://localhost:3306/busreservation";
            conn = DriverManager.getConnection (url, userName, password);
            PreparedStatement stmt = null;
            PreparedStatement stmt1 = null;
            PreparedStatement stmt2 = null;
            Statement mystmt = null;
            ResultSet rs = null;
            
            while(true){
                System.out.println("1.Book tickets 2.Cancel Ticket 3.Display details of booked tickets 4.EXIT");
               System.out.println("Enter your choice:");
               choice = read.nextInt();
               switch(choice){
                   case 1:
                       System.out.println("Available buses are:");
                       System.out.println("FromPlace    ToPlace         PriceofOneSeat\n"+
                                          "Belgaum      Bangalore       700\n" +
                                          "Bangalore    Belgaum         700\n" +
                                          "Madikeri     Dharwad         800\n" +
                                          "Mysore       Hubli           750\n" +
                                          "Dharwad      Madikeri        800\n" +
                                          "Hubli        Mysore          750");
                       System.out.println("Enter name of passenger");
                       read.nextLine();
                       name = read.nextLine();
                       System.out.println("Enter phone number");
                       phone = read.nextLine();
                       System.out.println("Enter from place and to place");
                       fromplace = read.nextLine();
                       toplace = read.nextLine();
                       System.out.println("Enter number seats");
                       seats = read.nextInt();
                       System.out.println("Enter date you want to travel");
                       read.nextLine();
                       date = read.nextLine();
                       System.out.println("Enter mailID");
                       mailID = read.nextLine();
                       try {
                           //stmt = conn.createStatement();
                           // insert record
                           if(fromplace.equals("Belgaum"))
                               fare = 700*seats;
                           else if(fromplace.equals("Bangalore"))
                               fare = 700*seats;
                           else if(fromplace.equals("Madikeri"))
                               fare = 800*seats;
                           else if(fromplace.equals("Dharwad"))
                               fare = 800*seats;
                           else if(fromplace.equals("Hubli"))
                               fare = 750*seats;
                           else 
                               fare = 750*seats;
                           String ins_query = "INSERT INTO `busreservation`.`bookticket`(`NamePassenger`,`PhoneNo`,`fromPlace`,`toPlace`,`NoofSeats`,`Date`,`MailID`,`Fare`)VALUES(?,?,?,?,?,?,?,?)";
                           stmt = conn.prepareStatement(ins_query);
                           stmt.setString(1,name);
                           stmt.setString(2,phone);
                           stmt.setString(3,fromplace);
                           stmt.setString(4,toplace);
                           stmt.setInt(5,seats);
                           stmt.setString(6,date);
                           stmt.setString(7,mailID);
                           stmt.setInt(8,fare);
                           System.out.println("Total fare :"+fare);
                           System.out.println("Tickets Booked successfully: " + stmt.executeUpdate());
                           System.out.println("Tickets are sent to you via mail");
                       }
                       catch (SQLException ex){
                           // handle any errors
                            System.out.println("SQLException: " + ex.getMessage());
                            System.out.println("SQLState: " + ex.getSQLState());
                            System.out.println("VendorError: " + ex.getErrorCode());
                       }
                       break;
                   case 2:
                       System.out.println("Enter name of passenger if booked");
                       read.nextLine();
                       name = read.nextLine();
                        try {
                            String ins_query1 ="DELETE FROM `busreservation`.`bookticket` WHERE NamePassenger = ?;";
                            stmt = conn.prepareStatement(ins_query1);
                            stmt.setString(1,name);
                            if(stmt.executeUpdate()==0){
                                System.out.println("You have not booked tickets");
                            }
                            else
                                System.out.println("Cancelled Tickets Successfully");
                        }
                        catch (SQLException ex){
                        // handle any errors
                            System.out.println("SQLException: " + ex.getMessage());
                            System.out.println("SQLState: " + ex.getSQLState());
                            System.out.println("VendorError: " + ex.getErrorCode());
                        }
                        break;
                   case 3:
                       try {
                           mystmt = conn.createStatement();
                            String str = "select * from bookticket;";
                            rs = mystmt.executeQuery(str);
                            
                            // Extract data from result set
                            System.out.println("---------------------------------Booking Details--------------------------\n");
                            System.out.println("PassengerName       PhoneNo             From        To          NumberOfSeats       Date");
                            while(rs.next()){
                            //Retrieve by column name
                                name = rs.getString("NamePassenger");
                                phone = rs.getString("PhoneNo");
                                fromplace = rs.getString("fromPlace");
                                toplace = rs.getString("toPlace");
                                seats = rs.getInt("NoofSeats");
                                date = rs.getString("Date");
                                System.out.println(name+"           "+phone+"           "+fromplace+"       "+toplace+"         "+seats+"       "+date);
                                
                            }
                            System.out.println ("\n-------------END-------------\n");
                        }
                       catch (SQLException ex){
                        // handle any errors
                            System.out.println("SQLException: " + ex.getMessage());
                            System.out.println("SQLState: " + ex.getSQLState());
                            System.out.println("VendorError: " + ex.getErrorCode());
                        }
                       break;
                   case 4: System.exit(0);
               }
            } 
        }
        catch(Exception ex){
            
        }
        
    }
}