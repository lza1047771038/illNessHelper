package wust.student.illnesshepler.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isCanScroll = false;
    private boolean isHasScrollAnim=false;


    /**
     * 设置其是否能滑动
     * @param isCanScroll false 禁止滑动， true 可以滑动
     */
    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    public void setHasScrollAnim(boolean isHasScrollAnim){
        this.isHasScrollAnim=isHasScrollAnim;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    /**
     * 设置其是否去求切换时的滚动动画
     *isHasScrollAnim为false时，会去除滚动效果
     */
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,isHasScrollAnim);
    }
}
