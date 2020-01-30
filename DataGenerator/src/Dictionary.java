import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {
    private static Map<String, List<String>> dictionary = new HashMap<>();
    Dictionary() {
        WordsRetrieval wordsRetrieval = new WordsRetrieval();
        List<WordsRetrieval.Word> words = wordsRetrieval.getWords();
        fillDictionary(words);
    }
    public static String getRandomWord(String type) {
        return null;
    }

    private void fillDictionary(List<WordsRetrieval.Word> words) {
        for (WordsRetrieval.Word word : words) {
            dictionary.computeIfAbsent(word.type, (k) -> new ArrayList<>());
            dictionary.get(word.type).add(word.value);
        }
    }
}
