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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.blogspot.atifsoftwares.animatoolib.*;
import com.facebook.shimmer.*;
import com.google.android.flexbox.*;
import com.google.android.material.*;
import com.unity3d.ads.*;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.jetbrains.kotlin.*;
import org.json.*;

public class MoreFragmentActivity extends Fragment {
	
	private double versionCode = 0;
	private String updateLink = "";
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear_top;
	private LinearLayout linear6;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private TextView textview1;
	private CircleImageView circleimageview1;
	private TextView textview2;
	private TextView textview3;
	private ImageView imageview9;
	private TextView textview8;
	private ImageView imageview10;
	private ImageView imageview1;
	private TextView textview4;
	private ImageView imageview2;
	private ImageView imageview3;
	private TextView textview5;
	private ImageView imageview4;
	private ImageView imageview5;
	private TextView textview6;
	private ImageView imageview6;
	private ImageView imageview7;
	private TextView textview7;
	private ImageView imageview8;
	
	private Intent intent = new Intent();
	private RequestNetwork request;
	private RequestNetwork.RequestListener _request_request_listener;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.more_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		vscroll1 = _view.findViewById(R.id.vscroll1);
		linear1 = _view.findViewById(R.id.linear1);
		linear_top = _view.findViewById(R.id.linear_top);
		linear6 = _view.findViewById(R.id.linear6);
		linear2 = _view.findViewById(R.id.linear2);
		linear3 = _view.findViewById(R.id.linear3);
		linear4 = _view.findViewById(R.id.linear4);
		linear5 = _view.findViewById(R.id.linear5);
		textview1 = _view.findViewById(R.id.textview1);
		circleimageview1 = _view.findViewById(R.id.circleimageview1);
		textview2 = _view.findViewById(R.id.textview2);
		textview3 = _view.findViewById(R.id.textview3);
		imageview9 = _view.findViewById(R.id.imageview9);
		textview8 = _view.findViewById(R.id.textview8);
		imageview10 = _view.findViewById(R.id.imageview10);
		imageview1 = _view.findViewById(R.id.imageview1);
		textview4 = _view.findViewById(R.id.textview4);
		imageview2 = _view.findViewById(R.id.imageview2);
		imageview3 = _view.findViewById(R.id.imageview3);
		textview5 = _view.findViewById(R.id.textview5);
		imageview4 = _view.findViewById(R.id.imageview4);
		imageview5 = _view.findViewById(R.id.imageview5);
		textview6 = _view.findViewById(R.id.textview6);
		imageview6 = _view.findViewById(R.id.imageview6);
		imageview7 = _view.findViewById(R.id.imageview7);
		textview7 = _view.findViewById(R.id.textview7);
		imageview8 = _view.findViewById(R.id.imageview8);
		request = new RequestNetwork((Activity) getContext());
		
		linear6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent = new Intent(getContext().getApplicationContext(), SettingsActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent);
				Animatoo.animateInAndOut(getContext());
			}
		});
		
		linear2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getContext().getApplicationContext(), "Feature will be available soon!");
			}
		});
		
		linear3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getContext().getApplicationContext(), "Feature will be available soon!");
			}
		});
		
		linear4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent = new Intent(getContext().getApplicationContext(), AboutActivity.class);
				startActivity(intent);
				Animatoo.animateInAndOut(getContext());
			}
		});
		
		linear5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getContext().getApplicationContext(), "Checking...");
				request.startRequestNetwork(RequestNetworkController.GET, "https://jellee11.github.io/Jellee-Storage/version.json", "a", _request_request_listener);
			}
		});
		
		_request_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				try {
					JSONObject jsonObject = new JSONObject(_response);
					JSONObject ver = jsonObject.getJSONObject("version");
					updateLink = ver.getString("updateLink");
					if (ver.getInt("versionCode") > (int) versionCode) {
						com.google.android.material.snackbar.Snackbar.make(linear1, "New version found. Please update to new version", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("Update", new View.OnClickListener(){
							@Override
							public void onClick(View _view) {
								intent.setAction(Intent.ACTION_VIEW);
								intent.setData(Uri.parse(updateLink));
								intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
								startActivity(intent);
							}
						}).show();
					} else {
						SketchwareUtil.showMessage(getContext().getApplicationContext(), "You're in latest version");
					}
				} catch (Exception e) {
					SketchwareUtil.showMessage(getContext().getApplicationContext(), "Something went wrong...");
				}
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				SketchwareUtil.showMessage(getContext().getApplicationContext(), "Failed to check updates");
			}
		};
	}
	
	private void initializeLogic() {
		versionCode = 2;
	}
	
}