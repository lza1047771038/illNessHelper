package wust.student.illnesshepler.CustomViews;

import android.content.Context;
import android.util.AttributeSet;

import androidx.core.widget.NestedScrollView;

public class MyNestScrollView extends NestedScrollView {

    private MyScrollViewListener scrollViewListener = null;

    public void setScrollViewListener(MyScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY > 6000 ? 6000 : velocityY / 7 * 5);
    }


    public MyNestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public interface MyScrollViewListener {
        void onScrollChanged(MyNestScrollView scrollView, int x, int y, int oldx, int oldy);
    }

}
