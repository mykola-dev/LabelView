package ds.labelview;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.text.*;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

public class LabelView extends View {

    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int MONOSPACE = 3;

    private StaticLayout layout;
    private TextPaint textPaint;
    private CharSequence text;
    private int gravity = Gravity.START;
    private int textColor = Color.BLACK;
    private float size = dp(14);
    private int maximum = 0;

    public LabelView(Context context) {
        super(context);
        initPaint();
    }

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);

        int typefaceIndex = -1;
        int styleIndex = -1;
        String fontFamily = null;
        float shadowRadius;
        float shadowDx;
        float shadowDy;
        int shadowColor;

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LabelView);
       /* text = a.getText(R.styleable.LabelView_text);
        size = a.getDimension(R.styleable.LabelView_textSize, size);
        textColor = a.getColor(R.styleable.LabelView_textColor, textColor);
        gravity = a.getInt(R.styleable.LabelView_gravity, gravity);
        typefaceIndex = a.getInt(R.styleable.LabelView_typeface, typefaceIndex);
        styleIndex = a.getInt(R.styleable.LabelView_textStyle, styleIndex);
        fontFamily = a.getString(R.styleable.LabelView_fontFamily);
        shadowRadius = a.getDimension(R.styleable.LabelView_shadowRadius, 0);
        shadowDx = a.getDimension(R.styleable.LabelView_shadowDx, 0);
        shadowDy = a.getDimension(R.styleable.LabelView_shadowDy, 0);
        shadowColor = a.getColor(R.styleable.LabelView_shadowColor, Color.BLACK);*/
        text = a.getText(R.styleable.LabelView_android_text);
        size = a.getDimension(R.styleable.LabelView_android_textSize, size);
        textColor = a.getColor(R.styleable.LabelView_android_textColor, textColor);
        gravity = a.getInt(R.styleable.LabelView_android_gravity, gravity);
        typefaceIndex = a.getInt(R.styleable.LabelView_android_typeface, typefaceIndex);
        styleIndex = a.getInt(R.styleable.LabelView_android_textStyle, styleIndex);
        fontFamily = a.getString(R.styleable.LabelView_android_fontFamily);
        shadowRadius = a.getFloat(R.styleable.LabelView_android_shadowRadius, 0);
        shadowDx = a.getFloat(R.styleable.LabelView_android_shadowDx, 0);
        shadowDy = a.getFloat(R.styleable.LabelView_android_shadowDy, 0);
        shadowColor = a.getColor(R.styleable.LabelView_android_shadowColor, Color.BLACK);
        maximum = a.getInt(R.styleable.LabelView_android_maxLines, maximum);
        a.recycle();

        initPaint();

        if (shadowRadius > 0)
            textPaint.setShadowLayer(shadowRadius, shadowDx, shadowDy, shadowColor);

        setTypefaceFromAttrs(fontFamily, typefaceIndex, styleIndex);
    }

    private void initPaint() {
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(size);
        textPaint.setColor(textColor);
        if (text == null)
            text = "";
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = (int) Layout.getDesiredWidth(text, textPaint) + getPaddingLeft() + getPaddingRight();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }


        layout = provideLayout(text, width - getPaddingLeft() - getPaddingRight());

        int desiredHeight = getTextHeight() + getPaddingTop() + getPaddingBottom();

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        int verticalOffset = 0;

        // translate in by our padding
        /* shortcircuit calling getVerticaOffset() */
        if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) != Gravity.TOP) {
            verticalOffset = getVerticalOffset();
        }
        canvas.translate(getPaddingLeft(), getPaddingTop() + verticalOffset);
        canvas.clipRect(0, 0, layout.getWidth(), getTextHeight());
        layout.draw(canvas);
        canvas.restore();

    }

    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence t) {
        text = t != null ? t : "";
        requestLayout();
        invalidate();
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
        requestLayout();
        invalidate();
    }

    public void setTextSize(float size) {
        Context c = getContext();
        Resources r = c.getResources();
        int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, r.getDisplayMetrics());
        textPaint.setTextSize(pixels);
        requestLayout();
        invalidate();
    }

    public void setTextColor(int color) {
        textPaint.setColor(color);
        invalidate();
    }

    public void setTypeface(Typeface tf) {
        if (textPaint.getTypeface() != tf) {
            textPaint.setTypeface(tf);

            if (layout != null) {
                requestLayout();
                invalidate();
            }
        }
    }

    public void setTypeface(Typeface tf, int style) {
        if (style > 0) {
            if (tf == null) {
                tf = Typeface.defaultFromStyle(style);
            } else {
                tf = Typeface.create(tf, style);
            }

            setTypeface(tf);
            // now compute what (if any) algorithmic styling is needed
            int typefaceStyle = tf != null ? tf.getStyle() : 0;
            int need = style & ~typefaceStyle;
            textPaint.setFakeBoldText((need & Typeface.BOLD) != 0);
            textPaint.setTextSkewX((need & Typeface.ITALIC) != 0 ? -0.25f : 0);
        } else {
            textPaint.setFakeBoldText(false);
            textPaint.setTextSkewX(0);
            setTypeface(tf);
        }
    }

    public void setShadowLayer(float radius, float dx, float dy, int color) {
        textPaint.setShadowLayer(radius, dx, dy, color);
        invalidate();
    }

    public void setMaxLines(int maxlines) {
        maximum = maxlines;
        requestLayout();
        invalidate();
    }

    private StaticLayout provideLayout(CharSequence text, int width) {
        return new StaticLayout(text, textPaint, width, getLayoutAlignment(), 1, 0, true);
    }

    private void setTypefaceFromAttrs(String fontFamily, int typefaceIndex, int styleIndex) {
        Typeface tf = null;
        if (fontFamily != null/* && typefaceIndex == -1*/) {
            tf = Typeface.create(fontFamily, styleIndex);
            if (tf != null) {
                setTypeface(tf);
                return;
            }
        }
        switch (typefaceIndex) {
            case SANS:
                tf = Typeface.SANS_SERIF;
                break;

            case SERIF:
                tf = Typeface.SERIF;
                break;

            case MONOSPACE:
                tf = Typeface.MONOSPACE;
                break;
        }

        setTypeface(tf, styleIndex);
    }

    private Layout.Alignment getLayoutAlignment() {
        Layout.Alignment alignment;
        switch (gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
            case Gravity.END:
            case Gravity.RIGHT:
                alignment = Layout.Alignment.ALIGN_OPPOSITE;
                break;
            case Gravity.CENTER_HORIZONTAL:
                alignment = Layout.Alignment.ALIGN_CENTER;
                break;
            default:
                alignment = Layout.Alignment.ALIGN_NORMAL;
                break;
        }
        return alignment;
    }

    private int getVerticalOffset() {
        int voffset = 0;
        final int gravity = this.gravity & Gravity.VERTICAL_GRAVITY_MASK;

        int boxht = getBoxHeight();
        int textht = getTextHeight();

        if (textht < boxht) {
            if (gravity == Gravity.BOTTOM)
                voffset = boxht - textht;
            else // (gravity == Gravity.CENTER_VERTICAL)
                voffset = (boxht - textht) >> 1;
        }
        return voffset;
    }

    private int getTextHeight() {
        if (maximum > 0 && maximum < layout.getLineCount())
            return layout.getLineTop(maximum);
        else {
            return layout.getHeight();
        }
    }

    private int getBoxHeight() {
        int padding = getPaddingTop() + getPaddingBottom();
        return getMeasuredHeight() - padding;
    }

    private float dp(float dips) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, getResources().getDisplayMetrics());
    }
}
