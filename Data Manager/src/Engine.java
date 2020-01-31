import java.io.*;

public class Engine {
    public static Dictionary dictionary = new Dictionary();
    public static void main (String[] args) throws IOException {
        FileScrap fileScrap = new FileScrap();
        try {
            fileScrap.scrapVerbs();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Engine engine = new Engine();
        engine.init();
        engine.getWords();
        engine.writeAllWords();
        Generator generator = new Generator(1000);
        generator.buildScenario1(1000);

    }

    private void getWords() {
        for (File file : files) {
            try {
                reader = new BufferedReader(new FileReader(file.getName()));
                String line;
                while ((line = reader.readLine()) != null) {
                    dictionary.addWord(line, file.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void init () {
        for (File file : files) {
            dictionary.addEntryToGroupedWords(file.getName());
        }
    }

    private void writeAllWords() {
        try {
            BufferedWriter allDataWriter =
                    new BufferedWriter(new FileWriter(new File("words")));
            for (String word : dictionary.words.keySet()) {
                StringBuilder builder = new StringBuilder(dictionary.words.get(word));
                if (builder.toString().equals("prepositions")){
                    continue;
                }
                builder.deleteCharAt(builder.toString().length() - 1);
                allDataWriter.write(word + " " + builder.toString() + "\n");
            }
            allDataWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private final File adjectives = new File("adjectives");
    private final File verbs = new File("verbs");
    private final File prepositions = new File("prepositions");
    private final File nouns = new File("nouns");
    private final File[] files = new File[]{adjectives, verbs, prepositions, nouns};
    private BufferedReader reader;
}
