import java.util.*;

public class Dictionary {
    private Map<String, List<String>> dictionary = new HashMap<>();
    Dictionary() {
        WordsRetrieval wordsRetrieval = new WordsRetrieval();
        List<WordsRetrieval.Word> words = wordsRetrieval.getWords();
        fillDictionary(words);
    }
    public String getRandomWord(String type) {
        List<String> words = dictionary.get(type);
        if (words.isEmpty()) {
            System.out.println((char)27 + "[31m" + "Empty list!!!");
        }
        return words.get((new Random()).nextInt(words.size()));
    }

    private void fillDictionary(List<WordsRetrieval.Word> words) {
        for (WordsRetrieval.Word word : words) {
            dictionary.computeIfAbsent(word.type, (k) -> new ArrayList<>());
            dictionary.get(word.type).add(word.value);
        }
    }

    public void printWords() {
        for (String type : dictionary.keySet()) {
            System.out.println(type + " **** ");
            for (String word : dictionary.get(type)) {
                System.out.println(word);
            }
        }
    }
}
