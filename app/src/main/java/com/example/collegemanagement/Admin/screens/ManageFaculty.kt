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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
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
import com.example.collegemanagement.ui.theme.LightOrange
import com.example.collegemanagement.ui.theme.Purple80



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageFaculty(navController: NavController) {

    val context = LocalContext.current
    val facultyViewModel: FacultyViewModel = viewModel()

    val isUploaded by facultyViewModel.isPosted.observeAsState(false)
    val isDeleted by facultyViewModel.isDeleted.observeAsState(false)
    val categoryList by facultyViewModel.categoryList.observeAsState(null)

    val option = remember { mutableStateListOf<String>() }
    facultyViewModel.getCategory()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var isCategory by remember { mutableStateOf(false) }
    var isTeacher by remember { mutableStateOf(false) }
    var mExpanded by remember { mutableStateOf(false) }

    var showDeleteDialog by remember { mutableStateOf(false) }


    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    val teacherCountMap by facultyViewModel.categoryTeacherCount.observeAsState(mapOf())

    LaunchedEffect(Unit) {
        facultyViewModel.loadCategoryTeacherCounts()
    }


    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    LaunchedEffect(isUploaded) {
        if (isUploaded) {
            //Toast.makeText(context, "Data Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
            isCategory = false
            isTeacher = false
            category = ""
            name = ""
            email = ""
            position = ""
            showDialog = false
        }
    }

    LaunchedEffect(isDeleted) {
        if (isDeleted) {
            Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Manage Faculty",
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null, modifier = Modifier.size(30.dp))
                    }
                }
            )
        }
        ,
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }, containerColor = LightOrange) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Spacer(modifier = Modifier.height(28.dp))
            if (categoryList.isNullOrEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Add category", fontSize = 20.sp, color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(8.dp)) {
                    items(categoryList!!) { catName ->
                        val teacherCount = teacherCountMap[catName] ?: 0

                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .height(100.dp)
                                .fillMaxWidth()
                                .clickable {
                                    val route = Routes.FacultyDetailScreen.route.replace(
                                        "{catName}",
                                        catName
                                    )
                                    navController.navigate(route)
                                },
                            colors = CardDefaults.cardColors(containerColor = LightOrange),
                            elevation = CardDefaults.elevatedCardElevation(6.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(top=4.dp, start =  6.dp, end = 6.dp, bottom = 2.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(catName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color(0xFF644132)
                                )
                                Spacer(modifier = Modifier.height(30.dp))
                                Row {
                                    Spacer(modifier = Modifier.width(85.dp))
                                    Text(
                                        "$teacherCount faculty",
                                        fontSize = 14.sp,
                                        color = Color(0xFF644132),
                                        fontStyle = FontStyle.Italic,
                                        modifier = Modifier.alpha(0.6f)

                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Select Action") },
            confirmButton = {
                Column {
                    Button(onClick = {
                        isCategory = true
                        isTeacher = false
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Add Category")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        isTeacher = true
                        isCategory = false
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text("Add Teacher")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(onClick = { showDialog = false }, modifier = Modifier.fillMaxWidth()) {
                        Text("Cancel")
                    }
                }
            },
            dismissButton = {}
        )
    }

    if (isCategory) {
        AddCategoryDialog(
            category = category,
            onCategoryChange = { category = it },
            onAddClick = {
                if (category.isNotBlank()) facultyViewModel.uploadCategory(category)
                else Toast.makeText(context, "Please provide category", Toast.LENGTH_SHORT).show()
            },
            onDismiss = { isCategory = false; showDialog = false }
        )
    }

    if (isTeacher) {
        AddTeacherDialog(
            imageUri = imageUri,
            name = name,
            email = email,
            position = position,
            category = category,
            categoryList = categoryList ?: emptyList(),
            mExpanded = mExpanded,
            onImageSelect = { launcher.launch("image/*") },
            onNameChange = { name = it },
            onEmailChange = { email = it },
            onPositionChange = { position = it },
            onCategoryChange = { mExpanded = !mExpanded },
            onCategorySelect = {
                category = it
                mExpanded = false
            },
            onAddClick = {
                if (imageUri == null || name.isBlank() || email.isBlank() || position.isBlank() || category.isBlank()) {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                } else {
                    facultyViewModel.saveFaculty(imageUri!!, name, email, position, category)
                }
            },
            onDismiss = {
                isTeacher = false
                showDialog = false
            }
        )
    }
}
@Composable
fun AddCategoryDialog(
    category: String,
    onCategoryChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onAddClick) {
                Text("Add Category")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Add Department") },
        text = {
            OutlinedTextField(
                value = category,
                onValueChange = onCategoryChange,
                placeholder = { Text("Category name...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTeacherDialog(
    imageUri: Uri?,
    name: String,
    email: String,
    position: String,
    category: String,
    categoryList: List<String>,
    mExpanded: Boolean,
    onImageSelect: () -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPositionChange: (String) -> Unit,
    onCategoryChange: () -> Unit,
    onCategorySelect: (String) -> Unit,
    onAddClick: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onAddClick) {
                Text("Add Teacher")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Add Teacher") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                Image(
                    painter = if (imageUri == null) painterResource(id = R.drawable.image)
                    else rememberAsyncImagePainter(model = imageUri),
                    contentDescription = "Faculty Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable { onImageSelect() }
                        .align(Alignment.CenterHorizontally)
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    placeholder = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    placeholder = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = position,
                    onValueChange = onPositionChange,
                    placeholder = { Text("Position") },
                    modifier = Modifier.fillMaxWidth()
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Select Department") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = mExpanded)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    DropdownMenu(
                        expanded = mExpanded,
                        onDismissRequest = onCategoryChange
                    ) {
                        categoryList.forEach { dept ->
                            DropdownMenuItem(
                                text = { Text(dept) },
                                onClick = { onCategorySelect(dept) }
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { onCategoryChange() }
                    )
                }
            }
        }
    )
}


