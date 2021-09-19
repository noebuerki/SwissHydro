package ch.noebuerki.swisshydro.ui.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ch.noebuerki.swisshydro.R;
import ch.noebuerki.swisshydro.ui.fragments.AllFragment;
import ch.noebuerki.swisshydro.ui.fragments.FavoritesFragment;

public class TabFragmentPagerAdapter extends FragmentStateAdapter {

    private final int PAGE_COUNT = 2;
    private final Context context;

    private final int[] imageResId = {
            R.drawable.ic_star_white_24dp,
            R.drawable.ic_format_list_bulleted_white_24dp,
    };

    private final String[] tabTitles = new String[2];

    public TabFragmentPagerAdapter(FragmentActivity activity, Context context) {
        super(activity);

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

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return AllFragment.newInstance();
        }
        return FavoritesFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return PAGE_COUNT;
    }
}
