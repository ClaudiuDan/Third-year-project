import java.util.List;

public class CodeGenerator {

    public String generateAddEdgeAction (String from, String to, String action) {
        return "G.add_edge(\' " + from + " \',\' " + to + " \',action=\' " + action + " \') newline";
    }

    public String generateAddEdge (String from, String to) {
        return "G.add_edge(\' " + from + " \',\' " + to + " \') newline";
    }

    public String generateAddEdgeStructure(String noun, List<String> structure) {
        StringBuilder builder = new StringBuilder();
        for (String s : structure) {
            builder.append(generateAddEdge(noun, s));
        }
        return builder.toString();
    }

    public String generateStructureRelation (List<String> structure, String entity) {
        StringBuilder builtCode = new StringBuilder();
        for (String s : structure) {
            builtCode.append(generateAnswerRelation(entity, s));
        }
        return builtCode.toString();
    }

    public String generateAnswerRelation (String entity1, String entity2) {
        return "G.has_edge(\' " + entity1 + " \',\' " + entity2 + " \') newline";
    }

    public String generateAnswerIsThere (String entity) {
        return "G.has_node(\' " + entity + " \') newline";
    }
}
