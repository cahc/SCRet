package se.cc.scopus;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by crco0001 on 1/18/2018.
 */
public class XMLGrabber {


    OkHttpClient client = new OkHttpClient();

    public XMLGrabber(boolean useLocalProxy) {

        if(useLocalProxy) {

            System.setProperty("http.proxyHost", "127.0.0.1");
            System.setProperty("http.proxyPort", "8888");

        }

   System.setProperty("https.proxyHost", "127.0.0.1");System.setProperty("https.proxyPort", "8888");

    }

    public String generatURL(String eid, String key) {

     return "http://api.elsevier.com/content/abstract/eid/" + eid + "?apiKey=" + key +"&VIEW=FULL";

    }

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0.2 Safari/604.4.7")
                .url(url)
                .build();

        try (Response response = this.client.newCall(request).execute()) {

            return response.body().string();
        }
    }


    public static void main(String[] args) throws IOException {
        XMLGrabber client = new XMLGrabber(false);

       // String url = client.generatURL("2-s2.0-58149466160","7f59af901d2d86f78a1fd60c1bf9426a");
        String response = client.run("https://www.google.se");
        System.out.println(response);
    }


}
