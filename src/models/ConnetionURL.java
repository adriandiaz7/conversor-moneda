package models;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ConnetionURL {

    private String apiKey = "12a9d6c2f027302160c8bd37";
    private String url = "https://v6.exchangerate-api.com/v6/%s/latest/USD";

    public String buildURL() throws UnsupportedEncodingException {
//        String apiKeyEncoded = URLEncoder.encode(apiKey, "UTF-8");
        return String.format(url,apiKey);

    }

}
