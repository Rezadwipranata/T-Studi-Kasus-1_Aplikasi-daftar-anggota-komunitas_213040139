package com.application.komunitas_app.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.application.komunitas_app.R
import com.application.komunitas_app.data.database.Komunitas
import com.application.komunitas_app.data.viewmodel.KomunitasViewModel
import com.application.komunitas_app.navigation.Screen
import com.application.komunitas_app.views.utils.CustomUpdateButton
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberDetailScreen(id: Int, komunitasViewModel: KomunitasViewModel, navController: NavController) {
    val context = LocalContext.current
    var memberNameState: String? by remember { mutableStateOf(null) }
    var tglJoinState: String? by remember { mutableStateOf(null) }
    var memberLvlState: String? by remember { mutableStateOf(null) }
    var periodState: String? by remember { mutableStateOf(null) }
    var isMemberLvlDropDownExpanded by remember { mutableStateOf(false) }
    val memberLevelList = listOf("Bronze ", "Silver", "Gold", "Platinum")

    LaunchedEffect(Unit) {
        komunitasViewModel.getCommunity(id)
    }
    komunitasViewModel.getCommunity(id)

    val community = komunitasViewModel.getCommunity.observeAsState().value
    community ?: return
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(
                    text = "Details Member",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 60.dp),
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                OutlinedTextField(
                    value = memberNameState
                        ?: community.name,  // display database text if no modified text available
                    onValueChange = { memberNameState = it },
                    label = { Text(stringResource(id = R.string.member_name)) },
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = tglJoinState
                        ?: community.tglJoin,
                    onValueChange = { tglJoinState = it },
                    label = { Text(stringResource(id = R.string.join_date)) },
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Box {
                    OutlinedTextField(
                        value = memberLvlState ?: community.memberLvl,
                        onValueChange = { memberLvlState = it },
                        placeholder = { androidx.compose.material.Text(text = community.memberLvl) },
                        enabled = false,
                        modifier = Modifier
                            .clickable {
                                isMemberLvlDropDownExpanded = true
                            }
                            .fillMaxWidth(0.8f)
                            .border(1.dp, Color.Gray),
                        textStyle = TextStyle(color = Color.Black),
                        trailingIcon = { Icon(imageVector = Icons.Default.ArrowCircleDown, "") }
                    )

                    DropdownMenu(
                        expanded = isMemberLvlDropDownExpanded,
                        onDismissRequest = { isMemberLvlDropDownExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    ) {
                        memberLevelList.forEachIndexed { index, selectedItem ->
                            DropdownMenuItem(onClick = {
                                memberLvlState = selectedItem
                                isMemberLvlDropDownExpanded = false
                            }) {
                                androidx.compose.material.Text(selectedItem)
                            }
                            if (index != memberLevelList.lastIndex)
                                Divider(Modifier.background(Color.Black))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                OutlinedTextField(
                    value = periodState
                        ?: community.period,
                    onValueChange = { periodState = it },
                    label = { Text(stringResource(id = R.string.period)) },
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            Column(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                CustomUpdateButton(stringResource(id = R.string.update_member))
                {
                    // Create the Komunitas object
                    val komunitas = Komunitas(
                        name = memberNameState ?: community.name,
                        tglJoin = tglJoinState ?: community.tglJoin,
                        memberLvl = memberLvlState ?: community.memberLvl,
                        period = periodState ?: community.period
                    )

                    // Update the Komunitas in the database
                    komunitasViewModel.updateCommunity(
                        id,
                        komunitas.name,
                        komunitas.tglJoin,
                        komunitas.memberLvl,
                        komunitas.period
                    )
                    Toast.makeText(context, "Member updated successfully", Toast.LENGTH_SHORT)
                        .show()

                }
                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    val openDialog = remember { mutableStateOf(false) }

                    Button(onClick = { openDialog.value = true }) {
                        Text(text = "Delete Member")
                    }

                    if (openDialog.value) {
                        AlertDialog(
                            onDismissRequest = { openDialog.value = false },
                            title = {
                                Text(text = "Deleting Member")
                            },
                            text = {
                                Text(text = "Do you really want to Delete this Member ?")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        community?.let { id ->
                                            komunitasViewModel.deleteCommunity(id)
                                        }
                                        openDialog.value = false
                                        Toast.makeText(context, "Member Deleted successfully", Toast.LENGTH_SHORT)
                                            .show()
                                        navController.navigate(Screen.AllDonatesScreen.route)
                                    },
                                ) {
                                    Text(text = "CONFIRM")

                                }
                            },
                            dismissButton = {
                                Button(onClick = { openDialog.value = false }
                                ) {
                                    Text(text = "CANCEL")
                                }
                            }
                        )
                    }
                }

            }

        }

    }
}



