package com.lq.lib.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lq.lib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * 主页底部tabBar
 * 自定义属性:
 * <declare-styleable name="HomeBottomTabBar">
 * <attr name="titleMarginTop" format="dimension" />
 * <attr name="titleTextSize_sp" format="integer" />
 * <attr name="titleTextDefaultColor" format="color" />
 * <attr name="titleTextSelectColor" format="color" />
 * <attr name="iconWidth" format="dimension" />
 * <attr name="iconHeight" format="dimension" />
 * </declare-styleable>
 * <p>
 * <p>
 * 使用:
 * 1.在布局中引用
 * 2.调用initData方法
 * 3.调用setViewPage方法
 *
 * @author:mick
 * @time:2018/11/14
 */
public class HomeBottomTabBar extends LinearLayout {
    private static final String TAG = "HomeBottomTabBar";
    /**
     * text默认与顶部图片的默认距离
     */
    private static final int DEFAULT_TITLE_MARGIN_TOP = 0;
    /**
     * text默认字体大小sp
     */
    private static final int DEFAULT_TITLE_TEXT_SIZE = 14;
    /**
     * text默认字体颜色
     */
    private static final String DEFAULT_TITLE_TEXT_COLOR = "#666666";
    /**
     * text默认选中后的字体颜色
     */
    private static final String DEFAULT_TITLE_TEXT_COLOR_SELECT = "#000000";

    private List<String> mtitles;
    private List<Integer> mDefaultIcons;
    private List<Integer> mSelectIcons;
    private Context mContext;

    private float textMarginTop = DEFAULT_TITLE_MARGIN_TOP;
    private int titleTextSize = DEFAULT_TITLE_TEXT_SIZE;
    private int titleTextDefaultColor = Color.parseColor(DEFAULT_TITLE_TEXT_COLOR);
    private int titleTextSelectColor = Color.parseColor(DEFAULT_TITLE_TEXT_COLOR_SELECT);
    private float iconWidth = -1;
    private float iconHeight = -1;

    private int currentIndex;
    private ViewPager mViewPager;
    private OnTabChangeListener mOnTabChangeListener;
    private HomeFragmentAdapter mHomeFragmentAdapter;

    public HomeBottomTabBar(Context context) {
        this(context, null);
    }

    public HomeBottomTabBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomTabBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        mContext = context;
        setGravity(Gravity.CENTER_VERTICAL);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HomeBottomTabBar);
            if (ta != null) {
                textMarginTop = ta.getDimensionPixelSize(R.styleable.HomeBottomTabBar_titleMarginTop, DEFAULT_TITLE_MARGIN_TOP);
                titleTextSize = ta.getInteger(R.styleable.HomeBottomTabBar_titleTextSize_sp, DEFAULT_TITLE_TEXT_SIZE);
                titleTextDefaultColor = ta.getColor(R.styleable.HomeBottomTabBar_titleTextDefaultColor, titleTextDefaultColor);
                titleTextSelectColor = ta.getColor(R.styleable.HomeBottomTabBar_titleTextSelectColor, titleTextSelectColor);
                iconWidth = ta.getDimension(R.styleable.HomeBottomTabBar_iconWidth, iconWidth);
                iconHeight = ta.getDimension(R.styleable.HomeBottomTabBar_iconHeight, iconHeight);

                ta.recycle();
            }
        }


    }

    /**
     * 初始化
     *
     * @param titles       标题文字数组
     * @param defaultIcons 平常情况的图片
     * @param selectIcons  选中状态下的图片
     */
    public void initData(List<String> titles,
                         List<Integer> defaultIcons,
                         List<Integer> selectIcons) {
        boolean listNotNone = titles != null && defaultIcons != null && selectIcons != null;
        if (!listNotNone) {
            Log.d("TAG", "请检查数组是否为空");
            return;
        }
        boolean sizeEqual = titles.size() == defaultIcons.size() && defaultIcons.size() == selectIcons.size();
        if (!sizeEqual) {
            Log.d("TAG", "请检查数组位数是否相等");
        }

        mtitles = titles;
        mDefaultIcons = defaultIcons;
        mSelectIcons = selectIcons;

        LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        for (int i = 0; i < mtitles.size(); i++) {
            LinearLayout item = createItem(mtitles.get(i), mDefaultIcons.get(i));
            item.setTag(i);
            addView(item, layoutParams);
        }

        setSelection(currentIndex);
    }

    private void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
        mOnTabChangeListener = onTabChangeListener;
    }

    public void setViewPager(ViewPager viewPager, List<Fragment> fragments) {
        this.setViewPager(viewPager, fragments, ((FragmentActivity) mContext).getSupportFragmentManager());
    }

    public void setViewPager(ViewPager viewPager, List<Fragment> fragments, FragmentManager fm) {
        checkInit();
        mViewPager = viewPager;
        mHomeFragmentAdapter = new HomeFragmentAdapter(fm, fragments);
        mViewPager.setAdapter(mHomeFragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setSelection(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        setOnTabChangeListener(new OnTabChangeListener() {
            @Override
            public void onTabChange(int newPos) {
                mViewPager.setCurrentItem(newPos, false);
            }
        });

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (int) v.getTag();
                    if (mOnTabChangeListener != null) {
                        mOnTabChangeListener.onTabChange(index);
                    }
                }
            });
        }
    }

    /**
     * 动态的创建tab的item
     *
     * @param title    标题
     * @param imgResId
     * @return
     */
    private LinearLayout createItem(String title, int imgResId) {
        LinearLayout itemLayout = new LinearLayout(mContext);
        itemLayout.setOrientation(LinearLayout.VERTICAL);
        itemLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(imgResId);

        TextView textView = new TextView(mContext);
        textView.setSingleLine();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize);
        textView.setTextColor(titleTextDefaultColor);
        textView.setText(title);

        itemLayout.addView(imageView);
        itemLayout.addView(textView);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) textMarginTop;
        textView.setLayoutParams(layoutParams);

        return itemLayout;
    }

    private void setSelection(int index) {
        checkInit();
        if (index >= 0 && index < mtitles.size()) {
            updateItemSelectUI(currentIndex, false);
            updateItemSelectUI(index, true);

            currentIndex = index;
        } else {
            Log.d("TAG", "设置位置的时候下表越界了");
        }
    }

    private void updateItemSelectUI(int index, boolean selected) {
        LinearLayout item = (LinearLayout) getChildAt(index);
        ImageView imageView = (ImageView) item.getChildAt(0);
        TextView textView = (TextView) item.getChildAt(1);

        if (selected) {
            imageView.setImageResource(mSelectIcons.get(index));
            textView.setTextColor(titleTextSelectColor);
        } else {
            imageView.setImageResource(mDefaultIcons.get(index));
            textView.setTextColor(titleTextDefaultColor);
        }
    }

    private void checkInit() {
        boolean listNotNone = mtitles != null && mDefaultIcons != null && mSelectIcons != null;
        if (!listNotNone) {
            throw new ExceptionInInitializerError("使用前请先调用initData初始化");
        }
    }


    interface OnTabChangeListener {
        void onTabChange(int newPos);
    }

    /***===============================================适配器========================*****======*/
    class HomeFragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> mFragments = new ArrayList<>();
        ViewPager mViewPager;

        public HomeFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
