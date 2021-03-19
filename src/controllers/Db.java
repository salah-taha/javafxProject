package controllers;

import models.Room;
import models.User;
import models.UserType;

import java.sql.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class Db {
        private static  Db instance = null;
        private static final String DB_URL = "jdbc:h2:~/plProjectV6";
        private static Connection  conn = null;
        private static Statement stmt = null;

       final private String users = "Users";
        final private  String rooms = "Rooms";
        final private  String services = "Services";
     final private  String tickets = "Tickets";



        private Db(){
            createConnection();
            setupTables();



        }

        public static Db getInstance(){
            if(instance == null) instance = new Db();
            return instance;
        }

        public ResultSet executeQuery(String query){
           try{
               return stmt.executeQuery(query);
           }catch (SQLException e){
               e.printStackTrace();
               return null;
           }
        } public int excuteUpdate(String query){
           try{
               return stmt.executeUpdate(query);
           }catch (SQLException e){
               e.printStackTrace();
               return 0;
           }
        }

        public boolean login(String email,String password){
            try{

                ResultSet set = stmt.executeQuery(String.format("Select * from users where email = '%s' and password = '%s'",email,password));
                if(set.next()){
                    return true;
                }
                return false;
            }catch (SQLException e){
                System.out.println(e.getMessage());
                return false;
            }
        }

        public ResultSet getUser(){


            try{
                            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while( rs.next() )
            {
                String name = rs.getString("password");
                int id = rs.getInt("id");
                System.out.println( name );
                System.out.println( id );

            }
return null;            }catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }

            void getRoom(){
                try{
                    ResultSet rs = stmt.executeQuery("SELECT * FROM rooms");
                    while( rs.next() )
                    {
                        String name = rs.getString("title");
                        int id = rs.getInt("id");
                        System.out.println( name );
                        System.out.println( id );

                    }
                           }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            void getService(){
                try{
                    ResultSet rs = stmt.executeQuery("SELECT * FROM services");
                    while( rs.next() )
                    {
                        String name = rs.getString("name");
                        int id = rs.getInt("id");
                        System.out.println( name );
                        System.out.println( id );

                    }
                           }catch (SQLException e){
                    e.printStackTrace();
                }
            } void getTicket(){
                try{
                    ResultSet rs = stmt.executeQuery("SELECT * FROM tickets");
                    while( rs.next() )
                    {
                        int roomId = rs.getInt("roomID");
                        int id = rs.getInt("userId");
                        System.out.println( roomId );
                        System.out.println( id );

                    }
                           }catch (SQLException e){
                    e.printStackTrace();
                }
            }


        void createConnection(){
            try
            {
                Class.forName("org.h2.Driver");
                 conn = DriverManager.getConnection(DB_URL, "test", "" );
                 stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                         ResultSet.CONCUR_READ_ONLY);
            }
            catch( Exception e )
            {
                System.out.println( e.getMessage() );
            }
        }




        void setupTables(){

            try{

                stmt = conn.createStatement();
                DatabaseMetaData dbm = conn.getMetaData();
                ResultSet tables = dbm.getTables(null,null,rooms.toUpperCase(),null);
                if(!tables.next())
                {

                    stmt.executeUpdate("CREATE TABLE "+ users  + "("
                        +" id INTEGER PRIMARY KEY auto_increment,\n"
                        +" password varchar(255),\n"
                        +" email varchar(255),\n"
                        +" name varchar(255),\n"
                        +" type varchar(255)"
                        +" )");
                    stmt.executeUpdate("CREATE TABLE "+ rooms  + "("
                        +" id INTEGER PRIMARY KEY auto_increment,\n"
                        +" title varchar(255),\n"
                        +" type varchar(255),\n"
                        +" price REAL"
                        +" )");
                    stmt.executeUpdate("CREATE TABLE "+ services  + "("
                        +" id INTEGER PRIMARY KEY auto_increment,\n"
                        +" name varchar(255),\n"
                        +" description varchar(255),\n"
                        +" price REAL"
                        +" )");
                    stmt.executeUpdate("CREATE TABLE "+ tickets  + "("
                        +" id INTEGER PRIMARY KEY auto_increment,\n"
                        +" userId INTEGER ,\n"
                            +"FOREIGN KEY (userId) REFERENCES "+users+"(id) ON DELETE CASCADE,\n"
                        +" roomId INTEGER ,\n"
                            +"FOREIGN KEY (roomId) REFERENCES "+rooms+"(id) ON DELETE CASCADE,\n"
                        +" serviceId INTEGER ,\n"
                        +" FOREIGN KEY (serviceId) REFERENCES "+services+"(id) ON DELETE CASCADE,\n"
                        +" checkInDate Date,\n"
                        +" checkOutDate Date,\n"
                        +" totalPrice REAL"
                        +" )");
                    User user = new User("admin","admin@gmail.com",0, UserType.employee,"123456");
                    excuteUpdate(user.insertUser());
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }




    public LinkedList<Room> getRooms() {
       LinkedList<Room> roomsList = new LinkedList<Room>();
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        java.sql.Date date = new java.sql.Date(currentDate.getTime());
        try{
           ResultSet set= stmt.executeQuery("Select * From Rooms left join tickets on Rooms.id=Tickets.id where Tickets.checkOutDate < "+date+" OR Tickets.checkOutDate is null");
           while (set.next()){
               Room room = new Room(set.getString("title"),Room.getRoomTypeFromString(set.getString("type")),set.getInt("id"),set.getDouble("price"));
               roomsList.add(room);
           }
            return roomsList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    public LinkedList<Room> searchRooms(String roomType,LocalDate date){
        LinkedList<Room> roomsList = new LinkedList<Room>();
        java.sql.Date sqlDate =  java.sql.Date.valueOf(date);
        try{
            String query = String.format("Select * From Rooms left join Tickets on Rooms.id=Tickets.id where Rooms.type = '%s' and (Tickets.checkOutDate < %s or Tickets.checkOutDate is null)",roomType.toLowerCase(),date);
            ResultSet set= stmt.executeQuery(query);
            while (set.next()){
                System.out.println(set.getString("type"));
                Room room = new Room(set.getString("title"),Room.getRoomTypeFromString(set.getString("type")),set.getInt("id"),set.getDouble("price"));
                roomsList.add(room);
            }
            return roomsList;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

    }
}
