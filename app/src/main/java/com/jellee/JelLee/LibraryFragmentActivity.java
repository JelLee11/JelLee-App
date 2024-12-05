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
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.android.material.*;
import com.google.android.material.divider.MaterialDivider;
import com.unity3d.ads.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.jetbrains.kotlin.*;
import org.json.*;

public class LibraryFragmentActivity extends Fragment {
	
	private Recyclerview1Adapter adapter;
	private String downloadDirPath = "";
	private String libraryFilePath = "";
	private boolean isReverse = false;
	
	private ArrayList<HashMap<String, Object>> dataListmap = new ArrayList<>();
	
	private LinearLayout linear_top;
	private MaterialDivider divider;
	private SwipeRefreshLayout swiperefreshlayout1;
	private TextView textview1;
	private ImageView sortBtn;
	private NestedScrollView vscroll1;
	private LinearLayout linear1;
	private RecyclerView recyclerview1;
	private LinearLayout linear_error;
	private TextView textview3;
	private TextView textview_error;
	
	private Intent intent = new Intent();
	private SharedPreferences settings;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.library_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		linear_top = _view.findViewById(R.id.linear_top);
		divider = _view.findViewById(R.id.divider);
		swiperefreshlayout1 = _view.findViewById(R.id.swiperefreshlayout1);
		textview1 = _view.findViewById(R.id.textview1);
		sortBtn = _view.findViewById(R.id.sortBtn);
		vscroll1 = _view.findViewById(R.id.vscroll1);
		linear1 = _view.findViewById(R.id.linear1);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		linear_error = _view.findViewById(R.id.linear_error);
		textview3 = _view.findViewById(R.id.textview3);
		textview_error = _view.findViewById(R.id.textview_error);
		settings = getContext().getSharedPreferences("settings", Activity.MODE_PRIVATE);
		
		swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swiperefreshlayout1.setRefreshing(false);
				checkLibraryData();
			}
		});
		
		sortBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (isReverse) {
					sortBtn.setImageResource(R.drawable.icon_sort_down);
					Collections.reverse(dataListmap);
					adapter.notifyDataSetChanged();
					isReverse = false;
				} else {
					sortBtn.setImageResource(R.drawable.icon_sort_up);
					Collections.reverse(dataListmap);
					adapter.notifyDataSetChanged();
					isReverse = true;
				}
			}
		});
	}
	
	private void initializeLogic() {
		recyclerview1.setLayoutManager(new GridLayoutManager(getContext().getApplicationContext(), (int) 3));
		adapter = new Recyclerview1Adapter(dataListmap);
		recyclerview1.setAdapter(adapter);
		if (!settings.contains("librarySort")) {
			settings.edit().putString("librarySort", "title").commit();
		}
		checkLibraryData();
	}
	
	private void checkLibraryData(){
		downloadDirPath = FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Jellee/Library");
		libraryFilePath = downloadDirPath + "/library.json";
		File libraryFile = new File(libraryFilePath);
		try {
			File downloadDir = new File(downloadDirPath);
			if (!downloadDir.exists()) {
				recyclerview1.setVisibility(View.GONE);
				textview_error.setText("You didn't have any novels added to your library!");
				linear_error.setVisibility(View.VISIBLE);
			} else {
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
						dataListmap.clear();
						for (int i = 0; i < (int)(jsonArray.length()); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
							HashMap<String, Object> data = new HashMap<>();
							data.put("id", item.getString("id"));
							data.put("title", item.getString("title"));
							data.put("cover", item.getString("cover"));
							data.put("ratings", item.getString("ratings"));
							data.put("popularity", item.getString("popularity"));
							data.put("publisher", item.getString("publisher"));
							data.put("time", item.getString("time"));
							dataListmap.add(data);
						}
						_sortByData(settings.getString("librarySort", ""));
						recyclerview1.post(()->{
							adapter.notifyDataSetChanged();
						});
						recyclerview1.setVisibility(View.VISIBLE);
						linear_error.setVisibility(View.GONE);
					} else {
						recyclerview1.setVisibility(View.GONE);
						textview_error.setText("No data in library!");
						linear_error.setVisibility(View.VISIBLE);
					}
				} else {
					recyclerview1.setVisibility(View.GONE);
					textview_error.setText("You didn't have any novels added to your library!");
					linear_error.setVisibility(View.VISIBLE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onStart() {
		super.onStart();
		checkLibraryData();
	}
	public void _sortByData(final String _name) {
		SketchwareUtil.sortListMap(dataListmap, _name, false, true);
		adapter.notifyDataSetChanged();
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
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
			Glide.with(getContext().getApplicationContext()).load(_data.get((int)_position).get("cover").toString()).placeholder(R.drawable.load_image).error(R.drawable.no_image).into(imageview1);
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