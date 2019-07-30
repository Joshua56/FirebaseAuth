package www.joshmyapps.com.healthcare

import android.content.Context
import android.widget.Toast

fun Toast.showLongMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Toast.showShortMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}