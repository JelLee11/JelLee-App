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
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.blogspot.atifsoftwares.animatoolib.*;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.*;
import com.google.android.flexbox.*;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.*;
import com.google.android.material.appbar.AppBarLayout;
import com.unity3d.ads.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.jetbrains.kotlin.*;
import org.json.*;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class FilterActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private String tags = "";
	private TextView selectedView = null;
	private FlexboxItemManager flexManager;
	private MultiSelectFlexboxManager genreFlexManager;
	private String query = "";
	private String filterType = "";
	private String filterGenres = "";
	private String host = "";
	private Recyclerview1Adapter adapter;
	private BottomSheetDialog bottomSheetDialog;
	private View bottomSheetView;
	private boolean isLoading = false;
	private double page = 0;
	private double perPage = 0;
	
	private ArrayList<HashMap<String, Object>> labelFlexBox = new ArrayList<>();
	private ArrayList<String> labelStrings = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> dataListmap = new ArrayList<>();
	
	private SwipeRefreshLayout swiperefreshlayout1;
	private NestedScrollView vscroll1;
	private LinearLayout linear_main;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private RecyclerView recyclerview1;
	private ProgressBar progressbar1;
	private LinearLayout linear_error;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear3;
	private ImageView imageview1;
	private EditText searchField;
	private ImageView imageview2;
	private FlexboxLayout flexBox;
	private ImageView imageview3;
	private TextView textview3;
	private TextView textview_error;
	
	private RequestNetwork requestData;
	private RequestNetwork.RequestListener _requestData_request_listener;
	private Intent intent = new Intent();
	private SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.filter);
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
		swiperefreshlayout1 = findViewById(R.id.swiperefreshlayout1);
		vscroll1 = findViewById(R.id.vscroll1);
		linear_main = findViewById(R.id.linear_main);
		linear2 = findViewById(R.id.linear2);
		linear4 = findViewById(R.id.linear4);
		recyclerview1 = findViewById(R.id.recyclerview1);
		progressbar1 = findViewById(R.id.progressbar1);
		linear_error = findViewById(R.id.linear_error);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		linear3 = findViewById(R.id.linear3);
		imageview1 = findViewById(R.id.imageview1);
		searchField = findViewById(R.id.searchField);
		imageview2 = findViewById(R.id.imageview2);
		flexBox = findViewById(R.id.flexBox);
		imageview3 = findViewById(R.id.imageview3);
		textview3 = findViewById(R.id.textview3);
		textview_error = findViewById(R.id.textview_error);
		requestData = new RequestNetwork(this);
		settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);
		
		swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swiperefreshlayout1.setRefreshing(false);
				dataListmap.clear();
				linear_error.setVisibility(View.GONE);
				recyclerview1.setVisibility(View.GONE);
				progressbar1.setVisibility(View.VISIBLE);
				page = 1;
				_getQueryData(query, tags, page, perPage);
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				bottomSheetDialog.show();
			}
		});
		
		_requestData_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					JSONObject jsonObject = new JSONObject(_response);
					JSONArray novels = jsonObject.getJSONArray("data");
					if (!jsonObject.has("message")) {
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
							dataListmap.add(data);
						}
						adapter.notifyDataSetChanged();
						isLoading = false;
						progressbar1.setVisibility(View.GONE);
						linear_error.setVisibility(View.GONE);
						recyclerview1.setVisibility(View.VISIBLE);
						swiperefreshlayout1.setRefreshing(false);
					} else {
						if (jsonObject.getString("message").equals("No more data available!")) {
							isLoading = false;
							swiperefreshlayout1.setRefreshing(false);
							linear_error.setVisibility(View.GONE);
							recyclerview1.setVisibility(View.VISIBLE);
							progressbar1.setVisibility(View.GONE);
							SketchwareUtil.showMessage(getApplicationContext(), jsonObject.getString("message"));
						} else {
							dataListmap.clear();
							isLoading = false;
							swiperefreshlayout1.setRefreshing(false);
							linear_error.setVisibility(View.VISIBLE);
							recyclerview1.setVisibility(View.GONE);
							progressbar1.setVisibility(View.GONE);
							textview_error.setText(jsonObject.getString("message"));
						}
					}
				} catch (Exception e) {
					dataListmap.clear();
					isLoading = false;
					swiperefreshlayout1.setRefreshing(false);
					linear_error.setVisibility(View.VISIBLE);
					recyclerview1.setVisibility(View.GONE);
					progressbar1.setVisibility(View.GONE);
					textview_error.setText(e.getMessage());
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				dataListmap.clear();
				isLoading = false;
				swiperefreshlayout1.setRefreshing(false);
				linear_error.setVisibility(View.VISIBLE);
				recyclerview1.setVisibility(View.GONE);
				progressbar1.setVisibility(View.GONE);
				textview_error.setText("No internet connection. Please try again!");
			}
		};
	}
	
	private void initializeLogic() {
		setTitle("");
		_toolbar.setBackgroundColor(getResources().getColor(R.color.colorBackgroundPrimary));
		getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackgroundPrimary));
		recyclerview1.setLayoutManager(new GridLayoutManager(getApplicationContext(), (int) 3));
		adapter = new Recyclerview1Adapter(dataListmap);
		recyclerview1.setAdapter(adapter);
		linear3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)25, (int)2, 0xFF263238, getResources().getColor(R.color.colorBackgroundVariant)));
		progressbar1.setVisibility(View.VISIBLE);
		linear_error.setVisibility(View.GONE);
		page = 1;
		perPage = 10;
		searchField.setText("");
		query = getIntent().getStringExtra("tag");
		if (query.equals("publisher")) {
			tags = "publisher";
			query = "?query=" + getIntent().getStringExtra("text");
			_getQueryData(query, tags, page, perPage);
			flexboxForLabels(getIntent().getStringExtra("text"));
		}
		else
		if (query.equals("search")) {
			searchField.setText(getIntent().getStringExtra("text"));
			tags = "search";
			query = "?query=" + getIntent().getStringExtra("text");
			_getQueryData(query, tags, page, perPage);
			flexboxForLabels(getIntent().getStringExtra("text"));
		} else {
			tags = "filter";
			flexboxForLabels(query);
			query = "?type=" + query;
			_getQueryData(query, tags, page, perPage);
		}
		callBottomSheet();
		searchField.setOnEditorActionListener((v, actionId, event) -> {
			if(actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)){
				if(TextUtils.isEmpty(searchField.getText().toString())){
					((EditText)searchField).setError("Field is required");
				}else{
					dataListmap.clear();
					linear_error.setVisibility(View.GONE);
					recyclerview1.setVisibility(View.GONE);
					progressbar1.setVisibility(View.VISIBLE);
					tags = "search";
					query = "?query=" + searchField.getText().toString();
					page = 1;
					_getQueryData(query, tags, page, perPage);
					flexboxForLabels(searchField.getText().toString());
				}
				return true;
			}
			
			return false;
		});
		vscroll1.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)(view, scrollX, scrollY, oldScrollX, oldScrollY) -> {
			if(dataListmap.size() <= 0){
			}else if (dataListmap.size() >= 10 && scrollY == view.getChildAt(0).getMeasuredHeight() - view.getMeasuredHeight()&& !isLoading) {
				progressbar1.setVisibility(View.VISIBLE);
				recyclerview1.setVisibility(View.VISIBLE);
				linear_error.setVisibility(View.GONE);
				isLoading = true;
				page = page + 1;
				_getQueryData(query, tags, page, perPage);
			}
		});
	}
	
	private void callBottomSheet(){
		bottomSheetDialog = new BottomSheetDialog(FilterActivity.this);
		bottomSheetView = getLayoutInflater().inflate(R.layout.filter_sheet,null );
		bottomSheetDialog.setContentView(bottomSheetView);
		bottomSheetDialog.setCancelable(true);
		ImageView closeSheetBtn = bottomSheetView.findViewById(R.id.closeSheetBtn);
		FlexboxLayout flexType = bottomSheetView.findViewById(R.id.flexType);
		FlexboxLayout flexGenres = bottomSheetView.findViewById(R.id.flexGenres);
		LinearLayout linearSubmit = bottomSheetView.findViewById(R.id.linear_submit);
		linearSubmit.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, getResources().getColor(R.color.colorAccent)));
		ArrayList<HashMap<String, Object>> typeFlexList = typeFlexListmap();
		flexManager = new FlexboxItemManager(FilterActivity.this, flexType);
		flexManager.setOnItemClickListener((position, text) ->{
			filterType = text.toLowerCase();
		});
		flexManager.populateFlexbox(typeFlexList, tags);
		ArrayList<HashMap<String, Object>> genresList = genreFlexListMap();
		genreFlexManager = new MultiSelectFlexboxManager(FilterActivity.this, flexGenres);
		genreFlexManager.populateFlexbox(genresList, "genre");
		linearSubmit.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
				page = 1;
				filterGenres = genreFlexManager.getSelectedValuesAsString();
				dataListmap.clear();
				if (filterType.equals("") && filterGenres.equals("")) {
					String labels = "all";
					flexboxForLabels(labels);
					query = "?type=all";
					tags = "filter";
					_getQueryData(query, tags, page, perPage);
				}
				else
				if (!filterType.equals("") && filterGenres.equals("")) {
					String labels = filterType;
					flexboxForLabels(labels);
					query = "?type=" + filterType;
					tags = "filter";
					_getQueryData(query, tags, page, perPage);
				}
				else
				if (filterType.equals("") && !filterGenres.equals("")) {
					String labels = filterGenres;
					flexboxForLabels(labels);
					query = "?genre=" + filterGenres;
					tags = "filter";
					_getQueryData(query, tags, page, perPage);
				}
				else
				if (!filterType.equals("") && !filterGenres.equals("")) {
					String labels = filterType+ ","+filterGenres;
					flexboxForLabels(labels);
					query = "?type=" + filterType + "&genre=" + filterGenres;
					tags = "filter";
					_getQueryData(query, tags, page, perPage);
				}
				linear_error.setVisibility(View.GONE);
				recyclerview1.setVisibility(View.VISIBLE);
				progressbar1.setVisibility(View.VISIBLE);
				searchField.setText("");
				swiperefreshlayout1.setRefreshing(false);
				bottomSheetDialog.dismiss();
			}
		});
		closeSheetBtn.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
				bottomSheetDialog.dismiss();
			}
		});
	}
	
	private void flexboxForLabels(String tag){
		labelFlexBox.clear();
		flexBox.removeAllViews();
		if (tag.equals("")) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("label", "All");
				labelFlexBox.add(_item);
			}
			loadFlexLabel(labelFlexBox);
		} else {
			String text = tag.substring(0, 1).toUpperCase() + tag.substring(1);
			if (text.contains(",")) {
				labelStrings = new ArrayList<String>(Arrays.asList(text.split(",")));
				for (int i = 0; i < (int)(labelStrings.size()); i++) {
					String t = labelStrings.get((int)(i));
					String s = t.substring(0, 1).toUpperCase() + t.substring(1);
					{
						HashMap<String, Object> _item = new HashMap<>();
						_item.put("label", s);
						labelFlexBox.add(_item);
					}
				}
				loadFlexLabel(labelFlexBox);
			} else {
				{
					HashMap<String, Object> _item = new HashMap<>();
					_item.put("label", text);
					labelFlexBox.add(_item);
				}
				loadFlexLabel(labelFlexBox);
			}
		}
	}
	
	private void loadFlexLabel(ArrayList<HashMap<String, Object>> listmap){
		for (int j = 0; j < (int)(listmap.size()); j++) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.setMargins(5, 5, 5, 5);
			TextView flexText = new TextView(getApplicationContext());
			flexText.setPadding(8, 5, 8, 5);
			flexText.setGravity(Gravity.CENTER);
			flexText.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, getResources().getColor(R.color.colorAccent)));
			flexText.setTextColor(getResources().getColor(R.color.colorTextPrimary));
			flexText.setTextSize((int)10);
			flexText.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/opensansmedium.ttf"), 0);
			flexText.setText(listmap.get((int)j).get("label").toString());
			flexText.setLayoutParams(lp);
			flexBox.addView(flexText);
		}
	}
	
	private ArrayList<HashMap<String, Object>> typeFlexListmap(){
		ArrayList<HashMap<String, Object>> typeFlexList = new ArrayList<>();
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("typeFlex", "All");
			typeFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("typeFlex", "Latest");
			typeFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("typeFlex", "Popularity");
			typeFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("typeFlex", "Ratings");
			typeFlexList.add(_item);
		}
		return typeFlexList;
	}
	
	private ArrayList<HashMap<String, Object>> genreFlexListMap(){
		ArrayList<HashMap<String, Object>> genresFlexList = new ArrayList<>();
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Action");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Adult");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Adventure");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Comedy");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Drama");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Ecchi");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Fantasy");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Gender Bender");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Harem");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Historical");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Horror");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Josei");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Martial Arts");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Mature");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Mecha");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Mystery");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Psychological");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Romance");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "School Life");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Sci-Fi");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Seinen");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Shoujo");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Shoujo Ai");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Shounen");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Shounen Ai");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Slice of Life");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Smut");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Sports");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Supernatural");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Tragedy");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Wuxia");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Xianxia");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Xuanhuan");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Yaoi");
			genresFlexList.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("genre", "Yuri");
			genresFlexList.add(_item);
		}
		return genresFlexList;
	}
	
	
	@Override
	public void onBackPressed() {
		finish();
		Animatoo.animateSlideDown(FilterActivity.this);
	}
	public void _getQueryData(final String _type, final String _tag, final double _page, final double _perPage) {
		switch(_tag) {
			case "filter": {
				host = settings.getString("serverLink", "") + "filter" + _type + "&page=" + String.valueOf((long)(_page)) + "&perPage=" + String.valueOf((long)(_perPage));
				requestData.startRequestNetwork(RequestNetworkController.GET, host, "a", _requestData_request_listener);
				break;
			}
			case "publisher": {
				host = settings.getString("serverLink", "") + "publisher" + _type + "&page=" + String.valueOf((long)(_page)) + "&perPage=" + String.valueOf((long)(_perPage));
				requestData.startRequestNetwork(RequestNetworkController.GET, host, "a", _requestData_request_listener);
				break;
			}
			case "search": {
				host = settings.getString("serverLink", "") + "search" + _type + "&page=" + String.valueOf((long)(_page)) + "&perPage=" + String.valueOf((long)(_perPage));
				requestData.startRequestNetwork(RequestNetworkController.GET, host, "a", _requestData_request_listener);
				break;
			}
		}
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.filter_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final TextView textTitle = _view.findViewById(R.id.textTitle);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			
			textTitle.setText(_data.get((int)_position).get("title").toString());
			Glide.with(getApplicationContext()).load(_data.get((int)_position).get("cover").toString()).placeholder(R.drawable.load_image).error(R.drawable.no_image).into(imageview1);
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent = new Intent(getApplicationContext(), PageActivity.class);
					intent.putExtra("id", _data.get((int)_position).get("id").toString());
					intent.putExtra("title", _data.get((int)_position).get("title").toString());
					intent.putExtra("cover", _data.get((int)_position).get("cover").toString());
					startActivity(intent);
					Animatoo.animateInAndOut(FilterActivity.this);
				}
			});
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