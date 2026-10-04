package com.example.hcilab6

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.hcilab6.data.DemoData
import com.example.hcilab6.data.Energy
import com.example.hcilab6.data.Meal
import com.example.hcilab6.data.Storage

class FitFlowViewModel(app: Application) : AndroidViewModel(app) {

    private val storage = Storage(app)
    val weeklyGoal = 5

    // ----- Home / Daily Flow -----
    var energy by mutableStateOf(
        runCatching { Energy.valueOf(storage.string("energy", "MEDIUM")) }.getOrDefault(Energy.MEDIUM)
    )
        private set
    var workoutsDone by mutableIntStateOf(storage.int("done", 2))
        private set
    var personalization by mutableStateOf(storage.bool("personalization", true))
        private set

    val dailyPlan: String
        get() = if (personalization) energy.plan else "30-min standard full-body workout"

    fun updateEnergy(e: Energy) { energy = e; storage.putString("energy", e.name) }
    fun completeWorkout() {
        if (workoutsDone < weeklyGoal) { workoutsDone++; storage.putInt("done", workoutsDone) }
    }
    fun updatePersonalization(on: Boolean) { personalization = on; storage.putBool("personalization", on) }

    // ----- Workout builder -----
    val workout = mutableStateListOf<String>().apply { addAll(storage.loadWorkout()) }

    fun addExercise(name: String) {
        if (name.isNotBlank()) { workout.add(name.trim()); storage.saveWorkout(workout) }
    }
    fun moveUp(i: Int) {
        if (i > 0) { val t = workout[i]; workout[i] = workout[i - 1]; workout[i - 1] = t; storage.saveWorkout(workout) }
    }
    fun moveDown(i: Int) {
        if (i < workout.lastIndex) { val t = workout[i]; workout[i] = workout[i + 1]; workout[i + 1] = t; storage.saveWorkout(workout) }
    }
    fun removeExercise(i: Int) { workout.removeAt(i); storage.saveWorkout(workout) }

    // ----- Social -----
    var privateCircle by mutableStateOf(storage.bool("private_circle", true))
        private set
    val joined = mutableStateListOf<String>().apply { addAll(storage.stringSet("joined")) }
    val liked = mutableStateListOf<String>().apply { addAll(storage.stringSet("liked")) }

    fun updatePrivateCircle(on: Boolean) { privateCircle = on; storage.putBool("private_circle", on) }
    fun toggleJoin(title: String) {
        if (title in joined) joined.remove(title) else joined.add(title)
        storage.putStringSet("joined", joined.toSet())
    }
    fun toggleLike(post: String) {
        if (post in liked) liked.remove(post) else liked.add(post)
        storage.putStringSet("liked", liked.toSet())
    }

    // ----- Nutrition -----
    val meals = mutableStateListOf<Meal>().apply { addAll(storage.loadMeals()) }

    fun addMeal(m: Meal) { meals.add(m); storage.saveMeals(meals) }
    fun totalKcal(): Int = meals.sumOf { it.kcal }

    // ----- Privacy: delete all data -----
    fun deleteAllData() {
        storage.clearAll()
        energy = Energy.MEDIUM
        workoutsDone = 0
        personalization = true
        privateCircle = true
        workout.clear(); workout.addAll(DemoData.defaultWorkout)
        meals.clear(); joined.clear(); liked.clear()
    }
}