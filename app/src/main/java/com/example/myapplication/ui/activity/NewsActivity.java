package com.example.myapplication.ui.activity;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.ui.fragment.FindFragment;
import com.example.myapplication.ui.fragment.NewFragment;
import com.example.myapplication.ui.fragment.ReadFragment;
import com.example.myapplication.ui.fragment.SettingFragment;
import com.example.myapplication.ui.fragment.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends BaseActivity {

    private RadioGroup rg01;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView ;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main_option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item_01){
            showToast("item 01");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getLayoutR(int id) {
        return R.layout.activity_news;
    }

    @Override
    protected void initViews() {
        rg01 = (RadioGroup) findViewById(R.id.rg_01);
        initViewPager();
        initNavigationView();
        initToolbar();
        initDrawerLayout();
    }

    private void initDrawerLayout() {
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.navigation_drawer_open,R.string.navigation_draw_close);
        mDrawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();
    }

    private void initToolbar() {
        mToolbar= (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
    }

    private void initNavigationView() {
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView= (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    //显示ViewPager
    private void initViewPager() {
        mViewPager= (ViewPager) findViewById(R.id.view_pager_01);
        final List<Fragment> fragments=new ArrayList<>();
        fragments.add(new NewFragment());
        fragments.add(new VideoFragment());
        fragments.add(new ReadFragment());
        fragments.add(new FindFragment());
        fragments.add(new SettingFragment());

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    protected void initListener() {
        //关联ViewPager与RadioButton选项卡的状态
        //当点击RadioButton时，ViewPager显示的子界面工切换， 代码实现如下：
        rg01.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_new:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_video:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_read:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_find:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.rb_setting:
                        mViewPager.setCurrentItem(4);
                        break;
                }
            }
        });
        //当ViewPager子界面发生了改变时，要选中并高亮不同的RadioButton选项卡，代码实现如下
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rg01.check(R.id.rb_new);
                        break;
                    case 1:
                        rg01.check(R.id.rb_video);
                        break;
                    case 2:
                        rg01.check(R.id.rb_read);
                        break;
                    case 3:
                        rg01.check(R.id.rb_find);
                        break;
                    case 4:
                        rg01.check(R.id.rb_setting);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initDate() {

    }

}
