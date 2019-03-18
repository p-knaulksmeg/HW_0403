package by.paulk.hw_0403;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    private Toolbar mainPodcastsToolbar;
    private TabLayout forYouTabLayout;
    private ViewPager forYouViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        initialView();
        setToolbar();
        setTabLayout();
    }

    private void setTabLayout() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewEpisodesFragment(), "New episodes");
        adapter.addFragment(new InProgressFragment(), "In progress");
        adapter.addFragment(new DownloadsFragment(), "Downloads");
        forYouViewPager.setAdapter(adapter);
        forYouTabLayout.setupWithViewPager(forYouViewPager);
    }

    private void setToolbar() {

        setSupportActionBar(mainPodcastsToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void initialView() {
        mainPodcastsToolbar = findViewById(R.id.mainPodcastsToolbar);
        forYouTabLayout = findViewById(R.id.forYouTabLayout);
        forYouViewPager = findViewById(R.id.forYouViewPager);
    }
}
