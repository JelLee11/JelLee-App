package com.jellee.JelLee;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MultiSelectFlexboxManager {
    private final Context context;
    private final FlexboxLayout flexboxLayout;
    private final HashSet<Integer> selectedPositions = new HashSet<>();
    private final HashSet<String> selectedValues = new HashSet<>();
    private final int defaultBorderColor;
    private final int defaultBgColor;
    private final int selectedBgColor;

    public MultiSelectFlexboxManager(Context context, FlexboxLayout flexboxLayout) {
        this.context = context;
        this.flexboxLayout = flexboxLayout;
        this.defaultBorderColor = context.getResources().getColor(R.color.colorTextPrimary);
        this.defaultBgColor = Color.TRANSPARENT;
        this.selectedBgColor = context.getResources().getColor(R.color.colorAccent);
    }

    public void populateFlexbox(ArrayList<HashMap<String, Object>> items, String key) {
        flexboxLayout.removeAllViews(); // Clear existing items

        for (int i = 0; i < items.size(); i++) {
            String text = items.get(i).get(key).toString();
            boolean isSelected = selectedValues.contains(text); // Check if item is selected

            TextView textView = createTextView(text, i, isSelected);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            lp.setMargins(8, 8, 8, 8);
            textView.setLayoutParams(lp);

            flexboxLayout.addView(textView);
        }
    }

    private TextView createTextView(String text, int position, boolean isSelected) {
        TextView textView = new TextView(context);
        textView.setPadding(15, 10, 15, 10);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setTextSize(12);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/opensansmedium.ttf"), Typeface.NORMAL);
        textView.setTextColor(context.getResources().getColor(R.color.colorTextPrimary));
        updateBackground(textView, isSelected); // Set initial background based on state

        textView.setOnClickListener(v -> {
            toggleSelection(position, text);
            updateBackground(textView, selectedPositions.contains(position));
        });

        return textView;
    }

    private void toggleSelection(int position, String value) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(position); // Deselect
            selectedValues.remove(value);
        } else {
            selectedPositions.add(position); // Select
            selectedValues.add(value);
        }
    }

    private void updateBackground(TextView textView, boolean isSelected) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(10);
        drawable.setStroke(2, defaultBorderColor);
        drawable.setColor(isSelected ? selectedBgColor : defaultBgColor);
        textView.setBackground(drawable);
    }

    public HashSet<String> getSelectedValues() {
        return selectedValues;
    }

    public String getSelectedValuesAsString() {
        ArrayList<String> lowerCaseValues = new ArrayList<>();
        for (String value : selectedValues) {
            lowerCaseValues.add(value.toLowerCase());
        }
        return String.join(",", lowerCaseValues);
    }
}