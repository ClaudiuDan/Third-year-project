import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

@Deprecated
public class WordTypeExtractor {
    public WordsRetrieval.Word getWordType (String wordValue) {
        HttpRequest req = initConnection(wordValue);
        String data = readFromConnection(req);
        WordsRetrieval.Word word = getWord(data);
        return word;
    }

    private HttpRequest initConnection (String word) {
        try {
            GenericUrl url = new GenericUrl("https://www.google.com/search?q=" + word + "+word+meaning=&rlz=1C1CHBF_en-GBGB864GB864&oq=word+meaning&aqs=chrome..69i57j0l7.1106j0j7&sourceid=chrome&ie=UTF-8");
            NetHttpTransport netHttpTransport = new NetHttpTransport();
            HttpRequest req = netHttpTransport.createRequestFactory().buildGetRequest(url);
            req.setRequestMethod("GET");
            return req;
        } catch (ProtocolException | MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            TextHandler.close();
        }
        return null;
    }

    private String readFromConnection(HttpRequest req)  {
        String allData = "";
        HttpResponse response = null;
        try {
            response = req.execute();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getContent()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allData += line;
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        try {
            response.disconnect();
        }
        catch (IOException e) {
            TextHandler.close();
            e.printStackTrace();
        }
        return allData;
    }

    private static final String TYPES[] = new String[]{"noun","verb","adjective", "determiner &#183; pronoun &#183; adjective", "preposition", "determiner", "adverb"};
    private static final String START = "<span class=\"r0bn4c rQMQod\">", END = "</span>";
    private WordsRetrieval.Word getWord(String data) {
        WordsRetrieval.Word word = new WordsRetrieval.Word(getValue(data), getType(data));
        if (word.type != null && word.type.equals(TYPES[3])) {
            word.type = "determiner";
        }
        return word;
    }

    private String getValue(String data) {
        String s = "play();});});</script>";
        int index = data.indexOf(s);
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
