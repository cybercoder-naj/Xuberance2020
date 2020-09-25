package com.sxcs.xuberance2020.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.sxcs.xuberance2020.data.Constants.DATABASE_FIELD_TYPE
import com.sxcs.xuberance2020.data.EventType
import com.sxcs.xuberance2020.data.models.EventDetails
import com.sxcs.xuberance2020.data.models.Registration
import com.sxcs.xuberance2020.firebase.Authentication.schoolCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

object Database {
    private val eventScheduleRef = Firebase.firestore.collection("event_schedule")
    private val schoolsRef = Firebase.firestore.collection("schools")
    private val metadata = Firebase.firestore.collection("metadata")

    private val schoolNames = hashMapOf("SXCS" to "St. Xavier's Collegiate School")

    fun getLatestVersion(callback: (String) -> Unit) {
        metadata.document("version").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val latestVersion = it.result?.get("version")
                    callback(latestVersion.toString())
                }
            }
    }

    fun getEventsFromEventType(type: EventType, callback: (MutableList<EventDetails>?) -> Unit) =
        CoroutineScope(IO).launch {
            val querySnapshot = eventScheduleRef
                .whereEqualTo(DATABASE_FIELD_TYPE, type.toString())
                .get().await()

            val eventSchedule = mutableListOf<EventDetails>()
            for (document in querySnapshot.documents) {
                val event = document.toObject<EventDetails>()
                event?.let {
                    eventSchedule.add(it)
                }
            }
            withContext(Main) {
                callback(if (eventSchedule.isEmpty()) null else eventSchedule)
            }
        }

    fun getAllEvents(callback: (MutableList<EventDetails>) -> Unit) =
        CoroutineScope(IO).launch {
            val documents = eventScheduleRef.whereGreaterThan("numberPart", 0)
                .get().await()
            val docs = mutableListOf<EventDetails>()
            for (doc in documents.documents) {
                val event = doc.toObject<EventDetails>()
                event?.let {
                    docs.add(it)
                }
            }
            withContext(Main) {
                callback(docs)
            }
        }

    fun submitRegistrations(
        registration: Map<String, Registration>
    ) = CoroutineScope(IO).launch {
        Firebase.firestore.runBatch { batch ->
            for ((k, v) in registration) {
                val schoolRegis = schoolsRef
                    .document(schoolCode)
                    .collection("registrations")
                    .document(k)
                batch.set(schoolRegis, v)
            }
            val school = schoolsRef.document(schoolCode)
            batch.update(school, mapOf("hasRegistered" to true))
        }.await()
    }


    fun invest(
        investment: Map<String, Int>,
        callback: (Boolean) -> Unit
    ) = CoroutineScope(IO).launch {
        try {
            Firebase.firestore.runBatch { batch ->
                for ((k, v) in investment) {
                    val schoolLog = schoolsRef
                        .document(schoolCode)
                        .collection("logs")
                        .document(k)
                    batch.set(schoolLog, mapOf("invested" to v))
                }
            }.await()
            callback(true)
        } catch (e: Exception) {
            callback(false)
        }

        val school = schoolsRef.document(schoolCode)
        school.update(mapOf("hasInvested" to true))
            .addOnCompleteListener {
                callback(it.isSuccessful)
            }
    }

    fun hasInvested(callback: (Boolean) -> Unit) = CoroutineScope(IO).launch {
        val school = schoolsRef.document(schoolCode).get().await()
        withContext(Main) {
            callback((school["hasInvested"] as? Boolean) ?: false)
        }
    }

    fun hasRegistered(callback: (Boolean) -> Unit) = CoroutineScope(IO).launch {
        val school = schoolsRef.document(schoolCode).get().await()
        withContext(Main) {
            callback((school["hasRegistered"] as? Boolean) ?: false)
        }
    }

    fun getSchoolName() = schoolNames[schoolCode]

    suspend fun getLogForEvent(eventName: String, callback: (Int?) -> Unit) =
        withContext(IO) {
            val school = schoolsRef
                .document(schoolCode)
                .collection("logs")
                .document(eventName)
                .get().await()
            withContext(Main) {
                callback((school["invested"] as? Long)?.toInt())
            }
        }
}