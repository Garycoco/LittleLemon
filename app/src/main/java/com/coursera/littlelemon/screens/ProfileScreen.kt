package com.coursera.littlelemon.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavController
import com.coursera.littlelemon.R
import com.coursera.littlelemon.navigation.OnBoarding

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column {
        Header(modifier, navController)
        PersonalInformation(modifier = modifier, navController = navController)
    }
}
@Composable
fun Header(modifier: Modifier, navController: NavController) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(.25f)
            .padding(top = 20.dp)
    ) {
        IconButton( modifier = modifier.align(Alignment.TopStart), onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
        }
        Image(
            modifier = modifier.size(height = 60.dp, width = 200.dp).align(Alignment.TopCenter),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            contentScale = ContentScale.FillBounds,
        )
    }
}
@Composable
fun PersonalInformation(modifier: Modifier, navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences by lazy {
        context.getSharedPreferences("USER_PREFERENCES", ComponentActivity.MODE_PRIVATE)
    }
    val name = sharedPreferences.getString("first_name", "")
    val surname = sharedPreferences.getString("last_name", "")
    val email = sharedPreferences.getString("email", "")


    Box(modifier = modifier.padding(top = 10.dp, bottom = 20.dp, end = 10.dp, start = 10.dp)) {
        Column(modifier = modifier
            .fillMaxSize()
        ) {
            Text(text = "Personal Information", fontSize = 32.sp, fontWeight = FontWeight.W600)
            Spacer(modifier = modifier.height(20.dp))
            EditTextDesign(title = "First name", textFieldLabel = name!!)
            EditTextDesign(title = "Last name", textFieldLabel = surname!!)
            EditTextDesign(title = "Email", textFieldLabel = email!!)
        }
        Button(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            onClick = {
                sharedPreferences.edit(commit = true) {
                    clear()
                }
                navController.navigate(OnBoarding.route) {
                    launchSingleTop = true
                }
            }
        ) {
            Text(text = "Log out")
        }
    }
}
@Composable
fun EditTextDesign(title: String, textFieldLabel: String, modifier: Modifier = Modifier) {
    var email by remember {
        mutableStateOf(textFieldLabel)
    }
    Column(modifier = modifier.padding(bottom = 20.dp)) {
        Text(text = title)
        Spacer(modifier = modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = email,
            onValueChange = { newValue -> email = newValue }
        )
    }
}