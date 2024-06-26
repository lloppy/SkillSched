package com.example.skills.navigation.nav_bar.client

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.skills.navigation.ScreenClient
import com.example.skills.ui.theme.blackMaterial
import com.example.skills.ui.theme.orangeMaterial
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.items.dropletbutton.DropletButton


@Composable
fun ClientBottomNavigation(
    navController: NavController
) {
    val dropletButtons = listOf(
        ScreenClient.ClientMastersScreen,
        ScreenClient.ClientBookingsScreen,
        ScreenClient.ClientSettingsScreen,
    )

    var selectedItem by remember { mutableStateOf(1) }
    AnimatedNavigationBar(
        modifier = Modifier
            .padding(bottom = 8.dp, start = 10.dp, end = 10.dp)
            .height(80.dp),
        selectedIndex = selectedItem,
        ballColor = blackMaterial,
        barColor = blackMaterial,
        // barColor = Color.White, Ваня хочет этот цвет, а я не хочу
        cornerRadius = shapeCornerRadius(25.dp),
        ballAnimation = Parabolic(tween(Duration, easing = LinearOutSlowInEasing)),
        indentAnimation = Height(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(
                DoubleDuration,
                easing = { OvershootInterpolator().getInterpolation(it) })
        )
    ) {
        dropletButtons.forEachIndexed { index, item ->
            DropletButton(
                modifier = Modifier.fillMaxSize(),
                isSelected = selectedItem == index,
                icon = item.iconId,
                dropletColor = orangeMaterial,
                animationSpec = tween(
                    durationMillis = Duration,
                    easing = LinearEasing
                ),
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route) {
                        popUpTo(ScreenClient.ClientBookingsScreen.route) {
                            saveState = true
                        }
                        launchSingleTop = true

                    }
                }
            )
        }
    }
}

const val Duration = 500
const val DoubleDuration = 1000