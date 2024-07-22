package us.wazupbutrcup.hexChatTranslator;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Translator {

    private final String apiKey;

    public Translator(String apiKey) {
        this.apiKey = apiKey;
    }

    public String translate(String text, String sourceLang, String targetLang) {
        try {
            String encodedText = URLEncoder.encode(text, "UTF-8");
            String urlStr = String.format(
                    "https://translation.googleapis.com/language/translate/v2?key=%s&q=%s&source=%s&target=%s",
                    apiKey, encodedText, sourceLang, targetLang);
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            Scanner scanner = new Scanner(reader);
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            // Parse the response (simplified, you might want to use a JSON library)
            String translatedText = response.toString().split("\"translatedText\":\"")[1].split("\"")[0];
            return translatedText;

        } catch (Exception e) {
            e.printStackTrace();
            return text;
        }
    }
}
