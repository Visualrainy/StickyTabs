package com.example.lizhijun.stickytabs.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.lizhijun.stickytabs.activity.MainActivity;
import com.example.lizhijun.stickytabs.util.Logger;
import com.example.lizhijun.stickytabs.util.SystemUtils;


public class ContactContentView extends LinearLayout {
    private Context mContext = null;
    private boolean mOnce = false;

    private boolean mClose = false;
    private boolean mCanSliding = false;
    public boolean mSliding = false;
    private int mTouchSlop = 0;
    private float mDownX = 0;
    private float mDownY = 0;
    private ViewGroup mMainLayout = null;
    private int mTopLayoutHeight = 0;
    private ViewPager mDetailPager = null;
    private SlidingTabLayout mIndicator = null;
    private Scroller mScroller = null;
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private VelocityTracker mTracker = null;

    public ContactContentView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public ContactContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    protected void init(Context context) {
        mContext = context;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaxFlingVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity() * 10;
        mScroller = new Scroller(context);
    }

    private int mIdicatorHeight = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!mOnce) {
            mMainLayout = (ViewGroup) getParent();
            mTopLayoutHeight = mMainLayout.getChildAt(0).getMeasuredHeight();
            getLayoutParams().height = getMeasuredHeight() + mTopLayoutHeight;
            mIndicator = (SlidingTabLayout) getChildAt(0);
            mIdicatorHeight = mIndicator.getMeasuredHeight();
            mDetailPager = (ViewPager) getChildAt(1);
            mOnce = true;
        }
    }

    boolean first = false;
    boolean mFirstDisallowDispatch = true;
    boolean mFirstTop = true;
    float mLastX = 0;
    float mLastY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                mLastX = mDownX;
                mLastY = mDownY;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltDX = ev.getRawX() - mDownX;
                float deltDY = ev.getRawY() - mDownY;
                float deltX = ev.getRawY() - mLastX;
                float deltY = ev.getRawY() - mLastY;
                if (!mClose) {
                    ((MainActivity) getContext()).setListEnable(false);
                } else {
                    ((MainActivity) getContext()).setListEnable(true);
                }
                if (((MainActivity) getContext()).isListTop() && ((Math.abs(deltDY) >= Math.abs(deltDX))
                        || (Math.abs(deltY) >= Math.abs(deltX))) || mSliding) {
                    if (!(deltDY < 0 && mClose && !mSliding)) {
                        mSliding = true;
                        handleMove(ev);
                    }
                    if (mSliding) {
                        mDetailPager.scrollTo(mDetailPager.getCurrentItem() * SystemUtils.getScreenWidth(), 0);
                        mIndicator.mTabStrip.onViewPagerPageChanged(mDetailPager.getCurrentItem(), 0);
                        mLastX = ev.getRawX();
                        mLastY = ev.getRawY();
                        return true;
                    }
                }
                mLastX = ev.getRawX();
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                Logger.d("MotionEvent_ACTION: " + ev.getAction());
                handleUp();
                first = false;
                mFirstDisallowDispatch = false;
                mCanSliding = false;
                recycleTracker();
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void handleMove(MotionEvent ev) {
        float deltY = ev.getRawY() - mLastY;
        if (deltY > 0) {
            if (mMainLayout.getScrollY() - deltY <= 0) {
                deltY = mMainLayout.getScrollY();
                mSliding = false;
                mClose = false;
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                MotionEvent event = MotionEvent.obtain(ev);
                event.setAction(MotionEvent.ACTION_CANCEL |
                        (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                mDetailPager.dispatchTouchEvent(event);
                event.setAction(MotionEvent.ACTION_DOWN |
                        (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                event.setLocation(ev.getX(), ev.getY() - mIdicatorHeight);
                mDetailPager.dispatchTouchEvent(event);
                event.recycle();
            }
//			else {
//				deltY = mTopLayoutHeight - deltY;
//			}
        } else {
            if (mMainLayout.getScrollY() + Math.abs(deltY) >= mTopLayoutHeight) {
//				deltY = mTopLayoutHeight;
                deltY = mMainLayout.getScrollY() - mTopLayoutHeight;
                mClose = true;
                mSliding = false;
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                MotionEvent event = MotionEvent.obtain(ev);
                event.setAction(MotionEvent.ACTION_CANCEL |
                        (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                mDetailPager.dispatchTouchEvent(event);
                event.setAction(MotionEvent.ACTION_DOWN |
                        (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                event.setLocation(ev.getX(), ev.getY() - mIdicatorHeight);
                mDetailPager.dispatchTouchEvent(event);
                event.recycle();
            }
        }
        Logger.i("final deltY : " + deltY);
        mMainLayout.scrollTo(0, (int) (mMainLayout.getScrollY() - deltY));
    }

    public void handleUp() {
        mTracker.computeCurrentVelocity(1000);
        float velocityX = mTracker.getXVelocity();
        float velocityY = mTracker.getYVelocity();
        if (Math.abs(velocityY) > Math.abs(velocityX) && Math.abs(velocityY) > mMinFlingVelocity &&
                Math.abs(velocityY) < mMaxFlingVelocity && mSliding) {
            if (velocityY > 0) {
                expandLayout((int) (Math.abs(mMainLayout.getScrollY()) * 300.0 / mTopLayoutHeight));
            } else {
                shrinkLayout((int) (Math.abs(mTopLayoutHeight - mMainLayout.getScrollY()) * 300.0 / mTopLayoutHeight));
            }
            return;
        }
        if (mMainLayout.getScrollY() <= mTopLayoutHeight / 2) {
            //展开top
            expandLayout(-1);
        } else {
            //收缩top
            shrinkLayout(-1);
        }
    }

    public void addEventToTracker(MotionEvent ev) {
        if (mTracker == null) {
            mTracker = VelocityTracker.obtain();
        }
        mTracker.addMovement(ev);
    }

    public void recycleTracker() {
        if (mTracker != null) {
            mTracker.recycle();
            mTracker = null;
        }
    }

    public void expandLayout(int duration) {
        if (duration == -1) {
            duration = 300;
        }
        mScroller.startScroll(0, mMainLayout.getScrollY(), 0, -mMainLayout.getScrollY(), duration);
        postInvalidate();
    }

    public void shrinkLayout(int duration) {
        if (duration == -1) {
            duration = 300;
        }
        mScroller.startScroll(0, mMainLayout.getScrollY(), 0, mTopLayoutHeight - mMainLayout.getScrollY(), duration);
        postInvalidate();
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        if (mScroller.computeScrollOffset()) {
            mMainLayout.scrollTo(0, mScroller.getCurrY());
            postInvalidate();

            if (mScroller.isFinished()) {
                mClose = mMainLayout.getScrollY() == 0 ? false : true;
                mSliding = false;
            }
        }
    }


}
