package mobileappdevelopment.kevinholmes.partygamehelper;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import static mobileappdevelopment.kevinholmes.partygamehelper.MainActivity.mCurrentResults;

/**
 * Created by Kevin on 11/20/2017.
 */

public class ResultFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.results_fragment, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.result_list_view);

        ArrayList<ResultGame> resultGames = new ArrayList<>();
        for(String str : mCurrentResults) {
            resultGames.add(new ResultGame(str));
        }

        ResultAdapter adapter = new ResultAdapter(this.getContext(), resultGames);

        listView.setAdapter(adapter);

        return rootView;
    }
}
