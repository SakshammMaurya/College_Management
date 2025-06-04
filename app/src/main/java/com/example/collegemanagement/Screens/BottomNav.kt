package com.example.collegemanagement.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.collegemanagement.Models.BottomNavItem
import com.example.collegemanagement.Models.NavItems
import com.example.collegemanagement.Navigation.Routes
import com.example.collegemanagement.R
import com.example.collegemanagement.Viewmodel.AuthViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(navController: NavController) {

 val navController1 = rememberNavController()
 val context = LocalContext.current

 val scope = rememberCoroutineScope()
 val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
 val selectedItemIndex by rememberSaveable {
         mutableIntStateOf(0)
 }
 var showProfileDialog by remember {
     mutableStateOf(false)
 }
val authViewModel = AuthViewModel()

    var name by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }

    // for cloud messaging (push notifications)
    FirebaseMessaging.getInstance().subscribeToTopic("notices")
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("FCM", "Subscribed to notices topic")
            }
        }


    LaunchedEffect(Unit) {
        authViewModel.fetchUserProfile(
            onResult = { fetchedName, fetchedNumber ->
                name = fetchedName
                number = fetchedNumber
            },
            onError = { error -> Log.e("Profile", error) }
        )
    }

 val list = listOf(
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
    val list2 = listOf(

        BottomNavItem(
            "Home",
            R.drawable.home,
            Routes.Home.route
        ) ,
        BottomNavItem(
            "Faculty",
            R.drawable.graduate,
            Routes.Faculty.route
        ),
        BottomNavItem(
            "Gallery",
            R.drawable.gallery,
            Routes.Gallery.route
        ),
        BottomNavItem(
            "About us",
            R.drawable.info,
            Routes.AboutUs.route
        )
    )

 ModalNavigationDrawer(
  drawerState = drawerState,
  drawerContent = {
      Column {
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
                  list.forEachIndexed { index, items ->
                      NavigationDrawerItem(label = {
                          Text(text = items.title)
                      }, selected = index == selectedItemIndex, onClick = {
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
                  } }

          }
      }
 },
   content = {
      Scaffold(
       topBar = {
        TopAppBar(title = { Text(text = "National Institute of Technology Jamshedpur", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)},
         navigationIcon = {
          IconButton(onClick = { scope.launch { drawerState.open() } }) {
              Icon(painter = painterResource(id = R.drawable.menu), contentDescription =null,
               modifier = Modifier.size(24.dp))
          }
         },
            actions = {
                IconButton(onClick = { showProfileDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 4.dp)
                    )
                }
            })

       },
          bottomBar = {
              MyBottomNav(navController = navController1)
          }
      ){ padding->


          NavHost(navController = navController1,
              startDestination = Routes.Home.route,
              modifier = Modifier.padding(padding)){
                 composable(Routes.Home.route){
                  Home(navController )
              }
              composable(Routes.Faculty.route) {
                  Faculty(navController)
              }
              composable(Routes.Gallery.route) {
                  Gallery(navController)
              }
              composable(Routes.AboutUs.route) {
                  AboutUs(navController)
              }
          }
         
      }
       if (showProfileDialog) {
           AlertDialog(
               onDismissRequest = { showProfileDialog = false },
               title = { Text("Profile", textAlign = TextAlign.Center, fontSize = 22.sp, fontWeight = FontWeight.Medium) },
               text = {
                   Column(
                       horizontalAlignment = Alignment.CenterHorizontally,
                       verticalArrangement = Arrangement.Center
                   ) {
                       Text(
                           text = "Name : ${name}",
                           fontSize = 18.sp,
                           fontWeight = FontWeight.Bold
                           )
                       Spacer(modifier = Modifier.height(8.dp))
                       Text(
                           text = "Number : ${number}",
                           fontSize = 18.sp,
                           fontWeight = FontWeight.Bold
                           )
                       OutlinedButton(
                           onClick = {
                               showProfileDialog = false
                               authViewModel.logout {
                                   navController.navigate(Routes.Login.route) {
                                       popUpTo(0)
                                   }
                               }
                           }) {
                           Text(
                               "Logout",
                               textAlign = TextAlign.Center,
                               color = Color.Red,
                               fontSize = 16.sp,
                               //fontWeight = FontWeight.Normal
                           )
                       }

                       OutlinedButton(onClick = {
                           showProfileDialog = false
                           navController.navigate(Routes.ChangePassword.route)

                       }) {
                           Text("Change Password")
                       }
                   }
               },
               confirmButton = {},
               dismissButton = {
                   TextButton(onClick = { showProfileDialog = false }) {
                       Text("Close")
                   }
               }
           )
       }
   }

 )

}

@Composable
fun MyBottomNav(navController: NavController) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    val list = listOf(

     BottomNavItem(
      "Home",
      R.drawable.home,
      Routes.Home.route
     ) ,
     BottomNavItem(
             "Faculty",
     R.drawable.graduate,
     Routes.Faculty.route
    ),
     BottomNavItem(
      "Gallery",
      R.drawable.gallery,
      Routes.Gallery.route
     ),
     BottomNavItem(
  "About us",
  R.drawable.info,
  Routes.AboutUs.route
     )
    )

    BottomAppBar {
        list.forEach{

            val curRoute = it.route
            val otherRoute= try {
                backStackEntry.value!!.destination.route
            } catch(e: Exception){
                Routes.Home.route
            }
            val selected = curRoute == otherRoute

            NavigationBarItem(
                selected = selected,
                onClick = {navController.navigate(it.route){
                    popUpTo(navController.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                } },
                icon = {
                    Icon(painter = painterResource(id = it.icon), contentDescription = null
                         ,
                        modifier = Modifier.size(24.dp))
                }
            )
        }
    }



}
