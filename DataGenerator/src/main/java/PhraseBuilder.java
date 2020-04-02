import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhraseBuilder {
    private static int index = 0;
    private Phrase text, code;
    private static TextGenerator textGenerator = new TextGenerator();
    private static CodeGenerator codeGenerator = new CodeGenerator();
    private static PatternCreator patternCreator = new PatternCreator();
    private static QuestionPicker questionPicker;
    public Pair<Phrase> build () {
        text = new Phrase(); code = new Phrase();
        questionPicker = new QuestionPicker(textGenerator, codeGenerator, text, code);
        generatePhrase();
        return new Pair<>(text, code);
    }

    private static final int MAX_SENTENCES = 3;
    private void generatePhrase () {
        int numberSentences = Helper.chooseRandomPath(MAX_SENTENCES);
        for (int i = 0; i < numberSentences; i++) {
            generateSentence();
            index++;
        }
    }

    private static final int SENTENCE_TYPES = 3;
    private static final int QUESTIONS = 2;
    @SuppressWarnings("Duplicates")
    private Sentence generateSentence () {
        int path = Helper.chooseRandomPath(SENTENCE_TYPES);
        switch (path) {
            // noun + verb + noun
            case 1: {
                Dictionary.Pair<String, String> pair = patternCreator.pickPair("noun", "verb");
                String noun1 = pair.string1, verb = pair.string2;
                String noun2 = patternCreator.pickPair("verb", verb, "noun").string2;
                text.extend(textGenerator.generateSimpleSentenceNVN(noun1, verb, noun2), index);
                code.extend(codeGenerator.generateAddEdgeAction(noun1, noun2, verb), index);
                code.extend(codeGenerator.generateAddEdge(noun1, verb), index);
                QuestionPicker.pickQuestionsSimple(QUESTIONS, patternCreator, noun1, verb, noun2);
                break;
            }
            // noun + verb + adj
            case 2: {
                Dictionary.Pair<String, String> pair = patternCreator.pickPair("noun", "verb");
                String noun = pair.string1, verb = pair.string2;
                String adj = patternCreator.pickPair("verb", verb, "adjective").string2;
                text.extend(textGenerator.generateSimpleSentenceNVA(noun, verb, adj), index);
                code.extend(codeGenerator.generateAddEdgeAction(noun, adj, verb), index);
                code.extend(codeGenerator.generateAddEdge(noun, verb), index);
                QuestionPicker.pickQuestionsSimple(QUESTIONS, patternCreator, noun, verb, adj);
                break;
            }
            // adjNoun + verb + adjNoun
            case 3: {
                Dictionary.Pair<String, String> pair = patternCreator.pickPair("noun", "verb");
                String noun1 = pair.string1, verb = pair.string2;
                List<String> structure1 = buildAdjNounStructure(noun1);
                String noun2 = patternCreator.pickPair("verb", verb, "noun").string2;
                List<String> structure2 = buildAdjNounStructure(noun2);
                text.extend(textGenerator.generateStructureSentence(structure1, noun1, verb, structure2, noun2), index);
                code.extend(codeGenerator.generateAddEdge(noun1, verb), index);
                code.extend(codeGenerator.generateAddEdgeStructure(noun1, structure1), index);
                code.extend(codeGenerator.generateAddEdgeStructure(noun2, structure2), index);
                QuestionPicker.pickQuestionsStructure(QUESTIONS, patternCreator, verb, structure1, noun1, structure2, noun2);
                break;
            }
        }
        return null;
    }

    private static final int ADJECTIVES = 1;
    private List<String> buildAdjNounStructure(String noun) {
        int numberOfAdj = Helper.chooseRandomPath(ADJECTIVES);
        List<String> structure = new ArrayList<>();
        for (int i = 0; i < numberOfAdj; i++) {
            String pickedWord;
            //TODO: fix possible loop for more words in structure
            while (!isUnique(pickedWord = patternCreator.pickPairReversed("adjective", "noun", noun).string1, structure)) { }
            structure.add(pickedWord);
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
