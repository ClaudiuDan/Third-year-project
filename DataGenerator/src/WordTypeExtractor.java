import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WordTypeExtractor {
    public WordsRetrieval.Word getWordType (String wordValue) {
        HttpURLConnection conn = initConnection(wordValue);
        String data = readFromConnection(conn);
        WordsRetrieval.Word word = getWord(data);
        conn.disconnect();
        return word;
    }

    private HttpURLConnection initConnection (String word) {
        try {
            URL url = new URL("https://www.google.com/search?q=" + word + "+meaning&rlz=1C1CHBF_en-GBGB864GB864&oq=word+meaning&aqs=chrome..69i57j0l7.1106j0j7&sourceid=chrome&ie=UTF-8");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            return conn;
        } catch (ProtocolException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readFromConnection(HttpURLConnection conn)  {
        String allData = "";
        try {
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allData += line;
            }
        } catch (IOException e) {

            e.printStackTrace();

        }
        return allData;
    }

    private static final String TYPES[] = new String[]{"noun","verb","adjective", "determiner &#183; pronoun &#183; adjective", "preposition", "determiner", "adverb"};
    private static final String START = "<span>", END = "</span>";
    private WordsRetrieval.Word getWord(String data) {
        WordsRetrieval.Word word = new WordsRetrieval.Word(getValue(data), getType(data));
        if (word.type != null && word.type.equals(TYPES[3])) {
            word.type = "determiner";
        }
        return word;
    }

    private String getValue(String data) {
        String s = "data-dobid=\"hdw\"";
        int index;

        if (flag) {
            index = data.lastIndexOf(s);
        }
        else {
            index = data.indexOf(s);
        }
        flag = false;
        index += s.length() + 1;
        StringBuilder builder = new StringBuilder("");
        while (data.charAt(index) >= 'a' && data.charAt(index++) <= 'z') {
            builder.append(data.charAt(index - 1));
        }
        return builder.toString();
    }
    boolean flag = false;
    private String getType(String data) {
        int position = Integer.MAX_VALUE;
        String typeToReturn = null;
        for (String type : TYPES) {
            int index;
            if ((index = data.indexOf(START + type + END)) != -1 && index < position) {
                position = index;
                typeToReturn = type;
            }
        }

        // if it is past tense, change it to verb
        if (typeToReturn != null && typeToReturn.equals("adjective") && data.contains("past tense")) {
            typeToReturn = "verb";
            flag = true;
        }
        return typeToReturn;
    }
}
