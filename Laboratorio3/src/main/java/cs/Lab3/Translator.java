package cs.Lab3;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Translator {
    static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    static final String CLIENT_SECRET = "PUBLIC_SECRET";
    static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

    public Translator(){

    }

    public static String getJsonPayLoad(String fromLang, String toLang, String text) {
        return new StringBuilder()
                .append("{")
                .append("\"fromLang\":\"")
                .append(fromLang)
                .append("\",")
                .append("\"toLang\":\"")
                .append(toLang)
                .append("\",")
                .append("\"text\":\"")
                .append(text)
                .append("\"")
                .append("}")
                .toString();
    }

    public static String translate(String fromLang, String toLang, String text) throws IOException {

        if (text.length() >= 500 ||text.length() == 0) {
            throw new IOException("Se excedio el numero de caracteres");
        }

        if (fromLang.length() == 0) {
            throw new IOException("No se agrego idioma de origen");
        }

        if (toLang.length() == 0) {
            throw new IOException("No se agrego idioma de destino");
        }

        String jsonPayload = getJsonPayLoad(fromLang, toLang, text);

        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(jsonPayload.getBytes());
        os.flush();
        os.close();

        int statusCode = conn.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
        ));
        String output;
        StringBuilder textTranslated = new StringBuilder();
        while ((output = br.readLine()) != null) {
            textTranslated.append(output);
        }
        conn.disconnect();
        return textTranslated.toString();
    }

}
