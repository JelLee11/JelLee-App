package com.jellee.JelLee;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
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
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.jetbrains.kotlin.*;
import org.json.*;

public class AboutActivity extends AppCompatActivity {
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear5;
	private LinearLayout linear7;
	private LinearLayout linear9;
	private LinearLayout linear11;
	private LinearLayout linear_top;
	private CircleImageView circleimageview1;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear3;
	private CircleImageView circleimageview2;
	private LinearLayout linear4;
	private ImageView imageview1;
	private TextView textview3;
	private TextView textview4;
	private ImageView imageview5;
	private LinearLayout linear6;
	private ImageView imageview2;
	private TextView textview5;
	private TextView textview6;
	private ImageView imageview6;
	private LinearLayout linear8;
	private ImageView imageview3;
	private TextView textview7;
	private TextView textview8;
	private ImageView imageview7;
	private LinearLayout linear10;
	private ImageView imageview4;
	private TextView textview9;
	private TextView textview10;
	private ImageView imageview9;
	private LinearLayout linear12;
	private ImageView imageview8;
	private TextView textview11;
	private TextView textview12;
	
	private Intent intent = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.about);
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
		linear5 = findViewById(R.id.linear5);
		linear7 = findViewById(R.id.linear7);
		linear9 = findViewById(R.id.linear9);
		linear11 = findViewById(R.id.linear11);
		linear_top = findViewById(R.id.linear_top);
		circleimageview1 = findViewById(R.id.circleimageview1);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		linear3 = findViewById(R.id.linear3);
		circleimageview2 = findViewById(R.id.circleimageview2);
		linear4 = findViewById(R.id.linear4);
		imageview1 = findViewById(R.id.imageview1);
		textview3 = findViewById(R.id.textview3);
		textview4 = findViewById(R.id.textview4);
		imageview5 = findViewById(R.id.imageview5);
		linear6 = findViewById(R.id.linear6);
		imageview2 = findViewById(R.id.imageview2);
		textview5 = findViewById(R.id.textview5);
		textview6 = findViewById(R.id.textview6);
		imageview6 = findViewById(R.id.imageview6);
		linear8 = findViewById(R.id.linear8);
		imageview3 = findViewById(R.id.imageview3);
		textview7 = findViewById(R.id.textview7);
		textview8 = findViewById(R.id.textview8);
		imageview7 = findViewById(R.id.imageview7);
		linear10 = findViewById(R.id.linear10);
		imageview4 = findViewById(R.id.imageview4);
		textview9 = findViewById(R.id.textview9);
		textview10 = findViewById(R.id.textview10);
		imageview9 = findViewById(R.id.imageview9);
		linear12 = findViewById(R.id.linear12);
		imageview8 = findViewById(R.id.imageview8);
		textview11 = findViewById(R.id.textview11);
		textview12 = findViewById(R.id.textview12);
		
		linear5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.facebook.com/jelleeTv"));
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				Animatoo.animateInAndOut(AboutActivity.this);
			}
		});
		
		linear7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://t.me/shirou_18"));
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				Animatoo.animateInAndOut(AboutActivity.this);
			}
		});
		
		linear9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://discord.gg/dJNsuuVN"));
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				Animatoo.animateInAndOut(AboutActivity.this);
			}
		});
		
		linear11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.reddit.com/u/ShirouAkagane/s/Z6Wh1gvBu6"));
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				Animatoo.animateInAndOut(AboutActivity.this);
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("");
		_toolbar.setBackgroundColor(getResources().getColor(R.color.colorBackgroundPrimary));
		getWindow().setStatusBarColor(getResources().getColor(R.color.colorBackgroundPrimary));
	}
	
	@Override
	public void onBackPressed() {
		finish();
		Animatoo.animateSwipeRight(AboutActivity.this);
	}
}