public class Generator {
    protected MyStringBuilder textData = new MyStringBuilder(), codeData = new MyStringBuilder();
    public void startGeneration() {
        PhraseBuilder phraseBuilder = new PhraseBuilder();
        int examplesCounter = 0;
        while (examplesCounter < Params.EXAMPLES) {
            PhraseBuilder.Pair<PhraseBuilder.Phrase> phrase = phraseBuilder.build();
            textData.append(phrase.text);
            codeData.append(phrase.code);
            checkMax(phrase.code);
            examplesCounter++;
            System.out.println(examplesCounter);

        }
        System.out.println("max length is " + maximum);
        DataOutput dataOutput = new DataOutput();
        dataOutput.buildDatasets(textData, codeData);
    }

    private int maximum = -1;
    private void checkMax (PhraseBuilder.Phrase phrase) {
        int count = 0;
        for (PhraseBuilder.Sentence sentence : phrase.sentences) {
            count += sentence.value.split(" ").length;
        }
        if (maximum < count) {
            maximum = count;
        }
    }

    static class MyStringBuilder {
        StringBuilder data = new StringBuilder();
        void append (PhraseBuilder.Phrase phrase) {
            if (Params.QUESTIONS_SHUFFLED == true) {
                for (PhraseBuilder.Sentence sentence : phrase.sentences) {
                    if (!sentence.value.isEmpty()) {
                        if (sentence.index == -1 && !Params.POINTS) {
                            data.append(". ");
                        }
                        data.append(sentence.value + " ");
                    }
                }
            }
            else {
                for (PhraseBuilder.Sentence sentence : phrase.sentences) {
                    if (!sentence.value.isEmpty() && sentence.index > 0) {
                        data.append(sentence.value + " ");
                    }
                }

                boolean first = true;
                for (PhraseBuilder.Sentence sentence : phrase.sentences) {
                    if (sentence.index < 0 && !sentence.value.isEmpty()) {
                        if (first && sentence.index == -1 && !Params.POINTS) {
                            data.append(". ");
                            first = false;
                        }
                        data.append(sentence.value + " ");
                    }
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
