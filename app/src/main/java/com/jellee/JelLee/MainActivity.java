package com.jellee.JelLee;

import com.jellee.JelLee.StartupActivity;
import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.facebook.shimmer.*;
import com.google.android.flexbox.*;
import com.google.android.material.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.unity3d.ads.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.jetbrains.kotlin.*;
import org.json.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.provider.Settings;

public class MainActivity extends AppCompatActivity {
	
	private Animation scaleAnimation;
	private MainPagerAdapter adapter;
	private static final int REQUEST_PERMISSION_CODE = 1;
	
	private ViewPager2 viewpager1;
	private BottomNavigationView bottomnavigation1;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		viewpager1 = findViewById(R.id.viewpager1);
		bottomnavigation1 = findViewById(R.id.bottomnavigation1);
		
		bottomnavigation1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				final int _itemId = item.getItemId();
				switch((int)_itemId) {
					case ((int)R.id.nav_home): {
						item.setIcon(R.drawable.icon_home_round);
						startAnimationOnMenuItem(item, scaleAnimation);
						resetIcons(R.id.nav_home);
						viewpager1.setCurrentItem((int)0);
						break;
					}
					case ((int)R.id.nav_library): {
						item.setIcon(R.drawable.icon_local_library_round);
						startAnimationOnMenuItem(item, scaleAnimation);
						resetIcons(R.id.nav_library);
						viewpager1.setCurrentItem((int)1);
						break;
					}
					case ((int)R.id.nav_download): {
						item.setIcon(R.drawable.icon_file_download_round);
						startAnimationOnMenuItem(item, scaleAnimation);
						resetIcons(R.id.nav_download);
						viewpager1.setCurrentItem((int)2);
						break;
					}
					case ((int)R.id.nav_more): {
						item.setIcon(R.drawable.icon_more_horiz_round);
						startAnimationOnMenuItem(item, scaleAnimation);
						resetIcons(R.id.nav_more);
						viewpager1.setCurrentItem((int)3);
						break;
					}
				}
				return true;
			}
		});
	}
	
	private void initializeLogic() {
		adapter = new MainPagerAdapter(this);
		viewpager1.setAdapter(adapter);
		viewpager1.setUserInputEnabled(false);
		bottomnavigation1.setItemIconTintList(ContextCompat.getColorStateList(this, R.color.bottom_nav_item_color));
		bottomnavigation1.setItemTextColor(ContextCompat.getColorStateList(this, R.color.bottom_nav_item_color));
		scaleAnimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_nav);
		checkStoragePermission();
	}
	private void startAnimationOnMenuItem(MenuItem item, Animation animation) {
		View iconView = bottomnavigation1.findViewById(item.getItemId());
		if (iconView != null) {
			iconView.startAnimation(animation);
		}
	}
	
	private void resetIcons(int selectedItemId){
		for (int i = 0; i < bottomnavigation1.getMenu().size(); i++) {
			MenuItem menuItem = bottomnavigation1.getMenu().getItem(i);
			if (menuItem.getItemId() != selectedItemId) {
				switch (menuItem.getItemId()) {
					case R.id.nav_home:
					menuItem.setIcon(R.drawable.icon_home_outline);
					break;
					case R.id.nav_library:
					menuItem.setIcon(R.drawable.icon_local_library_outline);
					break;
					case R.id.nav_download:
					menuItem.setIcon(R.drawable.icon_download_outline);
					break;
					case R.id.nav_more:
					menuItem.setIcon(R.drawable.icon_more_horiz_outline);
					break;
				}
			}
		}
	}
	
	private void checkStoragePermission() {
		    // MANAGER_EXTERNAL_PERMISSION
		    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			      if (!Environment.isExternalStorageManager()) {
				        requestStoragePermission();
				      }
			    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			      // Permission scope storage for android 10 and above
			      if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
			          != PackageManager.PERMISSION_GRANTED) {
				        requestStoragePermission();
				      }
			    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			      if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
			          != PackageManager.PERMISSION_GRANTED) {
				        requestStoragePermission();
				      }
			    }
	}
	
	private void requestStoragePermission() {
		    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			      Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
			      startActivity(intent);
			    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			      ActivityCompat.requestPermissions(
			          this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
			    } else {
			      ActivityCompat.requestPermissions(
			          this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
			    }
	}
	
	  @Override
	  public void onRequestPermissionsResult(
	      int requestCode, String[] permissions, int[] grandResults) {
		    super.onRequestPermissionsResult(requestCode, permissions, grandResults);
		    if (requestCode == REQUEST_PERMISSION_CODE) {
			      if (grandResults.length > 0 && grandResults[0] == PackageManager.PERMISSION_GRANTED) {
				          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
					    if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
						        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
						    }
				}
				      } else {
				SketchwareUtil.showMessage(getApplicationContext(), "Permision access denied!");
			}
		}
	}
	
	
	@Override
	public void onBackPressed() {
		finishAffinity();
	}
}