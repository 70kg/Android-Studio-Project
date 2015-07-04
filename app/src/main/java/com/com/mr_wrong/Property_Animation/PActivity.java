package com.com.mr_wrong.Property_Animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.ImageView;

import com.example.mr_wrong.androidstudioproject.R;

/**
 * Created by Mr_Wrong on 15/7/4.
 */
public class PActivity extends BaseActivity {
    ImageView mTestImage;
    float x;

    @Override
    public void setContentView() {
        setContentView(R.layout.pending_layout);

        mTestImage = (ImageView) findViewById(R.id.iv_anim);
        x = mTestImage.getX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTestImage, "scaleY", 1.0f, 2.0f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mTestImage, "x",  800);
        animator.setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator, animator1);
        set.start();

    }

    @Override
    public void onBackPressed() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTestImage, "scaleY", 2.0f, 1.0f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mTestImage, "x",x);
        animator.setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator1, animator);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
