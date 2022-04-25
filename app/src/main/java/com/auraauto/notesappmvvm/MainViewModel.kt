package com.auraauto.notesappmvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.auraauto.notesappmvvm.database.room.dao.AppRoomDatabase
import com.auraauto.notesappmvvm.database.room.dao.repository.RoomRepository
import com.auraauto.notesappmvvm.model.Note
import com.auraauto.notesappmvvm.utils.REPOSITORY
import com.auraauto.notesappmvvm.utils.TYPE_FIREBASE
import com.auraauto.notesappmvvm.utils.TYPE_ROOM
import java.lang.IllegalArgumentException

class MainViewModel(application: Application): AndroidViewModel(application) {
    val context = application

    fun initDatabase(type: String, onSuccess: ()-> Unit){
        Log.d("checkData", "MainViewModel initDatabase with type: $type")
        when(type){
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
                REPOSITORY = RoomRepository(dao)
                onSuccess()
            }
        }
    }
}

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}