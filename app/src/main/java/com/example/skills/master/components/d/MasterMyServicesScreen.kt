package com.example.skills.master.components.d

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skills.R
import com.example.skills.data.viewmodel.MainViewModel
import com.example.skills.role.ScreenRole
import com.example.skills.role.components.CustomButton
import com.example.skills.ui.theme.paddingBetweenElements
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterMyServicesScreen(
    navigateToCreateCategory: () -> Unit,
    navigateToChangeCategory: () -> Unit,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Мои услуги",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.archive),
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        MasterMyServices(
            innerPadding,
            navigateToCreateCategory,
            navigateToChangeCategory,
            navController
        )
    }
}


@Composable
fun MasterMyServices(
    innerPadding: PaddingValues,
    navigateToCreateCategory: () -> Unit,
    navigateToChangeCategory: () -> Unit,
    navController: NavHostController
) {

//                Category("Добавить категорию", navigateToCreateCategory)
    val mainViewModel = MainViewModel()
    var selectedCategory by remember { mutableStateOf(if (mainViewModel.categories.isNotEmpty()) mainViewModel.categories.first().name else "") }

    Column(
        modifier = Modifier
            .padding(
                top = innerPadding.calculateTopPadding().plus(12.dp),
                bottom = 100.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier
                .weight(1f)
                .padding(
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            if (mainViewModel.categories.size <= 1) {
                Button(onClick = navigateToCreateCategory, Modifier.weight(1f)) {
                    Text("Добавить категорию")
                }
                Text(
                    text = "В вашем списке отсутствуют категории услуг. Чтобы добавить категорию, воспользуйтесь кнопкой выше.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 12.dp, top = 25.dp)
                )
            } else {
                LazyRow(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    items(mainViewModel.categories) { category ->
                        val interactionSource = remember { MutableInteractionSource() }
                        val viewConfiguration = LocalViewConfiguration.current

                        LaunchedEffect(interactionSource) {
                            var isLongClick = false
                            interactionSource.interactions.collectLatest { interaction ->
                                when (interaction) {
                                    is PressInteraction.Press -> {
                                        isLongClick = false
                                        delay(viewConfiguration.longPressTimeoutMillis)
                                        isLongClick = true
                                        // categories = categories.filter { it.name != category.name }
                                        navigateToChangeCategory.invoke()
                                        // Toast.makeText(context, "Long click", Toast.LENGTH_SHORT).show()
                                    }

                                    is PressInteraction.Release -> {
                                        if (isLongClick.not()) {
                                            // Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }
                        CategoryButton(
                            text = category.name,
                            onClick = {
                                category.action.invoke()
                                selectedCategory = category.name
                            },
                            interactionSource = interactionSource,
                            containerColor = if (category.name == selectedCategory) Color.Black else Color.White,
                            contentColor = if (category.name == selectedCategory) Color.White else Color.Gray
                        )
                    }
                }

                val selectedServicesByCategory =
                    mainViewModel.services.filter { it.category.name == selectedCategory }

                if (selectedServicesByCategory != null) {
                    LazyColumn {
                        items(selectedServicesByCategory) { singleService ->
                            SingleServiceCard(singleService, navController)
                        }
                    }
                } else {
                    Text(
                        text = "В этой категории пока нет услуг. \nЧтобы создать их, нажмите на иконку с плюсом в левом верхнем углу.",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 12.dp, top = 25.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        CustomButton(
            navigateTo = {
                try {
                    val serviceId = selectedCategory
                    navController.navigate(
                        ScreenRole.Master.CreateServiceCard.route.replace(
                            "{serviceId}",
                            serviceId
                        )
                    )
                } catch (e: IllegalArgumentException) { // нужно блин выбрать категорию, а не тыкать в пустоту
                }
            },
            buttonText = "Добавить услугу"
        )
    }
}

@Composable
fun CategoryButton(
    text: String,
    onClick: () -> Unit,
    containerColor: Color,
    contentColor: Color,
    interactionSource: MutableInteractionSource
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(45.dp)
            .padding(end = paddingBetweenElements),
        shape = RoundedCornerShape(40.dp),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = BorderStroke(1.dp, Color.Gray)

    ) {
        Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Normal)
    }
}