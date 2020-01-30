import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileScrap {
    public void scrapVerbs() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("verbs")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("verbsProcessed")));
        List<String> words = new ArrayList<>();
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            words.add(s.split("\t")[0]);
        }
        for (String word : words)
            bufferedWriter.write(word + "\n");
        bufferedWriter.close();
    }
}
