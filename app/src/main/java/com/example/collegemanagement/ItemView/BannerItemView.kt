package com.example.collegemanagement.ItemView

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.Models.BannerModel
import com.example.collegemanagement.R
import java.util.Date


@Composable
fun BannerItemView(
    bannerModel: BannerModel,
    delete: (docId: BannerModel) -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        ConstraintLayout {
            val (image, deleteIcon, expiryText) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(model = bannerModel.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                    },
                contentScale = ContentScale.Crop
            )

            // Delete icon
            Card(
                modifier = Modifier
                    .constrainAs(deleteIcon) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(4.dp)
                    .clickable {
                        delete(bannerModel)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp)
                )
            }

            // Expiry Text
            val expiryLabel = when {
                bannerModel.isAutoExpire == true && bannerModel.expiryTime != null -> {
                    try {
                        val formatter = java.text.SimpleDateFormat("dd MMM yyyy • hh:mm a", java.util.Locale.getDefault())
                        "Expires on: ${formatter.format(bannerModel.expiryTime!!.toDate())}"

                    } catch (e: Exception) {
                        Log.e("BannerItemView", "Date formatting error: ${e.localizedMessage}")
                        "Expires on: Unknown"
                    }
                }

                !bannerModel.isAutoExpire!! -> "Expires: Never"
                else -> ""
            }

            Text(
                text = expiryLabel,
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(expiryText) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                    }
            )
        }
    }
}


