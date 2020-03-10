import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.IndexWordSet;
import net.sf.extjwnl.dictionary.Dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Engine {
    public static void main (String[] args) {


        Generator generator = new Generator();
        generator.startGeneration();
//        TextHandler textHandler = new TextHandler();
//        textHandler.produceWords();
    }
}
