package com.coursera.littlelemon.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.coursera.littlelemon.R
import com.coursera.littlelemon.navigation.Profile
import com.coursera.littlelemon.ui.theme.DarkGreen
import com.coursera.littlelemon.ui.theme.Yellow

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences by lazy {
        context.getSharedPreferences("USER_PREFERENCES", ComponentActivity.MODE_PRIVATE)
    }
    val name = sharedPreferences.getString("first_name", "")
    Column(modifier = Modifier) {
        HomeHeader(navController = navController)
        Hero()
    }
}

@Composable
fun HomeHeader(modifier: Modifier = Modifier, navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(.10f)
            .padding(10.dp),
    ) {
        Box(modifier = modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .size(height = 40.dp, width = 120.dp)
                    .align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "profile image",
                modifier = modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(Profile.route)
                    }
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
fun Hero(modifier: Modifier = Modifier) {
    var searchPhrase by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .background(color = DarkGreen)
            .padding(horizontal = 10.dp, vertical = 20.dp)
    ) {
        Text(text = "Little Lemon", fontSize = 32.sp, color = Yellow)
        Text(text = "Chicago", fontSize = 22.sp, color = Color.White)
        Row {
            Box(modifier = modifier.fillMaxWidth().padding(vertical = 10.dp)) {
                Text(
                    text = "We are a family owned Mediterranean restaurant," +
                            " focused on traditional recipes served with a modern twist.",
                    color = Color.White,
                    lineHeight = 20.sp,
                    modifier = modifier
                        .padding(end = 10.dp)
                        .align(Alignment.BottomStart)
                        .fillMaxWidth(.60f)
                )
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = "house dish",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.TopEnd)
                        .fillMaxWidth(.40f)
                        .size(height = 150.dp, width = 0.dp)
                )
            }
        }
        
        OutlinedTextField(
            value = searchPhrase,
            onValueChange = { newValue -> searchPhrase = newValue },
            leadingIcon = {
               Icon(imageVector = Icons.Default.Search, contentDescription = "Search")            
            },
            placeholder = { Text(text = "Enter search phrase") },
            modifier = modifier
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()
        )
    }
}