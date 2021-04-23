package com.app.roomwithkotlincoroutine.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.roomwithkotlincoroutine.db.DatabaseHelper
import com.app.roomwithkotlincoroutine.viewmodel.RoomDBViewModel

class ViewModelFactory(private val dbHelper: DatabaseHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SingleNetworkCallViewModel::class.java)) {
//            return SingleNetworkCallViewModel(apiHelper, dbHelper) as T
//        }
//        if (modelClass.isAssignableFrom(SeriesNetworkCallsViewModel::class.java)) {
//            return SeriesNetworkCallsViewModel(apiHelper, dbHelper) as T
//        }
//        if (modelClass.isAssignableFrom(ParallelNetworkCallsViewModel::class.java)) {
//            return ParallelNetworkCallsViewModel(apiHelper, dbHelper) as T
//        }
        if (modelClass.isAssignableFrom(RoomDBViewModel::class.java)) {
            return RoomDBViewModel(dbHelper) as T
        }
//        if (modelClass.isAssignableFrom(TimeoutViewModel::class.java)) {
//            return TimeoutViewModel(apiHelper, dbHelper) as T
//        }
//        if (modelClass.isAssignableFrom(TryCatchViewModel::class.java)) {
//            return TryCatchViewModel(apiHelper, dbHelper) as T
//        }
//        if (modelClass.isAssignableFrom(ExceptionHandlerViewModel::class.java)) {
//            return ExceptionHandlerViewModel(apiHelper, dbHelper) as T
//        }
//        if (modelClass.isAssignableFrom(LongRunningTaskViewModel::class.java)) {
//            return LongRunningTaskViewModel(apiHelper, dbHelper) as T
//        }
//        if (modelClass.isAssignableFrom(TwoLongRunningTasksViewModel::class.java)) {
//            return TwoLongRunningTasksViewModel(apiHelper, dbHelper) as T
//        }
//        if (modelClass.isAssignableFrom(IgnoreErrorAndContinueViewModel::class.java)) {
//            return IgnoreErrorAndContinueViewModel(apiHelper, dbHelper) as T
//        }
        throw IllegalArgumentException("Unknown class name")
    }

}