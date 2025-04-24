package com.example.collegemanagement.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.Viewmodel.CollegeInfoViewModel

@Composable
fun AboutUs(navController: NavController) {

    Column(
        //horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(8.dp)
    ){

        val collegeInfoViewModel : CollegeInfoViewModel = viewModel()
        val collegeInfo by collegeInfoViewModel.collegeInfo.observeAsState(null)
        collegeInfoViewModel.getCollegeInfo()

        if (collegeInfo != null) {
            
            Image(painter = rememberAsyncImagePainter(model = collegeInfo!!.imageUrl), contentDescription = "College",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = collegeInfo!!.name!!,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = collegeInfo!!.desc!!,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = collegeInfo!!.address!!,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = collegeInfo!!.websiteLink!!,
                color = Color.Blue,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
            


        }
    }
}