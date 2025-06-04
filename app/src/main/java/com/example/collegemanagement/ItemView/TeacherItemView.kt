package com.example.collegemanagement.ItemView

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.Models.BannerModel
import com.example.collegemanagement.Models.FacultyModel
import com.example.collegemanagement.Models.NoticeModel
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.Constants.FACULTY
import com.example.collegemanagement.Utils.Constants.NOTICE
import com.example.collegemanagement.Utils.Constants.isAdmin
import com.example.collegemanagement.ui.theme.OrangeYellow1
import com.example.collegemanagement.ui.theme.OrangeYellow3


@SuppressLint("SuspiciousIndentation")
@Composable
fun TeacherItemView(
    facultyModel: FacultyModel,
    delete:(facultyModel: FacultyModel)->Unit
     ){

    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { showDialog = true }
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(Color.Transparent)
    ) {
        ConstraintLayout {
           // val (image, delete) = createRefs()

            Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement =Arrangement.Center){
                Spacer(modifier = Modifier.height(5.dp))
                Image(painter = rememberAsyncImagePainter(model = facultyModel.imageUrl),
                    contentDescription = "banner_image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .clip(CircleShape)

                )
                Spacer(modifier = Modifier.width(10.dp))
                Column() {
                    Text(
                        text = facultyModel.name!!,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = Color(0xFF96614a)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = facultyModel.position!!,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color(0xFFfaa27c).copy(alpha = 0.7f),
                        fontStyle = FontStyle.Italic
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null,
                    modifier = Modifier.clickable {
                        showDialog = true
                    })

            }
        }
    }
    if(showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                if(isAdmin)
                Button(onClick = {
                    delete(facultyModel)
                    showDialog = false
                },
                    modifier = Modifier.width(120.dp)) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    Text("Delete", modifier = Modifier.padding(start = 4.dp))
                }
            },
            text =  {
                Column ( horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 22.dp)) {
                    Text(text = facultyModel.name!!,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        fontWeight =FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black)
                    AsyncImage(
                        model = facultyModel.imageUrl,
                        contentDescription = "Full Teacher Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally),
                    )
                    Text(text = "Email : ${facultyModel.email!!} ",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        fontWeight =FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.DarkGray)
                    Text(text = "Position : ${facultyModel.position!!} ",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        fontWeight =FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.DarkGray)
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
