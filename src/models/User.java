package models;

//1.User Module
// a.Add, update and delete employees
// b.Add, update and delete customers
// c.Add, update and delete rooms and its service


public class User {
    private String username;
    private String email;
    private int id;
    private UserType userType;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public User(String name,String email,int id,UserType type,String password){
        this.id = id;
        this.username = name;
        this.email = email;
        this.userType = type;
        this.password = password;
    }

    public String getUserTypeString(){
        String userType = "";
        switch (this.userType){
            case customer -> {
                userType =  "customer";
            }
            case employee -> {
                userType =  "employee";
            }

        }
        return userType;
    }

    public static UserType getUserTypeFromString(String roomTypeString){
        UserType type = UserType.customer;
        switch (roomTypeString.trim().toLowerCase()){
            case "customer" -> {
                type = UserType.customer;
            }
            case "employee" -> {
                type = UserType.employee;
            }

        }
        return type;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String insertUser() {

        return String.format("INSERT INTO Users (name, type, email,password) VALUES ('%s','%s','%s','%s')", this.username,
                this.userType, this.email,this.password);
    }

    public String delUser() {

        return String.format("DELETE FROM Users WHERE id = %d ", this.id);
    }

    public String updateUser() {

        return String.format("UPDATE Users SET name='%s', type='%s', email='%s', password='%s' where id = %d",
                this.username, this.userType,this.email,this.password, this.id);
    }

    public static String searchById(int userId) {

        return String.format("SELECT * FROM Users where id = %d", userId);
    }

    public static String searchByTitle(String title) {
        return "SELECT * FROM Users where name Like '%"+title+"%'";
    }
}

