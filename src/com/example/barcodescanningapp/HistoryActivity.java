package com.example.barcodescanningapp;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class HistoryActivity extends Activity {

	// List view
	private ListView lv;

	// Listview Adapter
	ArrayAdapter<String> adapter;

	// Search EditText
	EditText inputSearch;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multiplehistory);

		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
		
		
		List<String> history = new ArrayList<String>();
		

		Intent i = this.getIntent();
		/*final String bus_stop_type = i.getExtras().getString("bus_stop_type");
		final String bus_stop_source = i.getExtras().getString(
				"bus_stop_source");
		final String bus_stop_destination = i.getExtras().getString(
				"bus_stop_destination");*/

		lv = (ListView) findViewById(R.id.list_view);
		//inputSearch = (EditText) findViewById(R.id.inputSearch);

		// Adding items to listview
		adapter = new ArrayAdapter<String>(this,
				R.layout.activity_singlehistory, R.id.bus_stop_name,
				history);
		lv.setAdapter(adapter);

		lv.setAdapter(adapter);

/*		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {

				String stop = ((TextView) view).getText().toString();
				if (bus_stop_type.contains("source")) {
					stop = bus_stop_type + '_' + stop + '_'
							+ bus_stop_destination;
				} else {
					stop = bus_stop_type + '_' + bus_stop_source + '_' + stop;
				}

				
				 * Context context = getApplicationContext();
				 * 
				 * CharSequence text = stop;
				 * 
				 * int duration = Toast.LENGTH_SHORT;
				 * 
				 * Toast toast = Toast.makeText(context, text, duration);
				 * 
				 * toast.show();
				 

				Intent intent = new Intent(ListBusStopActivity.this,
						MainActivity.class);
				intent.putExtra("stop_name", stop);
				startActivity(intent);

			}

		});*/

		/*inputSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				HistoryActivity.this.adapter.getFilter().filter(cs);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});*/

	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
