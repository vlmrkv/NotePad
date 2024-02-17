package com.mrkv.diary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavGraph()
        }
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "diaryList"
    ) {
        composable("diaryList") {
            DiaryList(navController)
        }
        //composable("createDiaryPage")
    }
}

@Composable
fun DiaryList(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(10) {
            DiaryListItem(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryListItem(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { navController.navigate("createDiaryPage") }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(red = 98, green = 32, blue = 24, alpha = 60)),
            Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoilImage(
                    imageModel = { "https://plus.unsplash.com/premium_photo-1671478394583-3d91fd9c7ca5" },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .size(70.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                )
                Column(
                    modifier = Modifier
                        .weight(2.0f)
                ) {
                    Text(
                        text = "Header of notes",
                        style = TextStyle(
                            fontSize = 24.sp,
                            textAlign = TextAlign.Justify,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Date of notes",
                        style = TextStyle(
                            textAlign = TextAlign.Justify
                        )
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1.0f)
                        .background(color = Cyan),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "2024-02-08\n13:00",
                        style = TextStyle(
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun DiaryPage() {
//    Box(
//        modifier = Modifier
//            .fillMaxHeight()
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.ic_launcher_foreground),
//            contentDescription = "Image",
//            alignment = Alignment.Center,
//            contentScale = ContentScale.Crop,
//        )
//        // we are creating a variable for
//        // getting a value of our text field.
//        val inputvalue = remember { mutableStateOf(TextFieldValue()) }
//        Column(
//            // we are using column to align our
//            // imageview to center of the screen.
//            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
//
//            // below line is used for specifying
//            // vertical arrangement.
//            verticalArrangement = Arrangement.Center,
//
//            // below line is used for specifying
//            // horizontal arrangement.
//            horizontalAlignment = Alignment.CenterHorizontally,
//        )
//        {
//            TextField(
//                // below line is used to get
//                // value of text field,
//                value = inputvalue.value,
//
//                // below line is used to get value in text field
//                // on value change in text field.
//                onValueChange = { inputvalue.value = it },
//
//                // below line is used to add placeholder
//                // for our text field.
//                placeholder = { Text("Enter user name") },
//
//                // modifier is use to add padding
//                // to our text field.
//                modifier = Modifier.padding(all = 16.dp).fillMaxWidth(),
//
//                // keyboard options is used to modify
//                // the keyboard for text field.
//                keyboardOptions = KeyboardOptions(
//                    // below line is use for capitalization
//                    // inside our text field.
//                    capitalization = KeyboardCapitalization.None,
//
//                    // below line is to enable auto
//                    // correct in our keyboard.
//                    autoCorrect = true,
//
//                    // below line is used to specify our
//                    // type of keyboard such as text, number, phone.
//                    keyboardType = KeyboardType.Text,
//                ),
//
//                // below line is use to specify
//                // styling for our text field value.
//                textStyle = TextStyle(color = Color.Black,
//                    // below line is used to add font
//                    // size for our text field
//                    fontSize = TextUnit.Companion.Unspecified,
//
//                    // below line is use to change font family.
//                    fontFamily = FontFamily.SansSerif),
//
//                // below line is use to give
//                // max lines for our text field.
//                maxLines = 2,
//
//                // active color is use to change
//                // color when text field is focused.
//                activeColor = colorResource(id = R.color.purple_200),
//
//                // single line boolean is use to avoid
//                // textfield entering in multiple lines.
//                singleLine = true,
//
//                // inactive color is use to change
//                // color when text field is not focused.
//                inactiveColor = Color.Gray,
//
//                // below line is use to specify background
//                // color for our text field.
//                backgroundColor = Color.LightGray,
//
//                // leading icon is use to add icon
//                // at the start of text field.
//                leadingIcon = {
//                    // In this method we are specifying
//                    // our leading icon and its color.
//                    Icon(Icons.Filled.AccountCircle, tint = colorResource(id = R.color.purple_200))
//                },
//                // trailing icons is use to add
//                // icon to the end of text field.
//                trailingIcon = {
//                    Icon(Icons.Filled.Info, tint = colorResource(id = R.color.purple_200))
//                },
//            )
//        }
//    }
//}
