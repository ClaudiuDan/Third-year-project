public class PatternCreator {

    // one dimension for each word type
    private int[][][] probs;

    // should automate this to automatically get the structure of the sentence and generate probs for possible pairs
    public void createCooccurrenceMatrix(Dictionary dictionary) {
        Integer[] sizes = dictionary.getSize();
        String[] correspStrings = dictionary.getIndexes();
        probs = new int[sizes[0]][sizes[1]][sizes[2]];
        for (int i = 0; i < sizes.length;)
    }
}
