package com.example.pierrechanson.prototypebottomsheet;

/**
 * Created by pierrechanson on 21/07/16.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

public class BackdropBottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private boolean mInit = false;
    private int mCollapsedY;
    private int mPeakHeight;
    private int mAnchorPointY;
    private int mCurrentChildY;
    private int statusBarHeight;
    private int actionBarHeight;

    public BackdropBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.BackdropBottomSheetBehavior_Params);
        setPeekHeight(a.getDimensionPixelSize(R.styleable.BackdropBottomSheetBehavior_Params_behavior_backdrop_peekHeight, 0));
        a.recycle();
        statusBarHeight = getStatusBarHeight(context);
        actionBarHeight = getActionBarHeight(context);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof LinearLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if(!mInit){
            init(child, dependency);
            return false;
        }
        if((mCurrentChildY = (int) ((dependency.getY()-mAnchorPointY) * mCollapsedY / (mCollapsedY-mAnchorPointY))) <= actionBarHeight)
            child.setY(mCurrentChildY = actionBarHeight);
        else
            child.setY(mCurrentChildY);
        return true;
    }

    private void init(@NonNull View child, @NonNull View dependency){
        mCollapsedY = dependency.getHeight() - (2 * mPeakHeight);
        mAnchorPointY = child.getHeight();
        mCurrentChildY = (int) dependency.getY();
        //TODO
        if(mCurrentChildY == mAnchorPointY || mCurrentChildY == mAnchorPointY-1 ||mCurrentChildY == mAnchorPointY+1)
            child.setY(0);
        else child.setY(mCurrentChildY);
        mInit = true;
    }

    public void setPeekHeight(int peakHeight) {
        this.mPeakHeight = peakHeight;
    }

    private int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}