package word.counter;

public interface BookWordsAnalyzer {
    // String bookString;
    // reads a book and finds how many times is every word used
    // when new book is read, erase the data for the old one
    void readBook(String book);

    // returns how many times the word is repeated in the book
    int wordOccurrences(String word);

    // returns how many unique words are used in the book
    int uniqueWordsCount();

    // returns the count of appearances of the mostly used word/words
    int mostlyUsedWordAppearances();

    // returns what type of words is used the most (repeated words do matter)
    // adjectives, nouns, verbs, unknown
    String mostlyUsedWordType();
}
