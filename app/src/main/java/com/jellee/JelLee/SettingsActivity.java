package com.jellee.JelLee;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.facebook.shimmer.*;
import com.google.android.flexbox.*;
import com.google.android.material.*;
import com.google.android.material.appbar.AppBarLayout;
import com.unity3d.ads.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.regex.*;
import org.jetbrains.kotlin.*;
import org.json.*;

public class SettingsActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double positionDefault = 0;
	private double positionDefault2 = 0;
	private double positionDefault3 = 0;
	private double positionDefault4 = 0;
	
	private ArrayList<String> publisherItems = new ArrayList<>();
	private ArrayList<String> librarySortList = new ArrayList<>();
	private ArrayList<String> serverList = new ArrayList<>();
	private ArrayList<String> providerList = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear6;
	private LinearLayout linear8;
	private LinearLayout linear4;
	private Spinner spinner1;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear5;
	private Spinner spinner2;
	private TextView textview3;
	private TextView textview4;
	private LinearLayout linear7;
	private Spinner spinner3;
	private TextView textview5;
	private TextView textview6;
	private LinearLayout linear9;
	private Spinner spinner4;
	private TextView textview7;
	private TextView textview8;
	
	private SharedPreferences settings;
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.settings);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear6 = findViewById(R.id.linear6);
		linear8 = findViewById(R.id.linear8);
		linear4 = findViewById(R.id.linear4);
		spinner1 = findViewById(R.id.spinner1);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		linear5 = findViewById(R.id.linear5);
		spinner2 = findViewById(R.id.spinner2);
		textview3 = findViewById(R.id.textview3);
		textview4 = findViewById(R.id.textview4);
		linear7 = findViewById(R.id.linear7);
		spinner3 = findViewById(R.id.spinner3);
		textview5 = findViewById(R.id.textview5);
		textview6 = findViewById(R.id.textview6);
		linear9 = findViewById(R.id.linear9);
		spinner4 = findViewById(R.id.spinner4);
		textview7 = findViewById(R.id.textview7);
		textview8 = findViewById(R.id.textview8);
		settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				String selectedItem = publisherItems.get(_position);
				settings.edit().putString("mainPublisher", selectedItem).commit();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				String selectedItem = serverList.get(_position);
				settings.edit().putString("server", selectedItem).commit();
				switch(selectedItem) {
					case "Server 1": {
						settings.edit().putString("serverLink", "https://jellee.vercel.app/novel/jellee/").commit();
						break;
					}
					case "Server 2": {
						settings.edit().putString("serverLink", "https://jellee-f9b5.vercel.app/novel/jellee/").commit();
						break;
					}
					case "Server 3": {
						settings.edit().putString("serverLink", "https://kizuko.vercel.app/novel/jellee/").commit();
						break;
					}
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("Settings");
		_toolbar.setBackgroundColor(getResources().getColor(R.color.colorBackgroundPrimary));
		getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackgroundPrimary));
		_spinner1AdapterLayout();
		_spinner2AdapterLayout();
		_spinner3AdapterLayout();
		_spinner4AdapterLayout();
		if (settings.contains("mainPublisher")) {
			positionDefault = publisherItems.indexOf(settings.getString("mainPublisher", ""));
			if ((int) positionDefault != -1) {
				spinner1.setSelection((int)(positionDefault));
			}
		} else {
			settings.edit().putString("mainPublisher", "Yen Press").commit();
			positionDefault = publisherItems.indexOf(settings.getString("mainPublisher", ""));
			if ((int) positionDefault != -1) {
				spinner1.setSelection((int)(positionDefault));
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
		Animatoo.animateSwipeRight(SettingsActivity.this);
	}
	public void _spinner2AdapterLayout() {
		librarySortList.add("Id");
		librarySortList.add("Title");
		librarySortList.add("Ratings");
		librarySortList.add("Popularity");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_items, librarySortList){
			 @Override
			public View getView(int position, View convertView, ViewGroup parent){
				convertView = getLayoutInflater().inflate(R.layout.dropdown_items, parent, false);
				ImageView image = convertView.findViewById(R.id.image1);
				TextView text = convertView.findViewById(R.id.text1);
				image.setVisibility(View.VISIBLE);
				image.setImageResource(R.drawable.icon_check_round);
				text.setText(librarySortList.get((int)(position)));
				return convertView;
			}
			 @Override
			public View getDropDownView(int position, View convertView, ViewGroup parent){
				convertView = getLayoutInflater().inflate(R.layout.dropdown_items, parent, false);
				ImageView image = convertView.findViewById(R.id.image1);
				TextView text = convertView.findViewById(R.id.text1);
				text.setText(librarySortList.get((int)(position)));
				if (spinner2.getSelectedItemPosition() == position) {
					text.setTextColor(getResources().getColor(R.color.colorAccent));
					image.setVisibility(View.VISIBLE);
					image.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
				} else {
					text.setTextColor(getResources().getColor(R.color.colorTextPrimary));
					image.setVisibility(View.GONE);
				}
				return convertView;
			}
		};
		spinner2.setAdapter(adapter);
		if (settings.contains("librarySort")) {
			positionDefault2 = librarySortList.indexOf(settings.getString("librarySort", ""));
			if ((int) positionDefault2 != -1) {
				spinner2.setSelection((int)(positionDefault2));
			}
		} else {
			settings.edit().putString("librarySort", "Title").commit();
			positionDefault2 = librarySortList.indexOf(settings.getString("librarySort", ""));
			if ((int) positionDefault2 != -1) {
				spinner2.setSelection((int)(positionDefault2));
			}
		}
		spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			 @Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String selectedItem = librarySortList.get(position);
				settings.edit().putString("librarySort", selectedItem).commit();
			}
			 @Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Handle case where no item is selected (optional)
			}
		});
	}
	
	
	public void _spinner1AdapterLayout() {
		publisherItems.add("Yen Press");
		publisherItems.add("J-Novel Club");
		publisherItems.add("Seven Seas Entertainment");
		publisherItems.add("Cross Infinite World");
		publisherItems.add("Tentai Books");
		publisherItems.add("Bookwalker");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_items, publisherItems){
			 @Override
			public View getView(int position, View convertView, ViewGroup parent){
				convertView = getLayoutInflater().inflate(R.layout.dropdown_items, parent, false);
				ImageView image = convertView.findViewById(R.id.image1);
				TextView text = convertView.findViewById(R.id.text1);
				image.setVisibility(View.VISIBLE);
				image.setImageResource(R.drawable.icon_check_round);
				text.setText(publisherItems.get((int)(position)));
				return convertView;
			}
			 @Override
			public View getDropDownView(int position, View convertView, ViewGroup parent){
				convertView = getLayoutInflater().inflate(R.layout.dropdown_items, parent, false);
				ImageView image = convertView.findViewById(R.id.image1);
				TextView text = convertView.findViewById(R.id.text1);
				text.setText(publisherItems.get((int)(position)));
				if (spinner1.getSelectedItemPosition() == position) {
					text.setTextColor(getResources().getColor(R.color.colorAccent));
					image.setVisibility(View.VISIBLE);
					image.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
				} else {
					text.setTextColor(getResources().getColor(R.color.colorTextPrimary));
					image.setVisibility(View.GONE);
				}
				return convertView;
			}
		};
		spinner1.setAdapter(adapter);
	}
	
	
	public void _spinner3AdapterLayout() {
		providerList.add("Moon Reader");
		providerList.add("ReadEra");
		providerList.add("eBoox");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_items, providerList){
			 @Override
			public View getView(int position, View convertView, ViewGroup parent){
				convertView = getLayoutInflater().inflate(R.layout.dropdown_items, parent, false);
				ImageView image = convertView.findViewById(R.id.image1);
				TextView text = convertView.findViewById(R.id.text1);
				image.setVisibility(View.VISIBLE);
				image.setImageResource(R.drawable.icon_check_round);
				text.setText(providerList.get((int)(position)));
				return convertView;
			}
			 @Override
			public View getDropDownView(int position, View convertView, ViewGroup parent){
				convertView = getLayoutInflater().inflate(R.layout.dropdown_items, parent, false);
				ImageView image = convertView.findViewById(R.id.image1);
				TextView text = convertView.findViewById(R.id.text1);
				text.setText(providerList.get((int)(position)));
				if (spinner3.getSelectedItemPosition() == position) {
					text.setTextColor(getResources().getColor(R.color.colorAccent));
					image.setVisibility(View.VISIBLE);
					image.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
				} else {
					text.setTextColor(getResources().getColor(R.color.colorTextPrimary));
					image.setVisibility(View.GONE);
				}
				return convertView;
			}
		};
		spinner3.setAdapter(adapter);
		if (settings.contains("mainProvider")) {
			positionDefault3 = providerList.indexOf(settings.getString("mainProvider", ""));
			if ((int) positionDefault3 != -1) {
				spinner3.setSelection((int)(positionDefault3));
			}
		} else {
			settings.edit().putString("mainProvider", "Moon Reader").commit();
			positionDefault3 = providerList.indexOf(settings.getString("mainProvider", ""));
			if ((int) positionDefault3 != -1) {
				spinner3.setSelection((int)(positionDefault3));
			}
		}
		spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			 @Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String selectedItem = providerList.get(position);
				settings.edit().putString("mainProvider", selectedItem).commit();
			}
			 @Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Handle case where no item is selected (optional)
			}
		});
	}
	
	
	public void _spinner4AdapterLayout() {
		serverList.add("Server 1");
		serverList.add("Server 2");
		serverList.add("Server 3");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_items, serverList){
			 @Override
			public View getView(int position, View convertView, ViewGroup parent){
				convertView = getLayoutInflater().inflate(R.layout.dropdown_items, parent, false);
				ImageView image = convertView.findViewById(R.id.image1);
				TextView text = convertView.findViewById(R.id.text1);
				image.setVisibility(View.VISIBLE);
				image.setImageResource(R.drawable.icon_check_round);
				text.setText(serverList.get((int)(position)));
				return convertView;
			}
			 @Override
			public View getDropDownView(int position, View convertView, ViewGroup parent){
				convertView = getLayoutInflater().inflate(R.layout.dropdown_items, parent, false);
				ImageView image = convertView.findViewById(R.id.image1);
				TextView text = convertView.findViewById(R.id.text1);
				text.setText(serverList.get((int)(position)));
				if (spinner4.getSelectedItemPosition() == position) {
					text.setTextColor(getResources().getColor(R.color.colorAccent));
					image.setVisibility(View.VISIBLE);
					image.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
				} else {
					text.setTextColor(getResources().getColor(R.color.colorTextPrimary));
					image.setVisibility(View.GONE);
				}
				return convertView;
			}
		};
		spinner4.setAdapter(adapter);
		if (settings.contains("server")) {
			positionDefault4 = serverList.indexOf(settings.getString("server", ""));
			if ((int) positionDefault4 != -1) {
				spinner4.setSelection((int)(positionDefault4));
			}
		} else {
			settings.edit().putString("server", "Server 1").commit();
			positionDefault4 = serverList.indexOf(settings.getString("server", ""));
			if ((int) positionDefault4 != -1) {
				spinner4.setSelection((int)(positionDefault4));
			}
		}
	}
	
	
	public void _restartApp() {
		intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
		Runtime.getRuntime().exit(0);
	}
	
}