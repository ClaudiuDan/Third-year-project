import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TextHandler {
    private File raw_text = new File("text");
    BufferedWriter writer;

    private Map<String, Boolean> apparitions = new HashMap<>();
    public void produceWords() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(raw_text));
            String line;
            WordTypeExtractor wordTypeExtractor = new WordTypeExtractor();
            writer = new BufferedWriter(new FileWriter("wordsFromText"));
            while ((line = reader.readLine()) != null) {
                String filtered = line.replaceAll("[^A-Za-z]+", " ");
                String[] splitted = filtered.split(" ");
                for (String rawValue : splitted) {
                    String value = rawValue.toLowerCase();
                    if (apparitions.get(value) != null) {
                        continue;
                    }
                    apparitions.put(value, true);
                    WordsRetrieval.Word word = wordTypeExtractor.getWordType(value);
                    if (word.type != null) {
                        writer.write(word.value + " " + word.type + "\n");
                        counter++;
                        System.out.println(counter);
                        if (counter == 10000) {
                            writer.close();
                            System.exit(0);
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static int counter = 0;

    public void close () throws IOException {
        writer.close();
    }
}
