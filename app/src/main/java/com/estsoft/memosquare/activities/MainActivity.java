package com.estsoft.memosquare.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;

import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.estsoft.memosquare.R;
import com.estsoft.memosquare.adapters.MainPagerAdapter;
import com.estsoft.memosquare.fragments.MainTabMymemoFragment;
import com.estsoft.memosquare.fragments.MainTabClipbookFragment;
import com.estsoft.memosquare.fragments.MainTabSquareFragment;

import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity{

    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // onCreate() has these following steps:
        // 1. Setup Toolbar
        // 2. Setup Drawer
        // 3. Setup View Pager
        // 4. Setup Floating Action Button

        // 1. Setup Toolbar
        {
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // 2. Setup Drawer
        {
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            menuItem.setChecked(true);
                            mDrawerLayout.closeDrawers();
                            return true;
                        }
                    });
        }

        // 3. Setup View Pager
        {
            //3.1. Initializing ViewPager
            viewPager = (ViewPager) findViewById(R.id.viewpager);

            //3.2. Creating PagerAdapter
            MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new MainTabMymemoFragment(), getString(R.string.main_yours));
            adapter.addFragment(new MainTabClipbookFragment(), getString(R.string.main_clipbook));
            adapter.addFragment(new MainTabSquareFragment(), getString(R.string.main_square));
            viewPager.setAdapter(adapter);

            //3.3. Initializing TabLayout
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

            //3.4. Connecting tabLayout, ViewPager
            tabLayout.setupWithViewPager(viewPager);
        }

        // 4. Setup Floating Action Button
        {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // handle click event on home icon(drawer icon)
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}