import java.util.*;

public class Engine {
    public static void main (String[] args) {
        if (Params.BUILD_VOCAB) {
            TextHandler textHandler = new TextHandler();
            textHandler.produceWordsStanfordTagger();
        }
        Generator generator = new Generator();
        generator.startGeneration();
    }
}
