import java.util.ArrayList;
import java.util.List;

public class TextGenerator {
    PatternCreator patternCreator;
    TextGenerator (PatternCreator patternCreator) {
        this.patternCreator = patternCreator;
    }
    public String generateSimpleSentence(List<WordsRetrieval.Word> words) {
        List<String> simpleWords = addRandomWords(words);
        simpleWords.add(".");
        String[] a = new String[words.size()];
        return concat(simpleWords.toArray(a));
    }

    private List<String> addRandomWords (List<WordsRetrieval.Word> words) {
        List<String> simpleWords = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            if (Math.random() < 0.5) {
                simpleWords.add(patternCreator.pickPairReversed("preposition", words.get(i).type, words.get(i).value).string1);
            }
            simpleWords.add(words.get(i).value);
        }
        return simpleWords;
    }

    public String generateStructureSentence(List<String> structure1, String noun1, String verb, List<String> structure2, String noun2) {
        String concatenation = "";
        for (String s : structure1) {
            concatenation = concat(concatenation, s);
        }
        concatenation = concat(concatenation, noun1, verb);
        for (String s : structure2) {
            concatenation = concat(concatenation, s);
        }
        return concat(concatenation, noun2, ".");
    }

    public String generateStructureQuestionHowIs(String noun) {
        return concat("how is", noun, "?");
    }

    public String generateQuestionCheckRelation(String entity1, String entity2) {
        return concat("is there a relation between", entity1, "and", entity2, "?");
    }
    public String generateQuestionDoesThat(String entity, String action) {
        return concat("does", entity, action, "?");
    }

    public String generateQuestionIsThere(String entity) { return concat("is there a", entity, "?");}

    public String generateQuestionDoesThatWrong(String entity,  String doesThat) {
        return concat("does", entity, doesThat, "?");
    }

    protected String concat(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strings) {
            if (!s.equals("")) {
                stringBuilder.append(s + " ");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.toString().length() - 1);
        return stringBuilder.toString();
    }
}
