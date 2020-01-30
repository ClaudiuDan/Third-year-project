public class Generator {

    private MyStringBuilder data = new MyStringBuilder();

    public void startGeneration() {
        generateSentence();
    }


    private void generateSentence() {
        int path = Helper.chooseRandomPath(Grammar.SENTENCE_LENGTH);
        switch (path) {
            case 1: {
                generateSentence();
                generateToken("and ");
                generateSentence();
                break;
            }
            case 2: {
                generateNoun();
                generateVerb();
                generateNoun();
                break;
            }
            case 3: {
                generateNoun();
                generateVerb();
                generateAdjective();
                break;
            }
        }
    }

    private void generateToken(String token) {
        data.append(token);
    }

    private void generateNoun() {
        data.append(Dictionary.getRandomWord("noun"));
    }

    private void generateVerb() {
        data.append(Dictionary.getRandomWord("verb"));
    }

    private void generateAdjective() {
        data.append(Dictionary.getRandomWord("adjective"));
    }

    class MyStringBuilder {
        StringBuilder data = new StringBuilder();
        void append (String string) {
            data.append(string + " ");
        }
    }
}
