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
 * Created by Kevin on 11/20/2017.
 */

public class ResultAdapter extends ArrayAdapter<ResultGame> {
    public ResultAdapter(@NonNull Context context, ArrayList<ResultGame> musicItems) {
        super(context, 0, musicItems);
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.result_item, parent, false);
        }

        // Get the {@link Place} object located at this position in the list
        ResultGame currentItem = getItem(position);

        TextView idTextView = (TextView) listItemView.findViewById(R.id.game_id_result);
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.game_name_result);

        // Get the Name
        idTextView.setText(currentItem.mId);

        if(currentItem.mName != null) nameTextView.setText(currentItem.mName);

        return listItemView;
    }
}
