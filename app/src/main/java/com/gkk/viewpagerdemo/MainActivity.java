package com.gkk.viewpagerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private TextView title;
    private LinearLayout points;
    private List<ImageView> viewList;
    int[] imgs = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};
    String[] titles = {"干露露", "白云飞", "电影节", "乐视网", "劳动节"};
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initDate();
        initEvent();
    }

    private void initEvent() {
        viewPager.setOnPageChangeListener(this);
        int middle = Integer.MAX_VALUE / 2;
        int extra = middle % viewList.size();
        int item = middle - extra;
        viewPager.setCurrentItem(item);
    }

    private void initDate() {
        viewList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(imgs[i]);
            iv.setScaleType(ScaleType.FIT_XY);
            viewList.add(iv);

            View point = new View(this);
            point.setBackgroundResource(R.drawable.point_normal);//设置点的背景
            LayoutParams params = new LayoutParams(10, 10);//设置点的大小
            if (i != 0) {
                params.leftMargin = 10;
            } else {
                point.setBackgroundResource(R.drawable.point_select);
                title.setText(titles[i]);
            }
            points.addView(point, params);
        }
        viewPager.setAdapter(new MyAdapter());
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.vp_pager);
        title = (TextView) findViewById(R.id.tv_title);
        points = (LinearLayout) findViewById(R.id.ll_point_content);
    }

    /**
     * 回调方法，当viewPager滚动的时候回调
     *
     * @param position             当前选中的位置
     * @param positionOffset       滑动的百分比
     * @param positionOffsetPixels 滑动的像素
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 回调方法，当viewPager选中的时候回调
     *
     * @param position 滑动之后显示的页面
     */
    @Override
    public void onPageSelected(int position) {
        position %= viewList.size();
        int childCount = points.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = points.getChildAt(i);
            view.setBackgroundResource(position == i ? R.drawable.point_select : R.drawable.point_normal);
        }
        title.setText(titles[position]);
    }

    /**
     * 回调方法，当viewPager滑动状态改变的时候回调
     *
     * @param state 一共有三种状态
     * @see ViewPager#SCROLL_STATE_IDLE = 0      闲置状态
     * @see ViewPager#SCROLL_STATE_DRAGGING = 1  拖动状态
     * @see ViewPager#SCROLL_STATE_SETTLING = 2  固定状态
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyAdapter extends PagerAdapter {

        //返回总页面的数量
        @Override
        public int getCount() {
            if (viewList != null) {
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        /**
         * 标记方法，用来判断缓存标记
         *
         * @param view   显示的view
         * @param object 标记
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 初始化item的方法
         *
         * @param container
         * @param position  要加载的位置
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= viewList.size();
            ImageView imageView = viewList.get(position);
            //添加显示的view
            viewPager.addView(imageView);
            //记录标记--return标记
            return imageView;
        }

        //销毁item的方法
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            position %= viewList.size();
            ImageView imageView = viewList.get(position);
            //移除view
            viewPager.removeView(imageView);
        }
    }
}
