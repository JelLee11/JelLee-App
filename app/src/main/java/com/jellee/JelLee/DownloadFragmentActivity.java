package com.jellee.JelLee;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import android.net.Uri;
import androidx.core.content.FileProvider;
import android.os.Environment;

public class DownloadFragmentActivity extends Fragment {
	
	private String downloadDirPath = "";
	private String historyFilePath = "";
	private Recyclerview1Adapter adapter;
	private String stringProvider = "";
	
	private ArrayList<HashMap<String, Object>> dataListmap = new ArrayList<>();
	
	private SwipeRefreshLayout swiperefreshlayout1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private MaterialDivider divider;
	private RecyclerView recyclerview1;
	private LinearLayout linear_error;
	private TextView textview1;
	private TextView textview2;
	private TextView textview_error;
	
	private Intent intent = new Intent();
	private SharedPreferences settings;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.download_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		swiperefreshlayout1 = _view.findViewById(R.id.swiperefreshlayout1);
		linear1 = _view.findViewById(R.id.linear1);
		linear2 = _view.findViewById(R.id.linear2);
		divider = _view.findViewById(R.id.divider);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		linear_error = _view.findViewById(R.id.linear_error);
		textview1 = _view.findViewById(R.id.textview1);
		textview2 = _view.findViewById(R.id.textview2);
		textview_error = _view.findViewById(R.id.textview_error);
		settings = getContext().getSharedPreferences("settings", Activity.MODE_PRIVATE);
		
		swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swiperefreshlayout1.setRefreshing(false);
				checkDownloadHistory();
			}
		});
	}
	
	private void initializeLogic() {
		swiperefreshlayout1.setRefreshing(false);
		recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
		adapter = new Recyclerview1Adapter(dataListmap);
		recyclerview1.setAdapter(adapter);
		checkDownloadHistory();
	}
	
	private void checkDownloadHistory(){
		downloadDirPath = FileUtil.getPublicDir(Environment.DIRECTORY_DOWNLOADS).concat("/Jellee/History");
		historyFilePath = downloadDirPath + "/history.json";
		File historyFile = new File(historyFilePath);
		try {
			File downloadDir = new File(downloadDirPath);
			if (!downloadDir.exists()) {
				recyclerview1.setVisibility(View.GONE);
				textview_error.setText("You didn't downloaded any novels yet!");
				linear_error.setVisibility(View.VISIBLE);
			} else {
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
						while(jsonArray.length() > 30) {
							jsonArray.remove(0);
						}
						BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile));
						writer.write(jsonArray.toString(4));
						writer.flush();
						writer.close();
						dataListmap.clear();
						for (int i = 0; i < (int)(jsonArray.length()); i++) {
							JSONObject item = jsonArray.getJSONObject(i);
							HashMap<String, Object> data = new HashMap<>();
							data.put("id", item.getString("id"));
							data.put("title", item.getString("title"));
							data.put("cover", item.getString("cover"));
							data.put("volume", item.getString("volume"));
							data.put("path", item.getString("path"));
							data.put("extension", item.getString("extension"));
							data.put("time", item.getString("time"));
							dataListmap.add(data);
						}
						Collections.reverse(dataListmap);
						recyclerview1.post(()->{
							adapter.notifyDataSetChanged();
						});
						recyclerview1.setVisibility(View.VISIBLE);
						linear_error.setVisibility(View.GONE);
					} else {
						recyclerview1.setVisibility(View.GONE);
						textview_error.setText("No download history!");
						linear_error.setVisibility(View.VISIBLE);
					}
				} else {
					recyclerview1.setVisibility(View.GONE);
					textview_error.setText("You didn't downloaded any novels yet!");
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
		checkDownloadHistory();
	}
	public void _Open(final String _path, final String _extension, final String _id, final String _title, final String _cover) {
		//File file = new File(Environment.getExternalStorageDirectory(), "Download"+_path);
		File file = new File(_path);
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.open_file, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
		deleteBtn.setText("Info");
		if (!settings.contains("mainProvider")) {
			settings.edit().putString("mainProvider", "com.flyersoft.moonreader").commit();
		}
		stringProvider = settings.getString("mainProvider", "");
		openBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (FileUtil.isExistFile(_path)) {
					Uri uri = FileProvider.getUriForFile(getActivity(), "com.jellee.JelLee.fileprovider", file);
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
								SketchwareUtil.showMessage(getContext().getApplicationContext(), "Moon+ Reader is not installed or cannot handle this file.");
								intent.setAction(Intent.ACTION_VIEW);
								intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.flyersoft.moonreader"));
								intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
								startActivity(intent);
								break;
							}
							case "ReadEra": {
								SketchwareUtil.showMessage(getContext().getApplicationContext(), "ReadEra is not installed or cannot handle this file.");
								intent.setAction(Intent.ACTION_VIEW);
								intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=org.readera"));
								intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
								startActivity(intent);
								break;
							}
							case "eBoox": {
								SketchwareUtil.showMessage(getContext().getApplicationContext(), "eBoox is not installed or cannot handle this file.");
								intent.setAction(Intent.ACTION_VIEW);
								intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.reader.books"));
								intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
								startActivity(intent);
								break;
							}
						}
					}
				} else {
					SketchwareUtil.showMessage(getContext().getApplicationContext(), "This file is not exist or has been removed!");
				}
				dialog.dismiss();
			}
		});
		deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent = new Intent(getContext().getApplicationContext(), PageActivity.class);
				intent.putExtra("id", _id);
				intent.putExtra("title", _title);
				intent.putExtra("cover", _cover);
				startActivity(intent);
				Animatoo.animateInAndOut(getContext());
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
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.download_history, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			
			textview1.setText(_data.get((int)_position).get("title").toString());
			textview2.setText(_data.get((int)_position).get("volume").toString().concat(" ".concat("(".concat(_data.get((int)_position).get("extension").toString().toUpperCase().concat(")")))));
			textview3.setText(_data.get((int)_position).get("time").toString());
			Glide.with(getContext()).load(_data.get((int)_position).get("cover").toString()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(imageview1);
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_Open(_data.get((int)_position).get("path").toString(), _data.get((int)_position).get("extension").toString(), _data.get((int)_position).get("id").toString(), _data.get((int)_position).get("title").toString(), _data.get((int)_position).get("cover").toString());
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