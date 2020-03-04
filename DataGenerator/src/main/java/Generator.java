public class Generator {

    private MyStringBuilder inputData = new MyStringBuilder();
    private MyStringBuilder targetData = new MyStringBuilder();
    private Dictionary dictionary = new Dictionary();
    PatternCreator patternCreator;
    public static final int SENTENCES = 10000;
    public static final int PHRASE_LEN = 5;
    public void startGeneration() {
        patternCreator = new PatternCreator(dictionary, "uniform");
        patternCreator.start();
        /*for (int i = 0; i < 100; i++) {
            Dictionary.Pair<String, String> pair = patternCreator.pickPair("verb", "noun");
            pair = patternCreator.pickPair("verb", 5, "noun");
            System.out.println(pair.string1 + " " + pair.string2);
        }*/
        int counter = 0;
        while (counter < SENTENCES) {
            generateSentence();
            inputData.removeLastSpace();
            inputData.appendNewLine();
            targetData.removeLastSpace();
            targetData.appendNewLine();
            counter++;
        }
        DataOutput dataOutput = new DataOutput();
        dataOutput.writeInput(inputData);
        dataOutput.writeTarget(targetData);
        dataOutput.write(inputData, targetData);
    }


    private void generateSentence() {
        int path = Helper.chooseRandomPath(Grammar.SENTENCE_LENGTH);
        switch (path) {
            case 1: {
                generateSentence();
//                generateSentence();
                break;
            }
            case 2: {
                Dictionary.Pair<String, String> pair = patternCreator.pickPair("noun", "verb");
                String noun1 = pair.string1, verb = pair.string2;
                pair = patternCreator.pickPair("verb", verb, "noun");
                String noun2 = pair.string2;
                inputData.append(noun1, verb, noun2, ".");
                targetData.append("if entities . find ( " + noun1 + " ) ! = None : newline" +
                        " newtab entities [ " + noun1 + " ] [ " + verb + " ] = " + noun2 + " newline");
                generateQuestion(verb, noun1);
                break;
            }
            case 3: {
                Dictionary.Pair<String, String> pair = patternCreator.pickPair("noun", "verb");
                String noun = pair.string1, verb = pair.string2;
                pair = patternCreator.pickPair("verb", verb, "adjective");
                String adj = pair.string2;
                inputData.append(noun, verb, adj, ".");
                targetData.append("if entities . find ( " + noun + " ) ! = None : newline" +
                        " newtab entities [ " + noun + " ] [ " + verb + " ] = " + noun + " newline");
                generateQuestion(verb, adj);
                break;
            }
        }
    }

    //TODO: variable parameters length append
    private void generateQuestion(String verb, String noun) {
        inputData.append(noun, verb); generateToken("?");
        targetData.append("print ( " + noun + " [ " + verb + " ] ) newline");
    }

    private String generateToken(String token) {
        inputData.append(token);
        return token;
    }

    private String generateRandomWord(MyStringBuilder builder, String type) {
        String word = dictionary.getRandomWord(type);
        builder.append(word);
        return word;
    }


    class MyStringBuilder {
        StringBuilder data = new StringBuilder();
        void append (String ... strings) {
            for (String s : strings) {
                data.append(s + " ");
            }
        }
        void removeLastSpace() {
            data.deleteCharAt(data.length() - 1);
        }
        void appendNewLine () {
            data.append("\n");
        }
        @Override
        public String toString() {
            return data.toString();
        }
    }
}
