package com.mayberry.todolist

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, text:String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

fun dpToPx(context: Context, dp: Int): Int {
    return (context.resources.displayMetrics.density * dp).toInt()
}