package com.example.lizhijun.stickytabs.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lizhijun.stickytabs.fragments.ContactDetailBaseFragment;
import com.example.lizhijun.stickytabs.fragments.ContactDetailFragment;
import com.example.lizhijun.stickytabs.R;
import com.example.lizhijun.stickytabs.util.SystemUtils;
import com.example.lizhijun.stickytabs.view.ContactContentView;
import com.example.lizhijun.stickytabs.view.SlidingTabLayout;

public class MainActivity extends ActionBarActivity implements OnClickListener
        , OnPageChangeListener {
    private ImageButton mCloseBtn = null;
    private ImageButton mMoreBtn = null;
    private TextView mContactNameTv = null;
    private ImageView mTopBg = null;
    private SlidingTabLayout mIndicator = null;
    private ViewPager mViewPager = null;
    private ContactContentView mContentView = null;
    private ImageView mContactCallIv = null;
    private ImageView mContactSmsIv = null;

    private ContactFragmentAdapter mAdapter = null;
    private String[] mTabTitle = null;

    private int mNoteCount = 0;
    private int mAttachCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    protected void init() {
        initView();
        initViewPager();
    }

    protected void initView() {
        // actionbar
        View customView = LayoutInflater.from(this).inflate(
                R.layout.custom_action_bar, null);
        mCloseBtn = (ImageButton) customView.findViewById(R.id.left_btn);
        mCloseBtn.setOnClickListener(this);
        mMoreBtn = (ImageButton) customView.findViewById(R.id.right_btn);
        mMoreBtn.setOnClickListener(this);
        mContactNameTv = (TextView) customView.findViewById(R.id.center_tv);
        mContactNameTv.setVisibility(View.VISIBLE);
        mContactNameTv.setText("johnson");
        getSupportActionBar().setCustomView(customView);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        mContentView = (ContactContentView) findViewById(R.id.contact_content);
        mContactCallIv = (ImageView) findViewById(R.id.contact_call);
        mContactCallIv.setOnClickListener(this);
        mContactSmsIv = (ImageView) findViewById(R.id.contact_sms);
        mContactSmsIv.setOnClickListener(this);
        mTopBg = (ImageView) findViewById(R.id.top_bg);
        initViewPager();
        initTopBgView();
    }

    public void initViewPager() {
        mTabTitle = getResources().getStringArray(R.array.contact_detial_tab_text);
        mAdapter = new ContactFragmentAdapter(getSupportFragmentManager());
        mIndicator = (SlidingTabLayout) findViewById(R.id.contact_indicator);
        mViewPager = (ViewPager) findViewById(R.id.contact_pager);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setOnPageChangeListener(this);
        mIndicator.setTabText(1, spanTabText(1, mTabTitle[1], mNoteCount++));
        mIndicator.setTabText(3, spanTabText(3, mTabTitle[3], mAttachCount++));
    }

    public SpannableString spanTabText(int index, String title, int num) {
        SpannableString tabContent = new SpannableString(title + " " + num);
        tabContent.setSpan(new ForegroundColorSpan(Color.parseColor("#ffb2b2b2")),
                title.length()+1, tabContent.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return tabContent;
    }

    public void initTopBgView() {
        int actionbarSize = SystemUtils.getActionbarSize(this);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)
                mTopBg.getLayoutParams();
        if(params == null) {
            params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
        }
        params.topMargin = -actionbarSize;
        mTopBg.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.left_btn:
                this.finish();
                break;
            case R.id.right_btn:
                break;
            default:
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if(SystemUtils.clickInView(ev, mContentView)) {
            mContentView.addEventToTracker(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public class ContactFragmentAdapter extends FragmentPagerAdapter {
        SparseArray<ContactDetailBaseFragment> detailFragments = new SparseArray<ContactDetailBaseFragment>();
        public ContactFragmentAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            detailFragments.put(position, (ContactDetailBaseFragment) fragment);
            return fragment;
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 0:
                    return ContactDetailFragment.newInstance(true);
                case 1:
                    return ContactDetailFragment.newInstance(false);
                case 2:
                    return ContactDetailFragment.newInstance(false);
                case 3:
                    return ContactDetailFragment.newInstance(false);
                case 4:
                    return ContactDetailFragment.newInstance(false);
                default:
                    break;
            }
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            detailFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub
            return mTabTitle[position];
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mTabTitle == null ? 0 : mTabTitle.length;
        }

        public ContactDetailBaseFragment getCurrentFragment(int position) {
            return detailFragments.get(position);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        ContactDetailBaseFragment fragment = mAdapter.getCurrentFragment(mViewPager.getCurrentItem());
        if(fragment != null) {
            ListView contactDetailList = fragment.getCurrentList();
            if(contactDetailList != null) {
                contactDetailList.setSelection(0);
            }
        }
        if(mNoteCount >= 10 || mAttachCount >= 10) {
            return ;
        }
        mIndicator.setTabText(1, spanTabText(1, mTabTitle[1], mNoteCount++));
        mIndicator.setTabText(3, spanTabText(3, mTabTitle[3], mAttachCount++));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }

    public boolean isListTop() {
        ContactDetailBaseFragment fragment = mAdapter.getCurrentFragment(mViewPager.getCurrentItem());
        if(fragment != null) {
            ListView contactDetailList = fragment.getCurrentList();
            if(contactDetailList != null) {
                if(contactDetailList.getChildCount() == 0) {
                    return true;
                }
                return contactDetailList.getChildAt(0).getTop() == 0;
            }
        }
        return false;
    }

    public void setListEnable(boolean enable) {
        ContactDetailBaseFragment fragment = mAdapter.getCurrentFragment(mViewPager.getCurrentItem());
        if(fragment != null) {
            ListView contactDetailList = fragment.getCurrentList();
            if(contactDetailList != null) {
                contactDetailList.setEnabled(enable);
            }
        }
    }
}
