package com.ahdz.oricalshelves.main.auth.activity;

import android.content.Intent;
import android.view.KeyEvent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.base.BaseActivity;
import com.ahdz.oricalshelves.databinding.ActivityHomePageBinding;
import com.ahdz.oricalshelves.main.auth.fragment.home.HomepageFragment;
import com.ahdz.oricalshelves.main.auth.fragment.mine.MineFragment;
import com.ahdz.oricalshelves.main.auth.fragment.shop.MallFragment;
import com.ahdz.oricalshelves.view.slidview.DataHolder;
import com.ahdz.oricalshelves.view.slidview.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity<ActivityHomePageBinding> {


    private HomePagePresent mPresent;
    private List<Fragment> fragmentList = new ArrayList<>();


    @Override
    protected int setLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    protected void initData() {
        mPresent = new HomePagePresent(this,mBindingView);
        mBindingView.setViewModule(mPresent);



        fragmentList.add(HomepageFragment.newInstance());//
        fragmentList.add(MallFragment.newInstance());//
        fragmentList.add(MineFragment.newInstance());//

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };
        mBindingView.viewPager.setAdapter(adapter);

        List<DataHolder> dataHolders = new ArrayList<>();

        dataHolders.add(new DataHolder(getResources().getDrawable(R.mipmap.hd_shouye_gray), getResources().getDrawable(R.mipmap.hd_shouye_char),
                "首页", getResources().getColor(R.color.colorGary)));
        dataHolders.add(new DataHolder(getResources().getDrawable(R.mipmap.hd_shopping_gray),  getResources().getDrawable(R.mipmap.hd_shopping_char),
                "超市", getResources().getColor(R.color.colorGary)));
        dataHolders.add(new DataHolder(getResources().getDrawable(R.mipmap.hd_mine_gray),  getResources().getDrawable(R.mipmap.hd_mine_char),
                "我的", getResources().getColor(R.color.colorGary)));

        mBindingView.slidView.setUpViewPager( mBindingView.viewPager, dataHolders);
        mBindingView.viewPager.setOffscreenPageLimit(5);



        //

    }

    private long time;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (System.currentTimeMillis() - time > 2000) {
                time = System.currentTimeMillis();
                showToast("再点击一次退出程序");
            } else {
                Intent intent = new Intent(BASE_ADDRESS);
                intent.putExtra(CLOSE_ALL, 1);
                //发送广播
                sendBroadcast(intent);
            }
        }
        return true;
    }
}
