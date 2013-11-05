package fragments;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.barcodescanningapp.MainActivity;
import com.example.barcodescanningapp.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


@SuppressLint("NewApi")
public class FirstTimeDialogFragment extends DialogFragment {
	
	JSONObject jsonResponse;
	String content=null;
	String time=null;
	private ProgressDialog progressDialog;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		final String response=MainActivity.responseString;
		
				if(response!=null){
					try{
						jsonResponse = new JSONObject(response);
						 content =jsonResponse.getString("content");
						 time =jsonResponse.getString("time");
					}catch (Exception e) {
						// TODO: handle exception
					}
				}//if ends here
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.activity_firsttime, null))
				.setPositiveButton(R.string.in,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								if(response!=null){
									progressDialog = new ProgressDialog(getActivity());
									progressDialog.setMessage("Adding Bus Stops, Please wait...");
									progressDialog.setIndeterminate(true);
									progressDialog.show();
									sendDataToServer(content, time, "0");
								}
								
							}
						})
				.setNegativeButton(R.string.out,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								progressDialog = new ProgressDialog(getActivity());
								progressDialog.setMessage("Sending Data to server, Please wait...");
								progressDialog.setIndeterminate(true);
								progressDialog.show();
								sendDataToServer(content, time, "1");
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}
	public void sendDataToServer(String id,String dt,String status)
	{
		RequestParams params=new RequestParams();
		params.put("content", id);
		params.put("time", dt);
		params.put("status", status);
		//sending data to server 
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(variables.HttpVariables.httpRequestURL,params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				progressDialog.hide();
			}
		});
	}

	
	
}