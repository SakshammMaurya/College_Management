package com.example.collegemanagement.Admin.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.ItemView.FacultyItemView
import com.example.collegemanagement.ItemView.NoticeItemView
import com.example.collegemanagement.Navigation.Routes
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.Constants.NOTICE
import com.example.collegemanagement.Viewmodel.FacultyViewModel
import com.example.collegemanagement.Viewmodel.NoticeViewModel
import com.example.collegemanagement.ui.theme.Purple80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageFaculty(navController: NavController) {

    val context = LocalContext.current
    val facultyViewModel : FacultyViewModel = viewModel()

    val isUploaded by facultyViewModel.isPosted.observeAsState(false)
    val isDeleted by facultyViewModel.isDeleted.observeAsState(false)
    val categoryList by facultyViewModel.categoryList.observeAsState(null)

    val option = arrayListOf<String>()
    facultyViewModel.getCategory()

    var imageUri by remember{
        mutableStateOf<Uri?>(  null)
    }
    var isCategory by remember{
        mutableStateOf(  false)
    }
    var mExpanded by remember{
        mutableStateOf(  false)
    }
    var isTeacher by remember{
        mutableStateOf(  false)
    }

    var name by remember{
        mutableStateOf(  "")
    }

    var email by remember{
        mutableStateOf(  "")
    }
    var position by remember{
        mutableStateOf(  "")
    }
    var category by remember{
        mutableStateOf(  "")
    }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    LaunchedEffect(isUploaded) {
        if(isUploaded){
            Toast.makeText(context, "Data Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
            isCategory=false
            isTeacher=false
            category=""
            name=""
            email=""
            position=""
        }
    }
    LaunchedEffect(isDeleted) {
        if(isDeleted){
            Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text(text = "Manage Faculty",
                    fontWeight = FontWeight.Bold)
            },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple80),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp()}) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft , contentDescription =null,
                            modifier = Modifier.size(30.dp))
                    }
                },

                )
        },
    ){ padding->

        Column(modifier = Modifier.padding(padding)){

           Row(modifier=Modifier.padding(8.dp)){

               Card(modifier = Modifier
                   .weight(1f)
                   .padding(4.dp)
                   .clickable {
                       isCategory = true
                       isTeacher = false
                   }){
                   Text(text = "Add Category",
                       fontWeight =  FontWeight.Bold,
                       fontSize = 18.sp,
                       modifier = Modifier
                           .padding(8.dp)
                           .fillMaxWidth(),
                       textAlign = TextAlign.Center)
               }
               Card(modifier = Modifier
                   .weight(1f)
                   .padding(4.dp)
                   .clickable {
                       isTeacher = true
                       isCategory = false
                   }){
                   Text(text = "Add Teacher",
                       fontWeight =  FontWeight.Bold,
                       fontSize = 18.sp,
                       modifier = Modifier
                           .padding(8.dp)
                           .fillMaxWidth(),
                       textAlign = TextAlign.Center)
               }
           }
            if(isCategory)
                ElevatedCard(modifier = Modifier.padding(8.dp)) {
                    OutlinedTextField(value = category, onValueChange = {
                        category = it
                    },
                        placeholder = { Text(text = "Category...")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp))


                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Button(onClick = {
                            if(category==""){
                                Toast.makeText(
                                    context,
                                    "Please provide category",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }else{
                                facultyViewModel.uploadCategory(category)
                            }



                        },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)) {
                            Text(text = "Add Category")
                        }
                        OutlinedButton(onClick = {
                            imageUri=null
                            isCategory = false
                            isTeacher = false
                                                 },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)) {
                            Text(text = "Cancel")
                        }
                    }
                }
            if(isTeacher)
                ElevatedCard(modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                ) {

                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {


                        Spacer(modifier = Modifier.height(5.dp))
                        Image(painter = if (imageUri == null) painterResource(id = R.drawable.image)
                        else rememberAsyncImagePainter(model = imageUri),
                            contentDescription = NOTICE,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(120.dp)
                                .width(120.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterHorizontally)
                                .clickable {
                                    launcher.launch("image/*")
                                }


                        )

                        OutlinedTextField(
                            value = name, onValueChange = {
                                name = it
                            },
                            placeholder = { Text(text = "Name...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )

                        OutlinedTextField(
                            value = email, onValueChange = {
                                email = it
                            },
                            placeholder = { Text(text = "Email...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )
                        OutlinedTextField(
                            value = position, onValueChange = {
                                position = it
                            },
                            placeholder = { Text(text = "Position...") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )
                        Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {


                            OutlinedTextField(
                                value = category, onValueChange = {
                                    category = it
                                },
                                readOnly = true,
                                placeholder = { Text(text = "Select Department...") },
                                label = { Text(text = "Department Name") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = mExpanded)
                                }
                            )

                            DropdownMenu(
                                expanded = mExpanded,
                                onDismissRequest = { mExpanded = false }) {

                                if (categoryList != null && categoryList!!.isNotEmpty()) {
                                    option.clear()
                                    for (data in categoryList!!) {
                                        option.add(data)
                                    }
                                }

                                option.forEach { selectedOption ->
                                    DropdownMenuItem(text = { Text(text = selectedOption) },
                                        onClick = {
                                            category = selectedOption
                                            mExpanded = false

                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                }
                            }
                            Spacer(modifier = Modifier
                                .matchParentSize()
                                .padding(10.dp)
                                .clickable {
                                    mExpanded = !mExpanded
                                })
                        }







                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    if (imageUri == null) {
                                        Toast.makeText(
                                            context,
                                            "Please select image",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (name == "" || email == "" || position == "" || category=="") {
                                        Toast.makeText(
                                            context,
                                            "Please provide all fields",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        facultyViewModel.saveFaculty(
                                            imageUri!!,
                                            name,
                                            email,
                                            position,
                                            category
                                        )
                                    }


                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(4.dp)
                            ) {
                                Text(text = "Add Teacher")
                            }
                            OutlinedButton(
                                onClick = {
                                    imageUri = null
                                    isCategory = false
                                    isTeacher = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(4.dp)
                            ) {
                                Text(text = "Cancel")
                            }
                        }
                    }

                }

            LazyColumn (){
                items(categoryList?: emptyList()){
                    FacultyItemView(
                        catName = it,
                        delete = { docId->
                            facultyViewModel.deleteCategory(docId)

                        }, onClick = {categoryName->
                            val routes = Routes.FacultyDetailScreen.route.replace("{catName}",categoryName)

                            navController.navigate(routes)
                        }
                    )
                }
            }

        }

    }

}