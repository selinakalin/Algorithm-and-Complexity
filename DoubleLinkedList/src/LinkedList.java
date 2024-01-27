import java.util.logging.Level;
import java.util.logging.Logger;

public class LinkedList implements HW2Interface {

    private DLNode head ;
    public LinkedList(){
        this.head = null;
    }

    public void Insert(int newElement, int pos) throws Exception{
        if(pos < 0){
            throw LinkedListException();
        }
        DLNode newNode = new DLNode();
        newNode.Element = newElement;

        if(pos == 0){
            newNode.right = head;
            newNode.left = null;

            if(head != null){
                head.left = newNode;
            }
            head = newNode;
        }else {
            DLNode ref = head;
            int ref_position = 0;

            while(ref_position < pos - 1 && ref != null){
                ref = ref.right;
                ref_position++;
            }
            if (ref == null){
                throw LinkedListException();
            }
            newNode.right = ref.right;
            newNode.left = ref;

            if(ref.right != null){
                ref.right.left = newNode;
            }
            ref.right = newNode;
        }
    }
    public int Delete(int pos) throws Exception{
        if(pos < 0 || head == null){
            throw LinkedListException();
        }
        int deletedValue;
        if(pos == 0){
            deletedValue = head.Element;
            head = head.right;
            if (head != null) {
                head.left = null;
            }
        }else {
            DLNode ref = head;
            int ref_position = 0;
            while(ref_position < pos && ref != null){
                ref = ref.right;
                ref_position++;
            }
            if (ref == null){
                throw LinkedListException();
            }
            deletedValue = ref.Element;
            if(ref.left != null){
                ref.left.right = ref.right;
            }
            if(ref.right != null){
                ref.right.left = ref.left;
             }
        }
        return deletedValue;
    }
    public void ReverseLink(){
        DLNode temp = null;
        DLNode ref = head;

        while (ref != null) {
            temp = ref.left;
            ref.left = ref.right;
            ref.right = temp;
            ref = ref.left;
        }
        if (temp != null) {
            head = temp.left;
        }
    }
    @Override
    public void SquashL() {
        DLNode tempNode = head;
        int position = 0;
        boolean bool = true;
        while(tempNode != null && tempNode.right != null  ){
            int key = tempNode.Element;
            int count = 1;

            if(position == 0 ){
                position++;
            }else{
                position += 2;
            }

            while(tempNode.right.Element == key ){
                count++;
                tempNode = tempNode.right;

                if (tempNode.right == null){
                    bool = false;

                    try {
                        this.Delete(position);
                    }
                    catch (Exception ex){

                    }
                    break;
                }
                try {
                    this.Delete(position);
                }
                catch (Exception ex){

                }
            }
            tempNode = tempNode.right;
            try {
                this.Insert(count, position);
            }
            catch (Exception ex){
                System.out.println(ex.toString());
            }


        }
        if(bool){
            try {
                this.Insert(1, position+2);
            }
            catch (Exception ex){
                System.out.println(ex.toString());
            }
        }


    }

    @Override
    public void OplashL() {
        DLNode tempNode = head;
        int position = 0;
        while(tempNode != null && tempNode.right != null){
            int key = tempNode.Element;
            int count = tempNode.right.Element;
            DLNode nextNode = tempNode.right.right;
            for(int i = 0; i < count; i++){
                try {
                    this.Insert(key, position);
                    position++;
                }
                catch (Exception ex){
                    System.out.println(ex.toString());
                }
            }
            try {
                this.Delete(position);
                this.Delete(position);
            }
            catch (Exception ex){
                System.out.println(ex.toString());
            }
            tempNode = nextNode;
        }
    }

    @Override
    public void Output() {
        DLNode ref = this.head;
        System.out.print("The Elements in the list are : ");

        while (ref != null) {
            System.out.print(ref.Element + " ");
            ref = ref.right;
        }
        System.out.println("");
    }

    @Override
    public void ROutput() {
        DLNode ref = head;
        while(ref != null) {
            ref = ref.right;
            if (ref.right == null) {
                break;
            }
        }

        System.out.print("The Reverse Elements in the list are : ");
        while(ref != null) {
            System.out.print(ref.Element + " ");
            ref = ref.left;
        }
        System.out.println("");
    }

    @Override
    public Exception LinkedListException() {
        return new Exception("Not supported yet.");
    }
    public String toString() {
        DLNode dummy = this.head;

        String elements;
        for(elements = ""; dummy != null; dummy = dummy.right) {
            elements = elements + dummy.Element + " ";
        }

        return elements;
    }
}