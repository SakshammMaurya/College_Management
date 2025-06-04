package com.example.collegemanagement.ItemView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.Models.BannerModel
import com.example.collegemanagement.Models.GalleryModel
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.Constants
import com.example.collegemanagement.Utils.Constants.isAdmin
import com.example.collegemanagement.Viewmodel.GalleryViewModel


@Composable
fun ImageItemView(
     imageUrl:String,
     galleryModel: GalleryModel,
     cat:String,
     delete:(cat:String, image:String )->Unit
     ){
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {

        Image(
            painter = rememberAsyncImagePainter(model = imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    showDialog = true
                }
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFF48380c).copy(alpha = 0.15f),
                            Color(0xFF181304).copy(alpha = 0.35f),
                            Color(0xFF181304).copy(alpha = 0.55f),
                            Color.Black.copy(alpha = 0.75f)
                        ),
                        startY = 16f
                    )
                )

        )

//        Column(
//            modifier = Modifier
//                .align(Alignment.BottomStart)
//                .padding(12.dp)
//        ) {
//            Text(
//                text = "Carnival Festival",
//                color = Color.White,
//                fontWeight = FontWeight.Bold,
//                fontSize = 16.sp
//            )
//            Text(
//                text = "20 Feb 2022",
//                color = Color.White.copy(alpha = 0.85f),
//                fontSize = 12.sp
//            )
//        }
    }


    if(showDialog){
        AlertDialog(
            onDismissRequest = {
            showDialog = false
        },
            confirmButton = {
                if(isAdmin)
                    Button(onClick = {
                        delete(cat,imageUrl)
                        showDialog = false
                    },
                        modifier = Modifier.width(120.dp)) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                        Text("Delete", modifier = Modifier.padding(start = 4.dp))
                    }
            },
            text  ={
                Column ( horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 22.dp)) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Full Gallery Image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                    )
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog = false}, modifier = Modifier.width(120.dp)) {
                    Text("Close")

                }
            }
        )
    }

}

