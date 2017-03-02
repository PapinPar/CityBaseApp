package chi_software.citybase.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import chi_software.citybase.R;
import chi_software.citybase.core.BaseActivity;
import chi_software.citybase.ui.StartScreen;

public class TutorialActivity extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private ArrayList<Integer> mListDrawble;
    private RadioGroup radioGroup;
    private ViewPager pager;
    private Button mSkipBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_layout);
        init();
    }

    private void init() {
        mListDrawble = new ArrayList<>();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mSkipBtn = (Button) findViewById(R.id.skip_btn);
        radioGroup.setOnCheckedChangeListener(this);
        mSkipBtn.setOnClickListener(this);
        getMyDrawable();
        pager = (ViewPager) findViewById(R.id.splashPager);
        PagerAdapter pagerAdapter = new SplashAdapter(getSupportFragmentManager());
        if (pager != null) {
            pager.setAdapter(pagerAdapter);
        }
        pager.setOnPageChangeListener(this);
    }

    private void getMyDrawable() {
        mListDrawble.add(R.layout.tutorial_1_layout);
        mListDrawble.add(R.layout.tutorial_2_layout);
        mListDrawble.add(R.layout.tutorial_3_layout);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        radioGroup.check(radioGroup.getChildAt(position).getId());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioBtnOne:
                mSkipBtn.setText("Пропустить");
                pager.setCurrentItem(0, true);
                break;
            case R.id.radioBtnTwo:
                mSkipBtn.setText("Пропустить");
                pager.setCurrentItem(1, true);
                break;
            case R.id.radioBtnThree:
                mSkipBtn.setText("Далее");
                pager.setCurrentItem(2, true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Intent startScreen = new Intent(TutorialActivity.this, StartScreen.class);
        startActivity(startScreen);
        finish();
    }

    private class SplashAdapter extends FragmentPagerAdapter {

        public SplashAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TutorialFragment.newInstance(position, mListDrawble);
        }

        @Override
        public int getCount() {
            return mListDrawble.size();
        }
    }
}
