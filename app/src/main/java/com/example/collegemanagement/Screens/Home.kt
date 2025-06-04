package com.example.collegemanagement.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.example.collegemanagement.ItemView.NoticeItemView
import com.example.collegemanagement.Viewmodel.BannerViewModel
import com.example.collegemanagement.Viewmodel.CollegeInfoViewModel
import com.example.collegemanagement.Viewmodel.NoticeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Home(navController: NavController) {

    val bannerViewModel : BannerViewModel = viewModel()
    bannerViewModel.getBanner()
    val bannerList by bannerViewModel.bannerList.observeAsState(null)


    val collegeInfoViewModel : CollegeInfoViewModel = viewModel()
    val collegeInfo by collegeInfoViewModel.collegeInfo.observeAsState(null)
    collegeInfoViewModel.getCollegeInfo()

    val noticeViewModel : NoticeViewModel = viewModel()
    val noticeList by noticeViewModel.noticeList.observeAsState(null)
    noticeViewModel.getNotice()

    val pagerState = rememberPagerState(initialPage = 0)
    val imageSlider = ArrayList<AsyncImagePainter>()

    if(bannerList!=null) {
        bannerList!!.forEach {
            imageSlider.add(rememberAsyncImagePainter(model = it.imageUrl))
        }
    }

    LaunchedEffect(key1 = Unit) {
        try {
            while(true){
                yield()
                delay(2600)
                pagerState.animateScrollToPage(page = (pagerState.currentPage+1) % pagerState.pageCount)
            }
        } catch (e: Exception) {
            
        }
    }

    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        item {
            Spacer(modifier = Modifier.height(12.dp))
            HorizontalPager(
                count = imageSlider.size,
                state = pagerState
            ) { pager ->

                Card(modifier = Modifier.height(250.dp)) {
                    Image(
                        painter = imageSlider[pager], contentDescription = "Banner",
                        Modifier
                            .height(250.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                }

            }
            Spacer(modifier = Modifier.height(6.dp))

        }

        item{

            Row (horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()){
                    repeat(imageSlider.size){ index->
                        val isSelected = pagerState.currentPage== index
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(if (isSelected) 8.dp else 6.dp)
                                .background(
                                    if (isSelected) Color.DarkGray else Color.LightGray,
                                    shape = CircleShape
                                )
                        ){

                        }

                    }

                }
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Notice",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textDecoration = TextDecoration.Underline

            )
                Spacer(modifier = Modifier.height(8.dp))

        }
//        item{
//            if(collegeInfo!=null){
//                Text(text = collegeInfo!!.name!!,
//                    color = Color.Black,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = collegeInfo!!.desc!!,
//                    color = Color.Black,
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 16.sp
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = collegeInfo!!.address!!,
//                    color = Color.Black,
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 16.sp
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = collegeInfo!!.websiteLink!!,
//                    color = Color.Blue,
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 16.sp
//                )
//
//                Spacer(modifier = Modifier.height(12.dp))
//                Text(text = "Notice",
//                    color = Color.Black,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//
//            }
//        }
        items(noticeList?: emptyList()){
            NoticeItemView(
                noticeModel = it,
                delete = { notice->
                    noticeViewModel.deleteNotice(notice)
                }
            )
        }
    }




}