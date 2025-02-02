import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import com.google.gson.Gson;

import models.Activity;


public class GitHubActivities {
    public static void main(String[] args) throws Exception {

        Gson gson = new Gson();

        try {
            System.out.println("\033[1;32mWelcome to Github Activities search!\033[0m");
            System.out.println("\033[1;32mThere are the most recents activities from " + args[0] + " Github Account:\033[0m");
            var client = HttpClient.newHttpClient();

            var request = HttpRequest.newBuilder(
                    URI.create("https://api.github.com/users/" + args[0] + "/events"))
                    .header("accept", "application/json")
                    .build();

            var response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

            Activity[] activities = gson.fromJson(response.body(), Activity[].class);

            for (Activity activity : activities) {
                System.out.println(activity.toString());
            }
        } catch (Exception e) {
            System.err.println("\033[1;31mError: " + e.getMessage() + "\033[0m");
            System.err.println("\033[1;31mProvide a valid GitHub Username in the program arguments!\033[0m");
        }
    }
}
