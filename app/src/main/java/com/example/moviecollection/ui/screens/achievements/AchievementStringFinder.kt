package com.example.moviecollection.ui.screens.achievements

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.moviecollection.R
import com.example.moviecollection.data.models.AchievementName

class AchievementPositions(
) {
    companion object {
        const val ACHIEVEMENT_NAME: Int = 0
        const val ACHIEVEMENT_CONDITION = 1
    }
}

@Composable
fun achievementStringFinder(name: String): Array<String> {
    when (AchievementName.valueOf(name)) {
        AchievementName.Movie1 -> {
            return arrayOf(
                stringResource(R.string.Movie1_ach_name),
                stringResource(R.string.Movie1_ach_desc))
        }
        AchievementName.Movie5 -> {
            return arrayOf(
                stringResource(R.string.Movie5_ach_name),
                stringResource(R.string.Movie5_ach_desc))
        }
        AchievementName.Movie10 -> {
            return arrayOf(
                stringResource(R.string.Movie10_ach_name),
                stringResource(R.string.Movie10_ach_desc))
        }
        AchievementName.Movie20 -> {
            return arrayOf(
                stringResource(R.string.Movie20_ach_name),
                stringResource(R.string.Movie20_ach_desc))
        }
        AchievementName.Movie30 -> {
            return arrayOf(
                stringResource(R.string.Movie30_ach_name),
                stringResource(R.string.Movie30_ach_desc))
        }
        AchievementName.Movie40 -> {
            return arrayOf(
                stringResource(R.string.Movie40_ach_name),
                stringResource(R.string.Movie40_ach_desc))
        }
        AchievementName.Movie50 -> {
            return arrayOf(
                stringResource(R.string.Movie50_ach_name),
                stringResource(R.string.Movie50_ach_desc))
        }
        AchievementName.Screening1 -> {
            return arrayOf(
                stringResource(R.string.Screening1_ach_name),
                stringResource(R.string.Screening1_ach_desc)
            )
        }
        AchievementName.Screening5 ->  {
            return arrayOf(
                stringResource(R.string.Screening5_ach_name),
                stringResource(R.string.Screening5_ach_desc)
            )
        }
        AchievementName.Screening20 ->  {
            return arrayOf(
                stringResource(R.string.Screening20_ach_name),
                stringResource(R.string.Screening20_ach_desc)
            )
        }
        AchievementName.Screening40 ->  {
            return arrayOf(
                stringResource(R.string.Screening40_ach_name),
                stringResource(R.string.Screening40_ach_desc)
            )
        }
        AchievementName.Screening60 ->  {
            return arrayOf(
                stringResource(R.string.Screening60_ach_name),
                stringResource(R.string.Screening60_ach_desc)
            )
        }
        AchievementName.Screening80 ->  {
            return arrayOf(
                stringResource(R.string.Screening80_ach_name),
                stringResource(R.string.Screening80_ach_desc)
            )
        }
    }
}