package mobileappdevelopment.kevinholmes.partygamehelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted {

    private EditText mTextEdit;
    private Button mAddButton;
    private Button mRemoveButton;
    private Button mCompareButton;
    private ListView mUserList;
    public static JSONObject rJsonObj;

    public static String mUserSelected;

    public static ArrayList<SteamUser> Users = new ArrayList<>();

    public static SteamUserAdapter mUserAdapter;
    public static ResultAdapter mResultAdapter;
    public static boolean rSuccess;
    public static ArrayList<String> mCurrentResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Users = new ArrayList<>();

        mTextEdit = (EditText) findViewById(R.id.name_input);
        mUserList = (ListView) findViewById(R.id.id_list_view);

        mAddButton = (Button) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    setUserSelected(mTextEdit.getText().toString());
                    SteamAPIAdapter request = new SteamAPIAdapter(MainActivity.this);
                    request.execute(mUserSelected);
                } catch (Exception e) {
                    Log.d(null, "Couldn't convert!");
                }
            }
        });

        mRemoveButton = (Button) findViewById(R.id.remove_button);

        mCompareButton = (Button) findViewById(R.id.compare_button);
        mCompareButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    Compare();
                    ResultFragment rf = new ResultFragment();
                    rf.show(getFragmentManager(), "Test");
                } catch (Exception e) {
                    Log.d(null, "Couldn't convert!");
                }
            }
        });

    }

    private ArrayList<Integer> Compare() {
        if(Users.size() < 2) {
            showError("Not enough users!");
            return null;
        }
        ArrayList<String> commons = new ArrayList<>();
        for(String entry : Users.get(0).mGameIds) {
            commons.add(entry);
        }

        ArrayList<String> pendingDeletions = new ArrayList<String>();

        for(SteamUser user : Users) {
            if(user == Users.get(0)) continue;

            for(String entry : commons) {
                if(!user.mGameIds.contains(entry)) {
                    if(!pendingDeletions.contains(entry)) pendingDeletions.add(entry);
                }
            }
        }

        for(String entry : pendingDeletions) {
            commons.remove(entry);
        }

        mCurrentResults = commons;

        return null;
    }

    public void DisplayUsers() {
        if(rJsonObj == null) {
            showError("Error retrieving data");
            return;
        }

        JSONArray jsonRootItems = null;
        try {
            JSONObject rootResponse = rJsonObj.getJSONObject("response");
            int game_count = (int) rootResponse.get("game_count");
            JSONArray gamesJson = (JSONArray) rootResponse.get("games");

            ArrayList<JSONObject> objs = new ArrayList<>();
            for(int i = 0; i < gamesJson.length(); i++) {
                objs.add((JSONObject) gamesJson.get(i));
            }

            ArrayList<String> ids = new ArrayList<>();

            for(JSONObject obj : objs) {
                int appid = (int)(obj.get("appid"));
                ids.add(String.valueOf(appid));
            }
            SteamUser user = new SteamUser(mUserSelected, game_count, ids);
            Users.add(user);
        } catch (JSONException e) {
            e.printStackTrace();
            showError("Malformed JSON received");
        }

        mUserAdapter = new SteamUserAdapter(this.getApplicationContext(), Users);
        ListView userList = (ListView) findViewById(R.id.id_list_view);

        userList.setAdapter(mUserAdapter);
    }

    public String getUserSelected() {
        return mUserSelected;
    }

    public void setUserSelected(String mUserSelected) {
        if(mUserSelected.equals("")) mRemoveButton.setEnabled(false);
        else mRemoveButton.setEnabled(true);
        this.mUserSelected = mUserSelected;
    }

    @Override
    public void onTaskCompleted(String response) {
        DisplayUsers();
    }

    public void showError(String str) {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getSupportFragmentManager(), str);
    }
}
