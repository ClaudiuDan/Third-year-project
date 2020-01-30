import java.util.*;

public class Dictionary {
    private Map<String, String> words = new HashMap<>();
    private Map<String, List<String>> groupedWords = new HashMap<String, List<String>>();
    public void addWord (String word, String type) {
        words.put(word, type);
        groupedWords.get(type).add(word);
    }

    public void addEntryToGroupedWords(String entry) {
        groupedWords.put(entry, new ArrayList<>());
    }

    public void printWords () {
        for (String key : words.keySet()) {
            System.out.println(key + " " + words.get(key));
        }
    }

    public String getRandomWord(String type) {
        Random random = new Random();
        int size = groupedWords.get(type).size();
        int index = random.nextInt(size);
        return groupedWords.get(type).get(index);
    }
}
