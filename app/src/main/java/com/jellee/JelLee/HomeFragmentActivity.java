package com.jellee.JelLee;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.view.inputmethod.EditorInfo;
import android.webkit.*;
import android.widget.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.*;
import com.google.android.flexbox.*;
import com.google.android.material.*;
import com.unity3d.ads.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.jetbrains.kotlin.*;
import org.json.*;

public class HomeFragmentActivity extends Fragment {
	
	private boolean isDataLoading = false;
	private Handler handler = new Handler();;
	private Runnable runnable;
	private int counter = 0;
	private Recyclerview1Adapter adapter1;
	private Recyclerview3Adapter adapter3;
	private Recyclerview4Adapter adapter4;
	private Recyclerview2Adapter adapter2;
	private String stringRatings = "";
	private String stringPublisher = "";
	
	private ArrayList<HashMap<String, Object>> sliderListmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> newestListmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> popularListmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> publisherListmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> mostRatedListmap = new ArrayList<>();
	
	private SwipeRefreshLayout swiperefreshlayout1;
	private NestedScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private CardView cardview1;
	private LinearLayout new_release_linear;
	private LinearLayout mostrated_linear;
	private LinearLayout popular_linear;
	private LinearLayout publisher_linear;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear3;
	private ImageView imageview1;
	private EditText searchField;
	private LinearLayout linear4;
	private ViewPager viewpager1;
	private LinearLayout linear6;
	private RecyclerView recyclerview1;
	private TextView textview3;
	private ImageView imageview2;
	private LinearLayout linear8;
	private RecyclerView recyclerview2;
	private TextView textview4;
	private ImageView imageview3;
	private LinearLayout linear10;
	private RecyclerView recyclerview3;
	private TextView textview5;
	private ImageView imageview4;
	private LinearLayout linear12;
	private RecyclerView recyclerview4;
	private TextView textview6;
	private ImageView imageview5;
	
	private RequestNetwork requestNetwork;
	private RequestNetwork.RequestListener _requestNetwork_request_listener;
	private Intent intent = new Intent();
	private SharedPreferences settings;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.home_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		swiperefreshlayout1 = _view.findViewById(R.id.swiperefreshlayout1);
		vscroll1 = _view.findViewById(R.id.vscroll1);
		linear1 = _view.findViewById(R.id.linear1);
		linear2 = _view.findViewById(R.id.linear2);
		cardview1 = _view.findViewById(R.id.cardview1);
		new_release_linear = _view.findViewById(R.id.new_release_linear);
		mostrated_linear = _view.findViewById(R.id.mostrated_linear);
		popular_linear = _view.findViewById(R.id.popular_linear);
		publisher_linear = _view.findViewById(R.id.publisher_linear);
		textview1 = _view.findViewById(R.id.textview1);
		textview2 = _view.findViewById(R.id.textview2);
		linear3 = _view.findViewById(R.id.linear3);
		imageview1 = _view.findViewById(R.id.imageview1);
		searchField = _view.findViewById(R.id.searchField);
		linear4 = _view.findViewById(R.id.linear4);
		viewpager1 = _view.findViewById(R.id.viewpager1);
		linear6 = _view.findViewById(R.id.linear6);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		textview3 = _view.findViewById(R.id.textview3);
		imageview2 = _view.findViewById(R.id.imageview2);
		linear8 = _view.findViewById(R.id.linear8);
		recyclerview2 = _view.findViewById(R.id.recyclerview2);
		textview4 = _view.findViewById(R.id.textview4);
		imageview3 = _view.findViewById(R.id.imageview3);
		linear10 = _view.findViewById(R.id.linear10);
		recyclerview3 = _view.findViewById(R.id.recyclerview3);
		textview5 = _view.findViewById(R.id.textview5);
		imageview4 = _view.findViewById(R.id.imageview4);
		linear12 = _view.findViewById(R.id.linear12);
		recyclerview4 = _view.findViewById(R.id.recyclerview4);
		textview6 = _view.findViewById(R.id.textview6);
		imageview5 = _view.findViewById(R.id.imageview5);
		requestNetwork = new RequestNetwork((Activity) getContext());
		settings = getContext().getSharedPreferences("settings", Activity.MODE_PRIVATE);
		
		swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swiperefreshlayout1.setRefreshing(false);
			}
		});
		
		recyclerview1.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {
				super.onScrollStateChanged(recyclerView, _scrollState);
				
			}
			
			@Override
			public void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {
				super.onScrolled(recyclerView, _offsetX, _offsetY);
				LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
				int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
				int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
				for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
					View itemView = layoutManager.findViewByPosition(i);
					if (itemView != null) {
						int itemWidth = itemView.getWidth();
						int recyclerViewWidth = recyclerView.getWidth();
						// Calculate the item’s visibility
						float leftVisible = Math.max(0, itemView.getLeft());
						float rightVisible = Math.min(recyclerViewWidth, itemView.getRight());
						
						// Calculate the percentage of visibility
						float visibilityPercentage = (rightVisible - leftVisible) / itemWidth;
						
						if (visibilityPercentage < 1.0f) {
							// Scale from 0.8 to 1.0 based on visibility with a short animation for smoothness
							float targetScale = 0.8f + (0.2f * visibilityPercentage);
							itemView.animate().scaleX(targetScale).scaleY(targetScale)
							.setDuration(80) // Short duration for responsive effect
							.start();
						} else {
							// Fully visible items should smoothly animate back to original size
							itemView.animate().scaleX(1.0f).scaleY(1.0f)
							.setDuration(80)
							.start();
						}
					}
				}
				
			}
		});
		
		imageview2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent = new Intent(getContext().getApplicationContext(), FilterActivity.class);
				intent.putExtra("tag", "latest");
				startActivity(intent);
				Animatoo.animateSlideUp(getContext());
			}
		});
		
		recyclerview2.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {
				super.onScrollStateChanged(recyclerView, _scrollState);
				
			}
			
			@Override
			public void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {
				super.onScrolled(recyclerView, _offsetX, _offsetY);
				LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
				int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
				int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
				for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
					View itemView = layoutManager.findViewByPosition(i);
					if (itemView != null) {
						int itemWidth = itemView.getWidth();
						int recyclerViewWidth = recyclerView.getWidth();
						// Calculate the item’s visibility
						float leftVisible = Math.max(0, itemView.getLeft());
						float rightVisible = Math.min(recyclerViewWidth, itemView.getRight());
						
						// Calculate the percentage of visibility
						float visibilityPercentage = (rightVisible - leftVisible) / itemWidth;
						
						if (visibilityPercentage < 1.0f) {
							// Scale from 0.8 to 1.0 based on visibility with a short animation for smoothness
							float targetScale = 0.8f + (0.2f * visibilityPercentage);
							itemView.animate().scaleX(targetScale).scaleY(targetScale)
							.setDuration(80) // Short duration for responsive effect
							.start();
						} else {
							// Fully visible items should smoothly animate back to original size
							itemView.animate().scaleX(1.0f).scaleY(1.0f)
							.setDuration(80)
							.start();
						}
					}
				}
				
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent = new Intent(getContext().getApplicationContext(), FilterActivity.class);
				intent.putExtra("tag", "ratings");
				startActivity(intent);
				Animatoo.animateSlideUp(getContext());
			}
		});
		
		recyclerview3.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {
				super.onScrollStateChanged(recyclerView, _scrollState);
				
			}
			
			@Override
			public void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {
				super.onScrolled(recyclerView, _offsetX, _offsetY);
				LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
				int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
				int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
				for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
					View itemView = layoutManager.findViewByPosition(i);
					if (itemView != null) {
						int itemWidth = itemView.getWidth();
						int recyclerViewWidth = recyclerView.getWidth();
						// Calculate the item’s visibility
						float leftVisible = Math.max(0, itemView.getLeft());
						float rightVisible = Math.min(recyclerViewWidth, itemView.getRight());
						
						// Calculate the percentage of visibility
						float visibilityPercentage = (rightVisible - leftVisible) / itemWidth;
						
						if (visibilityPercentage < 1.0f) {
							// Scale from 0.8 to 1.0 based on visibility with a short animation for smoothness
							float targetScale = 0.8f + (0.2f * visibilityPercentage);
							itemView.animate().scaleX(targetScale).scaleY(targetScale)
							.setDuration(80) // Short duration for responsive effect
							.start();
						} else {
							// Fully visible items should smoothly animate back to original size
							itemView.animate().scaleX(1.0f).scaleY(1.0f)
							.setDuration(80)
							.start();
						}
					}
				}
				
			}
		});
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent = new Intent(getContext().getApplicationContext(), FilterActivity.class);
				intent.putExtra("tag", "popularity");
				startActivity(intent);
				Animatoo.animateSlideUp(getContext());
			}
		});
		
		recyclerview4.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {
				super.onScrollStateChanged(recyclerView, _scrollState);
				
			}
			
			@Override
			public void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {
				super.onScrolled(recyclerView, _offsetX, _offsetY);
				LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
				int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
				int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
				for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
					View itemView = layoutManager.findViewByPosition(i);
					if (itemView != null) {
						int itemWidth = itemView.getWidth();
						int recyclerViewWidth = recyclerView.getWidth();
						// Calculate the item’s visibility
						float leftVisible = Math.max(0, itemView.getLeft());
						float rightVisible = Math.min(recyclerViewWidth, itemView.getRight());
						
						// Calculate the percentage of visibility
						float visibilityPercentage = (rightVisible - leftVisible) / itemWidth;
						
						if (visibilityPercentage < 1.0f) {
							// Scale from 0.8 to 1.0 based on visibility with a short animation for smoothness
							float targetScale = 0.8f + (0.2f * visibilityPercentage);
							itemView.animate().scaleX(targetScale).scaleY(targetScale)
							.setDuration(80) // Short duration for responsive effect
							.start();
						} else {
							// Fully visible items should smoothly animate back to original size
							itemView.animate().scaleX(1.0f).scaleY(1.0f)
							.setDuration(80)
							.start();
						}
					}
				}
				
			}
		});
		
		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent = new Intent(getContext().getApplicationContext(), FilterActivity.class);
				intent.putExtra("tag", "publisher");
				intent.putExtra("text", textview6.getText().toString().replace("Top ", ""));
				startActivity(intent);
				Animatoo.animateSlideUp(getContext());
			}
		});
		
		_requestNetwork_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				swiperefreshlayout1.setRefreshing(false);
				switch(_tag) {
					case "latest": {
						_GetNewestData(_response, false);
						break;
					}
					case "most-rated": {
						_GetMostRatedData(_response, false);
						break;
					}
					case "popular": {
						_GetPopularData(_response, false);
						break;
					}
					case "publisher": {
						_GetPublisherData(_response, false);
						break;
					}
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				switch(_tag) {
					case "latest": {
						showErrorLayout(new_release_linear, recyclerview1, "latest", newestListmap);
						break;
					}
					case "most-rated": {
						showErrorLayout(mostrated_linear, recyclerview2, "most-rated", mostRatedListmap);
						break;
					}
					case "popular": {
						showErrorLayout(linear4, viewpager1, "popular", sliderListmap);
						showErrorLayout(popular_linear, recyclerview3, "popular", popularListmap);
						break;
					}
					case "publisher": {
						showErrorLayout(publisher_linear, recyclerview4, "publisher", publisherListmap);
						break;
					}
				}
			}
		};
	}
	
	private void initializeLogic() {
		linear3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)2, 0xFF263238, getResources().getColor(R.color.colorBackgroundVariant)));
		recyclerview1.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
		recyclerview2.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
		recyclerview3.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
		recyclerview4.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
		adapter1 = new Recyclerview1Adapter(newestListmap);
		recyclerview1.setAdapter(adapter1);
		adapter2 = new Recyclerview2Adapter(mostRatedListmap);
		recyclerview2.setAdapter(adapter2);
		adapter3 = new Recyclerview3Adapter(popularListmap);
		recyclerview3.setAdapter(adapter3);
		adapter4 = new Recyclerview4Adapter(publisherListmap);
		recyclerview4.setAdapter(adapter4);
		if (!settings.contains("mainPublisher")) {
			settings.edit().putString("mainPublisher", "Yen Press").commit();
		}
		textview6.setText("Top ".concat(settings.getString("mainPublisher", "")));
		showShimmer("newest");
		showShimmer("slider");
		showShimmer("mostrated");
		showShimmer("publisher");
		searchField.setOnEditorActionListener((v, actionId, event) -> {
			if(actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)){
				if(TextUtils.isEmpty(searchField.getText().toString())){
					((EditText)searchField).setError("Field is required");
				}else{
					intent = new Intent(getContext().getApplicationContext(), FilterActivity.class);
					intent.putExtra("tag", "search");
					intent.putExtra("text", searchField.getText().toString());
					startActivity(intent);
					Animatoo.animateSlideUp(getContext());
				}
				return true;
			}
			
			return false;
		});
	}
	
	private void showShimmer(String tag){
		switch(tag) {
			case "slider": {
				sliderListmap.clear();
				popularListmap.clear();
				for(int _repeat49 = 0; _repeat49 < (int)(10); _repeat49++) {
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("a", "a");
						sliderListmap.add(_item);
					}
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("a", "a");
						popularListmap.add(_item);
					}
				}
				viewpager1.setAdapter(new Viewpager1Adapter(sliderListmap));
				adapter3.notifyDataSetChanged();
				_RequestApi("popular");
				break;
			}
			case "newest": {
				newestListmap.clear();
				for(int _repeat66 = 0; _repeat66 < (int)(10); _repeat66++) {
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("a", "a");
						newestListmap.add(_item);
					}
				}
				adapter1.notifyDataSetChanged();
				_RequestApi("latest");
				break;
			}
			case "mostrated": {
				mostRatedListmap.clear();
				for(int _repeat108 = 0; _repeat108 < (int)(10); _repeat108++) {
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("a", "a");
						mostRatedListmap.add(_item);
					}
				}
				adapter2.notifyDataSetChanged();
				_RequestApi("most-rated");
				break;
			}
			case "publisher": {
				publisherListmap.clear();
				for(int _repeat117 = 0; _repeat117 < (int)(10); _repeat117++) {
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("a", "a");
						publisherListmap.add(_item);
					}
				}
				adapter4.notifyDataSetChanged();
				_RequestApi("publisher");
				break;
			}
		}
	}
	
	private void startSlider(){
		runnable = new Runnable(){
			 @Override
			public void run(){
				if(counter <= 9){
					viewpager1.setCurrentItem((int)counter);
					counter++;
				}else{
					counter = 0;
					viewpager1.setCurrentItem((int)counter);
				}
				handler.postDelayed(this, 5000);
			}
		};
		handler.post(runnable);
	}
	
	private void showErrorLayout(LinearLayout main, View view, String tag, ArrayList<HashMap<String, Object>> listmap){
		view.setVisibility(View.GONE);
		listmap.clear();
		if(main.findViewWithTag(tag) != null){
			return;
		}
		LinearLayout layout = new LinearLayout(getContext());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 400);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setTag(tag);
		layout.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		LinearLayout.LayoutParams _lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		_lp.setMargins(8, 0, 8, 0);
		TextView text1 = new TextView(getContext());
		text1.setGravity(Gravity.CENTER);
		text1.setPadding(8, 10, 8, 8);
		text1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/opensansmedium.ttf"), 0);
		text1.setTextColor(getResources().getColor(R.color.colorTextPrimary));
		text1.setTextSize((int)12);
		text1.setText("Failed to access data from the server. Please check your internet connection and try again.");
		LinearLayout btn1 = new LinearLayout(getContext());
		LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		btn1.setGravity(Gravity.CENTER);
		btn1.setPadding(15, 8, 15, 8);
		TextView text2 = new TextView(getContext());
		text2.setGravity(Gravity.CENTER);
		text2.setPadding(0,0,0,0);
		text2.setText("Try Again");
		text2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/opensansmedium.ttf"), 0);
		text1.setTextColor(getResources().getColor(R.color.colorTextPrimary));
		text2.setTextSize((int)12);
		btn1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, getResources().getColor(R.color.colorAccent)));
		btn1.addView(text2);
		btn1.setLayoutParams(btnLp);
		text1.setLayoutParams(_lp);
		layout.addView(text1);
		layout.addView(btn1);
		layout.setLayoutParams(lp);
		main.addView(layout);
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				view.setVisibility(View.VISIBLE);
				main.removeView(layout);
				switch(tag) {
					case "latest": {
						isDataLoading = false;
						newestListmap.clear();
						showShimmer("newest");
						break;
					}
					case "most-rated": {
						isDataLoading = false;
						mostRatedListmap.clear();
						showShimmer("mostrated");
						break;
					}
					case "popular": {
						isDataLoading = false;
						sliderListmap.clear();
						popularListmap.clear();
						showShimmer("slider");
						break;
					}
					case "publisher": {
						isDataLoading = false;
						publisherListmap.clear();
						showShimmer("publisher");
						break;
					}
				}
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		handler.post(runnable);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		handler.removeCallbacks(runnable);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(runnable);
	}
	public void _RequestApi(final String _type) {
		if (!settings.contains("serverLink")) {
			settings.edit().putString("serverLink", "https://jellee.vercel.app/novel/jellee/").commit();
		}
		if (!_type.equals("publisher")) {
			requestNetwork.startRequestNetwork(RequestNetworkController.GET, settings.getString("serverLink", "").concat(_type.concat("?page=1&perPage=10")), _type, _requestNetwork_request_listener);
		} else {
			requestNetwork.startRequestNetwork(RequestNetworkController.GET, settings.getString("serverLink", "").concat(_type.concat("?query=".concat(settings.getString("mainPublisher", "").concat("&sortBy=rating&page=1&perPage=10")))), "publisher", _requestNetwork_request_listener);
		}
	}
	
	
	public void _GetNewestData(final String _data, final boolean _loading) {
		isDataLoading = _loading;
		try {
			newestListmap.clear();
			JSONObject jsonObject = new JSONObject(_data);
			JSONArray novels = jsonObject.getJSONArray("data");
			for (int i = 0; i < (int)(novels.length()); i++) {
				JSONObject item = novels.getJSONObject(i);
				HashMap<String, Object> data = new HashMap<>();
				data.put("id", item.getString("id"));
				data.put("title", item.getString("title"));
				data.put("cover", item.getString("cover"));
				data.put("rating", item.getString("rating"));
				data.put("status", item.getString("status"));
				data.put("type", item.getString("type"));
				data.put("translation", item.getString("translation"));
				data.put("synopsis", Html.fromHtml(item.getString("synopsis")));
				newestListmap.add(data);
			}
			isDataLoading = true;
			recyclerview1.post(()->{
				adapter1.notifyDataSetChanged();
			});
		} catch (Exception e) {
			isDataLoading = false;
			showErrorLayout(new_release_linear, recyclerview1, "latest", newestListmap);
		}
	}
	
	
	public void _GetPopularData(final String _data, final boolean _loading) {
		isDataLoading = _loading;
		try {
			sliderListmap.clear();
			popularListmap.clear();
			JSONObject jsonObject = new JSONObject(_data);
			JSONArray novels = jsonObject.getJSONArray("data");
			for (int i = 0; i < (int)(novels.length()); i++) {
				JSONObject item = novels.getJSONObject(i);
				HashMap<String, Object> data = new HashMap<>();
				data.put("id", item.getString("id"));
				data.put("title", item.getString("title"));
				data.put("cover", item.getString("cover"));
				data.put("rating", item.getString("rating"));
				data.put("status", item.getString("status"));
				data.put("type", item.getString("type"));
				data.put("translation", item.getString("translation"));
				data.put("synopsis", Html.fromHtml(item.getString("synopsis")));
				sliderListmap.add(data);
				popularListmap.add(data);
			}
			isDataLoading = true;
			viewpager1.setAdapter(new Viewpager1Adapter(sliderListmap));
			((PagerAdapter)viewpager1.getAdapter()).notifyDataSetChanged();
			startSlider();
			recyclerview3.post(()->{
				adapter3.notifyDataSetChanged();
			});
		} catch (Exception e) {
			isDataLoading = _loading;
			showErrorLayout(linear4, viewpager1, "popular", sliderListmap);
			showErrorLayout(popular_linear, recyclerview3, "popular", popularListmap);
		}
	}
	
	
	public void _GetMostRatedData(final String _data, final boolean _loading) {
		isDataLoading = _loading;
		try {
			mostRatedListmap.clear();
			JSONObject jsonObject = new JSONObject(_data);
			JSONArray novels = jsonObject.getJSONArray("data");
			for (int i = 0; i < (int)(novels.length()); i++) {
				JSONObject item = novels.getJSONObject(i);
				HashMap<String, Object> data = new HashMap<>();
				data.put("id", item.getString("id"));
				data.put("title", item.getString("title"));
				data.put("cover", item.getString("cover"));
				data.put("rating", item.getString("rating"));
				data.put("status", item.getString("status"));
				data.put("type", item.getString("type"));
				data.put("translation", item.getString("translation"));
				data.put("synopsis", Html.fromHtml(item.getString("synopsis")));
				mostRatedListmap.add(data);
			}
			isDataLoading = true;
			recyclerview2.post(()->{
				adapter2.notifyDataSetChanged();
			});
		} catch (Exception e) {
			isDataLoading = false;
			showErrorLayout(mostrated_linear, recyclerview2, "most-rated", mostRatedListmap);
		}
	}
	
	
	public void _GetPublisherData(final String _data, final boolean _loading) {
		isDataLoading = _loading;
		try {
			publisherListmap.clear();
			JSONObject jsonObject = new JSONObject(_data);
			JSONArray novels = jsonObject.getJSONArray("data");
			for (int i = 0; i < (int)(novels.length()); i++) {
				JSONObject item = novels.getJSONObject(i);
				HashMap<String, Object> data = new HashMap<>();
				data.put("id", item.getString("id"));
				data.put("title", item.getString("title"));
				data.put("cover", item.getString("cover"));
				data.put("rating", item.getString("rating"));
				data.put("status", item.getString("status"));
				data.put("type", item.getString("type"));
				data.put("translation", item.getString("translation"));
				data.put("synopsis", Html.fromHtml(item.getString("synopsis")));
				publisherListmap.add(data);
			}
			isDataLoading = true;
			recyclerview4.post(()->{
				adapter4.notifyDataSetChanged();
			});
		} catch (Exception e) {
			isDataLoading = false;
			showErrorLayout(publisher_linear, recyclerview4, "publisher", publisherListmap);
		}
	}
	
	public class Viewpager1Adapter extends PagerAdapter {
		
		Context _context;
		ArrayList<HashMap<String, Object>> _data;
		
		public Viewpager1Adapter(Context _ctx, ArrayList<HashMap<String, Object>> _arr) {
			_context = _ctx;
			_data = _arr;
		}
		
		public Viewpager1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_context = getContext().getApplicationContext();
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public boolean isViewFromObject(View _view, Object _object) {
			return _view == _object;
		}
		
		@Override
		public void destroyItem(ViewGroup _container, int _position, Object _object) {
			_container.removeView((View) _object);
		}
		
		@Override
		public int getItemPosition(Object _object) {
			return super.getItemPosition(_object);
		}
		
		@Override
		public CharSequence getPageTitle(int pos) {
			// Use the Activity Event (onTabLayoutNewTabAdded) in order to use this method
			return "page " + String.valueOf(pos);
		}
		
		@Override
		public Object instantiateItem(ViewGroup _container,  final int _position) {
			View _view = LayoutInflater.from(_context).inflate(R.layout.slider, _container, false);
			
			final FrameLayout linear1 = _view.findViewById(R.id.linear1);
			final com.facebook.shimmer.ShimmerFrameLayout shimmerLayout = _view.findViewById(R.id.shimmerLayout);
			final ImageView imageBanner = _view.findViewById(R.id.imageBanner);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final ImageView imageCover = _view.findViewById(R.id.imageCover);
			final TextView textTitle = _view.findViewById(R.id.textTitle);
			final TextView textSynopsis = _view.findViewById(R.id.textSynopsis);
			final LinearLayout linear_btn = _view.findViewById(R.id.linear_btn);
			final ImageView imageview3 = _view.findViewById(R.id.imageview3);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			
			if (isDataLoading) {
				linear_btn.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, getResources().getColor(R.color.colorControlHighlight)));
				linear1.setVisibility(View.VISIBLE);
				shimmerLayout.setVisibility(View.GONE);
				textTitle.setText(_data.get((int)_position).get("title").toString());
				textSynopsis.setText(_data.get((int)_position).get("synopsis").toString());
				Glide.with(getContext()).load(_data.get((int)_position).get("cover").toString()).placeholder(R.drawable.load_image).error(R.drawable.no_image).into(imageBanner);
				Glide.with(getContext()).load(_data.get((int)_position).get("cover").toString()).placeholder(R.drawable.load_image).error(R.drawable.no_image).into(imageCover);
				linear_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						intent = new Intent(getContext().getApplicationContext(), PageActivity.class);
						intent.putExtra("id", _data.get((int)_position).get("id").toString());
						intent.putExtra("title", _data.get((int)_position).get("title").toString());
						intent.putExtra("cover", _data.get((int)_position).get("cover").toString());
						startActivity(intent);
						Animatoo.animateSlideUp(getContext());
					}
				});
			} else {
				linear1.setVisibility(View.GONE);
				shimmerLayout.setVisibility(View.VISIBLE);
			}
			
			_container.addView(_view);
			return _view;
		}
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.items, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final com.facebook.shimmer.ShimmerFrameLayout shimmer = _view.findViewById(R.id.shimmer);
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final TextView textTitle = _view.findViewById(R.id.textTitle);
			final FrameLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView imageCover = _view.findViewById(R.id.imageCover);
			final FrameLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView textScore = _view.findViewById(R.id.textScore);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView textTranslator = _view.findViewById(R.id.textTranslator);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			if (isDataLoading) {
				linear1.setVisibility(View.VISIBLE);
				shimmer.setVisibility(View.GONE);
				textScore.setTextSize((int)8);
				textTranslator.setTextSize((int)8);
				stringRatings = _data.get((int)_position).get("rating").toString();
				textScore.setText(stringRatings.equals("0") ? "??" : stringRatings.concat("%"));
				textTranslator.setText(_data.get((int)_position).get("translation").toString());
				textTitle.setText(_data.get((int)_position).get("title").toString());
				Glide.with(getContext()).load(_data.get((int)_position).get("cover").toString()).placeholder(R.drawable.load_image).error(R.drawable.no_image).into(imageCover);
				linear1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						intent = new Intent(getContext().getApplicationContext(), PageActivity.class);
						intent.putExtra("id", _data.get((int)_position).get("id").toString());
						intent.putExtra("title", _data.get((int)_position).get("title").toString());
						intent.putExtra("cover", _data.get((int)_position).get("cover").toString());
						startActivity(intent);
						Animatoo.animateInAndOut(getContext());
					}
				});
			} else {
				linear1.setVisibility(View.GONE);
				shimmer.setVisibility(View.VISIBLE);
				linear6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, 0xFFE0E0E0));
			}
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class Recyclerview2Adapter extends RecyclerView.Adapter<Recyclerview2Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.items, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final com.facebook.shimmer.ShimmerFrameLayout shimmer = _view.findViewById(R.id.shimmer);
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final TextView textTitle = _view.findViewById(R.id.textTitle);
			final FrameLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView imageCover = _view.findViewById(R.id.imageCover);
			final FrameLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView textScore = _view.findViewById(R.id.textScore);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView textTranslator = _view.findViewById(R.id.textTranslator);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			if (isDataLoading) {
				linear1.setVisibility(View.VISIBLE);
				shimmer.setVisibility(View.GONE);
				textScore.setTextSize((int)8);
				textTranslator.setTextSize((int)8);
				stringRatings = _data.get((int)_position).get("rating").toString();
				textScore.setText(stringRatings.equals("0") ? "??" : stringRatings.concat("%"));
				textTranslator.setText(_data.get((int)_position).get("translation").toString());
				textTitle.setText(_data.get((int)_position).get("title").toString());
				Glide.with(getContext()).load(_data.get((int)_position).get("cover").toString()).placeholder(R.drawable.load_image).error(R.drawable.no_image).into(imageCover);
				linear1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						intent = new Intent(getContext().getApplicationContext(), PageActivity.class);
						intent.putExtra("id", _data.get((int)_position).get("id").toString());
						intent.putExtra("title", _data.get((int)_position).get("title").toString());
						intent.putExtra("cover", _data.get((int)_position).get("cover").toString());
						startActivity(intent);
						Animatoo.animateInAndOut(getContext());
					}
				});
			} else {
				linear1.setVisibility(View.GONE);
				shimmer.setVisibility(View.VISIBLE);
				linear6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, 0xFFE0E0E0));
			}
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class Recyclerview3Adapter extends RecyclerView.Adapter<Recyclerview3Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview3Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.items, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final com.facebook.shimmer.ShimmerFrameLayout shimmer = _view.findViewById(R.id.shimmer);
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final TextView textTitle = _view.findViewById(R.id.textTitle);
			final FrameLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView imageCover = _view.findViewById(R.id.imageCover);
			final FrameLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView textScore = _view.findViewById(R.id.textScore);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView textTranslator = _view.findViewById(R.id.textTranslator);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			if (isDataLoading) {
				linear1.setVisibility(View.VISIBLE);
				shimmer.setVisibility(View.GONE);
				textScore.setTextSize((int)8);
				textTranslator.setTextSize((int)8);
				stringRatings = _data.get((int)_position).get("rating").toString();
				textScore.setText(stringRatings.equals("0") ? "??" : stringRatings.concat("%"));
				textTranslator.setText(_data.get((int)_position).get("translation").toString());
				textTitle.setText(_data.get((int)_position).get("title").toString());
				Glide.with(getContext()).load(_data.get((int)_position).get("cover").toString()).placeholder(R.drawable.load_image).error(R.drawable.no_image).into(imageCover);
				linear1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						intent = new Intent(getContext().getApplicationContext(), PageActivity.class);
						intent.putExtra("id", _data.get((int)_position).get("id").toString());
						intent.putExtra("title", _data.get((int)_position).get("title").toString());
						intent.putExtra("cover", _data.get((int)_position).get("cover").toString());
						startActivity(intent);
						Animatoo.animateInAndOut(getContext());
					}
				});
			} else {
				linear1.setVisibility(View.GONE);
				shimmer.setVisibility(View.VISIBLE);
				linear6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, 0xFFE0E0E0));
			}
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
	
	public class Recyclerview4Adapter extends RecyclerView.Adapter<Recyclerview4Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview4Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.items, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final com.facebook.shimmer.ShimmerFrameLayout shimmer = _view.findViewById(R.id.shimmer);
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final TextView textTitle = _view.findViewById(R.id.textTitle);
			final FrameLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView imageCover = _view.findViewById(R.id.imageCover);
			final FrameLayout linear3 = _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView textScore = _view.findViewById(R.id.textScore);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final TextView textTranslator = _view.findViewById(R.id.textTranslator);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			if (isDataLoading) {
				linear1.setVisibility(View.VISIBLE);
				shimmer.setVisibility(View.GONE);
				textScore.setTextSize((int)8);
				textTranslator.setTextSize((int)8);
				stringRatings = _data.get((int)_position).get("rating").toString();
				textScore.setText(stringRatings.equals("0") ? "??" : stringRatings.concat("%"));
				textTranslator.setText(_data.get((int)_position).get("translation").toString());
				textTitle.setText(_data.get((int)_position).get("title").toString());
				Glide.with(getContext()).load(_data.get((int)_position).get("cover").toString()).placeholder(R.drawable.load_image).error(R.drawable.no_image).into(imageCover);
				linear1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						intent = new Intent(getContext().getApplicationContext(), PageActivity.class);
						intent.putExtra("id", _data.get((int)_position).get("id").toString());
						intent.putExtra("title", _data.get((int)_position).get("title").toString());
						intent.putExtra("cover", _data.get((int)_position).get("cover").toString());
						startActivity(intent);
						Animatoo.animateInAndOut(getContext());
					}
				});
			} else {
				linear1.setVisibility(View.GONE);
				shimmer.setVisibility(View.VISIBLE);
				linear6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, 0xFFE0E0E0));
			}
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
			}
		}
	}
}