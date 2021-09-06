package ch.bbcag.swisshydro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import ch.bbcag.swisshydro.repository.api.NetworkUtils;
import ch.bbcag.swisshydro.ui.listadapters.TabFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        NetworkUtils.getInstance(getApplicationContext());

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager2 viewPager = findViewById(R.id.viewpager);
        TabFragmentPagerAdapter pagerAdapter = new TabFragmentPagerAdapter(this, getApplicationContext());
        viewPager.setAdapter(pagerAdapter);

        // Pass the viewPager to our tabLayout
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
        })).attach();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
    }

}