package build.trigger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by sbasyal on 4/8/15.
 * Using DockerHub "Remote Build Triggers" for triggering the DockerHub Automated Build using the Token.
 * This example with trigger a public repository "sbasyal/docker-hub-demo".
 */
public class BuildTriggerTest {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws IOException {
        System.out.println("Triggering the build...");

        // We can curl from command line to test, using the following.
        // $ curl --data "build=true" -X POST https://registry.hub.docker.com/u/sbasyal/docker-hub-demo/trigger/8dfd4435-eabf-4f15-a12b-61bf526ccae9/

        String url_string = "https://registry.hub.docker.com/u/sbasyal/docker-hub-demo/trigger/8dfd4435-eabf-4f15-a12b-61bf526ccae9/";

        URL url = new URL(url_string);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "build=true";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url_string);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Printing the result. It will print "OK" on success.
        System.out.println(response.toString());
        System.out.println("Finished triggering the build. Next build cannot be triggered before 5 minutes is passed!");
    }
}
