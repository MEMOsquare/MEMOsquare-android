package com.estsoft.memosquare.activities;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.estsoft.memosquare.R;
import com.estsoft.memosquare.adapters.MainPagerAdapter;
import com.estsoft.memosquare.fragments.MainTabMymemoFragment;
import com.estsoft.memosquare.fragments.MainTabClipbookFragment;
import com.estsoft.memosquare.fragments.MainTabSquareFragment;
import com.estsoft.memosquare.utils.PrefUtils;
import com.facebook.login.LoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity{

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.fab) FloatingActionButton fab;


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
            setSupportActionBar(mToolbar);
            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.drawable.logo_main);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // 2. Setup Drawer
        {
            //set user profile image, profile name
            View hView =  navigationView.getHeaderView(0);
            ImageView nav_profileimage = (ImageView)hView.findViewById(R.id.nav_profile_image);
            TextView nav_profilename = (TextView)hView.findViewById(R.id.nav_profile_name);
            nav_profileimage.setImageBitmap(PrefUtils.getCurrentUser(MainActivity.this).getPicture_bitmap());
            nav_profilename.setText(PrefUtils.getCurrentUser(MainActivity.this).getName());

            //set menu item selected listeber
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                //sign out 처리
                                case R.id.menuitem_signout:
                                    PrefUtils.clearCurrentUser(MainActivity.this);
                                    // facebook login manager
                                    LoginManager.getInstance().logOut();
                                    Intent intent=new Intent(MainActivity.this, WelcomeActivity.class);
                                    startActivity(intent);
                                    finish();
                            }
                            menuItem.setChecked(true);
                            mDrawerLayout.closeDrawers();
                            return false;
                        }
                    });
        }

        // 3. Setup View Pager
        {
            //3.1. Initializing ViewPager ->bindview로 처리
            //3.2. Creating PagerAdapter
            MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new MainTabMymemoFragment(), getString(R.string.main_mymemo));
            adapter.addFragment(new MainTabClipbookFragment(), getString(R.string.main_clipbook));
            adapter.addFragment(new MainTabSquareFragment(), getString(R.string.main_square));
            viewPager.setAdapter(adapter);

            //3.3. Initializing TabLayout


            //3.4. Connecting tabLayout, ViewPager
            tabLayout.setupWithViewPager(viewPager);
        }

        // 4. Setup Floating Action Button
        {

            //// TODO: 2016-11-01 fab처리
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