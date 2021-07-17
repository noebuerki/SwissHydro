package com.example.swisshydro.ui.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.swisshydro.R;
import com.example.swisshydro.ui.fragments.AllFragment;
import com.example.swisshydro.ui.fragments.FavoritesFragment;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 2;
    private final Context context;

    private final int[] imageResId = {
            R.drawable.round_star_24,
            R.drawable.round_format_list_bulleted_24,
    };

    private final String[] tabTitles = new String[2];

    public TabFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;

        tabTitles[0] = context.getString(R.string.fav_tab_title);
        tabTitles[1] = context.getString(R.string.all_tab_title);
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.tab, null);
        TextView tabText = v.findViewById(R.id.tabText);
        tabText.setText(tabTitles[position]);
        ImageView tabIcon = v.findViewById(R.id.tabIcon);
        tabIcon.setImageResource(imageResId[position]);
        return v;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return AllFragment.newInstance();
            default:
                return FavoritesFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
