package com.badlogic.utils;
//  android.view.MFrameLayout

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;


//---public class PerFrameLayout extends FrameLayout {
public class PerFrameLayout extends ViewGroup {

    private static final int DEFAULT_CHILD_GRAVITY = Gravity.TOP | Gravity.START;


    @ViewDebug.ExportedProperty(category = "drawing")
    private Drawable mForeground;

    @ViewDebug.ExportedProperty(category = "measurement")
    boolean mMeasureAllChildren = false;

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingLeft = 0;

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingTop = 0;

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingRight = 0;

    @ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingBottom = 0;

    @ViewDebug.ExportedProperty(category = "drawing")
    private int mForegroundGravity = Gravity.FILL;

    /** {@hide} */
    @ViewDebug.ExportedProperty(category = "drawing")
    protected boolean mForegroundInPadding = true;

    private final ArrayList<View> mMatchParentChildren = new ArrayList<View>(1);

    //=====================

    public PerFrameLayout(@NonNull Context context) {
        super(context);
    }

    public PerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //=====================

    /**
     * Describes how the foreground is positioned. Defaults to START and TOP.
     *
     * @param foregroundGravity See {@link Gravity}
     *
     * @see #getForegroundGravity()
     *
     * @attr ref android.R.styleable#View_foregroundGravity
     */
    /*public void setForegroundGravity(int foregroundGravity) {
        if (getForegroundGravity() != foregroundGravity) {
            super.setForegroundGravity(foregroundGravity);

            // calling get* again here because the set above may apply default constraints
            final Drawable foreground = getForeground();
            if (getForegroundGravity() == Gravity.FILL && foreground != null) {
                Rect padding = new Rect();
                if (foreground.getPadding(padding)) {
                    mForegroundPaddingLeft = padding.left;
                    mForegroundPaddingTop = padding.top;
                    mForegroundPaddingRight = padding.right;
                    mForegroundPaddingBottom = padding.bottom;
                }
            } else {
                mForegroundPaddingLeft = 0;
                mForegroundPaddingTop = 0;
                mForegroundPaddingRight = 0;
                mForegroundPaddingBottom = 0;
            }

            requestLayout();
        }
    }*/

    /**
     * Returns the drawable used as the foreground of this FrameLayout. The
     * foreground drawable, if non-null, is always drawn on top of the children.
     *
     * @return A Drawable or null if no foreground was set.
     */
    public Drawable getForeground() {
        return mForeground;
    }

    private int getPaddingLeftWithForeground() {
        return mForegroundInPadding ? Math.max(this.getPaddingLeft(), mForegroundPaddingLeft) :
                this.getPaddingLeft() + mForegroundPaddingLeft;
    }

    private int getPaddingRightWithForeground() {
        return mForegroundInPadding ? Math.max(this.getPaddingRight(), mForegroundPaddingRight) :
                this.getPaddingRight() + mForegroundPaddingRight;
    }

    private int getPaddingTopWithForeground() {
        return mForegroundInPadding ? Math.max(this.getPaddingTop(), mForegroundPaddingTop) :
                this.getPaddingTop() + mForegroundPaddingTop;
    }

    private int getPaddingBottomWithForeground() {
        return mForegroundInPadding ? Math.max(this.getPaddingBottom(), mForegroundPaddingBottom) :
                this.getPaddingBottom() + mForegroundPaddingBottom;
    }

    @Deprecated
    public boolean getConsiderGoneChildrenWhenMeasuring() {
        return getMeasureAllChildren();
    }

    /**
     * Determines whether all children, or just those in the VISIBLE or
     * INVISIBLE state, are considered when measuring.
     *
     * @return Whether all children are considered when measuring.
     */
    public boolean getMeasureAllChildren() {
        return mMeasureAllChildren;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {

        public LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height, gravity);
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(@NonNull MarginLayoutParams source) {
            super(source);
        }

        //---------------
        private boolean perParent = false;
        public float perWidth = -1;
        public float perHeight = -1;
        public float perLeft = -1;
        public float perTop = -1;
        public float perRight = -1;
        public float perBottom = -1;

        public float perCenterX = -1;
        public float perCenterY = -1;
        //----

        public void setPerParent(boolean mark){
            perParent = mark;
        }
    }

    /*public static class LayoutParams extends MarginLayoutParams {
        *//**
         * The gravity to apply with the View to which these layout parameters
         * are associated.
         *
         * @see Gravity
         *
         * @attr ref android.R.styleable#FrameLayout_Layout_layout_gravity
         *//*
        public int gravity = -1;

        *//**
         * {@inheritDoc}
         *//*
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);

            *//*TypedArray a = c.obtainStyledAttributes(attrs, com.android.internal.R.styleable.FrameLayout_Layout);
            gravity = a.getInt(com.android.internal.R.styleable.FrameLayout_Layout_layout_gravity, -1);
            a.recycle();*//*
        }

        *//**
         * {@inheritDoc}
         *//*
        public LayoutParams(int width, int height) {
            super(width, height);
        }

        *//**
         * Creates a new set of layout parameters with the specified width, height
         * and weight.
         *
         * @param width the width, either {@link #MATCH_PARENT},
         *        {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param height the height, either {@link #MATCH_PARENT},
         *        {@link #WRAP_CONTENT} or a fixed size in pixels
         * @param gravity the gravity
         *
         * @see Gravity
         *//*
        public LayoutParams(int width, int height, int gravity) {
            super(width, height);
            this.gravity = gravity;
        }

        *//**
         * {@inheritDoc}
         *//*
        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        *//**
         * {@inheritDoc}
         *//*
        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }
    }*/

    //todo ==============================================

    @Override
    @TargetApi(14)
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ///---super.onMeasure();
        int count = getChildCount();

        final boolean measureMatchParentChildren =
                MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY ||
                        MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        mMatchParentChildren.clear();

        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (mMeasureAllChildren || child.getVisibility() != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                maxWidth = Math.max(maxWidth,
                        child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                maxHeight = Math.max(maxHeight,
                        child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                childState = combineMeasuredStates(childState, child.getMeasuredState());
                if (measureMatchParentChildren) {
                    if (lp.width == LayoutParams.MATCH_PARENT ||
                            lp.height == LayoutParams.MATCH_PARENT) {
                        mMatchParentChildren.add(child);
                    }
                }
            }
        }

        // Account for padding too
        maxWidth += getPaddingLeftWithForeground() + getPaddingRightWithForeground();
        maxHeight += getPaddingTopWithForeground() + getPaddingBottomWithForeground();

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        // Check against our foreground's minimum height and width
        final Drawable drawable = getForeground();
        if (drawable != null) {
            maxHeight = Math.max(maxHeight, drawable.getMinimumHeight());
            maxWidth = Math.max(maxWidth, drawable.getMinimumWidth());
        }

        int width_self = resolveSizeAndState(maxWidth, widthMeasureSpec, childState);
        int height_self = resolveSizeAndState(maxHeight, heightMeasureSpec,childState << MEASURED_HEIGHT_STATE_SHIFT);
        setMeasuredDimension(width_self,height_self);

        count = mMatchParentChildren.size();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                final View child = mMatchParentChildren.get(i);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int childWidthMeasureSpec;
                if (lp.width == LayoutParams.MATCH_PARENT) {
                    final int width = Math.max(0, getMeasuredWidth()
                            - getPaddingLeftWithForeground() - getPaddingRightWithForeground()
                            - lp.leftMargin - lp.rightMargin);
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                            width, MeasureSpec.EXACTLY);
                } else {
                    childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                            getPaddingLeftWithForeground() + getPaddingRightWithForeground() +
                                    lp.leftMargin + lp.rightMargin,
                            lp.width);
                }

                final int childHeightMeasureSpec;
                if (lp.height == LayoutParams.MATCH_PARENT) {
                    final int height = Math.max(0, getMeasuredHeight()
                            - getPaddingTopWithForeground() - getPaddingBottomWithForeground()
                            - lp.topMargin - lp.bottomMargin);
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                            height, MeasureSpec.EXACTLY);
                } else {
                    childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                            getPaddingTopWithForeground() + getPaddingBottomWithForeground() +
                                    lp.topMargin + lp.bottomMargin,
                            lp.height);
                }

                boolean measure_finish = false;
                if (lp.perParent) {
                    measure_finish = measurePer(lp,child,widthMeasureSpec,heightMeasureSpec);
                }
                if (!measure_finish) {
                    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                }
            }
        }
    }

    protected void measureChildWithMargins(View child,
                                           int parentWidthMeasureSpec, int widthUsed,
                                           int parentHeightMeasureSpec, int heightUsed) {
        final LayoutParams lp = (LayoutParams) child.getLayoutParams();

        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                this.getPaddingLeft() + this.getPaddingRight() + lp.leftMargin + lp.rightMargin
                        + widthUsed, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                this.getPaddingTop() + this.getPaddingBottom() + lp.topMargin + lp.bottomMargin
                        + heightUsed, lp.height);

        boolean measure_finish = false;
        if (lp.perParent && lp.perWidth != -1 && lp.perHeight != -1) {
            measure_finish = measurePer(lp,child,parentWidthMeasureSpec,parentHeightMeasureSpec);
        }
        if (!measure_finish) {
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    //-----------------

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        ///super.onLayout();
        layoutChildren(left, top, right, bottom, false /* no force left gravity */);
    }

    @TargetApi(14)
    void layoutChildren(int left, int top, int right, int bottom, boolean forceLeftGravity) {
        final int count = getChildCount();

        final int parentLeft = getPaddingLeftWithForeground();
        final int parentRight = right - left - getPaddingRightWithForeground();

        final int parentTop = getPaddingTopWithForeground();
        final int parentBottom = bottom - top - getPaddingBottomWithForeground();

        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();

                final int width = child.getMeasuredWidth();
                final int height = child.getMeasuredHeight();

                int childLeft;
                int childTop;

                int gravity = lp.gravity;
                if (gravity == -1) {
                    gravity = DEFAULT_CHILD_GRAVITY;
                }

                final int layoutDirection = ViewCompat.getLayoutDirection(this);
                ///--this.getResolvedLayoutDirection();
                ///--final int layoutDirection = getLayoutDirection();
                final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
                final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

                if (lp.perParent) {
                    childLeft = layoutChildrenHorizontalPer(lp,absoluteGravity,width,
                            parentLeft,parentRight,forceLeftGravity);
                    childTop = layoutChildrenVerticalPer(lp,verticalGravity,height,parentTop,parentBottom);
                } else {
                    switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                        case Gravity.CENTER_HORIZONTAL:
                            childLeft = parentLeft + (parentRight - parentLeft - width) / 2 +
                                    lp.leftMargin - lp.rightMargin;
                            break;
                        case Gravity.RIGHT:
                            if (!forceLeftGravity) {
                                childLeft = parentRight - width - lp.rightMargin;
                                break;
                            }
                        case Gravity.LEFT:
                        default:
                            childLeft = parentLeft + lp.leftMargin;
                    }
                    switch (verticalGravity) {
                        case Gravity.TOP:
                            childTop = parentTop + lp.topMargin;
                            break;
                        case Gravity.CENTER_VERTICAL:
                            childTop = parentTop + (parentBottom - parentTop - height) / 2 +
                                    lp.topMargin - lp.bottomMargin;
                            break;
                        case Gravity.BOTTOM:
                            childTop = parentBottom - height - lp.bottomMargin;
                            break;
                        default:
                            childTop = parentTop + lp.topMargin;
                    }
                }
                child.layout(childLeft, childTop, childLeft + width, childTop + height);
            }
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

//        /*final View child = getChildAt(0);
//        LayoutParams hParams =  (LayoutParams)child.getLayoutParams();
//        hParams.leftMargin = (int)(this.getWidth() / 4f);
//        hParams.bottomMargin = (int)(this.getHeight() / 4f);
//        hParams.width = (int)(this.getWidth() / 2f);
//        hParams.height = (int)(this.getHeight() / 3f);
//        child.requestLayout();*/

    }

    //=======================

    private boolean measurePer(LayoutParams lp,View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec){
        /*int width_will = (int)(MeasureSpec.getSize(parentWidthMeasureSpec) / 2f);
        int height_will = (int)(MeasureSpec.getSize(parentHeightMeasureSpec) / 3f);*/
        int width_will = (int)(MeasureSpec.getSize(parentWidthMeasureSpec) * lp.perWidth);
        int height_will = (int)(MeasureSpec.getSize(parentHeightMeasureSpec) * lp.perHeight);
        int width_will_MeasureSpec = MeasureSpec.makeMeasureSpec(width_will, MeasureSpec.EXACTLY);
        int height_will_MeasureSpec = MeasureSpec.makeMeasureSpec(height_will, MeasureSpec.EXACTLY);

        child.measure(width_will_MeasureSpec, height_will_MeasureSpec);
        if (invokeSetMeasuredDimensionMark) {
            invokeSetMeasuredDimension(child, width_will, height_will);
        }

        /*ALog.i("","MFrameLayout-measurePer" + "\n"
                + "-width_will->" + width_will
                + "-height_will->" + height_will + "\n"
                + "-widthModeWill->" + MeasureSpec.toString(width_will_MeasureSpec)
                + "-heightModeWill->" + MeasureSpec.toString(height_will_MeasureSpec)
        );*/
        return true;
    }

    public boolean invokeSetMeasuredDimensionMark = false;

    private void invokeSetMeasuredDimension(View child, int width, int height) {
        try {
            java.lang.reflect.Method method = View.class.getDeclaredMethod(
                    "setMeasuredDimension", int.class, int.class);
            method.setAccessible(true);
            method.invoke(child, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int layoutChildrenHorizontalPer(LayoutParams lp , int absoluteGravity,
                                            int widthSelf, int parentLeft, int parentRight, boolean forceLeftGravity) {
        int childLeft = 0;
        switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.CENTER_HORIZONTAL:
                if (lp.perCenterX == -1) {
                    childLeft = parentLeft + (parentRight - parentLeft - widthSelf) / 2;
                } else {
                    childLeft = (int) (parentLeft + (parentRight - parentLeft) * lp.perCenterX - widthSelf/2f);
                }
                break;
            case Gravity.RIGHT:
                if (!forceLeftGravity) {
                    childLeft = parentRight - widthSelf - (int)(this.getWidth() * lp.perRight);
                    break;
                }
            case Gravity.LEFT:
            default:
                childLeft = parentLeft + (int)(this.getWidth() * lp.perLeft);
        }
        return childLeft;
    }

    private int layoutChildrenVerticalPer(LayoutParams lp ,int verticalGravity,
                                          int heightSelf ,int parentTop, int parentBottom) {
        int childTop = 0;
        switch (verticalGravity & Gravity.VERTICAL_GRAVITY_MASK) {
            case Gravity.CENTER_VERTICAL:
                if (lp.perCenterY == -1) {
                    childTop = parentTop + (parentBottom - parentTop - heightSelf) / 2;
                } else {
                    childTop = (int) (parentTop + (parentBottom - parentTop) * lp.perCenterY - heightSelf/2f) ;
                }
                break;
            case Gravity.BOTTOM:
                childTop = parentBottom - heightSelf - (int)(this.getHeight() * lp.perBottom);
                break;
            case Gravity.TOP:
            default:
                childTop = parentTop + (int)(this.getHeight() * lp.perTop);
        }
        return childTop;
    }


}
