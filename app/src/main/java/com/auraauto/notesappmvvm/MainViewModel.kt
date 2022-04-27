package com.auraauto.notesappmvvm

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.auraauto.notesappmvvm.database.firebase.AppFirebaseRepository
import com.auraauto.notesappmvvm.database.room.dao.AppRoomDatabase
import com.auraauto.notesappmvvm.database.room.dao.repository.RoomRepository
import com.auraauto.notesappmvvm.model.Note
import com.auraauto.notesappmvvm.utils.Constants.Keys.EMPTY
import com.auraauto.notesappmvvm.utils.DB_TYPE
import com.auraauto.notesappmvvm.utils.REPOSITORY
import com.auraauto.notesappmvvm.utils.TYPE_FIREBASE
import com.auraauto.notesappmvvm.utils.TYPE_ROOM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            TYPE_FIREBASE -> {
                REPOSITORY = AppFirebaseRepository()
                REPOSITORY.connectToDatabase(
                    { onSuccess() },
                    { Log.d("checkData", "Error: ${it}") }
                )
            }
        }
    }
    fun addNote(note: Note, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note = note){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun updateNote(note: Note, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(note = note){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun deleteNote(note: Note, onSuccess: () -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note = note){
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun readAllNotes() = REPOSITORY.readAll

    fun signOut(onSuccess: () -> Unit) {
        when(DB_TYPE.value) {
            TYPE_FIREBASE,
            TYPE_ROOM -> {
                REPOSITORY.signOut()
                DB_TYPE.value = EMPTY
                onSuccess()
            }
            else -> { Log.d("checkData", "signOut: ELSE: ${DB_TYPE.value}") }
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