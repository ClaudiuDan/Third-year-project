public class Generator {

    private MyStringBuilder inputData = new MyStringBuilder();
    private MyStringBuilder targetData = new MyStringBuilder();
    private Dictionary dictionary = new Dictionary();
    public void startGeneration() {
        generateSentence();
        DataOutput dataOutput = new DataOutput();
        dataOutput.writeInput(inputData);
        dataOutput.writeTarget(targetData);
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
                String noun1 = generateRandomWord(inputData,"noun");
                String verb = generateRandomWord(inputData, "verb");
                String noun2 = generateRandomWord(inputData,"noun");
                targetData.append(noun1 + verb + "=" + noun2 + ";");
                break;
            }
            case 3: {
                String noun = generateRandomWord(inputData,"noun");
                String verb = generateRandomWord(inputData,"verb");
                String adj = generateRandomWord(inputData,"adjective");
                targetData.append(noun + verb + "=" + adj + ";");
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

        @Override
        public String toString() {
            return data.toString();
        }
    }
}
