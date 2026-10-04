package com.example.hcilab6.data

data class Meal(val name: String, val kcal: Int)

enum class Energy(val label: String, val plan: String) {
    LOW("Low", "20-min gentle stretch and walk"),
    MEDIUM("Medium", "30-min full-body circuit"),
    HIGH("High", "40-min HIIT and core")
}

object DemoData {
    val defaultWorkout = listOf(
        "Warm-up jog 5 min", "Push-ups 3x12", "Squats 3x15", "Plank 3x40s", "Stretching 5 min"
    )
    val challenges = listOf("10k Steps Week", "30-Day Plank", "Morning Run Club")
    val posts = listOf(
        "Priya completed Morning Run Club!",
        "Nimal hit a 7-day streak!",
        "Family circle: Sunday walk at 7 am"
    )
    val foods = listOf(
        Meal("Rice and curry", 520),
        Meal("Egg sandwich", 340),
        Meal("Fruit salad", 160),
        Meal("Grilled chicken bowl", 450)
    )
}