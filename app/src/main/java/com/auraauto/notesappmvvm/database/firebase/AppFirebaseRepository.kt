package com.auraauto.notesappmvvm.database.firebase

import androidx.lifecycle.LiveData
import com.auraauto.notesappmvvm.database.DatabaseRepository
import com.auraauto.notesappmvvm.model.Note
import com.auraauto.notesappmvvm.utils.LOGIN
import com.auraauto.notesappmvvm.utils.PASSWORD
import com.google.firebase.auth.FirebaseAuth

class AppFirebaseRepository: DatabaseRepository {
    private val mAuth = FirebaseAuth.getInstance()

    override val readAll: LiveData<List<Note>>
        get() = TODO("Not yet implemented")

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFail(it.message.toString()) }
            }
    }
}