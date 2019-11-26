package wust.student.illnesshepler.CustomViews;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;
/*
艾科
解决在viewpager中对图片进行多指触控，放大缩小导致闪退问题
 */
public class FixedViewPager extends ViewPager {
    public FixedViewPager(Context context) {
        super(context);
    }

    public FixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super .dispatchTouchEvent(ev);
        } catch (IllegalArgumentException ignored) {
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        return false ;

    }
}
