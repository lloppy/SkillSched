package com.example.skills.role.components

import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skills.data.backend.ApiService
import com.example.skills.data.backend.AuthRequest
import com.example.skills.role.components.tools.EmailState
import com.example.skills.role.components.tools.EmailStateSaver
import com.example.skills.role.components.tools.PasswordState
import com.example.skills.ui.theme.backgroundMaterial
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavHostController,
    navigateToCodeVerification: () -> Unit
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
                        "Регистрация",
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
                                imageVector = Icons.Filled.ArrowBack,
                                tint = Color.Black,
                                contentDescription = "Localized description"
                            )
                        }
                    }

                }
            )
        },
    ) { innerPadding ->
        ContentSingIn(innerPadding, navigateToCodeVerification)
    }
}

@Composable
fun ContentSingIn(
    innerPadding: PaddingValues,
    navigateToCodeVerification: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var secondName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
        mutableStateOf(EmailState(email))
    }
    val passwordState = remember { PasswordState() }
    val passwordStateRepeat = remember { PasswordState() }
    val onSubmit = @androidx.compose.runtime.Composable {
        val apiService: ApiService = Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        if (emailState.isValid && passwordState.isValid) {
            val authRequest = AuthRequest(emailState.text.trim(), passwordState.text.trim())
//            LaunchedEffect(authRequest) {
//                val response = apiService.register(authRequest)
//                if (response.isSuccessful) {
//                    Log.e("RegistrationError", "body code is ${response.body()!!.token} ${response.body()}")
//                    navigateToCodeVerification() // тут переделать
//                } else {
//                    Log.e("RegistrationError", "Server returned an error: ${response.errorBody()}")
//                }
//            }
        } else {
            Log.e("RegistrationError", "emailState.isValid && passwordState.isValid not valid")
        }

    }
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
                Spacer(modifier = Modifier.height(8.dp))
                CustomOutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = "Имя"
                )
                Spacer(modifier = Modifier.height(spaceBetweenOutlinedTextField))
                CustomOutlinedTextField(
                    value = secondName,
                    onValueChange = { secondName = it },
                    label = "Фамилия"
                )

                Spacer(modifier = Modifier.height(spaceBetweenOutlinedTextField))
                Email(emailState, onImeAction = { focusRequester.requestFocus() })

                Spacer(modifier = Modifier.height(spaceBetweenOutlinedTextField))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text(text = "Номер телефона") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedLabelColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(spaceBetweenOutlinedTextField))
                Password(
                    label = "Пароль",
                    passwordState = passwordState,
                    modifier = Modifier.focusRequester(focusRequester),
//                onImeAction = {
//                    onSubmit()
//                }
                )
                Password(
                    label = "Повторите пароль",
                    passwordState = passwordStateRepeat,
                    modifier = Modifier.focusRequester(focusRequester),
//                onImeAction = {
//                    onSubmit()
//                }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            CustomButton(
                navigateTo = navigateToCodeVerification, //тут похоже переделать
                buttonText = "Зарегистрироваться"
                // action = onSubmit()
            )
        }
    }
}

var spaceBetweenOutlinedTextField = 10.dp