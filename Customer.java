
public class Customer {
    
    private String id;
    private int numberOfOrders;
    
    public Customer(String id, int moneyOwed) {
        this.id = id;
        this.numberOfOrders = moneyOwed;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getNumberOfOrders() {
        return numberOfOrders;
    }
    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", numberOfOrders=" + numberOfOrders + "]";
    }
    
    
    
    

}
