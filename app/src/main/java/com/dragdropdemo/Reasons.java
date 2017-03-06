package com.dragdropdemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class Reasons extends FragmentActivity {
    AnimatorSet set1;
    private Animator mCurrentAnimator;
    private int mShortAnimationDuration = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reasons);

        final View thumb1View = findViewById(R.id.imageView1);
        final View thumb2View = findViewById(R.id.imageView8);
        thumb2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumb2View, R.drawable.eight_card_grid_front);

//                ObjectAnimator anim = ObjectAnimator.ofFloat(view,"scaleX",2.5f);
//                anim.setDuration(3000); // duration 3 seconds
//                anim.start();
//                ObjectAnimator anim2 = ObjectAnimator.ofFloat(view,"scaleY",2.5f);
//                anim2.setDuration(3000); // duration 3 seconds
//                anim2.start();
//                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(Reasons.this,R.animator.flipping);
//                set.setTarget(view);
//                set.start();

//                set1 = (AnimatorSet) AnimatorInflater.loadAnimator(Reasons.this,R.animator.flipping);
//                set1.setTarget(view);
//                set1.setDuration(mShortAnimationDuration);
//                set1.start();
            }
        });

//        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    private void zoomImageFromThumb(final View thumbView, int imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
        expandedImageView.setImageResource(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

//        set1 = (AnimatorSet) AnimatorInflater.loadAnimator(Reasons.this,R.animator.flipping);
//        set1.setTarget(expandedImageView);
//        set1.setDuration(mShortAnimationDuration);
//        set1.start();

//        ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.flipping);
//        anim.setTarget(expandedImageView);
//        anim.setDuration(mShortAnimationDuration);
//
//
//        ObjectAnimator anim2 = (ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left, finalBounds.left));
//        ObjectAnimator anim3 = ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top);
//        ObjectAnimator anim4 = ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f);
//        ObjectAnimator anim5 =ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).

//        AnimatorSet set2 = (AnimatorSet) AnimatorInflater.loadAnimator(Reasons.this, R.animator.flipping);
//        set2.setTarget(expandedImageView);
//        set2.setDuration(mShortAnimationDuration);
//        set2.setInterpolator(new DecelerateInterpolator());

//        Animator anim = AnimatorInflater.loadAnimator(Reasons.this, R.animator.flipping);

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f));
//        .with(anim);

        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
//        set2.start();
        set.start();
//        set.playTogether(set, set2);


        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal));

                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }


//    ObjectAnimator anim = ObjectAnimator.ofFloat(imageView,"scaleX",2.5f);
//    anim.setDuration(3000); // duration 3 seconds
//    anim.start();
//    // Make the object height 50%
//    ObjectAnimator anim2 = ObjectAnimator.ofFloat(imageView,"scaleY",2.5f);
//    anim2.setDuration(3000); // duration 3 seconds
//    anim2.start();
//    AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.multianimation);
//    set.setTarget(imageView);
//    set.start();

}

