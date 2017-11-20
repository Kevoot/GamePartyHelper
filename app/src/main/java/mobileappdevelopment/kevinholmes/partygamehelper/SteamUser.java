package mobileappdevelopment.kevinholmes.partygamehelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/19/2017.
 */

public class SteamUser {
    String mId;
    String mName;
    String mProfileName;
    int mGameCount;
    ArrayList<String> mGameIds;
    ArrayList<String> mGameTitles;

    JSONObject mReceivedJSON;


    public SteamUser() {

    }

    public SteamUser(String id) {
        this.mId = id;
    }

    public SteamUser(String id, int gameCount, ArrayList<String> gameIds) {
        this.mId = id;
        this.mGameCount = gameCount;
        mGameIds = new ArrayList<>();
        for(String ids : gameIds) {
            mGameIds.add(ids);
        }
    }

    public void fillFields() {
        try {
            mName = mReceivedJSON.getString("/* TODO: figure out proper string*/");
            mProfileName = mReceivedJSON.getString("/* TODO: figure out proper string*/");
            // Send 2nd Call to get user's games
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
