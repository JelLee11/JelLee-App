package com.jellee.JelLee;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {

  public MainPagerAdapter(FragmentActivity activity) {
    super(activity);
  }

  @Override
  public int getItemCount() {
    return 4;
  }

  @Override
  public Fragment createFragment(int positon) {
    switch (positon) {
      case 0:
        return new HomeFragmentActivity();
      case 1:
        return new LibraryFragmentActivity();
      case 2:
        return new DownloadFragmentActivity();
      case 3:
        return new MoreFragmentActivity();
      default:
        return new HomeFragmentActivity();
    }
  }
}
