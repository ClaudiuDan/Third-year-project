public class Engine {
    public static void main (String[] args) {
        Generator generator = new Generator();
        generator.startGeneration();
        TextHandler textHandler = new TextHandler();
        textHandler.produceWords();
    }
}
