public class TextGenerator extends Generator{

    TextGenerator () {
        data = new MyStringBuilder();
    }

    public void generateSimpleSentence(String noun, String verb, String word) {
        data.append(noun, verb, word, ".");
    }

    public void generateQuestionCheckRelation(String entity1, String entity2) {
        data.append("is there a relation between", entity1, "and", entity2, "?");
    }
    public void generateQuestionIfEntityDoes(String entity, String action) {
        data.append("does", entity, action, "?");
    }
}
