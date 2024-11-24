package com.example.devops2.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.devops2.fragment.HomeFragment;
import com.example.devops2.fragment.LibraryFragment;
import com.example.devops2.fragment.SubscriptionFragment;
import com.example.devops2.fragment.UploadFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new UploadFragment();
            case 2:
                return new SubscriptionFragment();
            case 3:
                return new LibraryFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Home";
                break;
            case 1:
                title = "Upload";
                break;
            case 2:
                title = "Subscription";
                break;
            case 3:
                title = "Library";
                break;
        }
        return title;
    }
}
