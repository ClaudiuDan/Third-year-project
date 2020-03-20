public class TextGenerator extends Generator{
    private PatternCreator patternCreator;
    TextGenerator (PatternCreator patternCreator) {
        this.patternCreator = patternCreator; data = new MyStringBuilder();
    }

    public void generateSimpleSentence(String noun, String verb, String word) {
        data.append(noun, verb, word, ".");
    }

    public void generateQuestionCheckRelation(String entity1, String entity2) {
        data.append("is there a relation between", entity1, "and", entity2, "?");
    }
    public void generateQuestionDoesThat(String entity, String action) {
        data.append("does", entity, action, "?");
    }

    public void generateQuestionIsThere(String entity) { data.append("is there a", entity, "?");}

    public String generateQuestionDoesThatWrong(String entity) {
        String doesThat = patternCreator.pickPair("noun", entity, "verb").string2;
        data.append("does", entity, doesThat, "?");
        return doesThat;
    }
}
