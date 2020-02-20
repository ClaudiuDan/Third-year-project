public class Generator {

    private MyStringBuilder inputData = new MyStringBuilder();
    private MyStringBuilder targetData = new MyStringBuilder();
    private Dictionary dictionary = new Dictionary();
    PatternCreator patternCreator;
    public static final int SENTENCES = 10000;
    public void startGeneration() {
        patternCreator = new PatternCreator(dictionary, "uniform");
        patternCreator.createCooccurrenceMatrix();
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
                generateToken("and");
                generateSentence();
                break;
            }
            case 2: {
                /*String noun1 = generateRandomWord(inputData,"noun");
                String verb = generateRandomWord(inputData, "verb");
                String noun2 = generateRandomWord(inputData,"noun");*/
                Dictionary.Pair<String, String> pair = patternCreator.pickPair("noun", "verb");
                String noun1 = pair.string1, verb = pair.string2;
                pair = patternCreator.pickPair("verb", dictionary.getPosition("verb", verb), "noun");
                String noun2 = pair.string2;
                inputData.append(noun1); inputData.append(verb); inputData.append(noun2);
                //targetData.append(noun2 + " " + verb + " " + noun1);
                targetData.append(noun1 + verb + " = " + noun2 + " ;");
                break;
            }
            case 3: {
                /*String noun = generateRandomWord(inputData,"noun");
                String verb = generateRandomWord(inputData,"verb");
                String adj = generateRandomWord(inputData,"adjective");*/
                Dictionary.Pair<String, String> pair = patternCreator.pickPair("noun", "verb");
                String noun = pair.string1, verb = pair.string2;
                pair = patternCreator.pickPair("verb", dictionary.getPosition("verb", verb), "adjective");
                String adj = pair.string2;
                inputData.append(noun); inputData.append(verb); inputData.append(adj);
                //targetData.append(adj + " " + verb + " " + noun);
                targetData.append(noun + verb + " = " + adj + " ;");
                break;
            }
        }
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
        void append (String string) {
            data.append(string + " ");
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
