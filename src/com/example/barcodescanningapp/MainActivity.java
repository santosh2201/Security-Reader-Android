package com.example.barcodescanningapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import fragments.FirstTimeDialogFragment;

/**
 * 
 *
 */
public class MainActivity extends Activity implements OnClickListener {
	public static String responseString = null;
	// UI instance variables
	private Button scanBtn;
	private TextView formatTxt, contentTxt;
	private ProgressDialog progressDialog;
	int status;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// instantiate UI items
		scanBtn = (Button) findViewById(R.id.scan_button);
		formatTxt = (TextView) findViewById(R.id.scan_format);
		contentTxt = (TextView) findViewById(R.id.scan_content);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));

		// listen for clicks
		scanBtn.setOnClickListener(this);
	}

	public void onClick(View v) {
		// check for scan button
		if (v.getId() == R.id.scan_button) {
			// instantiate ZXing integration class
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			// start scanning
			scanIntegrator.initiateScan();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// retrieve result of scanning - instantiate ZXing object
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		// check we have a valid result
		if (scanningResult != null) {
			// get content from Intent Result
			String scanContent = scanningResult.getContents();
			// get format name of data scanned

			long currentTime = System.currentTimeMillis();
			sendDataToServer(scanContent, currentTime + "");
			contentTxt.setText("Student: " + scanContent);
		} else {
			// invalid scan data or scan canceled
			Toast toast = Toast.makeText(getApplicationContext(),
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	/**
	 * sends data to server asynchronously
	 * 
	 * */
	void sendDataToServer(String scanContent, String currentTime) {
		RequestParams params = new RequestParams();
		params.put("content", scanContent);
		params.put("time", currentTime);
		// sending data to server

		progressDialog = new ProgressDialog(MainActivity.this);
		progressDialog.setMessage("Sending Data to server, Please wait...");
		progressDialog.setIndeterminate(true);
		progressDialog.show();

		AsyncHttpClient client = new AsyncHttpClient();
		client.post(variables.HttpVariables.httpRequestURL, params,
				new AsyncHttpResponseHandler() {
					@SuppressLint("NewApi")
					@Override
					public void onSuccess(String response) {
						progressDialog.hide();
						// formatTxt.setText("FORMAT: "+response);
						if (response.contains("true"))
							formatTxt.setText("FORMAT: " + response);
						else {
							responseString = response;
							try {
								JSONObject jsonResponse = new JSONObject(
										response);
								String id = jsonResponse.getString("_id");
								status = jsonResponse.getInt("status");
								
								JSONArray inOutArrey = jsonResponse
										.getJSONArray("time_in_out");
								if(status==1 && inOutArrey.length()>0){
									Long time = Long.parseLong(inOutArrey.getJSONObject(inOutArrey.length()-1).getString("intime"));
									formatTxt.setText("Previous in time: "
											+ dateConverter(time));
								}
								if(status==0 && inOutArrey.length()>0){
									Long time = Long.parseLong(inOutArrey.getJSONObject(inOutArrey.length()-2).getString("outtime"));
									formatTxt.setText("Previous out time: "
											+ dateConverter(time));
								}
								

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								FragmentManager fm = getFragmentManager();
								DialogFragment newFragment = new FirstTimeDialogFragment();
								newFragment.setRetainInstance(true);
								newFragment.show(fm, "dialog");
							}
							/**/
						}
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.student_history:
			
			if (responseString == null) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"No scan data received!", Toast.LENGTH_SHORT);
				toast.show();

			} else {
				try {
					Intent intent = new Intent(MainActivity.this,
							HistoryActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				}
			}

			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}
	
	
	public String dateConverter(Long x) {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd/MM/yyyy hh:mm:ss.SSS");
		String dateString = formatter.format(new Date(x));
		// System.out.println(dateString);
		return dateString;

	}
	

}