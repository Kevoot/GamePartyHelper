package mobileappdevelopment.kevinholmes.partygamehelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kevin on 11/19/2017.
 */

public class SteamUserAdapter extends ArrayAdapter<SteamUser> {
    public SteamUserAdapter(@NonNull Context context, ArrayList<SteamUser> bookItems) {
        super(context, 0, bookItems);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
             listItemView = LayoutInflater.from(getContext()).inflate(
                     R.layout.layout_item, parent, false);
        }

        // Get the {@link Book} object located at this position in the list
        SteamUser currentItem = getItem(position);
        TextView id = (TextView) listItemView.findViewById(R.id.steam_id);
        id.setText(currentItem.mId);
        TextView numGames = (TextView) listItemView.findViewById(R.id.num_games);
        numGames.setText("# of games: " + currentItem.mGameCount);

        // TextView TitleTextView = (TextView) listItemView.findViewById(R.id.title_text_view);
        // TextView AuthorsTextView = (TextView) listItemView.findViewById(R.id.authors_text_view);

        // Get the Title
        // TitleTextView.setText(currentItem.title);

        // Get the Authors
        // AuthorsTextView.setText(currentItem.authors);

        // Return the whole list item layout so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
