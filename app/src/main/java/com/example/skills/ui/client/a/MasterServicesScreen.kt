package com.example.skills.ui.client.a

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skills.data.api.CategoryResponse
import com.example.skills.data.viewmodel.MainViewModel
import com.example.skills.data.viewmodel.route.BookingViewModel
import com.example.skills.ui.client.a.new_booking.ServiceCardClient
import com.example.skills.ui.components.tools.LoadingScreen
import com.example.skills.ui.theme.paddingBetweenElements

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterServicesScreen(
    bookingViewModel: BookingViewModel,
    navController: NavHostController,
    navigateToSelectDate: () -> Unit,
    viewModel: MainViewModel
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
                        "Выберите услугу",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        val isLoading by viewModel.isLoading.collectAsState()
        if (isLoading) {
            LoadingScreen()
        } else {
            MasterMyServices(
                innerPadding,
                bookingViewModel,
                navigateToSelectDate,
                viewModel
            )
        }
    }
}


@Composable
fun MasterMyServices(
    innerPadding: PaddingValues,
    bookingViewModel: BookingViewModel,
    navigateToSelectDate: () -> Unit,
    viewModel: MainViewModel
) {
    val master = bookingViewModel.data1.value!!

    val receivedCategories = bookingViewModel.data1.value!!.categories
    var selectedCategory by remember { mutableStateOf(if (receivedCategories?.isNotEmpty() == true) receivedCategories!!.first().name else "") }

    val services by viewModel.servicesLiveDataClient.observeAsState()

    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = innerPadding.calculateTopPadding().plus(12.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        if (receivedCategories!!.isEmpty()) {
            Text(
                text = "В списке мастера отсутствуют категории услуг",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier.padding(start = 12.dp, top = 25.dp)
            )
        } else {
            LazyRow(Modifier.fillMaxWidth()) {
                items(receivedCategories) { category ->
                    val interactionSource = remember { MutableInteractionSource() }

                    CategoryButton(
                        text = category.name.toString(),
                        onClick = {
                            {}
                            selectedCategory = category.name
                        },
                        interactionSource = interactionSource,
                        containerColor = if (category.name == selectedCategory) Color.Black else Color.White,
                        contentColor = if (category.name == selectedCategory) Color.White else Color.Gray
                    )
                }
            }

            val selectedServicesByCategory =
                services?.filter { it.category.name == selectedCategory }

            if (selectedServicesByCategory != null) {
                LazyColumn(modifier = Modifier.padding(bottom = 100.dp)) {
                    items(selectedServicesByCategory) { singleService ->
                        ServiceCardClient(
                            singleService,
                            navigateToSelectDate,
                            master,
                            bookingViewModel
                        )
                    }
                }
            } else {
                Text(
                    text = "В этой категории пока нет услуг.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 12.dp, top = 25.dp)
                )
            }
        }
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
