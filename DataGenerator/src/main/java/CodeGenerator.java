public class CodeGenerator extends Generator {

    public void generateAddEdgeAction (String from, String to, String action) {
        data.append("G.add_edge( \' " + from + " \', \' " + to + " \', action=\' " + action + " \') newline");
    }

    public void generateAddEdge (String from, String to) {
        data.append("G.add_edge( \' " + from + " \',\' " + to + " \') newline");
    }

    public void generateAnswerRelation (String entity1, String entity2) {
        data.append("G.has_edge(\' " + entity1 + " \',\' " + entity2 + " \') newline");
    }

    public void generateAnswerIsThere (String entity) {
        data.append("G.has_node(\' " + entity + " \') newline");
    }
}
