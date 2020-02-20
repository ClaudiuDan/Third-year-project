import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PatternCreator {

    private Dictionary dictionary;

    PatternCreator(Dictionary dictionary, String mode) {
        this.dictionary = dictionary;
        if (mode.equals("uniform")) {
            CHANCE = 0; BOOSTER = 1; REDUCER = 1;
        }
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
                        double p = generateValue();
                        probs.get(concat)[word1Index][word2Index] = p;
                        sum += p;

                    }
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
                if (current + mat[i][j]< location) {
                    current += mat[i][j];
                }
                else {
                    return dictionary.getWordsPair(type1, i, type2, j);
                }
            }
        }
        return null;
    }
    public Dictionary.Pair<String,String> pickPair(String type1, int index1, String type2) {
        String concat = type1 + type2;
        Double location = (new Random()).nextDouble() * sumOf(concat, index1);
        double current  = 0;
        double[][] mat = probs.get(concat);
        int i = index1;
        for (int j = 0; j < mat[i].length; j++) {
            if (current + mat[i][j]< location) {
                current += mat[i][j];
            }
            else {
                //System.out.print(mat[i][j] + " ");
                return dictionary.getWordsPair(type1, i, type2, j);
            }
        }
        return null;
    }

    private double sumOf (String concat, int index) {
        double sum = 0;
        for (int j = 0; j < probs.get(concat)[index].length; j++) {
            sum += probs.get(concat)[index][j];
        }
        return sum;
    }

    private double CHANCE = 0.01;
    private double BOOSTER = 100, REDUCER = 10;
    private double generateValue() {
        Random random = new Random();
        if (Math.random() < CHANCE) {
            System.out.println("aici");
            return BOOSTER * Math.abs(random.nextGaussian());
        }
        return random.nextFloat() / REDUCER;
    }

}
