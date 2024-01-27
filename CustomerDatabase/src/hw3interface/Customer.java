/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw3interface;

/**
 *
 * @author egerm
 */
public class Customer {
    private String Name;
    private String Surname;
    private int ID;
    private Item Link;

    public Customer(String name, String surname, int id, Item link){
        this.Name = name;
        this.Surname = surname;
        this.ID = id;
        this.Link = link;
    }
    public Customer(){

    }
    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public int getID() {
        return ID;
    }

    public Item getLink() {
        return Link;
    }
    public void setLink(Item Link) {
        this.Link = Link;
    }

    @Override
    public String toString(){
        String retstr = "Name " + Name;
        retstr = retstr + " Surname " + Surname;
        retstr = retstr + " ID " + ID;
        return retstr;
    }

}
