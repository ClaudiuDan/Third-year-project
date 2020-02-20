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
            BufferedWriter errorWriter = new BufferedWriter(new FileWriter("errors"));
            addMissingWords();

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
                    if (word.value.equals("word")) {
                        errorWriter.write(rawValue + "\n");
                        continue;
                    }
                    if (word.type != null && !word.value.equals("word")) {

                        writer.write(value + " " + word.value + " " + word.type + "\n");
                        counter++;
                        System.out.println(counter);
                        if (counter == 10) {
                            writer.close();
                            errorWriter.close();
                            System.exit(0);
                        }
                    }
                }
            }
            writer.close();
            errorWriter.close();
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

    private void addMissingWords() throws IOException {
        writer.write("word word noun\n");
        writer.write("is is noun\n");
    }
}
