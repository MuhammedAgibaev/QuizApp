package com.agibaev.quizapp.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.agibaev.quizapp.R;
import com.agibaev.quizapp.history.HistoryFragment;
import com.agibaev.quizapp.settings.SettingsFragment;
import com.agibaev.quizapp.util.SimpleFragmentAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String Quiz = "Quiz";
    public static final String HISTORY = "History";
    public static final String SETTINGS = "Settings";
    public static final int MAIN_FRAG = 0;
    public static final int HISTORY_FRAG = 1;
    public static final int SETTINGS_FRAG = 2;
    private MainViewModel mViewModel;

    @BindView(R.id.bottom_nav)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        SimpleFragmentAdapter mSimpleFragmentAdapter = new SimpleFragmentAdapter(getSupportFragmentManager());
        mSimpleFragmentAdapter.setFragment(getFragment());
        viewPager.setAdapter(mSimpleFragmentAdapter);
        setSlidePages();
    }

    private void setSlidePages() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_main:
                    viewPager.setCurrentItem(MAIN_FRAG);
                    setTitle(Quiz);
                    break;
                case R.id.nav_history:
                    viewPager.setCurrentItem(HISTORY_FRAG);
                    setTitle(HISTORY);
                    break;
                case R.id.nav_settings:
                    viewPager.setCurrentItem(SETTINGS_FRAG);
                    setTitle(SETTINGS);
                    break;
            }
            return true;
        });
        onSlidePage();
    }

    private void onSlidePage() {
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
    }

    private List<Fragment> getFragment() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MainFragment());
        fragmentList.add(new HistoryFragment());
        fragmentList.add(new SettingsFragment());
        return fragmentList;
    }
}