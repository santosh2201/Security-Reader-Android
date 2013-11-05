package com.example.barcodescanningapp;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * This is demo code to accompany the Mobiletuts+ tutorial:
 * - Using Barcode Scanning in Android Apps
 * 
 * Sue Smith
 * May 2013
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	//UI instance variables
	private Button scanBtn;
	private TextView formatTxt, contentTxt;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//instantiate UI items
		scanBtn = (Button)findViewById(R.id.scan_button);
		formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView)findViewById(R.id.scan_content);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));

		//listen for clicks
		scanBtn.setOnClickListener(this);
	}

	public void onClick(View v){
		//check for scan button
		if(v.getId()==R.id.scan_button){
			//instantiate ZXing integration class
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			//start scanning
			scanIntegrator.initiateScan();
		}
	}
	
    

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		//retrieve result of scanning - instantiate ZXing object
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		//check we have a valid result
		if (scanningResult != null) {
			//get content from Intent Result
			String scanContent = scanningResult.getContents();
			//get format name of data scanned
			String scanFormat = scanningResult.getFormatName();
			//output to UI
			long currentTime= System.currentTimeMillis();
			
			
			//storing data url
			String url="http://10.20.254.11:9000/store";
			RequestParams params=new RequestParams();
			params.put("content", scanContent);
			params.put("time", currentTime+"");
			//sending data to server 
			AsyncHttpClient client = new AsyncHttpClient();
			client.post(url,params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					formatTxt.setText("FORMAT: "+response);
				}
			});
			//formatTxt.setText("FORMAT: "+scanFormat);
			contentTxt.setText("CONTENT: "+scanContent);
		}
		else{
			//invalid scan data or scan canceled
			Toast toast = Toast.makeText(getApplicationContext(), 
					"No scan data received!", Toast.LENGTH_SHORT);
			toast.show();
		}
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
	        	/*Toast toast = Toast.makeText(getApplicationContext(), 
						"No scan data received!", Toast.LENGTH_SHORT);
				toast.show();*/
	        	/*Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
	            String message = contentTxt.getText().toString();
	            intent.putExtra(message, false);
	            startActivity(intent);*/
	        	try {
	        		Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
		        	  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        	  startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				}
	        	
	        	  return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);

	    }

	}
	
	
	

}
