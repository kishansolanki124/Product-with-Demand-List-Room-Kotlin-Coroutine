package com.app.roomwithkotlincoroutine

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Environment
import android.text.InputType
import android.util.Patterns
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.regex.Matcher
import java.util.regex.Pattern

fun setRecyclerViewLayoutManager(recyclerView: RecyclerView, context: Context) {
    val layoutManager = LinearLayoutManager(context)
    recyclerView.layoutManager = layoutManager
    recyclerView.itemAnimator = DefaultItemAnimator()
    recyclerView.isNestedScrollingEnabled = true
}

fun setGridLayoutManager(recyclerView: RecyclerView, context: Context, spanCount: Int) {
    val layoutManager = GridLayoutManager(context, spanCount)
    recyclerView.layoutManager = layoutManager
    //recyclerView.itemAnimator = DefaultItemAnimator()
    recyclerView.isNestedScrollingEnabled = true
}

fun TextView.showStrikeThrough(show: Boolean) {
    paintFlags =
        if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

fun showSnackBar(message: String?, activity: Activity?) {
    if (null != activity && null != message) {
        hideKeyboard(activity)
        Snackbar.make(
            activity.findViewById(android.R.id.content),
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

private var pgDialog: Dialog? = null

fun dismissProgressDialog() {
    if (pgDialog != null) {
        pgDialog?.dismiss()
        pgDialog = null
    }
}

@SuppressLint("SimpleDateFormat")
fun formatDate(dateToFormat: String, inputFormat: String, outputFormat: String): String {
    try {
        print("Input Date Date is $dateToFormat")
        val convertedDate: String = SimpleDateFormat(outputFormat)
            .format(
                SimpleDateFormat(inputFormat)
                    .parse(dateToFormat)!!
            )
        print("Output Date is $convertedDate")

        //Update Date
        return convertedDate
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

fun openBrowser(context: Context, url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(browserIntent)
}

fun CharSequence.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.saveTo(path: String) {
    URL(this).openStream().use { input ->
        FileOutputStream(File(path)).use { output ->
            input.copyTo(output)
        }
    }
}

fun getStorageDir(context: Context, fileName: String): String {
    //create folder
    val file: File =
        File(context.getExternalFilesDir(null)!!.absolutePath + "/patidarsamaj/files")
    if (!file.mkdirs()) {
        file.mkdirs()
    }
    return file.absolutePath + File.separator + fileName
}

fun getRootDirPath(context: Context): String? {
    return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        val file: File = ContextCompat.getExternalFilesDirs(
            context.applicationContext,
            null
        )[0]
        file.absolutePath
    } else {
        context.applicationContext.filesDir.absolutePath
    }
}

fun shareTextAndImage(context: Context, fileName: String, shareableText: String) {
    val pdfFile = File(getRootDirPath(context) + "/" + fileName)
    val fileUri = FileProvider.getUriForFile(
        context,
        BuildConfig.APPLICATION_ID,
        pdfFile
    )
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "image/png"
    intent.putExtra(Intent.EXTRA_STREAM, fileUri)
    intent.putExtra(
        Intent.EXTRA_TEXT,
        shareableText + "\n\nઇન્સ્ટોલ કરો પાટીદાર સૌરભ એપ - https://play.google.com/store/apps/details?id=" +
                BuildConfig.APPLICATION_ID
    )

    context.startActivity(Intent.createChooser(intent, "Share"))
}

fun shareIntent(shareableText: String, context: Context) {
    val txtIntent = Intent(Intent.ACTION_SEND)
    txtIntent.type = "text/plain"
    txtIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
    txtIntent.putExtra(
        Intent.EXTRA_TEXT, shareableText
                + "\nDownload the app from https://play.google.com/store/apps/details?id=" +
                BuildConfig.APPLICATION_ID
    )
    context.startActivity(Intent.createChooser(txtIntent, "Share"))
}

fun browserIntent(context: Context, url: String) {
    var webpage = Uri.parse(url)
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        webpage = Uri.parse("http://$url")
    }
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}

fun extractYouTubeVideoId(ytUrl: String?): String? {
    var vId: String? = null
    val pattern: Pattern = Pattern.compile(
        "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$",
        Pattern.CASE_INSENSITIVE
    )
    val matcher: Matcher = pattern.matcher(ytUrl)
    if (matcher.matches()) {
        vId = matcher.group(1)
    }
    return vId
}

fun EditText.setMultiLineCapSentencesAndDoneAction() {
    imeOptions = EditorInfo.IME_ACTION_DONE
    setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_FLAG_MULTI_LINE)
}

fun EditText.setMultiLineCapSentencesAndNextAction() {
    imeOptions = EditorInfo.IME_ACTION_NEXT
    setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_FLAG_MULTI_LINE)
}

fun ProductWithCoupon.toMultiSelect() = ProductMuliSelect(
    id = id,
    name = name,
    email = email, avatar = avatar,
    discountId = discountId,
    type = type,
    amount = amount,
    price = price
)