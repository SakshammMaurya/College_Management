package com.example.collegemanagement.Admin.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collegemanagement.Models.DashboardItemModel
import com.example.collegemanagement.Models.NavItems
import com.example.collegemanagement.Navigation.Routes
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.standardQuadFromTo
import com.example.collegemanagement.Viewmodel.AuthViewModel
import com.example.collegemanagement.ui.TITLE_SIZE
import com.example.collegemanagement.ui.theme.Beige1
import com.example.collegemanagement.ui.theme.Beige2
import com.example.collegemanagement.ui.theme.Beige3
import com.example.collegemanagement.ui.theme.BlueViolet1
import com.example.collegemanagement.ui.theme.BlueViolet2
import com.example.collegemanagement.ui.theme.BlueViolet3
import com.example.collegemanagement.ui.theme.ButtonBlue
import com.example.collegemanagement.ui.theme.DarkerButtonBlue
import com.example.collegemanagement.ui.theme.DeepBlue
import com.example.collegemanagement.ui.theme.LightGreen1
import com.example.collegemanagement.ui.theme.LightGreen2
import com.example.collegemanagement.ui.theme.LightGreen3
import com.example.collegemanagement.ui.theme.OrangeYellow1
import com.example.collegemanagement.ui.theme.OrangeYellow2
import com.example.collegemanagement.ui.theme.OrangeYellow3
import com.example.collegemanagement.ui.theme.Purple80
import com.example.collegemanagement.ui.theme.TextWhite
import com.google.android.gms.common.Feature
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard2(navController: NavController){
    val authViewModel = AuthViewModel()
    val list = listOf(
        DashboardItemModel("Manage Banner",Routes.ManageBanner.route),
        DashboardItemModel("Manage Notice",Routes.ManageNotice.route),
        DashboardItemModel("Manage Gallery",Routes.ManageGallery.route),
        DashboardItemModel("Manage Faculty",Routes.ManageFaculty.route),
        DashboardItemModel("Manage College Info",Routes.ManageCollegeInfo.route),
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val list2 = listOf(
        NavItems(
            "Website",
            R.drawable.web
        ),
        NavItems(
            "Notice",
            R.drawable.reminders
        ),
        NavItems(
            "Notes",
            R.drawable.notes
        ),
        NavItems(
            "Contact Us",
            R.drawable.contactus
        )
    )

    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = {
                ModalDrawerSheet {
                        Column {


                            Image(
                                painter = painterResource(id = R.drawable.djlhc),
                                contentDescription = null,
                                modifier = Modifier.height(220.dp),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text = "NIT Jamshedpur",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.displayMedium
                            )
                            list2.forEachIndexed { index, items ->
                                NavigationDrawerItem(label = {
                                    Text(text = items.title)
                                }, selected = index == selectedItemIndex, onClick = {
                                    Toast.makeText(context, items.title, Toast.LENGTH_SHORT).show()
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = items.icon),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                        )
                                    })
                            }
                            Spacer(modifier = Modifier.height(250.dp))
                            Button(
                                onClick = {
                                    authViewModel.logout {
                                        navController.navigate(Routes.Login.route) {
                                            popUpTo(0) // Clear backstack
                                        }
                                    }
                                },
                                modifier = Modifier.padding(16.dp).align(Alignment.End)
                            ) {
                                Text(text = "Logout")

                            }
                        }

                }


        },
        modifier = Modifier.background(DeepBlue),
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(title = {
                        Text(
                            text = "Admin Dashboard",
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            color = Color.Black
                        )},
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(painter = painterResource(id = R.drawable.menu), contentDescription =null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Black)
                            }
                        },
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = Color.White
                        ))
                } ,
                content = {padding->
                    Box (
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxSize()
                            .padding(padding)
                    ){
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FeatureSection(
                                features = listOf(

                                    com.example.collegemanagement.Models.Feature(
                                        title = "Banner",
                                        R.drawable.web,
                                        BlueViolet1,
                                        BlueViolet2,
                                        BlueViolet3,
                                        Routes.ManageBanner.route
                                    ),
                                    com.example.collegemanagement.Models.Feature(
                                        title = "Notice",
                                        R.drawable.notification,
                                        LightGreen1,
                                        LightGreen2,
                                        LightGreen3,
                                        Routes.ManageNotice.route
                                    ),
                                    com.example.collegemanagement.Models.Feature(
                                        title = "Gallery",
                                        R.drawable.gallery,
                                        OrangeYellow1,
                                        OrangeYellow2,
                                        OrangeYellow3,
                                        Routes.ManageGallery.route
                                    ),
                                    com.example.collegemanagement.Models.Feature(
                                        title = "Faculty",
                                        R.drawable.graduate,
                                        Beige1,
                                        Beige2,
                                        Beige3,
                                        Routes.ManageFaculty.route
                                    ),
                                    com.example.collegemanagement.Models.Feature(
                                        title = "College Info",
                                        R.drawable.info,
                                        LightGreen1,
                                        LightGreen2,
                                        LightGreen3,
                                        Routes.ManageCollegeInfo.route
                                    )
                                ),
                                navController
                            )

                        }
                    }
                }
            )
        }
    )




}



@Composable
fun FeatureItem(
    feature: com.example.collegemanagement.Models.Feature,
    navController: NavController
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(7.5.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(feature.darkColor)



    ) {
        val width = constraints.maxWidth
        val height = constraints.maxHeight

        // Medium colored path
        val mediumColoredPoint1 = Offset(0f, height * 0.3f)
        val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
        val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
        val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
        val mediumColoredPoint5 = Offset(width * 1.4f, -height * 0.3f)

        val mediumColoredPath = Path().apply {
            moveTo(mediumColoredPoint1.x, mediumColoredPoint1.y)
            standardQuadFromTo(mediumColoredPoint1, mediumColoredPoint2)
            standardQuadFromTo(mediumColoredPoint2, mediumColoredPoint3)
            standardQuadFromTo(mediumColoredPoint3, mediumColoredPoint4)
            standardQuadFromTo(mediumColoredPoint4, mediumColoredPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }
        // Light colored path
        val lightPoint1 = Offset(0f, height * 0.35f)
        val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
        val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
        val lightPoint4 = Offset(width * 0.65f, height.toFloat())
        val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

        val lightColoredPath = Path().apply {
            moveTo(lightPoint1.x, lightPoint1.y)
            standardQuadFromTo(lightPoint1, lightPoint2)
            standardQuadFromTo(lightPoint2, lightPoint3)
            standardQuadFromTo(lightPoint3, lightPoint4)
            standardQuadFromTo(lightPoint4, lightPoint5)
            lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
            lineTo(-100f, height.toFloat() + 100f)
            close()
        }
        androidx.compose.foundation.Canvas(
            modifier = Modifier.fillMaxSize(),
        ) {
            drawPath(
                path = mediumColoredPath,
                color = feature.mediumColor
            )
            drawPath(
                path = lightColoredPath,
                color = feature.lightColor
            )
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Text(
                text = feature.title,
                textAlign = TextAlign.Center,
                color = TextWhite,
                style = MaterialTheme.typography.titleMedium,
                lineHeight = 26.sp,
                modifier = Modifier.align(Alignment.TopStart),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp

            )
            Icon(
                painter = painterResource(id = feature.iconId),
                contentDescription = feature.title,
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(35.dp)
            )
            Box(
                modifier = Modifier
                    .size(height = 35.dp, width = 80.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ButtonBlue)
                    .align(Alignment.BottomEnd)
                    .clickable {
                        navController.navigate(feature.route)
                    }
            ){
                Text(
                    text = "Manage",
                    color= TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(feature.route)
                        }
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(5.dp))
                        .background(ButtonBlue)

                )
            }


        }

    }


}

@Composable
fun FeatureSection(
    features: List<com.example.collegemanagement.Models.Feature>,
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(40.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight(),
            content = {
                    items(features.size) {
                        FeatureItem(
                            feature = features[it],
                            navController
                        )

                    }

            }
        )
    }

}
