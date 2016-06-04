package com.lanou3g.autohome.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by dllo on 16/5/30.
 */
public class MyDraweable extends Drawable {

    private Paint paint;//画笔
    private Bitmap bitmap;//要操作的Bitmap
    private RectF rectF;//矩形

    public MyDraweable(Bitmap bitmap) {
        this.bitmap = bitmap;
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        //位图渲染器
        //参数1  我们要操作的bitmap
        //参数2 3 x轴,y轴图片的填充类型
        //类型一共分为三种 :
        // REPEAT重复类型 CLAMP拉伸类型 (注意:这里的拉伸是指拉伸图片的最后一个像素) MIRROR镜像类型
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT,Shader.TileMode.REPEAT);
        paint.setShader(shader);//把shader设置到画笔上
    }

    /**
     * 这个方法是指draweable将被绘制在画布上的区域
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        rectF = new RectF(left,top,right,bottom);
    }

    /**
     * 获取Bitmap的高度
     * @return
     */
    @Override
    public int getIntrinsicHeight() {
        return bitmap.getHeight();

    }

    /**
     * 获取Bitmap的宽度
     * @return
     */
    @Override
    public int getIntrinsicWidth() {
        return bitmap.getWidth();
    }


    /**
     * 这是我们核心的方法 绘制我们想要的图片
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        //参数1  绘制的区域
        //参数2  x轴圆角半径
        //参数3  y轴圆角半径
        //参数4  画笔
        //canvas.drawRoundRect(rectF,120,120,paint);
        //参数1 2 是圆心坐标  3是半径
        canvas.drawCircle(getIntrinsicWidth()/2,getIntrinsicHeight()/2,getIntrinsicWidth()/2,paint);
    }

    /**
     * 设置透明度
     * @param alpha
     */
    @Override
    public void setAlpha(int alpha) {

    }

    /**
     * 设置滤镜,渲染颜色
     * @param colorFilter
     */
    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    /**
     * 获取透明度
     * @return
     */
    @Override
    public int getOpacity() {
        return 0;
    }
}
