package com.jellee.JelLee;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import com.google.android.flexbox.FlexboxLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class FlexboxItemManager {
    private final Context context;
    private final FlexboxLayout flexboxLayout;
    private TextView selectedView; // Keeps track of the selected view
    private final int defaultBorderColor;
    private final int defaultBgColor;
    private final int selectedBorderColor;
    private final int selectedBgColor;

    private OnItemClickListener listener;

    public FlexboxItemManager(Context context, FlexboxLayout flexboxLayout) {
        this.context = context;
        this.flexboxLayout = flexboxLayout;
        this.defaultBorderColor = context.getResources().getColor(R.color.colorTextPrimary);
        this.defaultBgColor = Color.TRANSPARENT;
        this.selectedBorderColor = Color.TRANSPARENT;
       this.selectedBgColor = context.getResources().getColor(R.color.colorAccent); // Example selected background color
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private TextView createTextView(String text, int position, boolean isSelected) {
        TextView textView = new TextView(context);
        textView.setPadding(15, 10, 15, 10);
        textView.setGravity(Gravity.CENTER);

        // Set border and background based on selection state
        updateBackground(textView, isSelected);
        
        textView.setText(text);
        textView.setTextSize(12);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/opensansmedium.ttf"), Typeface.NORMAL);
        textView.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));

        textView.setOnClickListener(v -> {
            handleSelection(textView, position, text);
        });

        return textView;
    }

    private void handleSelection(TextView textView, int position, String text) {
        if (selectedView != null && selectedView != textView) {
            // Reset the previous selected item's background
            updateBackground(selectedView, false);
        }

        // Update the new selected item's background
        selectedView = textView;
        updateBackground(selectedView, true);

        if (listener != null) {
            listener.onItemClick(position, text);
        }
    }

    private void updateBackground(TextView textView, boolean isSelected) {
        int borderColor = isSelected ? selectedBorderColor : defaultBorderColor;
        int bgColor = isSelected ? selectedBgColor : defaultBgColor;
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(10);
        drawable.setStroke(2, borderColor);
        drawable.setColor(bgColor);
        textView.setBackground(drawable);
    }

    public void populateFlexbox(ArrayList<HashMap<String, Object>> items, String defaultTag) {
        for (int i = 0; i < items.size(); i++) {
            String item = (String) items.get(i).get("typeFlex");
            boolean isSelected = item.equalsIgnoreCase(defaultTag);
            TextView textView = createTextView(item, i, isSelected);

            if (isSelected) {
                selectedView = textView;
            }

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lp.setMargins(8, 8, 8, 8);
            textView.setLayoutParams(lp);
            flexboxLayout.addView(textView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String text);
    }
}