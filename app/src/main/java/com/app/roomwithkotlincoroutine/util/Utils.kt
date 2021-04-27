package com.app.roomwithkotlincoroutine.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun setRecyclerViewLayoutManager(recyclerView: RecyclerView) {
    val layoutManager = LinearLayoutManager(recyclerView.context)
    recyclerView.layoutManager = layoutManager
    recyclerView.itemAnimator = DefaultItemAnimator()
    recyclerView.isNestedScrollingEnabled = true
    recyclerView.addItemDecorationVertical()

}

fun RecyclerView.addItemDecorationVertical() {
    this.addItemDecoration(
        DividerItemDecoration(
            this.context, DividerItemDecoration.VERTICAL
        )
    )
}

fun Activity.showSnackBar(message: String?) {
    if (null != message) {
        hideKeyboard(this)
        Snackbar.make(
            this.findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        ).show()
    }
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun hideKeyboard(activity: Activity) {
    val imm =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun ProductWithCoupon.toMultiSelect() = ProductMuliSelect(
    id = id,
    name = name,
    description = description,
    discountId = discountId,
    type = type,
    amount = amount,
    price = price
)

fun EditText.setActionNext() {
    this.imeOptions = EditorInfo.IME_ACTION_NEXT
    this.setRawInputType(InputType.TYPE_CLASS_TEXT)
}

@SuppressLint("SimpleDateFormat")
fun getDate(milliSeconds: Long, dateFormat: String?): String {
    // Create a DateFormatter object for displaying date in specified format.
    val formatter = SimpleDateFormat(dateFormat)

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return try {
        formatter.format(calendar.time)
    } catch (e: Exception) {
        ""
    }
}