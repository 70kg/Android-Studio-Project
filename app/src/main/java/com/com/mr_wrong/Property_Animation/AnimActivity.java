package com.com.mr_wrong.Property_Animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.Utils.LogUtils;
import com.example.mr_wrong.androidstudioproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Mr_Wrong on 15/7/3.
 */
public class AnimActivity extends BaseActivity {

    @InjectView(R.id.iv_anim)
    ImageView mTestImage;
    @InjectView(R.id.bt_ObjectAnimator)
    Button btObjectAnimator;
    @InjectView(R.id.bt_1)
    Button bt1;
    @InjectView(R.id.bt_2)
    Button bt2;
    @InjectView(R.id.bt_3)
    Button bt3;
    @InjectView(R.id.bt_4)
    Button bt4;
    @InjectView(R.id.bt_5)
    Button bt5;
    @InjectView(R.id.bt_6)
    Button bt6;
    @InjectView(R.id.bt_7)
    Button bt7;
    @InjectView(R.id.seekBar)
    SeekBar seekBar;
    @InjectView(R.id.sb_red)
    SeekBar sb_Red;
    @InjectView(R.id.sb_green)
    SeekBar sb_Green;
    @InjectView(R.id.sb_blue)
    SeekBar sb_Blue;
    @InjectView(R.id.sb_alpha)
    SeekBar sb_Alpha;
    @InjectView(R.id.tv_color)
    TextView tv_Color;

    @OnClick(R.id.bt_ObjectAnimator)
    public void sayHi(Button button) {
//        ObjectAnimator.ofFloat(mTestImage,"rotationY",0.0f,360f)
//                .setDuration(500)
//                .start();
        // 使用objanimator实现多个属性
//        ObjectAnimator animator = ObjectAnimator.ofFloat(mTestImage, "abc", 1f, 0f)
//                .setDuration(500);
//        animator.start();
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float cVal = (Float) valueAnimator.getAnimatedValue();
//                mTestImage.setAlpha(cVal);
//                mTestImage.setScaleX(cVal);
//                mTestImage.setScaleY(cVal);
//            }
//        });
//  使用PropertyvaluesHolder实现多个属性变化
        PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f, 1.0f);
        PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0f, 1.0f);
        PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0f, 1.0f);
        ObjectAnimator.ofPropertyValuesHolder(mTestImage, p1, p2, p3).setDuration(500).start();


    }

    @OnClick(R.id.bt_1)
    public void chuizhi() {
        //使用ValueAnimator 没有在属性上进行操作   (垂直移动)
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 500);
        valueAnimator.setTarget(mTestImage);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTestImage.setTranslationX((float) valueAnimator.getAnimatedValue());
                // valueAnimator.seta
            }
        });
        //不想实现全部方法  可以使用animatorlistneradapter
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
//                ViewGroup parent = (ViewGroup) mTestImage.getParent();
//                if (parent != null) {
//                    parent.removeView(mTestImage);
//                }
//                tool.ShowToast("删除图片");
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                //动画立即停止  停在当前位置
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @OnClick(R.id.bt_2)
    public void paowuxian() {
//          使用自定义的TypeValue实现抛物线
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(1000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        LinearInterpolator interpolator = new LinearInterpolator();

        interpolator.getInterpolation(0.3f);

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float v, PointF pointF, PointF t1) {
                PointF p = new PointF();
                // LogUtils.e(v);
                p.x = 200 * v * 3;
                p.y = 100 * v * 3 * v * 3;
                return p;
            }
        });
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF p = (PointF) valueAnimator.getAnimatedValue();
                mTestImage.setX(p.x);
                mTestImage.setY(p.y);
            }
        });


    }

    /**
     * 使用animator执行多动画
     */
    @OnClick(R.id.bt_3)
    public void play() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTestImage, "scaleX", 1.0f, 0f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mTestImage, "scaleY", 1.0f, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        //animatorSet.playSequentially();顺序执行
        animatorSet.playTogether(animator, animator1);
        animatorSet.start();
    }

    /**
     * 多动画依次执行
     */
    @OnClick(R.id.bt_4)
    public void play1() {
        float x = mTestImage.getX() + 400;
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mTestImage, "scaleX", 1.0f, 3.0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mTestImage, "scaleY", 1.0f, 3.0f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(mTestImage, "x", x, 0.f);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(mTestImage, "x", x);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.play(objectAnimator1).with(objectAnimator2);
        set.play(objectAnimator2).with(objectAnimator3);
        set.play(objectAnimator4).after(objectAnimator3);

        set.start();
    }

    @OnClick(R.id.bt_5)
    public void readfromxmlanim() {
        //Animator animator = AnimatorInflater.loadAnimator(this, R.animator.scalex);
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.testanim);
        mTestImage.setPivotX(0);
        mTestImage.setPivotY(0);
        mTestImage.invalidate();
        animator.setTarget(mTestImage);
        animator.start();
    }

    @OnClick(R.id.bt_6)
    public void LayoutTransition() {
        Intent intent = new Intent(this, myLayoutTransition.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_7)
    public void Transition() {
//        Intent intent = new Intent(this, PActivity.class);
//        this.startActivity(intent);
//        overridePendingTransition(0, 0);
        mTestImage.setImageBitmap(createViewBitmap(btObjectAnimator));

    }

    public Bitmap createViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.anim_layout);
    }

    //转换为十六进制
    private String toHexString(int color) {
        return Integer.toHexString(color);
    }


    class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekBar:
                    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                    int colorstart = Color.parseColor("#15d68f");
                    int colorend = Color.parseColor("#f2186b");
                    float c = (float) progress / (float) 100;
                    int color = (Integer) argbEvaluator.evaluate(c, colorstart, colorend);

                    LogUtils.i("#" + toHexString(Color.red(color))
                            + toHexString(Color.green(color)) +
                            toHexString(Color.blue(color)));

                    bt2.setBackgroundColor(color);
                    break;
                case R.id.sb_blue:
                    color_blue = seekBar.getProgress();
                    break;
                case R.id.sb_green:
                    color_green = seekBar.getProgress();
                    break;
                case R.id.sb_red:
                    color_red = seekBar.getProgress();
                    break;
                case R.id.sb_alpha:
                    color_alpha = seekBar.getProgress();
                    break;
            }
            tv_Color.setText("#" + toHexString(color_red)
                    + toHexString(color_green) +
                    toHexString(color_blue));
            int color1 = Color.argb(color_alpha, color_red, color_green, color_blue);
            bt1.setBackgroundColor(color1);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private int color_red = 128, color_blue = 128, color_green = 128, color_alpha = 255;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
        SeekBarChangeListener listener = new SeekBarChangeListener();
        seekBar.setOnSeekBarChangeListener(listener);
        sb_Blue.setOnSeekBarChangeListener(listener);
        sb_Green.setOnSeekBarChangeListener(listener);
        sb_Red.setOnSeekBarChangeListener(listener);
        sb_Alpha.setOnSeekBarChangeListener(listener);

        tv_Color.setText("#" + toHexString(color_red)
                + toHexString(color_green) +
                toHexString(color_blue));
        bt1.setBackgroundColor(Color.argb(255, 128, 128, 128));
    }
}
