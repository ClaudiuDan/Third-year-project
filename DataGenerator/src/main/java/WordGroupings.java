import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WordGroupings {
    List<Group> groups = new ArrayList<>();
    private static final int GROUP_SIZE = 3;

    WordGroupings () {
        try {
            buildGroups();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buildGroups () throws IOException {
        File raw_text = new File("text");
        BufferedReader reader = new BufferedReader(new FileReader(raw_text));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(" ");
            for (int i = 0; i < split.length - 1; i++) {
                Word[] arr = new Word[GROUP_SIZE];
                for (int j = 0; j < GROUP_SIZE; j++) {
                    arr[j] = new Word(null, null);
                }
                groups.add(new Group(arr));
            }
        }
    }

    public Group getGroup () {
        for (int i = 0; i < groups.size(); i++) {
            ;
        }
        return null;
    }

    class Group {
        private Word[] words;
        Group (Word... words) {
            this.words = words;
        }
    }

    class Word {
        String word, type;
        Word (String word, String type) {
            this.word = word;
            this.type = type;
        }
    }
}
