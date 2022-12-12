import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.simple.JSONObject;

public class Espresso {
  public static void main(String[] args) {
    UIAutomatorTest();
  }

  public static String generateBasicAuth() {
    String username = "gyanadeeps";
    String apiKey = "15a9ea3f-38fb-450c-9706-08a72ed71950";
    byte[] encodedBytes = Base64.getEncoder().encode((username + ":" + apiKey).getBytes());
    String encodeAuth = new String(encodedBytes);
    return "Basic " + encodeAuth;
  }

  public static void UIAutomatorTest() {
    try {
      List<String> tests = new ArrayList<String>();
      tests.add("com.todolist.app.version1");
      //tests.add("ChangeTextBehaviorTest");
      //tests.add("ChangeTextBehaviorTest#testChangeText_sameActivity");

      JSONObject jsonObject = new JSONObject();
   
      
      
      
      jsonObject.put("sessionName", "Automation test session");
      jsonObject.put("sessionDescription", ""); 
      jsonObject.put("noReset", true);
      jsonObject.put("fullReset", false);     
      jsonObject.put("deviceName", "Galaxy S20+ 5G (Payload Enabled)");
      // The tag is used for finding devices and the user can input only one tag. 
      // For example, the data value will be inputted: tagName="TagName1"
      jsonObject.put("tagName", "");
      jsonObject.put("platformVersion", "13");  
      // The given team is used for finding devices and the created session will be visible for all members within the team.
      jsonObject.put("groupId", 12170); // Group: AutomationDocs
      jsonObject.put("deviceGroup", "ORGANIZATION");
      jsonObject.put("app", "kobiton-store:v385600");
      jsonObject.put("testRunner", "https://kobiton-devvn.s3-ap-southeast-1.amazonaws.com/apps-test/uiautomator-espresso/esspresso-test-runner.apk"); 
      jsonObject.put("continueOnFailure", true);
      jsonObject.put("testFramework", "UIAUTOMATOR");
      
      jsonObject.put("tests", tests);
      
      

      String url = "https://api.kobiton.com/hub/session";
      URL uri = new URL(url);
      HttpURLConnection con = (HttpURLConnection) uri.openConnection();

      con.setDoOutput(true);
      con.setDoInput(true);
      con.setUseCaches(false);

      JSONObject configuration = new JSONObject();
      configuration.put("configuration", jsonObject);

      String postData = configuration.toString();

      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("Accept", "application/json");
      con.setRequestProperty("Authorization", generateBasicAuth());
      OutputStream os = con.getOutputStream();
      os.write(postData.getBytes());
      os.flush();

      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }

      in.close();
      con.disconnect();

      System.out.println("Result: " + response.toString());
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }
}
