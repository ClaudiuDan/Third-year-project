public class Generator {
    protected MyStringBuilder data = new MyStringBuilder();
    private static CodeGenerator codeGenerator = new CodeGenerator();
    private static TextGenerator textGenerator;
    private Dictionary dictionary = new Dictionary();
    PatternCreator patternCreator;
    public static final int SENTENCES = 10000, SEQUENCE_LIMIT = 2;
    public static int sequenceSize = 0;
    public void startGeneration() {
        patternCreator = new PatternCreator(dictionary, "uniform");
        patternCreator.start();
        textGenerator = new TextGenerator(patternCreator);

        int sequencesCounter = 0;
        while (sequencesCounter < SENTENCES) {
            sequenceSize = 0;
            generateSentence();

            // required to remove last space and add a new line
            textGenerator.sentenceFinished();
            codeGenerator.sentenceFinished();
            sequencesCounter++;
        }
        DataOutput dataOutput = new DataOutput();
        dataOutput.writeInput(textGenerator.data);
        dataOutput.writeTarget(codeGenerator.data);
        dataOutput.buildDatasets(textGenerator.data, codeGenerator.data);
    }


    private void generateSentence() {
        int QUESTIONS_LIMIT = 3;
        int path = Helper.chooseRandomPath(Grammar.SENTENCE_LENGTH);
        switch (path) {
            case 1: {
                if (sequenceSize < SEQUENCE_LIMIT) {
                    generateSentence();
                }
                if (sequenceSize < SEQUENCE_LIMIT) {
                    generateSentence();
                }
                break;
            }
            case 2: {
                sequenceSize++;
                Dictionary.Pair<String, String> pair = patternCreator.pickPair("noun", "verb");
                String noun1 = pair.string1, verb = pair.string2;
                pair = patternCreator.pickPair("verb", verb, "noun");
                String noun2 = pair.string2;
                textGenerator.generateSimpleSentence(noun1, verb, noun2);
                codeGenerator.generateAddEdgeAction(noun1, noun2, verb);
                codeGenerator.generateAddEdge(noun1, verb);
                for (int i = 0; i < 2; i++)
                    QuestionPicker.pickAQuestionSimple(textGenerator, codeGenerator, QUESTIONS_LIMIT, noun1, verb, noun2);
                break;
            }
            case 3: {
                sequenceSize++;
                Dictionary.Pair<String, String> pair = patternCreator.pickPair("noun", "verb");
                String noun = pair.string1, verb = pair.string2;
                pair = patternCreator.pickPair("verb", verb, "adjective");
                String adj = pair.string2;
                textGenerator.generateSimpleSentence(noun, verb, adj);
                codeGenerator.generateAddEdgeAction(noun, adj, verb);
                codeGenerator.generateAddEdge(noun, verb);
                QuestionPicker.pickAQuestionSimple(textGenerator, codeGenerator, QUESTIONS_LIMIT, noun, verb, adj);
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
            if (data.length() != 0 && data.charAt(data.length() - 1) == ' ') {
                data.deleteCharAt(data.length() - 1);
            }
        }
        void appendMarkerAndNewLine () {
            data.append(" \n");
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    protected void sentenceFinished() {
        data.removeLastSpace();
        data.appendMarkerAndNewLine();
    }
}
