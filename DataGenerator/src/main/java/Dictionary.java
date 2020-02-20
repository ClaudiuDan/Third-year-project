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

    private Integer[] sizes;
    private String[] typeIndex;
    public Integer[] getNumberOfTypes() {
        if (sizes == null) {
            sizes = new Integer[dictionary.keySet().size()];
            typeIndex = new String[sizes.length];
            Iterator<String> it = dictionary.keySet().iterator();
            int counter = 0;
            while (it.hasNext()) {
                String s = it.next();
                typeIndex[counter] = s;
                sizes[counter++] = dictionary.get(s).size();
            }
        }
        return sizes;
    }

    public Pair<String, String> getWordsPair(String type1, int index1, String type2, int index2) {
        return new Pair(dictionary.get(type1).get(index1), dictionary.get(type2).get(index2));
    }


    public String[] getIndexes() {
        return typeIndex;
    }

    class Pair<T, E> {
        public T string1;
        public E string2;
        Pair(T string1, E string2) {
            this.string1 = string1;
            this.string2 = string2;
        }
    }

    public int getPosition(String type, String word) {
        return dictionary.get(type).indexOf(word);
    }
}
