package com.example.collegemanagement.ItemView

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontFamily
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
import com.example.collegemanagement.Models.NoticeModel
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.Constants.isAdmin
import com.example.collegemanagement.ui.theme.LightGreen
import com.example.collegemanagement.ui.theme.LightGreen1
import com.example.collegemanagement.ui.theme.LightGreen2
import com.example.collegemanagement.ui.theme.LightGreen3
import com.example.collegemanagement.ui.theme.Purple40
import com.example.collegemanagement.ui.theme.Purple80
import java.text.SimpleDateFormat
import java.util.Locale


@SuppressLint("SuspiciousIndentation")
@Composable
fun NoticeItemView(
    noticeModel: NoticeModel,
    delete: (NoticeModel) -> Unit
) {
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    val formatter = remember {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(LightGreen)
            .clickable { showDialog = true }
    ) {
        // Stack items using Box scope
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = noticeModel.title ?: "",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(8.dp),
                color = Color(0xFF02150f)
            )

            Box(modifier = Modifier.height(180.dp)) {
                // Background image
                AsyncImage(
                    model = noticeModel.imageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentDescription = "Notice Image",
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
//                                    Color(0xFF032b1f).copy(alpha = 0.6f),
                                    Color(0xFF02150f).copy(alpha = 0.8f)
                                )
                            )
                        )
                )
                Text(
                    text = "Added: ${
                        noticeModel.timestamp?.toDate()?.let { formatter.format(it) } ?: "Unknown"
                    }",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic ,
                    modifier = Modifier.padding(8.dp).align(Alignment.BottomEnd)
                )



            }
        }
    }



    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = noticeModel.title!!,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(8.dp)
                    )

                    AsyncImage(
                        model = noticeModel.imageUrl,
                        contentDescription = "Full Notice Image",
                        contentScale = ContentScale.Fit,
                                modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(8.dp)),
                    )

                    if (noticeModel.link!!.isNotEmpty()) {
                        Text(
                            text = "Visit notice",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Purple40,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .clickable {
                                    val intent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse(noticeModel.link))
                                    context.startActivity(intent)
                                }
                        )
                    }
                }
            },

            confirmButton = {
                if(isAdmin)
                Button(onClick = {
                    delete(noticeModel)
                    showDialog = false
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    Text("Delete", modifier = Modifier.padding(start = 4.dp))
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "${noticeModel.title}\n${noticeModel.link}")
                        putExtra(Intent.EXTRA_STREAM, Uri.parse(noticeModel.imageUrl))
                        type = "image/*"
                        }

                    val shareIntent = Intent.createChooser(sendIntent, "Share Notice")
                    context.startActivity(shareIntent)
                }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                    Text("Share", modifier = Modifier.padding(start = 4.dp))
                }
            }
        )
    }
}


