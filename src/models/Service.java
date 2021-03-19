package models;

//Other service module
// a.Add, update and delete service (Service name, description, price, etc....)
// b.Generate statistical report for service usage

public class Service {
    String name;
    String description;
    double pricePerNight;
    int id;

    public Service(String name,String description,double price,int id){
        this.id = id;
        this.description = description;
        this.pricePerNight = price;
        this.name = name;
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String insertService() {

        return String.format("INSERT INTO Services (name, description, price) VALUES ('%s','%s',%f)", this.name,
                this.description, this.pricePerNight);
    }

    public String delService() {

        return String.format("DELETE FROM Services WHERE id = %d ", this.id);
    }

    public String updateService() {

        return String.format("UPDATE Services SET name='%s', description='%s', price=%f where id = %d",
                this.name, this.description,this.pricePerNight, this.id);
    }

    public static String searchById(int serviceId) {

        return String.format("SELECT * FROM Services where id = %d", serviceId);
    }

    public static String searchByTitle(String title) {
        return "SELECT * FROM Services where name Like '%"+title+"%'";
    }
}
