package com.coursera.littlelemon.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.coursera.littlelemon.R
import com.coursera.littlelemon.data.MenuItem
import com.coursera.littlelemon.navigation.Profile
import com.coursera.littlelemon.ui.theme.*

@Composable
fun HomeScreen(navController: NavController, menuItems: List<MenuItem>) {
    var searchPhrase by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        HomeHeader(navController = navController)
        Hero(searchPhrase = searchPhrase, onSearchTextChanged = { text -> searchPhrase = text })
        if (searchPhrase.isEmpty()) LowerPart(items = menuItems)
        else LowerPart(items = menuItems.filter { item ->
            item.title.contains(searchPhrase, ignoreCase = true)
        })
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
            .verticalScroll(rememberScrollState())
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
fun Hero(modifier: Modifier = Modifier, searchPhrase: String, onSearchTextChanged: (String) -> Unit) {
    Column(
        modifier = modifier
            .background(color = DarkGreen)
            .padding(horizontal = 10.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Little Lemon", fontSize = 64.sp, color = Yellow, fontFamily = markazi)
        Text(text = "Chicago", fontSize = 48.sp, color = Color.White, fontFamily = markazi)
        Row {
            Box(modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)) {
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
            onValueChange = { newValue -> onSearchTextChanged(newValue) },
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

@Composable
fun LowerPart(items: List<MenuItem>) {
    val tabsItems = listOf(
        "Starters",
        "Mains",
        "Desserts",
        "Drinks"
    )
    var tabText by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(text = "ORDER FOR DELIVERY!", style = Typography.h5, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(10.dp))
        LazyRow {
            items(
                items = tabsItems,
                itemContent = { item ->
                    TabsItem(item = item, onTabClick = { tabText = it })
                }
            )
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ) {
        items(
            items = if (tabText.isEmpty()) items else items.filter { it.category == tabText },
            itemContent = { menuItem ->
                MenuItems(item = menuItem)
            }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItems(
    modifier: Modifier = Modifier,
    item: MenuItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(10.dp)
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 10.dp)
                .weight(1f)
        ) {
            Divider(modifier = modifier.padding(bottom = 10.dp))
            Text(text = item.title, style = Typography.h5)
            Text(text = item.description, modifier = modifier.padding(vertical = 5.dp))
            Text(text = "$${item.price}", fontWeight = FontWeight.W600)
        }
        GlideImage(
            model = item.image,
            contentDescription = "item image",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(100.dp)
                .padding(top = 10.dp, end = 10.dp)
        )
    }
}

@Composable
fun TabsItem(item: String, onTabClick: (String) -> Unit) {
    Text(
        text = item,
        modifier = Modifier
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Grey200)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                onTabClick(item.lowercase())
            }
    )
}

/*
@Composable
@Preview(showBackground = true)
fun TabsItemPreview() {
    LittleLemonTheme {
        TabsItem(item = "Salads")
    }
}*/
