import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionPicker {
    private final static int SIMPLE_QUESTIONS = 4;
    private static TextGenerator textGenerator;
    private static CodeGenerator codeGenerator;
    private static PhraseBuilder.Phrase text, code;
    public QuestionPicker(TextGenerator textGenerator, CodeGenerator codeGenerator, PhraseBuilder.Phrase text, PhraseBuilder.Phrase code) {
        this.textGenerator = textGenerator;
        this.codeGenerator = codeGenerator;
        this.text = text; this.code = code;
    }

    public static void pickQuestionsSimple(int n, WordGroupings wordGroupings, String... words) {
        Random random = new Random();
        int limit = random.nextInt(n);
        int questionIndex = -1;

        for (int i = 0 ; i < limit; i++) {
            int pick = random.nextInt(SIMPLE_QUESTIONS);
            switch (pick) {
                case 0:
                    text.extend(textGenerator.generateQuestionCheckRelation(words[0], words[2]), questionIndex);
                    code.extend(codeGenerator.generateAnswerRelation(words[0], words[2]), questionIndex);
                    break;
                case 1:
                    text.extend(textGenerator.generateQuestionDoesThat(words[0], words[1]), questionIndex);
                    code.extend(codeGenerator.generateAnswerRelation(words[0], words[1]), questionIndex);
                    break;
                case 2:
                    text.extend(textGenerator.generateQuestionIsThere(words[0]), questionIndex);
                    code.extend(codeGenerator.generateAnswerIsThere(words[0]), questionIndex);
                    break;
                case 3:
//                    String doesThat = patternCreator.pickPair("noun", words[0], "verb").string2;
                    List<String> values = new ArrayList<>(), types = new ArrayList<>();
                    values.add(null); values.add(words[0]); values.add(null);
                    types.add(null); types.add("noun"); types.add("verb");
                    WordGroupings.Group group = wordGroupings.getPartGroup(values, types, 1);
                    if (group == null) continue;
                    String doesThat = group.words[2].value;
                    text.extend(textGenerator.generateQuestionDoesThatWrong(words[0], doesThat), questionIndex);
                    code.extend(codeGenerator.generateAnswerRelation(words[0], doesThat), questionIndex);
                    break;
            }
        }
    }

    private static final int STRUCTURE_QUESTIONS = 2;
    public static void pickQuestionsStructure(int n, String verb, List<String> structure1, String noun1,
                                       List<String> structure2, String noun2) {
        Random random = new Random();
        int limit = random.nextInt(n);
        int questionIndex = -1;
        for (int i = 0; i < limit; i++) {
            int pick = random.nextInt(STRUCTURE_QUESTIONS);
            switch (pick) {
                case 0:
                    text.extend(textGenerator.generateStructureQuestionHowIs(noun1), questionIndex);
                    code.extend(codeGenerator.generateStructureRelation(structure1, noun1), questionIndex);
                case 1:
                    text.extend(textGenerator.generateStructureQuestionHowIs(noun2), questionIndex);
                    code.extend(codeGenerator.generateStructureRelation(structure2, noun2), questionIndex);
            }
        }
    }
}
