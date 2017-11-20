package mobileappdevelopment.kevinholmes.partygamehelper;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

import static mobileappdevelopment.kevinholmes.partygamehelper.MainActivity.mUserSelected;
import static mobileappdevelopment.kevinholmes.partygamehelper.MainActivity.rJsonObj;

/**
 * Created by Kevin on 11/19/2017.
 */

class SteamAPIAdapter extends AsyncTask<String, Object, JSONObject> {
    String s = "";
    private mobileappdevelopment.kevinholmes.partygamehelper.OnTaskCompleted taskCompleted;

    public SteamAPIAdapter(OnTaskCompleted activityContext) {
        this.taskCompleted = activityContext;
    }

    @Override
    protected JSONObject doInBackground(String... keyword) {
        // Stop if cancelled
        android.os.Debug.waitForDebugger();
        if (isCancelled()) {
            return null;
        }
        String apiUrlString = "http://api.steampowered.com/IPlayerService/GetOwnedGames/v0001/?key=B4762A3497A4BBB812A1CBEAF29F2187&steamid="
                + mUserSelected + "&format=json";
        try {
            HttpURLConnection connection = null;
            // Build Connection.
            try {
                URL url = new URL(apiUrlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000); // 5 seconds
                connection.setConnectTimeout(5000); // 5 seconds
            } catch (MalformedURLException e) {
                // Impossible: The only two URLs used in the app are taken from string resources.
                e.printStackTrace();
            } catch (ProtocolException e) {
                // Impossible: "GET" is a perfectly valid request method.
                e.printStackTrace();
            }
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                Log.w(getClass().getName(), "SteamAPI request failed. Response Code: " + responseCode);
                connection.disconnect();
                return null;
            }

            // Read data from response.
            StringBuilder builder = new StringBuilder();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = responseReader.readLine();
            while (line != null) {
                builder.append(line);
                line = responseReader.readLine();
            }
            String responseString = builder.toString();
            Log.d(getClass().getName(), "Response String: " + responseString);
            JSONObject responseJson = new JSONObject(responseString);
            // Close connection and return response code.
            connection.disconnect();
            return responseJson;
        } catch (SocketTimeoutException e) {
            Log.w(getClass().getName(), "Connection timed out. Returning null");
            return null;
        } catch (IOException e) {
            Log.d(getClass().getName(), "IOException when connecting to SteamAPI.");
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            Log.d(getClass().getName(), "JSONException when connecting to SteamAPI.");
            e.printStackTrace();
            return null;
        }
    }

    // Handles the returned responseJson from doInBackground
    protected void onPostExecute(JSONObject rJson){
        android.os.Debug.waitForDebugger();
        rJsonObj = rJson;
        taskCompleted.onTaskCompleted("Done");
        return;
    }
}
