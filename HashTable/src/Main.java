public class Main {
    public static void main(String[] args) {
        Hash myHash = new Hash(1000);
        myHash.ReadFileandGenerateHash("text.txt", 1000);
        //If you want to see the hash table and word index you can apply this method
        //myHash.printHashTable();
        myHash.DisplayResult("DisplayResult.txt");
        myHash.DisplayResultOrdered("DisplayResultOrdered.txt");

        System.out.println("Number of Occurrences: " + myHash.checkWord("the"));

        System.out.println("Frequency: " +  myHash.showFrequency("the"));

        System.out.println("Max Repeated Word: " +myHash.showMaxRepeatedWord());

        System.out.println("Efficiency: " + myHash.TestEfficiency());

        System.out.println("Number of Collisions: " + myHash.NumberOfCollusion());
    }
}