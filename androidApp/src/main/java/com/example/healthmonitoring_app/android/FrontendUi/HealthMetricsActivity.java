package com.example.healthmonitoring_app.android.FrontendUi;

import android.os.Bundle;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.example.healthmonitoring_app.android.R;
import com.google.android.material.tabs.TabLayout;

public class HealthMetricsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_metrics);

        // Setup ViewPager and TabLayout
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        HealthMetricsPagerAdapter adapter = new HealthMetricsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // Set tab titles
        tabLayout.getTabAt(0).setText("Steps");
        tabLayout.getTabAt(1).setText("Heart Rate");
        tabLayout.getTabAt(2).setText("Sleep");

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}