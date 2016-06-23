package org.sonarsource.plugins.example.DatabaseAdapter;

import org.json.JSONObject;
import org.sonarsource.plugins.example.measures.SetSizeOnFilesSensor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class JSONReader {

    /**
     * Open a Stream with a URL, get the JSON response
     * @param url
     * @return
     */
    public static JSONObject getJSON(String url) {
        InputStream is;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            if (jsonText.toCharArray()[0] == '[') {
                jsonText = "{\"array\":" + jsonText + "}";
            }
            JSONObject json = new JSONObject(jsonText);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Read all the things
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Used for testing purposes only.
     * @param args
     */
    public static void main(String[] args) {
        GitHubAPIAdapter a = new GitHubAPIAdapter();
        a.sendWithAuth("https://api.github.com/users/thervh70");
//        SetSizeOnFilesSensor ex = new SetSizeOnFilesSensor();
//        System.out.println("ESBData.ts " + ex.calculateFile("ElementSelectionBehaviourData.ts"));
//        System.out.println("MainController.ts " + ex.calculateFile("MainController.ts"));
    }
}
