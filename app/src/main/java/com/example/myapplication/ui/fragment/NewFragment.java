package com.example.myapplication.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 雪无痕 on 2017/6/27.
 */

public class NewFragment extends BaseFragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initViews() {
        mTabLayout= (TabLayout) mRootView.findViewById(R.id.tab_layout);
        mViewPager= (ViewPager) mRootView.findViewById(R.id.view_pager_02);

        initViewPager();
    }

    private void initViewPager() {
        final String[] titles=new String[]{
          "头条","社会","科技","财经","体育","汽车"
        };

        final String[] channelId = new String[] {
                "T1348647909107",
                "T1348648037603",
                "T1348649580692",
                "T1348648756099",
                "T1348649079062",
                "T1348654060988",
        };
        final List<Fragment> mFragments=new ArrayList<>();
        for (int i = 0; i <titles.length ; i++) {
            ChildNewsFragment childNewsFragment=new ChildNewsFragment();
            childNewsFragment.setChannelId(channelId[i]);
            mFragments.add(childNewsFragment);
        }
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initDate() {

    }


}
