package com.example.moviecollection.ui.screens.achievements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviecollection.R
import com.example.moviecollection.ui.components.AchievementCard
import com.example.moviecollection.ui.components.GraphCard
import com.example.moviecollection.ui.components.StandardAppBar
import com.example.moviecollection.ui.screens.entityviewmodels.LockedAchievementsState
import com.example.moviecollection.ui.screens.entityviewmodels.ScreeningState
import com.example.moviecollection.ui.screens.entityviewmodels.UnlockedAchievementState
import com.example.moviecollection.utils.getDaysOfWeek
import java.util.Calendar

@Composable
fun AchievementsScreen (
    navController: NavHostController,
    screeningsState: ScreeningState,
    lockedAchievementsState: LockedAchievementsState,
    unlockedAchievementState: UnlockedAchievementState
) {
    Scaffold (
        topBar = {
            StandardAppBar(
                title = stringResource(R.string.achievements_screen_title),
                actions = {},
                navigateUp = { navController.navigateUp() }
            )
        }
    ) {paddingValues ->
        Column (
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            GraphCard(
                daysOfWeek = getDaysOfWeek(),
                screenings = screeningsState.screenings
            )
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.achieved_title),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                )
                {
                    items(unlockedAchievementState.achievements) {
                        val strings = achievementStringFinder(it.name)
                        AchievementCard(
                            name = strings[AchievementPositions.ACHIEVEMENT_NAME],
                            condition = strings[AchievementPositions.ACHIEVEMENT_CONDITION]
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.not_achieved_title),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                )
                {
                    items(lockedAchievementsState.achievements) {
                        val strings = achievementStringFinder(it.name)
                        AchievementCard(
                            name = strings[AchievementPositions.ACHIEVEMENT_NAME],
                            condition = strings[AchievementPositions.ACHIEVEMENT_CONDITION],
                            achieved = false
                        )
                    }
                }
            }
        }
    }
}