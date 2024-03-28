package com.application.komunitas_app.views

import HandleBackPress
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.komunitas_app.data.database.Komunitas
import com.application.komunitas_app.data.viewmodel.KomunitasViewModel
import com.application.komunitas_app.navigation.Screen
import com.application.komunitas_app.views.utils.TitleText
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.application.komunitas_app.R
import com.application.komunitas_app.ui.theme.GreenDark


@Composable
fun AllMembersScreen(navController: NavController, komunitasViewModel: KomunitasViewModel) {
    val communities: List<Komunitas> by komunitasViewModel.allCommunities.observeAsState(initial = listOf())
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(Unit) {
        komunitasViewModel.getCommunities()
    }

    LaunchedEffect(Unit) {
        komunitasViewModel.getCommunities()
    }

    Box(
        modifier = Modifier
            .background(color = GreenDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .background(color = GreenDark)
        ) {
            if (communities.isEmpty()) {
                TitleText(text = stringResource(id = R.string.all_members_title), modifier = Modifier,)
                Column(
                    modifier = Modifier.offset(y = -(35).dp)
                ) {
                    EmptyContent()
                }
            } else {
                TitleText(text = stringResource(id = R.string.all_members_title), modifier = Modifier,)
                LazyColumn(modifier = Modifier
                    .weight(1f)
                    .background(color = GreenDark), verticalArrangement = Arrangement.spacedBy(15.dp)) {
                    items(communities) { community ->
                        OutlinedCard(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(route = Screen.DonateDetailsScreen.route + "/" + community.id)
                                },
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                // Menambahkan gambar
                                Image(
                                    painter = painterResource(id = R.drawable.member_card),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(120.dp)
                                        .padding(start = 8.dp)
                                        .offset(y = -(8).dp)
                                )

                                Divider(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(1.dp)
                                        .background(Color.Black),
                                    color = Color.Gray
                                )

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = community.name,
                                        modifier = Modifier
                                            .padding(16.dp, 8.dp, 0.dp, 0.dp),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp
                                    )
                                    Text(
                                        text = "Category - ${community.memberLvl}",
                                        modifier = Modifier
                                            .padding(16.dp, 8.dp, 0.dp, 0.dp),
                                        fontStyle = FontStyle.Italic,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Join : ${community.tglJoin}",
                                        modifier = Modifier
                                            .padding(16.dp, 8.dp, 0.dp, 0.dp),
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.End
                                    )
                                    Text(
                                        text = "${community.period}",
                                        modifier = Modifier.padding(16.dp, 8.dp, 0.dp, 0.dp),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    if (backDispatcher != null) {
        HandleBackPress(backDispatcher) {
            navController.navigate(Screen.HomeScreen.route)
        }
    }
}

@Composable
fun EmptyContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = GreenDark),
    ) {
        Icon(
            modifier = Modifier.size(120.dp),
            painter = painterResource(
                R.drawable.person
            ), contentDescription = stringResource(
                R.string.no_member
            ),
            tint = Color.LightGray
        )
        androidx.compose.material.Text(
            text = stringResource(
                R.string.text_empty_content
            ),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize,
            color = Color.LightGray
        )
    }
}
