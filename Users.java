public class Users {
    
    private String role;
    private int roleId;
    private double amount;
    private double price;

    public Users(String role, int roleId, double amount, double price) {
        this.role = role;
        this.roleId = roleId;
        this.amount = amount;
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        return role + roleId + "\tAmount: " + amount + "\tPrice: " + price;
    }
}
