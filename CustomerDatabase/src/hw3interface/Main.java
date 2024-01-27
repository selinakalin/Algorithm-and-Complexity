package hw3interface;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        // TODO code application logic here
        EEM480DataBase MyDataBase = new EEM480DataBase();
        Customer DummyCustomer = new Customer();
        MyDataBase.readFromFile("c:\\MyData.txt");
        Float exps = MyDataBase.getTotalTradeofCustomer(13456);
        System.out.println(MyDataBase.search_Customer(13456) + " Total Expense : " + exps);
        System.out.println(" The Total Trade: " + MyDataBase.getTotalTrade());
        MyDataBase.listItems (13456);
        Customer newc = new Customer();
        newc = MyDataBase.getNewCustomer("Ali", "veli", 4950);
        MyDataBase.addCustomer (newc);
        MyDataBase.addNewItem(4950, "Karburator", "Monday", 145.8F);
        MyDataBase.addNewItem(4950, "Laptop", "Tuesday", 2340);
        System.out.println(" The Total Trade : " + MyDataBase.getTotalTrade());
        MyDataBase.listItems(4950);
    }
}