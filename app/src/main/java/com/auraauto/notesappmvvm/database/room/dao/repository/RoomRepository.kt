package com.auraauto.notesappmvvm.database.room.dao.repository

import androidx.lifecycle.LiveData
import com.auraauto.notesappmvvm.database.DatabaseRepository
import com.auraauto.notesappmvvm.database.room.dao.NoteRoomDao
import com.auraauto.notesappmvvm.model.Note

class RoomRepository(private val noteRoomDao: NoteRoomDao): DatabaseRepository {
    override val readAll: LiveData<List<Note>>
        get() = noteRoomDao.getAllNotes()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.addNote(note = note)
        onSuccess()
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.updateNote(note = note)
        onSuccess()
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.deleteNote(note = note)
        onSuccess()
    }

}