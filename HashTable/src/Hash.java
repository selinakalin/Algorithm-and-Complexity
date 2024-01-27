import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.*;

public class Hash implements Hash_Interface {

    private LinkedList<WordInfo>[] table;
    private int size = 0;

    public Hash(int size) {
        this.size = size;
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new LinkedList<>();
        }
    }

    // WordInfo class to store word information including its index and hash
    private static class WordInfo {
        String word;
        int index;
        int hash;
        int frequency;
        public WordInfo(String word, int index, int hash) {
            this.word = word;
            this.index = index;
            this.hash = hash;
        }
        public WordInfo(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }
    }

    @Override
    public Integer GetHash(String mystring) {
        // Aggregation-Based ASCII Hashing

        //
        //         int hash = 0;
        //         for (int i = 0; i < mystring.length(); i++) {
        //             hash += mystring.charAt(i);
        //         }
        //         return hash % size;
        /////////////////////////////////////////////////////////////////////////////
        // XOR-Based ASCII Hashing

        //        int hash = 0;
        //
        //        for (int i = 0; i < mystring.length(); i++) {
        //            hash = (hash ^ mystring.charAt(i));
        //        }
        //
        //        return hash % size;
        /////////////////////////////////////////////////////////////////////////////
        //
        // Prime Number-Based Hashing
        int prime = 31;

        int hash = prime;

        for (int i = 0; i < mystring.length(); i++) {
            hash = (hash * prime) + mystring.charAt(i);
        }
        hash = Math.abs(hash) % size;

        return hash;
    }

    @Override
    public void ReadFileandGenerateHash(String filename, int size) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int currentIndex = 0;

            while ((line = br.readLine()) != null) {
                // Remove punctuation marks using regular expressions
                String cleanLine = line.replaceAll("[^a-zA-Z\\s]", "");

                // Extract words using regular expressions
                Pattern pattern = Pattern.compile("\\b\\w+\\b");
                Matcher matcher = pattern.matcher(cleanLine);

                while (matcher.find()) {
                    String word = matcher.group().toLowerCase();
                    int hash = GetHash(word);
                    table[hash].add(new WordInfo(word, currentIndex++, hash));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // I created a new method to show the hash table.
    // If you want to see the hash table and word index you can apply this method
//    public void printHashTable() {
//        for (int i = 0; i < size; i++) {
//            System.out.print("Index " + i + ": ");
//            for (WordInfo wordInfo : table[i]) {
//                System.out.print(wordInfo.word + "(Index: " + wordInfo.index + ") ");
//            }
//            System.out.println();
//        }
//    }
    @Override
    public void DisplayResult(String OutputFile) {
        List<String> kelime = new ArrayList<>();
        try (PrintWriter writer = new PrintWriter(OutputFile)) {
            for (int i = 0; i < size; i++) {
                for (WordInfo wordInfo : table[i]) {
                    int frequency = 0;
                    if(kelime.contains(wordInfo.word)){
                        continue;
                    }
                    // Hesaplama işlemini burada gerçekleştir
                    for (int j = 0; j < size; j++) {
                        for (WordInfo otherWordInfo : table[j]) {
                            if (wordInfo.word.equals(otherWordInfo.word)) {
                                frequency++;
                            }
                        }
                    }
                    kelime.add(wordInfo.word);
                    writer.println(wordInfo.word + ": " + frequency + " frequency");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DisplayResult() {
        List<String> kelime = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (WordInfo wordInfo : table[i]) {
                int frequency = 0;
                if(kelime.contains(wordInfo.word)){
                    continue;}
                for (int j = 0; j < size; j++) {
                    for (WordInfo otherWordInfo : table[j]) {
                        if (wordInfo.word.equals(otherWordInfo.word)) {
                            frequency++;
                        }}}
                kelime.add(wordInfo.word);
                System.out.println(wordInfo.word + ": " + frequency + " frequency");}}}

    @Override
    public void DisplayResultOrdered(String OutputFile) {

        List<WordInfo> kelime = new ArrayList<>();
        try (PrintWriter writer = new PrintWriter(OutputFile)) {
            for (int i = 0; i < size; i++) {
                for (WordInfo wordInfo : table[i]) {
                    int frequency = 0;
                    if (kelime
                            .stream()
                            .anyMatch(x -> x.word.equals(wordInfo.word))) {
                        continue;
                    }

                    // Hesaplama işlemini burada gerçekleştir
                    for (int j = 0; j < size; j++) {
                        for (WordInfo otherWordInfo : table[j]) {
                            if (wordInfo.word.equals(otherWordInfo.word)) {
                                frequency++;
                            }
                        }
                    }
                    kelime.add(new WordInfo(wordInfo.word,frequency));
                }
            }
            kelime.sort(Comparator.comparingInt(value -> value.frequency));
            Collections.reverse(kelime);

            for (WordInfo customObj : kelime) {
                writer.println(customObj.word + " : " + customObj.frequency);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int showFrequency(String myword) {
            for (int i = 0; i < size; i++) {
                for (WordInfo wordInfo : table[i]) {
                    if(!myword.equals(wordInfo.word)){
                        continue;
                    }
                    int frequency = 0;
                    for (int j = 0; j < size; j++) {
                        for (WordInfo otherWordInfo : table[j]) {

                            if (wordInfo.word.equals(otherWordInfo.word)) {
                                frequency++;

                            }
                        }
                    }
                    return frequency;
                }
            }
        return -1;
    }

    @Override
    public String showMaxRepeatedWord() {
        List<WordInfo> kelime = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (WordInfo wordInfo : table[i]) {
                int frequency = 0;
                if (kelime
                        .stream()
                        .anyMatch(x -> x.word.equals(wordInfo.word))) {
                    continue;
                }

                // Hesaplama işlemini burada gerçekleştir
                for (int j = 0; j < size; j++) {
                    for (WordInfo otherWordInfo : table[j]) {
                        if (wordInfo.word.equals(otherWordInfo.word)) {
                            frequency++;
                        }
                    }
                }
                kelime.add(new WordInfo(wordInfo.word,frequency));
            }
        }
        kelime.sort(Comparator.comparingInt(value -> value.frequency));
        return kelime.get((int) (kelime.stream().count()-1)).word;
    }

    @Override
    public int checkWord(String myword) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (WordInfo wordInfo : table[i]) {
                if(!myword.equals(wordInfo.word)){
                    continue;
                }
                int frequency = 0;
                for (int j = 0; j < size; j++) {
                    for (WordInfo otherWordInfo : table[j]) {

                        if (wordInfo.word.equals(otherWordInfo.word)) {
                            indexes.add(otherWordInfo.index);
                            frequency++;
                        }
                    }
                }
                System.out.println("Indexes of " + myword + ": ");
                System.out.println(String.join(",",indexes.toString()));
                return frequency;
            }
        }
        return -1;
    }

    @Override
    public float TestEfficiency() {
        int totalElements = 0;

        for (int i = 0; i < size; i++) {
            totalElements += table[i].size();
        }

        // Calculate the load factor
        float loadFactor = (float) totalElements / size;

        int collusion = NumberOfCollusion();
        return (1 - loadFactor) * (1 - (float)collusion / totalElements);
    }

    @Override
    public int NumberOfCollusion() {
        int collisionCount = 0;

        for (int i = 0; i < size; i++) {
            LinkedList<WordInfo> currentList = table[i];

            // Create a list to check for duplicates
            List<WordInfo> indexList = new ArrayList<>();

            for (WordInfo wordInfo : currentList) {
                int hash = wordInfo.hash;

                // Check if the hash is already in the list
                boolean hashExists = indexList.stream().anyMatch(x -> x.hash == hash);
                boolean wordExists = indexList.stream().anyMatch(x -> x.word.equals(wordInfo.word));

                if (hashExists && !wordExists) {
                    collisionCount++;
                }

                // If the word does not exist in the list, add the WordInfo to the list
                if (!wordExists) {
                    indexList.add(wordInfo);
                }
            }
        }

        return collisionCount;
    }

}
