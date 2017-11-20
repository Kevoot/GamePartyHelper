package mobileappdevelopment.kevinholmes.partygamehelper;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Kevin on 11/19/2017.
 */

public class AlertDialogFragment extends DialogFragment {
    public View theView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View view = factory.inflate(R.layout.dialog_fragment, null);
        theView = view;
        builder.setView(view);
        return builder.create();
    }

    public void setErrorMsg(String str) {
        TextView textView = (TextView) theView.findViewById(R.id.error_msg_text_view);
        textView.setText(str);
    }
}
