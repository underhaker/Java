/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wordcounter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author work
 */
public class WordCounter implements BookWordsAnalyzer{
    private String bookString;
    private Map<String,Integer> words;
    private Map<String,Integer> wordsType;
    /**
     * @param args the command line arguments
     */
    
    public WordCounter(){
        words = new HashMap<String,Integer>();
        wordsType = new HashMap<String,Integer>();
    }
    @Override
    public void readBook(String book) {
        bookString = new StringBuilder(book).toString();
        wordsType = countTypes(book);
        words = countOccurences(book);
    }
//    private Map<String, Integer> countTypes(String book) {
//        Map<String,Integer> result=new LinkedHashMap<String,Integer>();
//        String[] wordArray=book.split(" ");
//        String type;
//        for(String word: wordArray){
//            type = new StringBuilder(getType(word)).toString();
//            result.put(type, Integer.SIZE)
//        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    private String getType(String word) {
        return word.substring(0,word.indexOf("_"));
    }
    private static int numberOfOccurences(String source,String word){
        int occurences = 0;
        if(source.contains(word)){
            int originalLength = source.length();
            int withoutLength = source.replace(word, "").length();
            occurences = (originalLength - withoutLength)/word.length();
        }
        return occurences;
    }
    private static Map countOccurences(String book){
        Map<String,Integer> result=new LinkedHashMap<String,Integer>();
        String[] wordArray=book.split(" ");
        for(int i=0; i<wordArray.length; i++){
            result.put(wordArray[i], numberOfOccurences(book,wordArray[i]));
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
        String[] wordsArray = bookString.split(" ");
        Set<String> hs = new HashSet<String>();
        for(String word: wordsArray){
            hs.add(word);
        }
        return hs.size();
    }

    @Override
    public int mostlyUsedWordAppearances() {
        int maxCount = 0;
//        String mostUsed = "";
        Set<Map.Entry<String, Integer>> set = words.entrySet();
        for (Map.Entry<String, Integer> entry : set){
//            String word = entry.getKey();
            int wordCount = entry.getValue();
            if(wordCount > maxCount){
                maxCount = wordCount;
//                mostUsed = new StringBuilder(word).toString();
            }
        }
        return maxCount;
    }

    @Override
    public String mostlyUsedWordType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public static void main(String[] args) {
       WordCounter wc = new WordCounter();
       wc.readBook("strange man is strange man");
       System.out.println(wc.wordOccurrences("strange"));
       System.out.println(wc.wordOccurrences("man"));
       System.out.println(wc.wordOccurrences("is"));
       System.out.println(wc.uniqueWordsCount());
        System.out.println(wc.mostlyUsedWordAppearances());
    }

    

    
}
