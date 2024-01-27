package hw3interface;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class EEM480DataBase implements DB_Interface {

    private ArrayList<Customer> customers;

    // Constructor to initialize the ArrayList of customers
    public EEM480DataBase() {

        this.customers = new ArrayList<>();
    }
    @Override
    public void addCustomer(Customer newCustomer) {
        customers.add(newCustomer);
    }
    @Override
    public void listItems(int ID) {
        // Search for the customer with the given ID
        Customer customer = search_Customer(ID);
        if (customer != null) {
            System.out.println(customer.getName() + " " + customer.getSurname() + " " + customer.getID() + " " + "Item list: ");

            Item currentItem = customer.getLink();
            while (currentItem != null) {
                System.out.println(currentItem.ItemName + " " +  currentItem.Date + " " +currentItem.Price);

                currentItem = currentItem.Link;
            }
            // If the customer has no items, indicate that there are no items
            if (customer.getLink() == null) {
                System.out.println("No items.");
            }
        } else {
            System.out.println("Customer not found.");
        }
    }
    @Override
    public Customer getNewCustomer(String Name, String Surname, int ID) {
        return new Customer(Name, Surname, ID, null);
    }
    @Override
    public void addNewItem(Integer ID, String ItemName, String Date, float Price){
        Customer customer = search_Customer(ID);

        if (customer != null) {
            // Create a new item and link it to the customer's existing items
            Item newItem = new Item();
            newItem.ItemName = ItemName;
            newItem.Date = Date;
            newItem.Price = Price;

            Item customerLink = customer.getLink();

            if (customerLink == null) {
                customer.setLink(newItem);
            }else {
                newItem.Link = customerLink;
                customer.setLink(newItem);
            }

        } else {
            // If the customer is not found, throw an exception
            throw new IDNotFoundException("Customer with ID " + ID + " not found.");
        }
    }
    @Override
    public Float getTotalTradeofCustomer(int ID) {
        Customer customer = search_Customer(ID);

        if(customer != null){
            Item currentItem = customer.getLink();
            float trade = 0;
            while (currentItem != null) {
                trade += currentItem.Price;
                currentItem = currentItem.Link;
            }
            return trade;
        }else {
            System.out.println("Customer not found.");
            return null;
        }

    }
    @Override
    public Float getTotalTrade() {
        float totalTrade = 0;
        for (Customer customer : customers) {
            if (customer != null) {
                Item currentItem = customer.getLink();

                while (currentItem != null) {
                    totalTrade += currentItem.Price;
                    currentItem = currentItem.Link;
                }
            }
        }
        return totalTrade;
    }
    @Override
    public void readFromFile(String path) {

        try {
            File file = new File(
                    "Mydata.txt");
            BufferedReader br
                    = new BufferedReader(new FileReader(file));
            String dataLine;

            // Read each line from the file
            while ((dataLine = br.readLine()) != null){
                String[] strArr = dataLine.split(" ");
                // Check if the first element of the line is an integer
                if (checkInt(strArr[0])){
                    int Id = Integer.parseInt(strArr[0]);
                    String ItemName = strArr[1];
                    String Date = strArr[2];
                    float Price = Float.parseFloat(strArr[3]);
                    addNewItem(Id,ItemName,Date,Price);
                }else{
                    // If it's not, parse the values and add a new customer
                    String Name = strArr[0];
                    String SurName = strArr[1];
                    int Id = Integer.parseInt(strArr[2]);
                    Customer customer = new Customer(Name,SurName,Id,null);
                    addCustomer(customer);
                }
            }

        }catch (Exception ex){
            // Print any exceptions that may occur during file reading
            System.out.println(ex.getMessage());
        }


    }

    // A method that checks whether the value in an array is an integer
    Boolean checkInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch(Exception ex) {
            return false;
        }
    }
    @Override
    public Customer search_Customer(int ID) {
        for (Customer customer : customers) {
            if (customer != null && customer.getID() == ID) {
                return customer;
            }
        }
        // Return null if customer is not found
        return null;
    }

    // Custom exception class for ID not found
    public static class IDNotFoundException extends RuntimeException {
        public IDNotFoundException(String message) {
            super(message);
        }
    }

}
