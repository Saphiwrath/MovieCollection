package com.example.moviecollection.data.models

enum class AchievementName(val type: AchievementType, val number: Int) {
    Movie1(AchievementType.Movie, 1),
    Movie5(AchievementType.Movie, 5),
    Movie10(AchievementType.Movie, 10),
    Movie20(AchievementType.Movie, 20),
    Movie30(AchievementType.Movie, 30),
    Movie40(AchievementType.Movie, 40),
    Movie50(AchievementType.Movie, 50),
    Screening1(AchievementType.Screening, 1),
    Screening5(AchievementType.Screening, 5),
    Screening20(AchievementType.Screening, 20),
    Screening40(AchievementType.Screening, 40),
    Screening60(AchievementType.Screening, 60),
    Screening80(AchievementType.Screening, 80)
}

enum class AchievementType {
    Movie, Screening
}