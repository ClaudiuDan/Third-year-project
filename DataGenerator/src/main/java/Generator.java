public class Generator {
    protected MyStringBuilder data = new MyStringBuilder();
    private static CodeGenerator codeGenerator = new CodeGenerator();
    private static TextGenerator textGenerator = new TextGenerator();
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
            textGenerator.sentenceFinished();
            codeGenerator.sentenceFinished();
            counter++;
        }
        DataOutput dataOutput = new DataOutput();
        dataOutput.writeInput(textGenerator.data);
        dataOutput.writeTarget(codeGenerator.data);
        dataOutput.buildDatasets(textGenerator.data, codeGenerator.data);
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
                textGenerator.generateSimpleSentence(noun1, verb, noun2);
                codeGenerator.generateAddEdgeAction(noun1, noun2, verb);
                textGenerator.generateQuestionCheckRelation(noun1, noun2);
                codeGenerator.generateAnswerRelation(noun1, noun2);
                break;
            }
            case 3: {
                Dictionary.Pair<String, String> pair = patternCreator.pickPair("noun", "verb");
                String noun = pair.string1, verb = pair.string2;
                pair = patternCreator.pickPair("verb", verb, "adjective");
                String adj = pair.string2;
                textGenerator.generateSimpleSentence(noun, verb, adj);
                textGenerator.generateQuestionCheckRelation(noun, adj);
                codeGenerator.generateAnswerRelation(noun, adj);
                break;
            }
        }
    }

    static class MyStringBuilder {
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

    protected void sentenceFinished() {
        data.removeLastSpace();
        data.appendNewLine();
    }
}
