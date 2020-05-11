import java.util.*;

public class Engine {
    public static void main (String[] args) {
//        TextHandler textHandler = new TextHandler();
//        textHandler.produceWordsStanfordTagger();
        Generator generator = new Generator();
        generator.startGeneration();
        WordGroupings wordGroupings = new WordGroupings();
        for (int i = 0; i < 0; i++) {
            List<String> types = new ArrayList<>();
            List<String> values = new ArrayList<>();
            types.add("noun"); types.add("preposition"); types.add("noun");
            values.add("man"); values.add(null); values.add(null);
            WordGroupings.Group group = wordGroupings.getPartGroup(values, types, 0);
            for (WordGroupings.Word word : group.words) {
                System.out.print(word.value + " ");
            }
            System.out.println(group.occurrences);
            System.out.println();
        }
    }
}
