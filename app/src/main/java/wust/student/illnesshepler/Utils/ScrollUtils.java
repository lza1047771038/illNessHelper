package wust.student.illnesshepler.Utils;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 滑动效果辅助类
 */
public final class ScrollUtils {
    /**
     * 私有构造防止new实例
     */
    private ScrollUtils() {
    }

    /**
     * 返回一个有效值，该值的范围在【minValue,MaxValue】
     */
    public static float getFloat(final float value, final float minValue, final float maxValue) {
        return Math.min(maxValue, Math.max(minValue, value));
    }

    /**
     * 创建一个带透明度的color的颜色值，透明度范围0-255
     * 背景色改变用到它
     * @param alpha    透明度
     * @param baseColor 传入颜色值
     */
    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

    /**
     * 为视图添加监听，不需要的时候需要移除监听器
     * @param view     监听的目标视图
     * @param runnable Runnable to be executed after the view is laid out.
     */
    public static void addOnGlobalLayoutListener(final View view, final Runnable runnable) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                runnable.run();
            }
        });
    }

    /**
     * 混合两种颜色。并指定透明度
     * @param fromColor
     * @param toColor   .
     * @param toAlpha
     * @return Mixed color value in ARGB. Alpha is fixed value (255).
     */
    public static int mixColors(int fromColor, int toColor, float toAlpha) {
        float[] fromCmyk = cmykFromRgb(fromColor);
        float[] toCmyk = cmykFromRgb(toColor);
        float[] result = new float[4];
        for (int i = 0; i < 4; i++) {
            result[i] = Math.min(1, fromCmyk[i] * (1 - toAlpha) + toCmyk[i] * toAlpha);
        }
        return 0xff000000 + (0x00ffffff & ScrollUtils.rgbFromCmyk(result));
    }

    /**
     * RGB颜色转换为CMYK颜色。
     *
     * @param rgbColor Target color.
     * @return CMYK array.
     */
    public static float[] cmykFromRgb(int rgbColor) {
        int red = (0xff0000 & rgbColor) >> 16;
        int green = (0xff00 & rgbColor) >> 8;
        int blue = (0xff & rgbColor);
        float black = Math.min(1.0f - red / 255.0f, Math.min(1.0f - green / 255.0f, 1.0f - blue / 255.0f));
        float cyan = 1.0f;
        float magenta = 1.0f;
        float yellow = 1.0f;
        if (black != 1.0f) {
            // black 1.0 causes zero divide
            cyan = (1.0f - (red / 255.0f) - black) / (1.0f - black);
            magenta = (1.0f - (green / 255.0f) - black) / (1.0f - black);
            yellow = (1.0f - (blue / 255.0f) - black) / (1.0f - black);
        }
        return new float[]{cyan, magenta, yellow, black};
    }

    /**
     * CYMK颜色转换为RGB颜色。
     * 这个方法不会检查是否非空cmyk或有4个元素的数组。
     *
     * @param cmyk 目标CYMK颜色。每个值应该在0.0到1.0 f之间, 应设置在这个秩序:青色,品红色,黄色,黑色。
     * @return ARGB color. Alpha is fixed value (255).
     */
    public static int rgbFromCmyk(float[] cmyk) {
        float cyan = cmyk[0];
        float magenta = cmyk[1];
        float yellow = cmyk[2];
        float black = cmyk[3];
        int red = (int) ((1.0f - Math.min(1.0f, cyan * (1.0f - black) + black)) * 255);
        int green = (int) ((1.0f - Math.min(1.0f, magenta * (1.0f - black) + black)) * 255);
        int blue = (int) ((1.0f - Math.min(1.0f, yellow * (1.0f - black) + black)) * 255);
        return ((0xff & red) << 16) + ((0xff & green) << 8) + (0xff & blue);
    }
}