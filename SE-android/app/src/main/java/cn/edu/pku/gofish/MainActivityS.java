package cn.edu.pku.gofish;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by leonardo on 16/5/10.
 */
public class MainActivityS extends AppCompatActivity {
    private FragmentHomepage fragmentHomepage;
    private FragmentMessage fragmentMessage;
    private FragmentFavorite fragmentFavorite;
    private FragmentMe fragmentMe;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_s);


        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        /*
        tabLayout.addTab(tabLayout.newTab().setText("主页").setIcon(R.drawable.icon_homepage_black));
        tabLayout.addTab(tabLayout.newTab().setText("消息").setIcon(R.drawable.icon_message_black));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_add_black));
        tabLayout.addTab(tabLayout.newTab().setText("收藏").setIcon(R.drawable.icon_heart_black));
        tabLayout.addTab(tabLayout.newTab().setText("我的").setIcon(R.drawable.icon_me_black));
        */
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        SampleFragmentPagerAdapter pagerAdapter =
                new SampleFragmentPagerAdapter(getSupportFragmentManager(), this);

        viewPager.setAdapter(pagerAdapter);


        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(pagerAdapter.getTabView(i));
            }
        }

        viewPager.setCurrentItem(0);

    }
    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 5;
        private String tabTitles[] = new String[]{"主页","消息","添加","收藏","我的"};
        private Context context;


        public View getTabView(int position) {
            View v;
            TextView tv;
            ImageView img;
            switch (position){
                case 0:
                    v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
                    tv = (TextView) v.findViewById(R.id.textView);
                    tv.setText("主页");
                    img = (ImageView) v.findViewById(R.id.imageView);
                    img.setImageResource(R.drawable.icon_homepage_select);
                    return v;
                case 1:
                    v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
                    tv = (TextView) v.findViewById(R.id.textView);
                    tv.setText("消息");
                    img = (ImageView) v.findViewById(R.id.imageView);
                    img.setImageResource(R.drawable.icon_message_select);
                    return v;
                case 2:
                    v = LayoutInflater.from(context).inflate(R.layout.custom_tab2, null);
                    img = (ImageView) v.findViewById(R.id.imageView);
                    img.setImageResource(R.drawable.icon_add_black);
                    return v;
                case 3:
                    v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
                    tv = (TextView) v.findViewById(R.id.textView);
                    tv.setText("收藏");
                    img = (ImageView) v.findViewById(R.id.imageView);
                    img.setImageResource(R.drawable.icon_favorite_select);
                    return v;
                case 4:
                    v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
                    tv = (TextView) v.findViewById(R.id.textView);
                    tv.setText("我的");
                    img = (ImageView) v.findViewById(R.id.imageView);
                    img.setImageResource(R.drawable.icon_me_select);
                    return v;
                default:
                    return null;
            }

            /*View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) v.findViewById(R.id.textView);
            tv.setText(tabTitles[position]);
            ImageView img = (ImageView) v.findViewById(R.id.imageView);
            //img.setImageResource(imageResId[position]);
            return v;*/
        }

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if(fragmentHomepage == null)
                    {
                        fragmentHomepage = new FragmentHomepage();
                    }
                    return fragmentHomepage;
                case 1:
                    if(fragmentMessage == null)
                    {
                        fragmentMessage = new FragmentMessage();
                    }
                    return fragmentMessage;
                case 2:
                    if(fragmentFavorite == null)
                    {
                        fragmentFavorite = new FragmentFavorite();
                    }
                    return fragmentFavorite;
                case 3:
                    if(fragmentFavorite == null)
                    {
                        fragmentFavorite = new FragmentFavorite();
                    }
                    return fragmentFavorite;
                case 4:
                    if(fragmentMe == null)
                    {
                        fragmentMe = new FragmentMe();
                    }
                    return fragmentMe;
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
    class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    if(fragmentHomepage == null)
                    {
                        fragmentHomepage = new FragmentHomepage();
                    }
                    return fragmentHomepage;
                case 1:
                    if(fragmentMessage == null)
                    {
                        fragmentMessage = new FragmentMessage();
                    }
                    return fragmentMessage;
                case 2:
                    if(fragmentFavorite == null)
                    {
                        fragmentFavorite = new FragmentFavorite();
                    }
                    return fragmentFavorite;
                case 3:
                    if(fragmentFavorite == null)
                    {
                        fragmentFavorite = new FragmentFavorite();
                    }
                    return fragmentFavorite;
                case 4:
                    if(fragmentMe == null)
                    {
                        fragmentMe = new FragmentMe();
                    }
                    return fragmentMe;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

}
