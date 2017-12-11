package com.homecaravan.android.consumer.utils;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.utils.viewanimator.AnimationListener;
import com.homecaravan.android.consumer.utils.viewanimator.ViewAnimator;

public class AnimUtils {

    private static final long DURATION_ANIM = 200;
    private static final long DURATION_BUTTON_MENU = 200;
    private static final long DURATION_PROGRESS = 500;

    public static void changeTextColor(Context context, int fromColor, int toColor, TextView textView) {
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(textView, "textColor",
                ContextCompat.getColor(context, fromColor),
                ContextCompat.getColor(context, toColor));
        colorAnim.setDuration(DURATION_ANIM);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }

    public static void changeBackgroundDrawable(Context context, int fromDrawable, int toDrawable, View view) {
        Drawable backgrounds[] = new Drawable[2];
        backgrounds[0] = ContextCompat.getDrawable(context, fromDrawable);
        backgrounds[1] = ContextCompat.getDrawable(context, toDrawable);

        TransitionDrawable transitionDrawable = new TransitionDrawable(backgrounds);
        view.setBackground(transitionDrawable);
        transitionDrawable.startTransition((int) DURATION_ANIM);
    }


    public static void changeBackgroundColor(Context context, int fromColor, int toColor, View view) {
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(view, "backgroundColor",
                ContextCompat.getColor(context, fromColor),
                ContextCompat.getColor(context, toColor));
        colorAnim.setDuration(DURATION_ANIM);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }

    public static void changeColorFilter(Context context, int fromColor, int toColor, ImageView imageView) {
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(imageView, "colorFilter",
                ContextCompat.getColor(context, fromColor),
                ContextCompat.getColor(context, toColor));
        colorAnim.setDuration(DURATION_ANIM);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }

    public static void changeStatusBarColor(Context context, int fromColor, int toColor, final Activity activity) {

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), ContextCompat.getColor(context, fromColor), ContextCompat.getColor(context, toColor));
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int color = (int) animator.getAnimatedValue();
                activity.getWindow().setStatusBarColor(color);

            }

        });
        colorAnimation.setDuration(200);
        colorAnimation.start();
    }

    public static void scaleView(final View view, float fX, float tX, float fY, final float tY) {
        view.setVisibility(View.VISIBLE);
        ScaleAnimation scaleAnimation = new ScaleAnimation(fX, tX, fY, tY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (tY == 0) {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        scaleAnimation.setDuration(DURATION_ANIM);
        scaleAnimation.setFillAfter(false);
        view.startAnimation(scaleAnimation);
    }

    public static void scaleRouteDrag(View view, float fX, float tX, float fY, float tY) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(fX, tX, fY, tY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(DURATION_ANIM);
        scaleAnimation.setFillAfter(true);
        view.startAnimation(scaleAnimation);
    }

    public static void animUnlockAgent(ImageView iv, ImageView bg, TextView textView, TextView textView1, TextView textView2) {
        ViewAnimator.animate(bg).rotation(0, 500).duration(1000).start();
        ViewAnimator.animate(iv).scale(0, 1).fadeIn().duration(1000).start();
        ViewAnimator.animate(textView).fadeIn().duration(1000).start();
        ViewAnimator.animate(textView1).fadeIn().duration(1000).start();
        ViewAnimator.animate(textView2).fadeIn().duration(1000).start();
    }

    public static void fadeView(View view, float toAlpha) {
        view.animate().alpha(toAlpha).setDuration(DURATION_ANIM).start();
    }

    private static ValueAnimator slideAnimator(int start, int end, final View view) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    public static ValueAnimator resizeCalendar(final View view, int startHeight, final int endHeight) {
        ValueAnimator mAnimator = slideAnimator(startHeight, endHeight, view);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        return mAnimator;
    }


    public static void resizeLayout(final View view, int startHeight, final int endHeight, final String targetView) {
        if (!targetView.equalsIgnoreCase("layoutChooseType")) {
            view.setVisibility(View.VISIBLE);
        }
        if (targetView.equalsIgnoreCase("agentDashboard")) {
            view.setVisibility(View.VISIBLE);
        }
        ValueAnimator mAnimator = slideAnimator(startHeight, endHeight, view);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (targetView.equalsIgnoreCase("agentDashboard") && endHeight == 0) {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mAnimator.start();

    }


    private static ValueAnimator slideAnimatorDashboard(int start, int end, final View view) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    public static void expandView(View view) {
        view.setVisibility(View.VISIBLE);
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);
        ValueAnimator mAnimator = slideAnimatorDashboard(0, view.getMeasuredHeight(), view);
        mAnimator.start();
    }

    public static void collapseView(final View view, final View view2) {
        int finalHeight = view.getHeight();
        ValueAnimator mAnimator = slideAnimatorDashboard(finalHeight, 0, view);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
                if (view2 != null) {
                    view2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mAnimator.start();
    }

    public static void moveView(View view, float x) {
        ViewCompat.animate(view).translationX(x).setDuration(DURATION_ANIM).start();
    }

    public static void showViewFromBottom(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            ViewAnimator.animate(view)
                    .translationY(1500, 0)
                    .alpha(0, 1)
                    .interpolator(new DecelerateInterpolator())
                    .duration(200).start();
        }
    }

    public static void hideViewFromBottom(final View view) {

        ViewAnimator.animate(view)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        view.setVisibility(View.GONE);
                    }
                })
                .translationY(view.getY(), 1500)
                .alpha(1, 0)
                .interpolator(new DecelerateInterpolator())
                .duration(200).start();
    }

    public static void animationButtonMenuWithCloseView(final DrawerArrowDrawable drawerArrowDrawable) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (drawerArrowDrawable != null) {
                    drawerArrowDrawable.setProgress(1 - animation.getAnimatedFraction());
                }
            }
        });
        animator.start();
    }

    public static void animationButtonMenuWithCloseView(final DrawerArrowDrawable drawerArrowDrawable, final View view, final boolean b) {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", 0, 1500).setDuration(DURATION_BUTTON_MENU);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (drawerArrowDrawable != null) {
                    drawerArrowDrawable.setProgress(1 - valueAnimator.getAnimatedFraction());
                }
            }
        });
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (b) {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    public static void scrolling(View sv, int position) {
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofInt(sv, "scrollY", position);
        objectAnimatorY.setStartDelay(0);
        objectAnimatorY.setDuration(300);
        objectAnimatorY.start();
    }


    public static ObjectAnimator animationButtonMenuWithCloseViewListener(final DrawerArrowDrawable drawerArrowDrawable, final View view, final boolean b) {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", 0, 1500).setDuration(DURATION_BUTTON_MENU);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (drawerArrowDrawable != null) {
                    drawerArrowDrawable.setProgress(1 - valueAnimator.getAnimatedFraction());
                }
            }
        });
        return objectAnimator;
    }

    public static void animationButtonMenuWithOpenView(final DrawerArrowDrawable drawerArrowDrawable, final View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", 1500, 0).setDuration(DURATION_BUTTON_MENU);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (drawerArrowDrawable != null) {
                    drawerArrowDrawable.setProgress(valueAnimator.getAnimatedFraction());
                }
            }
        });

        objectAnimator.start();
    }

    public static ObjectAnimator animationButtonMenuWithOpenViewWithListener(final DrawerArrowDrawable drawerArrowDrawable, final View view, boolean b) {
        if (b) {
            view.setVisibility(View.VISIBLE);
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", 1500, 0).setDuration(DURATION_BUTTON_MENU);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (drawerArrowDrawable != null) {
                    drawerArrowDrawable.setProgress(valueAnimator.getAnimatedFraction());
                }
            }
        });
        return objectAnimator;
    }

    public static void slideRightToLeft(final View view, final int fX, final int tX, final boolean b) {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", fX, tX).setDuration(DURATION_ANIM);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (b && tX == 0) {
                    view.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (b && fX == 0) {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    public static ObjectAnimator slideLeftToRight(final View view, float fX, int tX) {
        return ObjectAnimator.ofFloat(view, "translationX", fX, tX).setDuration(DURATION_BUTTON_MENU);
    }

    public static ObjectAnimator slideLeftToRightNoDuration(final View view, float fX, int tX) {
        return ObjectAnimator.ofFloat(view, "translationX", fX, tX).setDuration(0);
    }

    public static void slideUp(Context context, View view) {
        view.setVisibility(View.VISIBLE);
        Animation slideUpAnimation = AnimationUtils.loadAnimation(context,
                R.anim.slide_up_animation);
        view.setAnimation(slideUpAnimation);
        slideUpAnimation.start();
    }

    public static Animation slideDownListener(Context context, final View view) {
        Animation slideDownAnimation = AnimationUtils.loadAnimation(context,
                R.anim.slide_down_animation);
        view.setAnimation(slideDownAnimation);
        return slideDownAnimation;
    }

    public static void slideDown(Context context, final View view) {
        Animation slideDownAnimation = AnimationUtils.loadAnimation(context,
                R.anim.slide_down_animation);
        slideDownAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.setAnimation(slideDownAnimation);
        slideDownAnimation.start();
    }

    public static void moveX(View view, float xMove) {
        view.animate().translationX(xMove).setDuration(DURATION_ANIM).start();
    }

    public static void tabSelectionListingDetail(final Context context, final View viewShow, final TextView textShow) {
        viewShow.setVisibility(View.VISIBLE);
        viewShow.animate().alpha(1).setDuration(DURATION_ANIM).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                changeTextColor(context, R.color.colorTextMain, R.color.color_vote_down, textShow);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }

    public static void tabDisSelectionListingDetail(final Context context, final View viewHide, final TextView textHide) {

        viewHide.animate().alpha(0).setDuration(DURATION_ANIM).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                changeTextColor(context, R.color.color_vote_down, R.color.colorTextMain, textHide);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                viewHide.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }

    public static void inProgress(View view) {
        final ObjectAnimator animShow = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).setDuration(DURATION_PROGRESS);
        final ObjectAnimator animHide = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).setDuration(DURATION_PROGRESS);
        animHide.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animShow.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        animHide.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animShow.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animHide.start();
    }

    public static void changeBgMain(Context context, final View relativeLayout, boolean open, ImageView imageView, DrawerArrowDrawable drawerArrowDrawable,
                                    int drawable) {
        if (open) {
            imageView.setImageDrawable(ContextCompat.getDrawable(context, drawable));
            ViewAnimator.animate(relativeLayout).fadeIn().duration(600).start();
        } else {
            imageView.setImageDrawable(drawerArrowDrawable);
            ViewAnimator.animate(relativeLayout).fadeOut().onStop(new AnimationListener.Stop() {
                @Override
                public void onStop() {
                    relativeLayout.setVisibility(View.GONE);
                }
            }).duration(600).start();
        }
    }
}
