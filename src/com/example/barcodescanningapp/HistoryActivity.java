package com.example.barcodescanningapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryActivity extends Activity {

	// List view
	private ListView lv;
	String id;
	String intime;
	String outtime;
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
		TextView Student = (TextView) findViewById(R.id.textView1);
		JSONObject jsonResponse;
		System.out.println(MainActivity.responseString);
		try {
			jsonResponse = new JSONObject(MainActivity.responseString);
			id = jsonResponse.getString("_id");
			JSONArray inOutArrey = jsonResponse.getJSONArray("time_in_out");
			Student.setText("Student: " + id);
			// history.add(inOutArrey.getJSONObject(0).getString("intime"));
			for (int i = inOutArrey.length() - 1; i >= 0; i--) {
				String in = inOutArrey.getJSONObject(i).getString("intime");
				String out = inOutArrey.getJSONObject(i).getString("outtime");
				// System.out.println(in+"_________"+out);
				if (in.contains("none")) {
					intime = "none";
				} else {
					Long inl = Long.parseLong(in);
					intime = dateConverter(inl);
				}

				if (out.contains("none")) {
					outtime = "none";
				} else {
					Long outl = Long.parseLong(out);
					outtime = dateConverter(outl);
				}

				String mix = "In:" + intime + "                 Out:" + outtime;
				history.add(mix);
				// System.out.println(intime+"_________"+outtime);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// Student.setText("Student: "+e);
			e.printStackTrace();
		}

		lv = (ListView) findViewById(R.id.list_view);

		adapter = new ArrayAdapter<String>(HistoryActivity.this,
				R.layout.activity_singlehistory, R.id.bus_stop_name, history);
		lv.setAdapter(adapter);

		lv.setAdapter(adapter);

	}

	public String dateConverter(Long x) {

		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd/MM/yyyy hh:mm:ss.SSS");
		String dateString = formatter.format(new Date(x));
		// System.out.println(dateString);
		return dateString;

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
