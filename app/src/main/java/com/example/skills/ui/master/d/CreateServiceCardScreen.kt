package com.example.skills.ui.master.d

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skills.data.entity.Category
import com.example.skills.data.entity.Duration
import com.example.skills.data.entity.ServiceRequest
import com.example.skills.data.viewmodel.MainViewModel
import com.example.skills.ui.components.CustomButton
import com.example.skills.ui.components.CustomOutlinedTextField
import com.example.skills.ui.components.spaceBetweenOutlinedTextField
import com.example.skills.ui.components.tools.LoadingScreen
import com.example.skills.ui.theme.backgroundMaterial

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateServiceCardScreen(
    selectedCategory: Category,
    navController: NavHostController,
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
                        "Создать услугу",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    Row {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                tint = Color.Black,
                                contentDescription = "Localized description"
                            )
                        }
                    }

                }
            )
        },
    ) { innerPadding ->
        val isLoading by viewModel.isLoading.collectAsState()
        if (isLoading) {
            LoadingScreen()
        } else {
            ContentCreateServiceCard(innerPadding, navController, selectedCategory, viewModel)
        }
    }
}

@Composable
fun ContentCreateServiceCard(
    innerPadding: PaddingValues,
    navController: NavHostController,
    selectedCategory: Category,
    viewModel: MainViewModel
) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundMaterial)
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 8.dp,
                    end = 8.dp
                )
            ) {
                Text(text = "Категория: " + selectedCategory.name)
                Spacer(modifier = Modifier.height(8.dp))

                CustomOutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Название"
                )
                Spacer(modifier = Modifier.height(spaceBetweenOutlinedTextField))

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text(text = "Цена в рублях") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedLabelColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(spaceBetweenOutlinedTextField))

                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text(text = "Длительность в минутах") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedLabelColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(spaceBetweenOutlinedTextField))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "Описание") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedLabelColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            CustomButton(
                navigateTo = {
                    if (name.isNotEmpty() && price.isNotEmpty() && duration.isNotEmpty() && description.isNotEmpty()) {
                        try {
                            viewModel.addService(
                                ServiceRequest(
                                    name,
                                    description,
                                    price.toInt(),
                                    Duration(duration.toInt() / 60, duration.toInt() % 60),
                                    category = selectedCategory
                                ),
                                selectedCategory.id
                            ) { successful ->
                                if (successful) {
                                    navController.popBackStack()
                                }
                            }
                        } catch (_: Exception) {
                        }
                    }
                },
                enabled = if (name.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && duration.isNotEmpty()) true else false,
                buttonText = "Создать"
            )
        }
    }
}