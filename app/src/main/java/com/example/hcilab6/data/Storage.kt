package com.example.hcilab6.data

import android.content.Context

class Storage(context: Context) {
    private val prefs = context.getSharedPreferences("fitflow_prefs", Context.MODE_PRIVATE)

    fun string(key: String, def: String): String = prefs.getString(key, def) ?: def
    fun putString(key: String, value: String) = prefs.edit().putString(key, value).apply()

    fun int(key: String, def: Int): Int = prefs.getInt(key, def)
    fun putInt(key: String, value: Int) = prefs.edit().putInt(key, value).apply()

    fun bool(key: String, def: Boolean): Boolean = prefs.getBoolean(key, def)
    fun putBool(key: String, value: Boolean) = prefs.edit().putBoolean(key, value).apply()

    fun stringSet(key: String): Set<String> = prefs.getStringSet(key, emptySet())?.toSet() ?: emptySet()
    fun putStringSet(key: String, value: Set<String>) = prefs.edit().putStringSet(key, value).apply()

    fun loadWorkout(): List<String> {
        val raw = prefs.getString("workout", null) ?: return DemoData.defaultWorkout
        return raw.split("\n").filter { it.isNotBlank() }
    }

    fun saveWorkout(list: List<String>) = putString("workout", list.joinToString("\n"))

    fun loadMeals(): List<Meal> =
        string("meals", "").split("\n").mapNotNull { line ->
            val p = line.split("|")
            if (p.size == 2) p[1].toIntOrNull()?.let { Meal(p[0], it) } else null
        }

    fun saveMeals(list: List<Meal>) = putString("meals", list.joinToString("\n") { "${it.name}|${it.kcal}" })

    fun clearAll() = prefs.edit().clear().apply()
}