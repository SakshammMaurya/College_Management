package com.example.collegemanagement.ItemView

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.Models.BannerModel
import com.example.collegemanagement.Models.NoticeModel
import com.example.collegemanagement.R
import com.example.collegemanagement.Utils.Constants.isAdmin


@SuppressLint("SuspiciousIndentation")
@Composable
fun NoticeItemView(
    noticeModel: NoticeModel,
    delete:(noticeModel: NoticeModel)->Unit
     ){

    OutlinedCard(
        modifier = Modifier.padding(4.dp)
    ) {
        ConstraintLayout {
            val (image, delete) = createRefs()

            Column {
                Text(
                    text = noticeModel.title!!,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                if (noticeModel.link != "")
                    Text(
                        text = noticeModel.link!!,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color.Blue
                    )
                Image(
                    painter = rememberAsyncImagePainter(model = noticeModel.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            if (isAdmin){
                Card(
                    modifier = Modifier
                        .constrainAs(delete) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .padding(4.dp)
                        .clickable {
                            delete(noticeModel)
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = null,
                        modifier = Modifier.padding(4.dp)
                    )
                }
        }


            
        }
    }

}

//@Preview(showSystemUi = true)
//@Composable
//fun BannerView(){
////    BannerItemView(bannerModel = BannerModel())
//}