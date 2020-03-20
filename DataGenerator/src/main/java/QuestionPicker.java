import java.util.Random;

public class QuestionPicker {
    private final static int SIMPLE_QUESTIONS = 4;
    public static void pickAQuestionSimple(TextGenerator textGenerator, CodeGenerator codeGenerator, int n, String... words) {
        Random random = new Random();
        int limit = random.nextInt(n);
        for (int i = 0 ; i < limit; i++) {
            int pick = random.nextInt(SIMPLE_QUESTIONS);
            switch (pick) {
                case 0:
                    textGenerator.generateQuestionCheckRelation(words[0], words[2]);
                    codeGenerator.generateAnswerRelation(words[0], words[2]);
                    break;
                case 1:
                    textGenerator.generateQuestionDoesThat(words[0], words[1]);
                    codeGenerator.generateAnswerRelation(words[0], words[1]);
                    break;
                case 2:
                    textGenerator.generateQuestionIsThere(words[0]);
                    codeGenerator.generateAnswerIsThere(words[0]);
                    break;
                case 3:
                    String otherWord = textGenerator.generateQuestionDoesThatWrong(words[0]);
                    codeGenerator.generateAnswerRelation(words[0], otherWord);
                    break;
            }
        }
    }
}
