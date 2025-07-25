import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Networking {
  public static HttpResponse<String> sendPostRequest(String end_url, String json) throws Exception {
    String url = "http://fantasyendpoint.duckdns.org:5000/" + end_url;
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
            .build();    
    HttpClient client = HttpClient.newBuilder().version(java.net.http.HttpClient.Version.HTTP_1_1).build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response;
  }
}

