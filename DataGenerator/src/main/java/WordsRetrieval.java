import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WordsRetrieval {

    private BufferedReader reader;

    WordsRetrieval () {
        try {
            reader = new BufferedReader(new FileReader(new File("words")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Word> getWords () {
        List<Word> words = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(" ");
                words.add(new Word(split[0], split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }

    static class Word {
        String type, value;

        Word (String value, String type) {
            this.type = type;
            this.value = value;
        }
    }
}
