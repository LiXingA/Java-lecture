package student.util;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class HttpUtil {
    public static void main(String[] args) throws Exception {
        JSONObject json = new JSONObject().put("query", "pineapple")
        .put("limit", "10");
        JSONObject httpsPostJson = HttpUtilTools.httpPostJson("http://az-chawk-descr-search:8000/search", json.toString().getBytes(),true);
        System.out.println(httpsPostJson);
    }

    

}