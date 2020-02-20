import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class TextHandler {
    private File raw_text = new File("text");
    public static BufferedWriter writer, errorWriter;
    static int lastPosition = 577;

    private Map<String, Boolean> apparitions = new HashMap<>();
    public void produceWords() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(raw_text));
            String line;
            WordTypeExtractor wordTypeExtractor = new WordTypeExtractor();
            writer = new BufferedWriter(new FileWriter("wordsFromText"));
            errorWriter = new BufferedWriter(new FileWriter("errors"));
            addMissingWords();
            int position = 0;
            while ((line = reader.readLine()) != null) {
                String filtered = line.replaceAll("[^A-Za-z]+", " ");
                String[] splitted = filtered.split(" ");
                for (String rawValue : splitted) {
                    String value = rawValue.toLowerCase();
                    if (position >= lastPosition) {
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
                            if (counter == 2) {
                                close();
                                System.exit(0);
                            }
                        }
                        lastPosition++;
                    }
                    position++;
                }
            }
            close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            close();
        }
    }
    static int counter = 0;

    public static void close () {
        try {
            System.out.println(lastPosition);
            writer.close();
            errorWriter.close();
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
}
