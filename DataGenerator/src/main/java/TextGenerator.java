import java.util.ArrayList;
import java.util.List;

public class TextGenerator {
    WordGroupings wordGroupings;
    TextGenerator (WordGroupings wordGroupings) {
        this.wordGroupings = wordGroupings;
    }
    public String generateSimpleSentence(WordGroupings.Word[] words) {
        List<String> simpleWords = addRandomWords(words);
        simpleWords.add(".");
        String[] a = new String[words.length];
        return concat(simpleWords.toArray(a));
    }

    private static final double RANDOM_CHANCE = 0.5;
    private List<String> addRandomWords (WordGroupings.Word[] words) {
        List<String> simpleWords = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            if (Math.random() < RANDOM_CHANCE) {
                List<String> values = new ArrayList<>(), types = new ArrayList<>();
                values.add(null); values.add(null); values.add(words[i].value);
                types.add(null); types.add("preposition"); types.add(words[i].type);
                if (i > 1) {
                    values.set(i - 2, words[i - 2].value);
                    types.set(i - 2, words[i - 2].type);
                }
                WordGroupings.Group group;
                group = wordGroupings.getPartGroup(values, types, 2);
                if (group != null) simpleWords.add(group.words[1].value);
            }
            simpleWords.add(words[i].value);
        }
        return simpleWords;
    }

    //TODO: add random words
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
