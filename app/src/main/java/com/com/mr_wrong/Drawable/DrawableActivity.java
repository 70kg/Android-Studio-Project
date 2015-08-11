package com.com.mr_wrong.Drawable;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.mr_wrong.androidstudioproject.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mr_Wrong on 15/8/10.
 */
public class DrawableActivity extends Activity {

    @InjectView(R.id.tv_test)
    TextView tvTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_drawable);
        ButterKnife.inject(this);


        String html="<html><head>" +
                "<title>TextView使用HTML</title>" +
                "</head>" +
                "<body>" +
                "<p><strong>强调</strong></p>" +
                "<p><em>斜体</em></p>"
                +"<p><a href=\"http://www.dreamdu.com/xhtml/\">超链接HTML入门</a>学习HTML!</p>" +
                "<p><font color=\"#aabb00\">颜色1</p>" +
                "<p><font color=\"#00bbaa\">颜色2</p>" +
                "<h1>标题1</h1>" +
                "<h3>标题2</h3>" +
                "<h6>标题3</h6>" +
                "<p>大于>小于<</p><p>" +
                "下面是网络图片</p><img src=\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\"/>" +
                "</body></html>";

        tvTest.setText(Html.fromHtml(html));


        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.testimage);
        //mTestDrawable.setImageDrawable(new RoundImageDrawable(bitmap));

        //StateListDrawable stateListDrawable = new StateListDrawable();

        //final ClipDrawable clipDrawable = (ClipDrawable) mTestImageView.getBackground();


//        ClipDrawable clipDrawable = new ClipDrawable(getResources().getDrawable(R.drawable.testimage)
//                , Gravity.LEFT, ClipDrawable.HORIZONTAL);

        // LogUtils.e(clipDrawable.getLevel());
        // clipDrawable.setLevel(5000);

        ValueAnimator animator = ValueAnimator.ofInt(0, 10000);
        animator.setDuration(2000);

        animator.setInterpolator(new DecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //clipDrawable.setLevel((int) valueAnimator.getAnimatedValue());
            }
        });
        animator.setStartDelay(1200);
        animator.start();


//        TransitionDrawable transitionDrawable = (TransitionDrawable) ivTransition.getDrawable();
//        transitionDrawable.startTransition(3000);

//        ImageView im = customLayout.getImageLeft();
//        im.setVisibility(View.VISIBLE);
//        im.setImageResource(R.drawable.right1);

//        customLayout.getEditText().setEnabled(true);
//        customLayout.getEditText().setText("这是中间的edittext");
//
//        customLayout.getTextRight().setVisibility(View.VISIBLE);
//        customLayout.getTextRight().setText("这是右边的textview");
    }

}
