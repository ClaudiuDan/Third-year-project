import java.util.ArrayList;
import java.util.List;
public class PhraseBuilder {
    private static int index = 0;
    private Phrase text, code;
    private static TextGenerator textGenerator;
    private static CodeGenerator codeGenerator = new CodeGenerator();
//    private static PatternCreator patternCreator = new PatternCreator();
    private static QuestionPicker questionPicker;
    private static WordGroupings wordGroupings = new WordGroupings();
    public Pair<Phrase> build () {
        text = new Phrase(); code = new Phrase();
        textGenerator = new TextGenerator(wordGroupings);
        questionPicker = new QuestionPicker(textGenerator, codeGenerator, text, code);
        generatePhrase();
        return new Pair<>(text, code);
    }

    private static final int MAX_SENTENCES = 3;
    private void generatePhrase () {
        int numberSentences = Helper.chooseRandomPath(MAX_SENTENCES);
        for (int i = 0; i < numberSentences; i++) {
            generateSentence(-1);
            index++;
        }
    }

    private static final int SENTENCE_TYPES = 3;
    private static final int QUESTIONS = 2;
    private static final String NOUN = "noun",  VERB = "verb", PREP = "preposition", ADJ = "adjective", ADV = "adverb";
    @SuppressWarnings("Duplicates")
    private void generateSentence (int forcePath) {
        int path = Helper.chooseRandomPath(SENTENCE_TYPES);
        if (forcePath != -1)
            path = forcePath;
        switch (path) {
            // noun + verb + noun
            case 1: {
                List<String> types = new ArrayList<>();
                types.add(NOUN); types.add(VERB); types.add(NOUN);
                WordGroupings.Group group = wordGroupings.getFullGroup(types);
                if (group == null) {
                    generateSentence(1);
                    return;
                }
                String[] tempValues = new String[] {group.words[0].value,
                        group.words[1].value, group.words[2].value};
                text.extend(textGenerator.generateSimpleSentence(group.words), index);
                code.extend(codeGenerator.generateAddEdgeAction(tempValues[0],
                        tempValues[2], tempValues[1]), index);
                code.extend(codeGenerator.generateAddEdge(tempValues[0], tempValues[1]), index);
                QuestionPicker.pickQuestionsSimple(QUESTIONS, wordGroupings, tempValues);
                break;
            }
            // noun + verb + adverb
            case 2: {
                List<String> types = new ArrayList<>();
                types.add(NOUN); types.add(VERB); types.add(ADV);
                WordGroupings.Group group = wordGroupings.getFullGroup(types);
                if (group == null) {
                    generateSentence(1);
                    return;
                }
                String[] tempValues = new String[] {group.words[0].value,
                        group.words[1].value, group.words[2].value};
                text.extend(textGenerator.generateSimpleSentence(group.words), index);
                code.extend(codeGenerator.generateAddEdgeAction(tempValues[0], tempValues[2], tempValues[1]), index);
                code.extend(codeGenerator.generateAddEdge(tempValues[0], tempValues[1]), index);
                QuestionPicker.pickQuestionsSimple(QUESTIONS, wordGroupings, tempValues);
                break;
            }
            // adjNoun + verb + adjNoun
            case 3: {
                List<String> types = new ArrayList<>();
                types.add(NOUN); types.add(VERB); types.add(NOUN);
                WordGroupings.Group group = wordGroupings.getFullGroup(types);
                if (group == null) {
                    generateSentence(1);
                    return;
                }
                String[] tempValues = new String[] {group.words[0].value,
                        group.words[1].value, group.words[2].value};
                List<String> structure1 = buildAdjNounStructure(tempValues[0]);
                List<String> structure2 = buildAdjNounStructure(tempValues[2]);
                text.extend(textGenerator.generateStructureSentence(structure1, tempValues[0], tempValues[1], structure2, tempValues[2]), index);
                code.extend(codeGenerator.generateAddEdge(tempValues[0], tempValues[1]), index);
                code.extend(codeGenerator.generateAddEdgeAction(tempValues[0], tempValues[2], tempValues[1]), index);
                code.extend(codeGenerator.generateAddEdgeStructure(tempValues[0], structure1), index);
                code.extend(codeGenerator.generateAddEdgeStructure(tempValues[2], structure2), index);
                QuestionPicker.pickQuestionsStructure(QUESTIONS, tempValues[1], structure1, tempValues[0], structure2, tempValues[2]);
                break;
            }
        }
    }

    private static final int ADJECTIVES = 3;
    private static final int BREAK_LIMIT = 5;
    private List<String> buildAdjNounStructure(String noun) {
        int numberOfAdj = Helper.chooseRandomPath(ADJECTIVES);
        List<String> structure = new ArrayList<>();
        List<String> types = new ArrayList<>(), values = new ArrayList<>();
        types.add(null); types.add(ADJ); types.add(NOUN);
        values.add(null); values.add(null); values.add(noun);
        for (int i = 0; i < numberOfAdj; i++) {
            WordGroupings.Group group = null;
            //TODO: fix possible loop for more words in structure
            int breakCounter = 0;
            while (breakCounter < BREAK_LIMIT && (group = wordGroupings.getPartGroup(values, types, 2)) != null &&
                    !isUnique(group.words[1].value, structure)) { ++breakCounter; group = null;}
            if (group != null) {
                structure.add(group.words[1].value);
            }
        }
        return structure;
    }

    private boolean isUnique(String word, List<String> words) {
        for (String s : words) {
            if (s.equals(word)) {
                return false;
            }
        }
        return true;
    }

    class Phrase {
        public List<Sentence> sentences = new ArrayList<>();

        public void extend (String sentence, int index) {
            sentences.add(new Sentence(sentence, index));
        }
    }

    class Sentence {
        String value ;
        int index;
        Sentence (String value, int index) {
            this.value = value;
            this.index = index;
        }
    }

    class Pair<T> {
        T text, code;
        Pair (T text, T code) {
            this.text = text;
            this.code = code;
        }
    }
}
