import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author Encin.Li
 */
public class HttpClientDemo {

    public static void main(String[] agrs) throws IOException {
        String url = "http://localhost:8801";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()){
            System.out.println(response.body().toString());
            System.out.println(response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
