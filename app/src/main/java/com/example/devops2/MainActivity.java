package com.example.devops2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import com.example.devops2.adapter.ViewPagerAdapter;
import com.example.devops2.databinding.ActivityMainBinding;
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);
        setUpViewPager();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    binding.viewPager.setCurrentItem(0);
                    break;

                case R.id.upload:
                    binding.viewPager.setCurrentItem(1);
                    break;

                case R.id.subscriptions:
                    binding.viewPager.setCurrentItem(2);
                    break;

                case R.id.library:
                    binding.viewPager.setCurrentItem(3);
                    break;
            }
            return true;
        });
    }

    private void setUpViewPager() {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case 0:
                        binding.bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        break;

                    case 1:
                        binding.bottomNavigationView.getMenu().findItem(R.id.upload).setChecked(true);
                        break;

                    case 2:
                        binding.bottomNavigationView.getMenu().findItem(R.id.subscriptions).setChecked(true);
                        break;

                    case 3:
                        binding.bottomNavigationView.getMenu().findItem(R.id.library).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}