import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrafficRouter {
    public static void main(String[] args) throws Exception {
        String url = System.getenv("SUPABASE_URL") + "/rest/v1/traffic_router?select=*";
        String key = System.getenv("SUPABASE_KEY");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("apikey", key)
            .header("Authorization", "Bearer " + key)
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        processData(response.body());
    }

    private static void processData(String json) {
    // Force the directory creation
    java.io.File distDir = new java.io.File("dist");
    if (!distDir.exists()) {
        boolean created = distDir.mkdirs();
        System.out.println("Directory created: " + created);
    }

    // Regex-based parsing
    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\{\"city\":\"(.*?)\",\"service_type\":\"(.*?)\",\"tool_name\":\"(.*?)\",\"affiliate_link\":\"(.*?)\"\\}");
    java.util.regex.Matcher matcher = pattern.matcher(json);
    
    while (matcher.find()) {
        String city = matcher.group(1);
        String service = matcher.group(2);
        String html = "<html><body><h1>Best " + service + " in " + city + "</h1>" +
                      "<p>Check out: <a href='" + matcher.group(4) + "'>" + matcher.group(3) + "</a></p></body></html>";
        
        try (java.io.FileWriter writer = new java.io.FileWriter("dist/" + city.replaceAll(" ", "_") + "_" + service.replaceAll(" ", "_") + ".html")) {
            writer.write(html);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

}
