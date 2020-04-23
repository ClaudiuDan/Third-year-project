public class Generator {
    protected MyStringBuilder textData = new MyStringBuilder(), codeData = new MyStringBuilder();
    public static final int EXAMPLES = 10000;
    public void startGeneration() {
        PhraseBuilder phraseBuilder = new PhraseBuilder();
        int examplesCounter = 0;
        while (examplesCounter < EXAMPLES) {
            PhraseBuilder.Pair<PhraseBuilder.Phrase> phrase = phraseBuilder.build();
            textData.append(phrase.text);
            codeData.append(phrase.code);
            examplesCounter++;
            System.out.println(examplesCounter);

        }
        DataOutput dataOutput = new DataOutput();
        dataOutput.buildDatasets(textData, codeData);
    }

    static class MyStringBuilder {
        StringBuilder data = new StringBuilder();
        void append (PhraseBuilder.Phrase phrase) {
            for (PhraseBuilder.Sentence sentence : phrase.sentences) {
                if (sentence.index != -1) {
                    data.append(sentence.value + " ");
                }
            }
            for (PhraseBuilder.Sentence sentence : phrase.sentences) {
                if (sentence.index == -1) {
                    data.append(sentence.value + " ");
                }
            }
            removeLastSpace();
            appendMarkerAndNewLine();
        }
        void removeLastSpace() {
            if (data.length() != 0 && data.charAt(data.length() - 1) == ' ') {
                data.deleteCharAt(data.length() - 1);
            }
        }
        void appendMarkerAndNewLine () {
            data.append("\n");
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }


}
