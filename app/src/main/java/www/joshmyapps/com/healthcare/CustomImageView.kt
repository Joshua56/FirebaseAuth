package www.joshmyapps.com.healthcare


import android.content.Context
import android.util.AttributeSet
import android.view.View

import androidx.appcompat.widget.AppCompatImageView

class CustomImageView : AppCompatImageView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //Unpack given dimens to new aspect ratio
        val threeTwoHeight = View.MeasureSpec.getSize(widthMeasureSpec) * 2 / 3
        val threeTwoHeightSpec = View.MeasureSpec.makeMeasureSpec(threeTwoHeight, View.MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, threeTwoHeightSpec)
    }
}
