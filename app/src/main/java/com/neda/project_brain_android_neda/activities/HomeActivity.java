package com.neda.project_brain_android_neda.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.neda.project_brain_android_neda.R;
import com.neda.project_brain_android_neda.fragments.CiteIdeaFragment;
import com.neda.project_brain_android_neda.fragments.HomeFragment;
import com.neda.project_brain_android_neda.fragments.NewIdeaFragment;
import com.neda.project_brain_android_neda.fragments.OriginalIdeaFragment;
import com.neda.project_brain_android_neda.fragments.ProfileFragment;
import com.neda.project_brain_android_neda.fragments.SearchByTitleFragment;
import com.neda.project_brain_android_neda.fragments.UpdateProfileFragment;
import com.neda.project_brain_android_neda.fragments.UserIdeasFragment;
import com.neda.project_brain_android_neda.fragments.UserToDoFragment;

public class HomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
    }

    private void init() {
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_idea).setText("Idea"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_home).setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_search).setText("Search"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_profile).setText("Profile"));

        tabLayout.selectTab(tabLayout.getTabAt(1));

        // Default Fragment
        // Home
        getSupportFragmentManager().beginTransaction().replace(R.id.container,
                HomeFragment.newInstance(), HomeFragment.class.getSimpleName()).commit();

        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
            // Idea
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    NewIdeaFragment.newInstance(), NewIdeaFragment.class.getSimpleName()).commit();
        } else if (tab.getPosition() == 1) {
            // Home
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    HomeFragment.newInstance(), HomeFragment.class.getSimpleName()).commit();
        } else if (tab.getPosition() == 2) {
            // Home
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    SearchByTitleFragment.newInstance(), SearchByTitleFragment.class.getSimpleName()).commit();
        } else {
            // Profile
            getSupportFragmentManager().beginTransaction().replace(R.id.container,
                    ProfileFragment.newInstance(), ProfileFragment.class.getSimpleName()).commit();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed() {
        Fragment currentFrag = getSupportFragmentManager().findFragmentById(R.id.container);

        if (currentFrag.getTag().equals(UpdateProfileFragment.class.getSimpleName()) ||
                currentFrag.getTag().equals(UserIdeasFragment.class.getSimpleName()) ||
                currentFrag.getTag().equals(UserToDoFragment.class.getSimpleName()) ||
                currentFrag.getTag().equals(CiteIdeaFragment.class.getSimpleName()) ||
                currentFrag.getTag().equals(OriginalIdeaFragment.class.getSimpleName())) {
            getSupportFragmentManager().beginTransaction().remove(currentFrag).commit();
        } else {
            finish();
        }
    }
}