package com.jellee.JelLee;

import android.Manifest;
import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import org.jetbrains.kotlin.*;
import org.json.*;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.animation.ValueAnimator;
import android.animation.ArgbEvaluator;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.ColorDrawable;
import java.net.URL;
import java.net.HttpURLConnection;
import android.net.Uri;
import androidx.core.content.FileProvider;
import android.os.Environment;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;
import com.unity3d.ads.UnityAds;

public class PageActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private SwipeRefreshLayout swipeRefreshLayout;
	private Recyclerview1Adapter adapter1;
	private boolean isDataLoaded = false;
	private Recyclerview2Adapter adapter2;
	private String volumeNumber = "";
	private String novelTitle = "";
	private String fileName = "";
	private String folderName = "";
	private String finalFileName = "";
	private String epubLink = "";
	private String pdfLink = "";
	private boolean isEpubExists = false;
	private boolean isPdfExists = false;
	private static final String directLink = "https://drive.google.com/uc?export=download&id=";
	private boolean isReverse = false;
	private String downloadDirPath = "";
	private String historyFilePath = "";
	private String timeNow = "";
	private String stringRatings = "";
	private String stringPopularity = "";
	private String libraryFilePath = "";
	private String stringPublisher = "";
	private boolean pageLoaded = false;
	private String stringProvider = "";
	
	private ArrayList<HashMap<String, Object>> flexListmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> recommendedListmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> volumeListmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> downloadHistoryListmap = new ArrayList<>();
	
	private NestedScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear_main;
	private FrameLayout framelayout1;
	private LinearLayout linear14;
	private LinearLayout linear20;
	private ImageView imageviewBanner;
	private FrameLayout linear12;
	private LinearLayout linear13;
	private CardView cardview2;
	private TextView textviewTitle;
	private ImageView imageviewCover;
	private LinearLayout addToFavButton;
	private LinearLayout linear16;
	private LinearLayout linear17;
	private ImageView addToFavIcon;
	private TextView addToFabText;
	private LinearLayout detailsButton;
	private LinearLayout volumesButton;
	private LinearLayout linear18;
	private TextView textview5;
	private LinearLayout linear19;
	private TextView textview6;
	private ProgressBar progressbar1;
	private LinearLayout linear21;
	private LinearLayout linear28;
	private LinearLayout linear_error;
	private FlexboxLayout flexBox;
	private LinearLayout linear22;
	private LinearLayout linear23;
	private LinearLayout linear24;
	private LinearLayout linear25;
	private LinearLayout linear26;
	private LinearLayout linear27;
	private RelativeLayout bannerLayout;
	private LinearLayout recommendation_linear;
	private TextView textview7;
	private TextView textview_status;
	private TextView textview9;
	private TextView textview_type;
	private TextView textview11;
	private TextView textview_publisher;
	private TextView textview13;
	private TextView textview_ratings;
	private TextView textview15;
	private TextView textview_popularity;
	private TextView textview17;
	private TextView textview_synopsis;
	private LinearLayout linear6;
	private RecyclerView recyclerview1;
	private TextView textview3;
	private LinearLayout linear29;
	private RelativeLayout bannerLayout2;
	private RecyclerView recyclerview2;
	private TextView textview18;
	private TextView textview_totalVolumes;
	private ImageView sortBtn;
	private TextView textview1;
	private TextView textview_error;
	
	private RequestNetwork requestData;
	private RequestNetwork.RequestListener _requestData_request_listener;
	private RequestNetwork requestRecommendations;
	private RequestNetwork.RequestListener _requestRecommendations_request_listener;
	private Intent intent = new Intent();
	private ObjectAnimator animationView = new ObjectAnimator();
	private Calendar calendar = Calendar.getInstance();
	private SharedPreferences settings;
	private ProgressDialog loading;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.page);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
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
		linear_main = findViewById(R.id.linear_main);
		framelayout1 = findViewById(R.id.framelayout1);
		linear14 = findViewById(R.id.linear14);
		linear20 = findViewById(R.id.linear20);
		imageviewBanner = findViewById(R.id.imageviewBanner);
		linear12 = findViewById(R.id.linear12);
		linear13 = findViewById(R.id.linear13);
		cardview2 = findViewById(R.id.cardview2);
		textviewTitle = findViewById(R.id.textviewTitle);
		imageviewCover = findViewById(R.id.imageviewCover);
		addToFavButton = findViewById(R.id.addToFavButton);
		linear16 = findViewById(R.id.linear16);
		linear17 = findViewById(R.id.linear17);
		addToFavIcon = findViewById(R.id.addToFavIcon);
		addToFabText = findViewById(R.id.addToFabText);
		detailsButton = findViewById(R.id.detailsButton);
		volumesButton = findViewById(R.id.volumesButton);
		linear18 = findViewById(R.id.linear18);
		textview5 = findViewById(R.id.textview5);
		linear19 = findViewById(R.id.linear19);
		textview6 = findViewById(R.id.textview6);
		progressbar1 = findViewById(R.id.progressbar1);
		linear21 = findViewById(R.id.linear21);
		linear28 = findViewById(R.id.linear28);
		linear_error = findViewById(R.id.linear_error);
		flexBox = findViewById(R.id.flexBox);
		linear22 = findViewById(R.id.linear22);
		linear23 = findViewById(R.id.linear23);
		linear24 = findViewById(R.id.linear24);
		linear25 = findViewById(R.id.linear25);
		linear26 = findViewById(R.id.linear26);
		linear27 = findViewById(R.id.linear27);
		bannerLayout = findViewById(R.id.bannerLayout);
		recommendation_linear = findViewById(R.id.recommendation_linear);
		textview7 = findViewById(R.id.textview7);
		textview_status = findViewById(R.id.textview_status);
		textview9 = findViewById(R.id.textview9);
		textview_type = findViewById(R.id.textview_type);
		textview11 = findViewById(R.id.textview11);
		textview_publisher = findViewById(R.id.textview_publisher);
		textview13 = findViewById(R.id.textview13);
		textview_ratings = findViewById(R.id.textview_ratings);
		textview15 = findViewById(R.id.textview15);
		textview_popularity = findViewById(R.id.textview_popularity);
		textview17 = findViewById(R.id.textview17);
		textview_synopsis = findViewById(R.id.textview_synopsis);
		linear6 = findViewById(R.id.linear6);
		recyclerview1 = findViewById(R.id.recyclerview1);
		textview3 = findViewById(R.id.textview3);
		linear29 = findViewById(R.id.linear29);
		bannerLayout2 = findViewById(R.id.bannerLayout2);
		recyclerview2 = findViewById(R.id.recyclerview2);
		textview18 = findViewById(R.id.textview18);
		textview_totalVolumes = findViewById(R.id.textview_totalVolumes);
		sortBtn = findViewById(R.id.sortBtn);
		textview1 = findViewById(R.id.textview1);
		textview_error = findViewById(R.id.textview_error);
		requestData = new RequestNetwork(this);
		requestRecommendations = new RequestNetwork(this);
		settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);
		
		addToFavButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (pageLoaded) {
					if (_checkIfExistInLibrary(getIntent().getStringExtra("id"))) {
						_RemoveToLibrary(getIntent().getStringExtra("id"));
					} else {
						_AddToLibrary(getIntent().getStringExtra("id"), getIntent().getStringExtra("title"), getIntent().getStringExtra("cover"), stringRatings, stringPopularity, stringPublisher);
					}
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "Data is not yet fully loaded!");
				}
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
		
		sortBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isReverse) {
					sortBtn.setImageResource(R.drawable.icon_sort_down);
					Collections.reverse(volumeListmap);
					adapter2.notifyDataSetChanged();
					isReverse = false;
				} else {
					sortBtn.setImageResource(R.drawable.icon_sort_up);
					Collections.reverse(volumeListmap);
					adapter2.notifyDataSetChanged();
					isReverse = true;
				}
			}
		});
		
		_requestData_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					flexListmap.clear();
					flexBox.removeAllViews();
					JSONObject jsonObject = new JSONObject(_response);
					JSONObject data = jsonObject.getJSONObject("data");
					stringRatings = data.getJSONObject("anilist").getString("rating");
					stringPopularity = data.getJSONObject("anilist").getString("popularity");
					textview_ratings.setText(stringRatings.equals("0") ? "??" : stringRatings.concat("%"));
					textview_popularity.setText(stringPopularity.equals("0") ? "??" : stringPopularity);
					textview_status.setText(data.getString("status"));
					textview_type.setText(data.getString("type"));
					textview_synopsis.setText(Html.fromHtml(data.getString("synopsis")));
					stringPublisher = data.getString("translation");
					textview3.setText("Top ".concat(stringPublisher));
					textview_publisher.setText(data.getString("translation"));
					JSONArray genres = data.getJSONArray("genres");
					for (int i = 0; i < (int)(genres.length()); i++) {
						{
							HashMap<String, Object> _item = new HashMap<>();
							_item.put("flexItem", genres.getString(i));
							flexListmap.add(_item);
						}
					}
					for (int j = 0; j < (int)(flexListmap.size()); j++) {
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
						lp.setMargins(8, 8, 8, 8);
						TextView flexText = new TextView(getApplicationContext());
						flexText.setPadding(15, 10, 15, 10);
						flexText.setGravity(Gravity.CENTER);
						flexText.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)10, (int)2, getResources().getColor(R.color.colorAccent), Color.TRANSPARENT));
						flexText.setTextSize((int)16);
						flexText.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/opensansmedium.ttf"), 0);
						flexText.setTextColor(getResources().getColor(R.color.colorAccent));
						flexText.setText(flexListmap.get((int)j).get("flexItem").toString());
						flexText.setLayoutParams(lp);
						flexBox.addView(flexText);
					}
					JSONArray volumeList = data.getJSONArray("volumes");
					if (volumeList.length() == 0) {
						textview_totalVolumes.setText("0");
					} else {
						for (int i = 0; i < (int)(volumeList.length()); i++) {
							JSONObject item = volumeList.getJSONObject(i);
							HashMap<String, Object> map = new HashMap<>();
							map.put("number", item.getString("number"));
							map.put("epub", item.getString("epub"));
							map.put("pdf", item.optString("pdf", ""));
							volumeListmap.add(map);
						}
						textview_totalVolumes.setText(String.valueOf((long)(volumeListmap.size())));
						recyclerview2.post(()->{
							adapter2.notifyDataSetChanged();
						});
					}
					linear21.setVisibility(View.VISIBLE);
					linear28.setVisibility(View.GONE);
					linear_error.setVisibility(View.GONE);
					progressbar1.setVisibility(View.GONE);
					swipeRefreshLayout.setRefreshing(false);
					pageLoaded = true;
				} catch (Exception e) {
					pageLoaded = false;
					textview_error.setText("Something went wrong! Please try again!");
					linear21.setVisibility(View.GONE);
					linear28.setVisibility(View.GONE);
					linear_error.setVisibility(View.VISIBLE);
					progressbar1.setVisibility(View.GONE);
					swipeRefreshLayout.setRefreshing(false);
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				textview_error.setText("No internet connection!");
				linear21.setVisibility(View.GONE);
				linear_error.setVisibility(View.VISIBLE);
				progressbar1.setVisibility(View.GONE);
				swipeRefreshLayout.setRefreshing(false);
				pageLoaded = false;
			}
		};
		
		_requestRecommendations_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					recommendedListmap.clear();
					JSONObject jsonObject = new JSONObject(_response);
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
						recommendedListmap.add(data);
					}
					isDataLoaded = true;
					recommendation_linear.setVisibility(View.VISIBLE);
					adapter1.notifyDataSetChanged();
				} catch (Exception e) {
					isDataLoaded = false;
					recommendation_linear.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				isDataLoaded = false;
				recommendation_linear.setVisibility(View.GONE);
			}
		};
	}
	
	private void initializeLogic() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		_app_bar.bringToFront();
		setTitle("");
		_UnityAds("5737747", true);
		swipeRefreshLayout.setRefreshing(false);
		swipeRefreshLayout.setProgressViewOffset(true, 0, 200);
		progressbar1.setVisibility(View.VISIBLE);
		linear21.setVisibility(View.GONE);
		linear_error.setVisibility(View.GONE);
		linear28.setVisibility(View.GONE);
		bannerLayout.setVisibility(View.GONE);
		bannerLayout2.setVisibility(View.GONE);
		recyclerview1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
		adapter1 = new Recyclerview1Adapter(recommendedListmap);
		recyclerview1.setAdapter(adapter1);
		recyclerview2.setLayoutManager(new LinearLayoutManager(this));
		adapter2 = new Recyclerview2Adapter(volumeListmap);
		recyclerview2.setAdapter(adapter2);
		if (_checkIfExistInLibrary(getIntent().getStringExtra("id"))) {
			changeAddToFavButton(true);
		} else {
			changeAddToFavButton(false);
		}
		colorApply();
		vscroll1.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
			boolean isTitleHidden = false;
			@Override
			public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
				if (scrollY > 100) {
					_toolbar.setBackgroundColor(getResources().getColor(R.color.colorBackgroundPrimary));
					getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackgroundPrimary));
					setTitle(getIntent().getStringExtra("title"));
				} else {
					getWindow().setStatusBarColor(Color.TRANSPARENT);
					_app_bar.setBackgroundColor(Color.TRANSPARENT);
					_toolbar.setBackgroundColor(Color.TRANSPARENT);
					setTitle("");
				}
			}
		});
		_getDefaultData();
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			 @Override
			public void onRefresh() {
				swipeRefreshLayout.setRefreshing(false);
				_getDefaultData();
				flexListmap.clear();
				recommendedListmap.clear();
				volumeListmap.clear();
				isDataLoaded = false;
			}
		});
		BannerView bannerView_bannerLayout = new BannerView(PageActivity.this, "Banner_Android", new UnityBannerSize(320, 50));
		bannerView_bannerLayout.setListener(bannerListener);
		bannerView_bannerLayout.load();
		bannerLayout.addView(bannerView_bannerLayout);
		BannerView bannerView_bannerLayout2 = new BannerView(PageActivity.this, "Banner_Android", new UnityBannerSize(320, 50));
		bannerView_bannerLayout2.setListener(bannerListener);
		bannerView_bannerLayout2.load();
		bannerLayout2.addView(bannerView_bannerLayout2);
		loading = new ProgressDialog(PageActivity.this);
		loading.setMessage("Loading ads, please wait...");
		loading.setCancelable(false);
		loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}
	
	private void colorApply(){
		addToFavButton.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, getResources().getColor(R.color.colorAccent)));
		int color1 = ContextCompat.getColor(this, android.R.color.transparent);
		int color2 = ContextCompat.getColor(this, R.color.colorAccent);
		GradientDrawable gb1 = new GradientDrawable();
		gb1.setCornerRadius(50f);
		gb1.setColor(color2);
		linear18.setBackground(gb1);
		linear18.setTag(color2);
		GradientDrawable gb2 = new GradientDrawable();
		gb2.setCornerRadius(50f);
		gb2.setColor(color1);
		linear19.setBackground(gb2);
		linear19.setTag(color1);
		View.OnClickListener switchBackground = new View.OnClickListener(){
			 @Override
			public void onClick(View view){
				int currentColor1 = (int) linear18.getTag();
				int currentColor2 = (int) linear19.getTag();
				if(view == linear18){
					animateBackgroundColor(linear18, currentColor1, color2);
					animateBackgroundColor(linear19, currentColor2, color1);
					linear18.setTag(color2);
					linear19.setTag(color1);
					if (isDataLoaded) {
						linear21.setVisibility(View.VISIBLE);
						linear28.setVisibility(View.GONE);
					} else {
						linear21.setVisibility(View.GONE);
						linear28.setVisibility(View.GONE);
					}
					_applySwitchAnimation(linear21);
				}else if(view == linear19){
					animateBackgroundColor(linear19, currentColor2, color2);
					animateBackgroundColor(linear18, currentColor1, color1);
					linear18.setTag(color1);
					linear19.setTag(color2);
					if (isDataLoaded) {
						linear21.setVisibility(View.GONE);
						linear28.setVisibility(View.VISIBLE);
					} else {
						linear21.setVisibility(View.GONE);
						linear28.setVisibility(View.GONE);
					}
					_applySwitchAnimation(linear28);
				}
			}
		};
		linear18.setOnClickListener(switchBackground);
		linear19.setOnClickListener(switchBackground);
	}
	
	private void animateBackgroundColor(View view, int startColor, int endColor){
		GradientDrawable backgroundDrawable;
		if(view.getBackground() instanceof GradientDrawable){
			backgroundDrawable = (GradientDrawable) view.getBackground();
		}else{
			backgroundDrawable = new GradientDrawable();
			backgroundDrawable.setCornerRadius(50f);
		}
		ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
		colorAnimation.setDuration(500);
		colorAnimation.addUpdateListener(animator -> {
			int animatedColor = (int) animator.getAnimatedValue();
			backgroundDrawable.setColor(animatedColor);
			view.setBackground(backgroundDrawable);
		});
		colorAnimation.start();
	}
	
	private void changeAddToFavButton(boolean isExistData){
		if (isExistData) {
			addToFavIcon.setImageResource(R.drawable.icon_local_library_round);
			addToFabText.setText("In Library");
			addToFavIcon.setColorFilter(0xFFE91E63, PorterDuff.Mode.SRC_ATOP);
			addToFabText.setTextColor(0xFFE91E63);
		} else {
			addToFavIcon.setImageResource(R.drawable.icon_local_library_outline);
			addToFabText.setText("Add to Library");
			addToFavIcon.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_ATOP);
			addToFabText.setTextColor(0xFFFFFFFF);
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	
	@Override
	public void onBackPressed() {
		finish();
		Animatoo.animateSwipeRight(PageActivity.this);
	}
	public void _getDefaultData() {
		textviewTitle.setText(getIntent().getStringExtra("title"));
		Glide.with(getApplicationContext()).load(getIntent().getStringExtra("cover")).placeholder(R.drawable.load_image).error(R.drawable.no_image).into(imageviewBanner);
		Glide.with(getApplicationContext()).load(getIntent().getStringExtra("cover")).placeholder(R.drawable.load_image).error(R.drawable.no_image).into(imageviewCover);
		requestData.startRequestNetwork(RequestNetworkController.GET, settings.getString("serverLink", "") + "info?id=" + getIntent().getStringExtra("id"), "a", _requestData_request_listener);
		requestRecommendations.startRequestNetwork(RequestNetworkController.GET, settings.getString("serverLink", "") + "recommendation?id=" + getIntent().getStringExtra("id"), "a", _requestRecommendations_request_listener);
		showShimmer();
	}
	
	private void showShimmer(){
		isDataLoaded = false;
		recommendedListmap.clear();
		for(int _repeat33 = 0; _repeat33 < (int)(10); _repeat33++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("a", "a");
				recommendedListmap.add(_item);
			}
		}
		adapter1.notifyDataSetChanged();
	}
	
	
	public void _applySwitchAnimation(final View _view) {
		animationView.cancel();
		animationView.setTarget(_view);
		animationView.setPropertyName("alpha");
		animationView.setFloatValues((float)(0.0d), (float)(1.0d));
		animationView.setInterpolator(new BounceInterpolator());
		animationView.setDuration((int)(500));
		animationView.start();
	}
	
	
	public void _DownloadFile(final String _url, final String _filename, final String _extension, final String _path) {
		if (!FileUtil.isExistFile(_path)) {
			FileUtil.makeDir(_path);
		}
		String extensionName = _extension.toUpperCase();
		String vol = _filename.replaceAll("_", " ");
		LayoutInflater inflater = LayoutInflater.from(PageActivity.this);
		View view = inflater.inflate(R.layout.download_dialog, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(PageActivity.this);
		builder.setView(view);
		builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
		dialog.show();
		ProgressBar progressDL = view.findViewById(R.id.progressbar1);
		ImageView dialogIcon = view.findViewById(R.id.dialog_icon);
		TextView dialogTitle = view.findViewById(R.id.dialog_title);
		TextView dialogFilename = view.findViewById(R.id.dialog_filename);
		TextView notifyMbProgress = view.findViewById(R.id.dialog_mb);
		TextView cancelBtn = view.findViewById(R.id.cancelBtn);
		TextView retryBtn = view.findViewById(R.id.retryBtn);
		TextView closeBtn = view.findViewById(R.id.closeBtn);
		dialogFilename.setText(novelTitle + " " + vol + " " + extensionName);
		final boolean[] isCancelled = {false};
		progressDL.setIndeterminate(true);
		Runnable downloadTask = () -> {
			try {
				URL url = new URL(_url);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.connect();
				int fileLength = connection.getContentLength();
				runOnUiThread(() -> {
					progressDL.setIndeterminate(false);
					progressDL.setProgress(0);
					dialogTitle.setText("Download Failed");
					dialogIcon.setImageResource(R.drawable.icon_download_circle);
					retryBtn.setVisibility(View.GONE);
					closeBtn.setVisibility(View.GONE);
				});
				InputStream input = new BufferedInputStream(connection.getInputStream());
				String filename = _filename + "." + _extension;
				File outputFile = new File(_path, filename);
				FileOutputStream output = new FileOutputStream(outputFile);
				byte[] data = new byte[4096];
				final long[] total = {0};
				int count;
				while((count = input.read(data)) != -1){
					if(isCancelled[0]){
						input.close();
						output.close();
						if(outputFile.exists()){
							outputFile.delete();
						}
						runOnUiThread(() -> {
							notifyMbProgress.setText("Download Cancelled");
							dialogTitle.setText("Download Cancelled");
							dialogIcon.setImageResource(R.drawable.icon_download_circle);
							progressDL.setProgress(0);
							cancelBtn.setVisibility(View.GONE);
							retryBtn.setVisibility(View.VISIBLE);
							closeBtn.setVisibility(View.VISIBLE);
						});
						return;
					}
					total[0] += count;
					int progress = (int) (total[0] * 100 / fileLength);
					runOnUiThread(() -> {
						dialogTitle.setText("Downloading...");
						dialogIcon.setImageResource(R.drawable.icon_download_circle);
						progressDL.setProgress(progress);
						String progressText = String.format("%.2f MB / %.2f MB", total[0] / (1024.0 * 1024.0), fileLength / (1024.0 * 1024.0));
						notifyMbProgress.setText(progressText);
						cancelBtn.setVisibility(View.VISIBLE);
						retryBtn.setVisibility(View.GONE);
						closeBtn.setVisibility(View.GONE);
					});
					output.write(data, 0, count);
				}
				output.flush(); output.close(); input.close();
				runOnUiThread(() -> {
					dialogTitle.setText("Download Completed");
					dialogIcon.setImageResource(R.drawable.icon_download_done_round);
					progressDL.setProgress(100);
					cancelBtn.setVisibility(View.GONE);
					retryBtn.setVisibility(View.GONE);
					closeBtn.setVisibility(View.VISIBLE);
					adapter2.notifyDataSetChanged();
					dialog.setCancelable(true);
					_AddDownloadHistoryData(getIntent().getStringExtra("id"), getIntent().getStringExtra("title"), getIntent().getStringExtra("cover"), _extension, _path.concat(filename), volumeNumber);
				});
			} catch (Exception e) {
				e.printStackTrace();
				runOnUiThread(() -> {
					dialogTitle.setText("Download Failed");
					dialogIcon.setImageResource(R.drawable.icon_download_circle);
					notifyMbProgress.setText("Download Failed: " + e.getMessage());
					progressDL.setIndeterminate(false);
					progressDL.setProgress(0);
					cancelBtn.setVisibility(View.GONE);
					retryBtn.setVisibility(View.VISIBLE);
					closeBtn.setVisibility(View.VISIBLE);
				});
			}
		};
		new Thread(downloadTask).start();
		retryBtn.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
				isCancelled[0] = false;
				dialogTitle.setText("Download Retrying");
				dialogIcon.setImageResource(R.drawable.icon_download_circle);
				progressDL.setIndeterminate(true);
				notifyMbProgress.setText("Download Retrying...");
				cancelBtn.setVisibility(View.VISIBLE);
				retryBtn.setVisibility(View.GONE);
				closeBtn.setVisibility(View.GONE);
				new Thread(downloadTask).start();
				dialog.setCancelable(true);
			}
		});
		closeBtn.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
				UnityAds.load("Interstitial_Android", loadListener);
				dialog.dismiss();
			}
		});
		cancelBtn.setOnClickListener(new View.OnClickListener(){ public void onClick(View v){
				isCancelled[0] = true;
				progressDL.setIndeterminate(false);
				dialogTitle.setText("Download Cancelled");
				dialogIcon.setImageResource(R.drawable.icon_download_circle);
				progressDL.setProgress(0);
				notifyMbProgress.setText("Download Cancelled");
				cancelBtn.setVisibility(View.GONE);
				retryBtn.setVisibility(View.VISIBLE);
				closeBtn.setVisibility(View.VISIBLE);
				dialog.setCancelable(true);
			}
		});
	}
	
	
	public void _OpenFileInProvider(final String _path, final String _extension) {
		File file = new File(Environment.getExternalStorageDirectory(), "Download"+_path);
		LayoutInflater inflater = LayoutInflater.from(PageActivity.this);
		View view = inflater.inflate(R.layout.open_file, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(PageActivity.this);
		builder.setView(view);
		builder.setCancelable(true);
		AlertDialog dialog = builder.create();
		dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
		dialog.show();
		ImageView dialogIcon = view.findViewById(R.id.dialog_icon);
		TextView dialogTitle = view.findViewById(R.id.dialog_title);
		TextView dialogFilename = view.findViewById(R.id.dialog_message);
		TextView cancelBtn = view.findViewById(R.id.cancelBtn);
		TextView deleteBtn = view.findViewById(R.id.deleteBtn);
		TextView openBtn = view.findViewById(R.id.openBtn);
		if (!settings.contains("mainProvider")) {
			settings.edit().putString("mainProvider", "com.flyersoft.moonreader").commit();
		}
		stringProvider = settings.getString("mainProvider", "");
		openBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (FileUtil.isExistFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat(_path))) {
					Uri uri = FileProvider.getUriForFile(PageActivity.this, "com.jellee.JelLee.fileprovider", file);
					Intent intent = new Intent(Intent.ACTION_VIEW);
					if (_extension.equals("pdf")) {
						intent.setDataAndType(uri, "application/pdf");
					} else {
						intent.setDataAndType(uri, "application/epub+zip");
					}
					switch(stringProvider) {
						case "Moon Reader": {
							intent.setPackage("com.flyersoft.moonreader");
							break;
						}
						case "ReadEra": {
							intent.setPackage("org.readera");
							break;
						}
						case "eBoox": {
							intent.setPackage("com.reader.books");
							break;
						}
					}
					intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					try {
						startActivity(intent);
						dialog.dismiss();
					} catch (Exception e) {
						switch(stringProvider) {
							case "Moon Reader": {
								SketchwareUtil.showMessage(getApplicationContext(), "Moon+ Reader is not installed or cannot handle this file.");
								intent.setAction(Intent.ACTION_VIEW);
								intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.flyersoft.moonreader"));
								intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
								startActivity(intent);
								break;
							}
							case "ReadEra": {
								SketchwareUtil.showMessage(getApplicationContext(), "ReadEra is not installed or cannot handle this file.");
								intent.setAction(Intent.ACTION_VIEW);
								intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=org.readera"));
								intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
								startActivity(intent);
								break;
							}
							case "eBoox": {
								SketchwareUtil.showMessage(getApplicationContext(), "eBoox is not installed or cannot handle this file.");
								intent.setAction(Intent.ACTION_VIEW);
								intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.reader.books"));
								intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
								startActivity(intent);
								break;
							}
						}
					}
				} else {
					SketchwareUtil.showMessage(getApplicationContext(), "This file is not exist or has been removed!");
				}
			}
		});
		deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FileUtil.deleteFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS) + _path);
				adapter2.notifyDataSetChanged();
				dialog.dismiss();
			}
		});
		cancelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dialog.dismiss();
			}
		});
	}
	
	
	public void _AddDownloadHistoryData(final String _id, final String _title, final String _cover, final String _extension, final String _path, final String _volume) {
		calendar = Calendar.getInstance();
		downloadDirPath = FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Jellee/History");
		historyFilePath = downloadDirPath + "/history.json";
		timeNow = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
		File historyFile = new File(historyFilePath);
		try {
			File downloadDir = new File(downloadDirPath);
			if (!downloadDir.exists()) {
				downloadDir.mkdirs();
			}
			JSONArray jsonArray;
			if (historyFile.exists()) {
				StringBuilder fileContent = new StringBuilder();
				BufferedReader reader = new BufferedReader(new FileReader(historyFile));
				String line;
				while((line = reader.readLine()) != null) {
					fileContent.append(line);
				}
				reader.close();
				if (!fileContent.toString().isEmpty()) {
					jsonArray = new JSONArray(fileContent.toString());
				} else {
					jsonArray = new JSONArray();
				}
			} else {
				historyFile.createNewFile();
				jsonArray = new JSONArray();
			}
			JSONObject newData = new JSONObject();
			newData.put("id", _id);
			newData.put("title", _title);
			newData.put("cover", _cover);
			newData.put("volume", _volume);
			newData.put("extension", _extension);
			newData.put("path", _path);
			newData.put("time", timeNow);
			jsonArray.put(newData);
			BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile));
			writer.write(jsonArray.toString(4));
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void _AddToLibrary(final String _id, final String _title, final String _cover, final String _ratings, final String _popularity, final String _publisher) {
		calendar = Calendar.getInstance();
		downloadDirPath = FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Jellee/Library");
		libraryFilePath = downloadDirPath + "/library.json";
		timeNow = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());
		File libraryFile = new File(libraryFilePath);
		try {
			File downloadDir = new File(downloadDirPath);
			if (!downloadDir.exists()) {
				downloadDir.mkdirs();
			}
			JSONArray jsonArray;
			if (libraryFile.exists()) {
				StringBuilder fileContent = new StringBuilder();
				BufferedReader reader = new BufferedReader(new FileReader(libraryFile));
				String line;
				while((line = reader.readLine()) != null) {
					fileContent.append(line);
				}
				reader.close();
				if (!fileContent.toString().isEmpty()) {
					jsonArray = new JSONArray(fileContent.toString());
				} else {
					jsonArray = new JSONArray();
				}
			} else {
				libraryFile.createNewFile();
				jsonArray = new JSONArray();
			}
			JSONObject newData = new JSONObject();
			newData.put("id", _id);
			newData.put("title", _title);
			newData.put("cover", _cover);
			newData.put("ratings", _ratings);
			newData.put("popularity", _popularity);
			newData.put("publisher", _publisher);
			newData.put("time", timeNow);
			jsonArray.put(newData);
			BufferedWriter writer = new BufferedWriter(new FileWriter(libraryFile));
			writer.write(jsonArray.toString(4));
			writer.flush();
			writer.close();
			changeAddToFavButton(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean _checkIfExistInLibrary(final String _id) {
		boolean dataExists = false;
		downloadDirPath = FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Jellee/Library");
		libraryFilePath = downloadDirPath + "/library.json";
		File libraryFile = new File(libraryFilePath);
		try {
			File downloadDir = new File(downloadDirPath);
			if (!downloadDir.exists()) {
				dataExists = false;
			}
			JSONArray jsonArray;
			if (libraryFile.exists()) {
				StringBuilder fileContent = new StringBuilder();
				BufferedReader reader = new BufferedReader(new FileReader(libraryFile));
				String line;
				while((line = reader.readLine()) != null) {
					fileContent.append(line);
				}
				reader.close();
				if (!fileContent.toString().isEmpty()) {
					jsonArray = new JSONArray(fileContent.toString());
					for (int i = 0; i < (int)(jsonArray.length()); i++) {
						JSONObject items = jsonArray.getJSONObject(i);
						if (items.has("id") && items.getString("id").equals(_id)) {
							dataExists = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (dataExists);
	}
	
	
	public void _RemoveToLibrary(final String _id) {
		downloadDirPath = FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Jellee/Library");
		libraryFilePath = downloadDirPath + "/library.json";
		File libraryFile = new File(libraryFilePath);
		try {
			File downloadDir = new File(downloadDirPath);
			JSONArray jsonArray;
			if (libraryFile.exists()) {
				StringBuilder fileContent = new StringBuilder();
				BufferedReader reader = new BufferedReader(new FileReader(libraryFile));
				String line;
				while((line = reader.readLine()) != null) {
					fileContent.append(line);
				}
				reader.close();
				if (!fileContent.toString().isEmpty()) {
					jsonArray = new JSONArray(fileContent.toString());
					for (int i = 0; i < (int)(jsonArray.length()); i++) {
						JSONObject items = jsonArray.getJSONObject(i);
						if (items.has("id") && items.getString("id").equals(_id)) {
							jsonArray.remove(i);
							changeAddToFavButton(false);
							break;
						}
					}
					BufferedWriter writer = new BufferedWriter(new FileWriter(libraryFile));
					writer.write(jsonArray.toString(4));
					writer.flush();
					writer.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void _UnityAds(final String _UnityGameID, final boolean _enableRealAds) {
		UnityAds.initialize(this, _UnityGameID, !_enableRealAds, new IUnityAdsInitializationListener() {
					@Override
					public void onInitializationComplete() {
								
				 		
						
						 }
				
				    @Override
				    public void onInitializationFailed(UnityAds.UnityAdsInitializationError IUnityAdErrorPlayBack, String errorMessage) {
					SketchwareUtil.showMessage(getApplicationContext(), "Error initializing Unity Ads");		
				}
		});
	}
	private IUnityAdsLoadListener loadListener = new IUnityAdsLoadListener() {
			
			@Override
			public void onUnityAdsAdLoaded(String __UnityPlacementID) {
					UnityAds.show(PageActivity.this, __UnityPlacementID, new UnityAdsShowOptions(), showListener);
			
		}
			@Override
			public void onUnityAdsFailedToLoad(String __UnityPlacementID, UnityAds.UnityAdsLoadError IUnityAdErrorPlayBack, String message) {
			SketchwareUtil.showMessage(getApplicationContext(), IUnityAdErrorPlayBack.toString());
			}
		
		};
	
	private BannerView.IListener bannerListener = new BannerView.IListener() {
				
					@Override
				public void onBannerLoaded(BannerView bannerAdView) {
							bannerLayout.setVisibility(View.VISIBLE);
			bannerLayout2.setVisibility(View.VISIBLE);
				}
				
				@Override
				public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo IUnityAdErrorPlayBack) {
							 
				}
				
				@Override
				public void onBannerShown(BannerView bannerAdView) {
							 
				}
				@Override
				public void onBannerClick(BannerView bannerAdView) {
							 
				}
				@Override
				public void onBannerLeftApplication(BannerView bannerAdView) {
							 
				}
				
	};
	private IUnityAdsShowListener showListener = new IUnityAdsShowListener() {
			 @Override
			public void onUnityAdsShowFailure(String __UnityPlacementID, UnityAds.UnityAdsShowError IUnityAdErrorPlayBack, String message) {
					  SketchwareUtil.showMessage(getApplicationContext(), IUnityAdErrorPlayBack.toString());
			}
			
		
			@Override
			public void onUnityAdsShowStart(String __UnityPlacementID) {
					
					 
			}
			
		
			@Override
			public void onUnityAdsShowClick(String __UnityPlacementID) {
					
					 
			}
			
		
			@Override
			public void onUnityAdsShowComplete(String __UnityPlacementID, UnityAds.UnityAdsShowCompletionState __UnityAdsState) {
			 SketchwareUtil.showMessage(getApplicationContext(), "Thank you!");		
		}
		
	};
	// UnityAds SDK 4.6.0 by t.me/ovidiuux 
	{
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
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
			if (isDataLoaded) {
				linear1.setVisibility(View.VISIBLE);
				shimmer.setVisibility(View.GONE);
				textScore.setTextSize((int)8);
				textTranslator.setTextSize((int)8);
				textScore.setText(_data.get((int)_position).get("rating").toString().concat("%"));
				textTranslator.setText(_data.get((int)_position).get("translation").toString());
				textTitle.setText(_data.get((int)_position).get("title").toString());
				Glide.with(getApplicationContext()).load(_data.get((int)_position).get("cover").toString()).placeholder(R.drawable.load_image).error(R.drawable.no_image).into(imageCover);
				linear1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						intent = new Intent(getApplicationContext(), PageActivity.class);
						intent.putExtra("id", _data.get((int)_position).get("id").toString());
						intent.putExtra("title", _data.get((int)_position).get("title").toString());
						intent.putExtra("cover", _data.get((int)_position).get("cover").toString());
						startActivity(intent);
						Animatoo.animateInAndOut(PageActivity.this);
						finish();
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
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.volumes, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout epubBtn = _view.findViewById(R.id.epubBtn);
			final LinearLayout pdfButton = _view.findViewById(R.id.pdfButton);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			
			epubBtn.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFF4CAF50));
			pdfButton.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFFB71C1C));
			volumeNumber = _data.get((int)_position).get("number").toString();
			textview1.setText(volumeNumber);
			String volumeName =  volumeNumber.replaceAll(" ", "_");
			novelTitle = getIntent().getStringExtra("title");
			fileName = novelTitle.replaceAll(" ", "_");
			String fileName1 = fileName.replaceAll(" [^a-zA-Z0-9_]","");
			folderName = novelTitle.replaceAll("[^a-zA-Z0-9 ]", "");
			finalFileName = fileName1 + "_" + volumeName;
			if (FileUtil.isExistFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS) + "/Jellee/Downloads/" + folderName + "/" + volumeName + "." + "epub")) {
				imageview1.setImageResource(R.drawable.icon_download_done_round);
			} else {
				imageview1.setImageResource(R.drawable.icon_download_circle);
			}
			if (FileUtil.isExistFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS) + "/Jellee/Downloads/" + folderName + "/" + volumeName + "." + "pdf")) {
				imageview2.setImageResource(R.drawable.icon_download_done_round);
			} else {
				imageview2.setImageResource(R.drawable.icon_download_circle);
			}
			epubBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					epubLink = _data.get((int)_position).get("epub").toString();
					if (FileUtil.isExistFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS) + "/Jellee/Downloads/" + folderName + "/" + volumeName + "." + "epub")) {
						_OpenFileInProvider("/Jellee/Downloads/" + folderName + "/" + volumeName + "." + "epub", "epub");
					} else {
						volumeNumber = _data.get((int)_position).get("number").toString();
						String volumeName = volumeNumber.replaceAll(" ", "_");
						_DownloadFile(directLink.concat(epubLink), volumeName, "epub", FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS) + "/Jellee/Downloads/" + folderName + "/");
						UnityAds.load("Rewarded_Android", loadListener);
					}
				}
			});
			pdfButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					pdfLink = _data.get((int)_position).get("pdf").toString();
					if (TextUtils.isEmpty(pdfLink)) {
						SketchwareUtil.showMessage(getApplicationContext(), "Pdf is not yet available!");
					} else {
						if (FileUtil.isExistFile(FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS) + "/Jellee/Downloads/" + folderName + "/" + volumeName + "." + "pdf")) {
							_OpenFileInProvider("/Jellee/Downloads/" + folderName + "/" + volumeName + "." + "pdf", "pdf");
						} else {
							volumeNumber = _data.get((int)_position).get("number").toString();
							String volumeName = volumeNumber.replaceAll(" ", "_");
							_DownloadFile(directLink.concat(pdfLink), volumeName, "pdf", FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS) + "/Jellee/Downloads/" + folderName + "/");
							UnityAds.load("Rewarded_Android", loadListener);
						}
					}
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