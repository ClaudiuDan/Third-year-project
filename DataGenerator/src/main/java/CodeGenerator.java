public class CodeGenerator extends Generator {
    private static MyStringBuilder targetData = new MyStringBuilder();
    public static void generateAddEdgeAction (String from, String to, String action) {
        targetData.append("G.add_edge(" + from + "," + to + ", action=" + action + ") newline");
    }
}
