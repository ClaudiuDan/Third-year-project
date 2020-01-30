import java.io.*;
import java.util.Random;

public class Generator {
    private int N_SEN;
    private final static int NR_SCEN = 4;
    Generator(int size) {
        N_SEN = size;
    }

    public void buildScenario1 (int size) throws IOException {
        BufferedWriter inputWriter = new BufferedWriter(new FileWriter(new File("input")));
        BufferedWriter targetWriter = new BufferedWriter(new FileWriter(new File("target")));

        for (int i = 0; i < size; i++) {
            String noun = Engine.dictionary.getRandomWord("nouns");
            String verb = Engine.dictionary.getRandomWord("verbs");
            String adjective = Engine.dictionary.getRandomWord("adjectives");
            String prep = Engine.dictionary.getRandomWord("prepositions");

            Data data = new Data(noun + "\n" + verb + "\n" + adjective,
                    adjective + "\n" + verb + "\n" + noun);
            inputWriter.write(data.input + "\n");
            targetWriter.write(data.target + "\n");
        }
        inputWriter.close();
        targetWriter.close();
    }

    class Data {
        String input;
        String target;
        Data (String input, String target) {
            this.input = input;
            this.target = target;
        }
    }
}
