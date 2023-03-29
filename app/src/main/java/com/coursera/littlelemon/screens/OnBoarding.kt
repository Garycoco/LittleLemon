package com.coursera.littlelemon.screens

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.coursera.littlelemon.R
import com.coursera.littlelemon.navigation.Home
import com.coursera.littlelemon.ui.theme.DarkGreen
import com.coursera.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun OnBoarding(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences by lazy {
        context.getSharedPreferences("USER_PREFERENCES", ComponentActivity.MODE_PRIVATE)
    }
    var firstname by remember {
        mutableStateOf("")
    }
    var lastname by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = modifier
                    .fillMaxSize()
                    .size(20.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo),
                contentScale = ContentScale.FillHeight,
            )
        }

        Row(
            modifier = modifier
                .background(color = DarkGreen)
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.onBoardingTopText),
                color = Color.White
            )
        }

        Column(
            modifier = modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            VertSpace(height = 30)
            Text(text = "Personal Information")
            VertSpace(height = 20)
            Text(text = "First name")
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = firstname,
                onValueChange = { newValue -> firstname = newValue } ,
            )
            VertSpace(height = 20)
            Text(text = "Last Name")
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = lastname,
                onValueChange = { newValue -> lastname = newValue } ,
            )
            VertSpace(height = 20)
            Text(text = "Email")
            OutlinedTextField(
                modifier = modifier.fillMaxWidth(),
                value = email,
                onValueChange = { newValue -> email = newValue } ,
            )

            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                onClick = {
                    if (firstname.isBlank() || lastname.isBlank() || email.isBlank()) {
                         showToast(context)
                    } else {
                        sharedPreferences.edit(commit = true) {
                            putString("first_name", firstname)
                            putString("last_name", lastname)
                            putString("email", email)
                        }.also {
                            navController.navigate(Home.route)
                        }
                    }
                }
            ) {
                Text(text = "Register")
            }
        }
    }
}
private fun showToast(context: Context) {
    Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
}

@Composable
private fun VertSpace(height: Int) {
    Spacer(modifier = Modifier.height(height = height.dp))
}
@Composable
@Preview(showBackground = true)
fun OnBoardingPreview() {
    LittleLemonTheme {
        OnBoarding(navController = rememberNavController())
    }
}