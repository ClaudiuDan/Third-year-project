import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PatternCreator {

    private Dictionary dictionary;

    PatternCreator(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    // one dimension for each word type
    private Map<String, double[][]> probs = new HashMap();
    private Map<String, Double> totalWeights = new HashMap<>();
    // should automate this to automatically get the structure of the sentence and generate probs for possible pairs
    public void createCooccurrenceMatrix() {
        Integer[] sizes = dictionary.getNumberOfTypes();
        String[] correspStrings = dictionary.getIndexes();
        for (int i = 0; i < sizes.length; i++) {
            for (int j = 0; j < sizes.length; j++) {
                String concat = correspStrings[i] + correspStrings[j];
                probs.put(concat, new double[sizes[i]][sizes[j]]);
                double sum = 0;
                for (int word1Index = 0; word1Index < sizes[i]; word1Index++) {
                    for (int word2Index = 0; word2Index < sizes[j]; word2Index++) {
                        generateValue(word1Index, word2Index, probs.get(concat));
                        sum += probs.get(concat)[word1Index][word2Index];
                    }
                    System.out.println();
                }
                totalWeights.put(concat, sum);
            }
        }
    }

    public Dictionary.Pair<String,String> pickPair(String type1, String type2) {
        String concat = type1 + type2;
        Double location = (new Random()).nextDouble() * totalWeights.get(concat);
        double current  = 0;
        double[][] mat = probs.get(concat);
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (current < location) {
                    current += mat[i][j];
                }
                else {
                    return dictionary.getWordsPair(type1, i, type2, j);
                }
            }
        }
        return null;
    }

    private static final double CHANCE = 0.1;
    private void generateValue(int i, int j, double[][] mat) {
        Random random = new Random();
        if (Math.random() < CHANCE) {
            mat[i][j] = Math.abs(random.nextGaussian());
        }
        else {
            mat[i][j] = random.nextFloat() / 10;
        }
    }
    
}
