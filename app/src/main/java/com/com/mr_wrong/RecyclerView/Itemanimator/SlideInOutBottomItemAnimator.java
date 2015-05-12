/*
 * ******************************************************************************
 *   Copyright (c) 2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */
package com.com.mr_wrong.RecyclerView.Itemanimator;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class SlideInOutBottomItemAnimator extends BaseItemAnimator {

    private float mOriginalY;
    private float mDeltaY;

    public SlideInOutBottomItemAnimator(RecyclerView recyclerView) {
        super(recyclerView);
    }

    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).setDuration(getRemoveDuration()).
                translationY(+mDeltaY).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                ViewCompat.setTranslationY(view, +mDeltaY);
                dispatchRemoveFinished(holder);
                mRemoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
        mRemoveAnimations.add(holder);
    }

    @Override
    protected void prepareAnimateAdd(RecyclerView.ViewHolder holder) {
        retrieveItemPosition(holder);
        ViewCompat.setTranslationY(holder.itemView, +mDeltaY);
    }

    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).translationY(0)
                .setDuration(getAddDuration()).
                setListener(new VpaListenerAdapter() {
                    @Override
                    public void onAnimationCancel(View view) {
                        ViewCompat.setTranslationY(view, 0);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        dispatchAddFinished(holder);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
        mAddAnimations.add(holder);
    }


    private void retrieveItemPosition(final RecyclerView.ViewHolder holder){
       mOriginalY = mRecyclerView.getLayoutManager().getDecoratedTop(holder.itemView);
       mDeltaY = mRecyclerView.getHeight() - mOriginalY;
    }


    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        return false;
    }
}
