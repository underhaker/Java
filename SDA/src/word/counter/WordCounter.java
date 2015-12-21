package word.counter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class WordCounter implements BookWordsAnalyzer {
    private String bookString;
    private Map<String, Integer> words;
    private static String[] wordArray;
    private int[] types;

    public WordCounter() {
        words = new HashMap<String, Integer>();
        types = new int[4];
    }

    @Override
    public void readBook(String book) {
        words = new HashMap<String, Integer>();
        types = new int[4];

        bookString = new StringBuilder(filterBook(book)).toString();
        words = countOccurences(bookString);

    }

    private String filterBook(String book) {
        wordArray = book.split(" ");
        types = countTypes(book);
        String filteredBook = "";
        for (int i = 0; i < wordArray.length; i++) {
            wordArray[i] = wordArray[i].substring(2, wordArray[i].length());
            filteredBook += new StringBuilder(wordArray[i] + " ").toString();
        }

        return filteredBook;
    }

    private int[] countTypes(String book) {
        String type = "";
        int[] types = new int[4];
        for (String word : wordArray) {
            type = new StringBuilder(getType(word)).toString();
            switch (type) {
                case "a":
                    types[0]++;
                    break;
                case "n":
                    types[1]++;
                    break;
                case "v":
                    types[2]++;
                    break;
                case "u":
                    types[3]++;
                    break;
            }
        }
        return types;
    }

    private String getType(String word) {
        return word.substring(0, word.indexOf("_"));
    }

    private static int numberOfOccurences(String source, String word) {
        int occurences = 0;
        if (source.contains(word)) {
            int originalLength = source.length();
            int withoutLength = source.replace(word, "").length();
            occurences = (originalLength - withoutLength) / word.length();
        }
        return occurences;
    }

    private static Map<String, Integer> countOccurences(String book) {
        Map<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < wordArray.length; i++) {
            result.put(wordArray[i], numberOfOccurences(book, wordArray[i]));
        }
        return result;
    }

    @Override
    public int wordOccurrences(String word) {
        int occurences = numberOfOccurences(bookString, word);
        return occurences;
    }

    @Override
    public int uniqueWordsCount() {
        int uniqueWordsCounter = 0;
        Set<Map.Entry<String, Integer>> set = words.entrySet();
        for (Map.Entry<String, Integer> entry : set) {
            if (entry.getValue() == 1)
                uniqueWordsCounter++;
        }
        return uniqueWordsCounter;
    }

    @Override
    public int mostlyUsedWordAppearances() {
        int maxCount = 0;
        Set<Map.Entry<String, Integer>> set = words.entrySet();
        for (Map.Entry<String, Integer> entry : set) {
            int wordCount = entry.getValue();
            if (wordCount > maxCount) {
                maxCount = wordCount;
            }
        }
        return maxCount;
    }

    @Override
    public String mostlyUsedWordType() {
        int max_type = 0, type_index = 0;
        String result = "";
        for (int i = 0; i < types.length; i++) {
            if (types[i] > max_type) {
                max_type = types[i];
                type_index = i;
            }
        }
        switch (type_index) {
            case 0:
                result = new StringBuilder("adverb").toString();
                break;
            case 1:
                result = new StringBuilder("noun").toString();
                break;
            case 2:
                result = new StringBuilder("verb").toString();
                break;
            case 3:
                result = new StringBuilder("unknown").toString();
                break;
        }
        return result;
    }

    public static void main(String[] args) {
        WordCounter wc = new WordCounter();
        wc.readBook("a_strange n_man v_is a_strange n_man");
        System.out.print("wordOccurences-strange:");
        System.out.println(wc.wordOccurrences("strange"));
        System.out.print("wordOccurences-man:");
        System.out.println(wc.wordOccurrences("man"));
        System.out.print("wordOccurences-is:");
        System.out.println(wc.wordOccurrences("is"));
        System.out.print("uniqueWordsCount:");
        System.out.println(wc.uniqueWordsCount());
        System.out.print("mostlyUsedWordAppearances:");
        System.out.println(wc.mostlyUsedWordAppearances());
        System.out.print("mostlyUsedWordType:");
        System.out.println(wc.mostlyUsedWordType());
    }

}
