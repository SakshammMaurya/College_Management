@file:Suppress("NAME_SHADOWING")

package com.example.collegemanagement.ItemView

import android.app.AlertDialog
import android.hardware.lights.Light
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.Models.BannerModel
import com.example.collegemanagement.Models.GalleryModel
import com.example.collegemanagement.Models.NoticeModel
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.Constants.isAdmin
import com.example.collegemanagement.ui.theme.BrightYellow
import com.example.collegemanagement.ui.theme.LightYellow
import com.example.collegemanagement.ui.theme.OrangeYellow1
import com.example.collegemanagement.ui.theme.OrangeYellow2
import com.example.collegemanagement.ui.theme.OrangeYellow3


@Composable
fun GalleryItemView(
    galleryModel: GalleryModel,
    delete:(galleryModel: GalleryModel)->Unit,
    deleteImage: (cat:String,image:String)->Unit
){

    var showDialog by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(LightYellow),

        ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()){
            val (category, delete) = createRefs()

            Text(text = galleryModel.category!!,
                textAlign = if(isAdmin) TextAlign.Left else TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = if(isAdmin) 20.dp else 10.dp, vertical = 8.dp, )
                    .fillMaxWidth(),
                fontWeight =FontWeight.ExtraBold,
                fontSize = 20.sp,
                color = Color(0xFF604B10),
                fontStyle = FontStyle.Italic
                )

            if(isAdmin)
            Card(
                modifier = Modifier
                    .constrainAs(delete) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(4.dp)
                    .clickable {
                        showDialog = true

                    },
                colors = CardDefaults.cardColors(Color.Transparent)
            ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Category",
                        tint = Color(0xFF302608).copy(alpha = 0.7f)

                    )


            }


            
        }

        LazyRow(){

            items(galleryModel.images?: emptyList()){
               ImageItemView(imageUrl= it,cat=galleryModel.category!!, galleryModel = galleryModel, delete = {cat:String,imageUrl->
                   deleteImage(cat,imageUrl)
               })
            }
        }

        if(showDialog){
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                confirmButton = {
                    if(isAdmin)
                        Button(onClick = {
                            delete(galleryModel)
                            showDialog = false
                        },
                            modifier = Modifier.width(120.dp)) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                            Text("Delete", modifier = Modifier.padding(start = 4.dp))
                        }
                },
                text  = {
                    Text(text = " Doy you want to delete ' ${galleryModel.category} ' with all its images ?",
                         textAlign = TextAlign.Center)
                },
                dismissButton = {
                    OutlinedButton(onClick = { showDialog = false}, modifier = Modifier.width(120.dp)) {
                        Text("Close")

                    }
                }
            )
        }
    }

}
