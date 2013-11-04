package fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.example.barcodescanningapp.MainActivity;
import com.example.barcodescanningapp.R;


public class FirstTimeDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.activity_firsttime, null))
				.setPositiveButton(R.string.in,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
							}
						})
				.setNegativeButton(R.string.out,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}
	
}
