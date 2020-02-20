import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.dictionary.Dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Engine {
    public static void main (String[] args) {
        Generator generator = new Generator();
        generator.startGeneration();
        TextHandler textHandler = new TextHandler();
        textHandler.produceWords();
    }
}
