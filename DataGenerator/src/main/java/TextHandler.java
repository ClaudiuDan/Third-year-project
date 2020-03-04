import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.IndexWordSet;
import net.sf.extjwnl.dictionary.Dictionary;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TextHandler {
    private File raw_text = new File("text");
    public static BufferedWriter writer, errorWriter;
    private BufferedReader manualWordsReader;
    static int lastPosition = 0;

    TextHandler() {
        try {
            writer = new BufferedWriter(new FileWriter("wordsFromText"));
            errorWriter = new BufferedWriter(new FileWriter("errors"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int counter = 0;
    private Map<String, Boolean> apparitions = new HashMap<>();


    public void produceWords() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(raw_text));
            String line;
            addManualWords();
            while ((line = reader.readLine()) != null) {
                String filtered = line.replaceAll("[^A-Za-z]+", " ");
                String[] splitted = filtered.split(" ");
                for (String rawValue : splitted) {
                    String value = rawValue.toLowerCase();
                    if (apparitions.get(value) != null) {
                        continue;
                    }
                    apparitions.put(value, true);
                    WordsRetrieval.Word word = getWord(value);
                    word = filterWord(word);
                    if (word == null) {
                        errorWriter.write(rawValue + "\n");
                        continue;
                    }
                    counter++;
                    System.out.println(counter);
                    addWord(word.value, value, word.type);
                }
            }
            close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            close();
        }
    }
    private void addWord (String generalValue, String value, String type) {
        try {
            writer.write(generalValue + " " + value + " " + type + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void close () {
        try {
            System.out.println(lastPosition);
            writer.close();
            errorWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addManualWords() {
        try {
            manualWordsReader = new BufferedReader(new FileReader(new File("manualWords")));
            String line;
            while ((line = manualWordsReader.readLine()) != null) {
                String[] split = line.split(" ");
                addWord(split[0], split[0], split[1]);
                apparitions.put(split[0], true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addMissingWords() {
        if (lastPosition == 0) {
            try {
                writer.write("word word noun\n");
                writer.write("is is noun\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private WordsRetrieval.Word getWord(String wordValue) {
        IndexWord savedIndex = null;
        try {
            Dictionary dictionary = Dictionary.getDefaultResourceInstance();
            IndexWordSet indexes = dictionary.lookupAllIndexWords(wordValue);
            int maximum = -1;
            for (IndexWord indexWord : indexes.getIndexWordArray()) {
                if (maximum <= indexWord.getSenses().size()) {
                    maximum = indexWord.getSenses().size();
                    savedIndex = indexWord;
                }
            }
        } catch (JWNLException e) {
            e.printStackTrace();
        }
        if (savedIndex == null) {
            return null;
        }
        return new WordsRetrieval.Word(savedIndex.getLemma(), savedIndex.getPOS().getLabel());
    }

    private WordsRetrieval.Word filterWord(WordsRetrieval.Word word) {
        if (word == null) {
            return null;
        }

        if (word.value.length() <= 1) {
            return null;
        }
        return word;
    }
}
