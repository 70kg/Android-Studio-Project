package com.com.mr_wrong.GaoFeng;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mr_wrong.androidstudioproject.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by Mr_Wrong on 15/7/6.
 */
public class GFMainActivity extends FragmentActivity implements View.OnClickListener {
    @InjectView(R.id.id_viewpager)
    ViewPager mViewpager;
    @InjectView(R.id.id_tab_weixin_img)
    ImageButton mImageButton1;
    @InjectView(R.id.id_tab_weixin)
    LinearLayout mLyout1;
    @InjectView(R.id.id_tab_frd_img)
    ImageButton mImageButton2;
    @InjectView(R.id.id_tab_frd)
    LinearLayout mLyout2;
    @InjectView(R.id.id_tab_address_img)
    ImageButton mImageButton3;
    @InjectView(R.id.id_tab_address)
    LinearLayout mLyout3;
    @InjectView(R.id.id_tab_settings_img)
    ImageButton mImageButton4;
    @InjectView(R.id.id_tab_settings)
    LinearLayout mLyout4;

    List<Fragment> mFragments;
    FragmentPagerAdapter mAdapter;

    @InjectView(R.id.tv_top)
    TextView mTopText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gf_main);
        ButterKnife.inject(this);
        init();

    }

    private void init() {
        mFragments = new ArrayList<Fragment>();
        Fragmen1 fragmen1 = new Fragmen1();
        Fragmen2 fragmen2 = new Fragmen2();
        Fragmen3 fragmen3 = new Fragmen3();
        Fragmen4 fragmen4 = new Fragmen4();

        mFragments.add(fragmen1);
        mFragments.add(fragmen2);
        mFragments.add(fragmen3);
        mFragments.add(fragmen4);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        mViewpager.setAdapter(mAdapter);

        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                int currentitem = mViewpager.getCurrentItem();
                setTab(currentitem);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mLyout1.setOnClickListener(this);
        mLyout2.setOnClickListener(this);
        mLyout3.setOnClickListener(this);
        mLyout4.setOnClickListener(this);
    }

    private void setTab(int i) {
        resetImage();

        switch (i) {
            case 0:
                mImageButton1.setImageResource(R.drawable.tab_weixin_pressed);
                mTopText.setText("精品推荐");
                break;
            case 1:
                mImageButton2.setImageResource(R.drawable.tab_find_frd_pressed);
                mTopText.setText("理财列表");
                break;
            case 2:
                mImageButton3.setImageResource(R.drawable.tab_address_pressed);
                mTopText.setText("我的财富");
                break;
            case 3:
                mImageButton4.setImageResource(R.drawable.tab_settings_pressed);
                mTopText.setText("个人中心");
                break;
        }

    }

    private void resetImage() {
        mImageButton1.setImageResource(R.drawable.tab_weixin_normal);
        mImageButton2.setImageResource(R.drawable.tab_find_frd_normal);
        mImageButton3.setImageResource(R.drawable.tab_address_normal);
        mImageButton4.setImageResource(R.drawable.tab_settings_normal);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_tab_weixin:
                setselect(0);
                break;
            case R.id.id_tab_frd:
                setselect(1);

                break;
            case R.id.id_tab_address:
                setselect(2);

                break;
            case R.id.id_tab_settings:
                setselect(3);

                break;
        }
    }

    private void setselect(int i) {
        setTab(i);
        mViewpager.setCurrentItem(i);
    }
}
