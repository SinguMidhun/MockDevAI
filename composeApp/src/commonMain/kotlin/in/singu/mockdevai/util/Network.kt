package `in`.singu.mockdevai.util

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized

@OptIn(InternalCoroutinesApi::class)
object Network : SynchronizedObject(){

    private var firebase : Firebase? = null
    private var firebaseAuth : FirebaseAuth? = null
    private var firebaseRealtimeDB : FirebaseDatabase? = null
    private var firebaseFirestore : FirebaseFirestore? = null

    fun getFirebase() : Firebase {
        if(firebase == null){
            synchronized(this) {
                firebase = Firebase
            }
        }
        return firebase!!
    }

    fun getFirebaseAuth() : FirebaseAuth {
        if(firebaseAuth == null){
            synchronized(this) {
                firebaseAuth = Firebase.auth
            }
        }
        return firebaseAuth!!
    }

    fun getFirebaseDatabase() : FirebaseDatabase {
        if(firebaseRealtimeDB == null){
            synchronized(this) {
                firebaseRealtimeDB = Firebase.database
            }
        }
        return firebaseRealtimeDB!!
    }

    fun getFirebaseFirestore() : FirebaseFirestore {
        if(firebaseFirestore == null){
            synchronized(this) {
                firebaseFirestore = Firebase.firestore
            }
        }
        return firebaseFirestore!!
    }

}