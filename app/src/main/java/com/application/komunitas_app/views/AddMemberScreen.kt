package com.application.komunitas_app.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleDown
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.application.komunitas_app.R
import com.application.komunitas_app.data.database.Komunitas
import com.application.komunitas_app.data.viewmodel.KomunitasViewModel
import com.application.komunitas_app.ui.theme.GreenDark
import com.application.komunitas_app.views.utils.CustomButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemberScreen(komunitasViewModel: KomunitasViewModel) {
    val context = LocalContext.current
    var memberName by rememberSaveable { mutableStateOf("") }
    var tglJoin by rememberSaveable { mutableStateOf("") }
    var memberLvl by rememberSaveable { mutableStateOf("") }
    var period by rememberSaveable { mutableStateOf("") }
    var isMemberLvlDropDownExpanded by remember { mutableStateOf(false) }
    val memberLevelList = listOf("Bronze ", "Silver", "Gold", "Platinum")

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Add New Member",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = GreenDark,
                modifier = Modifier
                    .padding(top = 70.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = memberName,
                label = { Text(stringResource(id = R.string.member_name)) },
                onValueChange = {
                    memberName = it
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = tglJoin,
                label = { Text(stringResource(id = R.string.join_date)) },
                onValueChange = {
                    tglJoin = it
                },
            )
            Spacer(modifier = Modifier.height(13.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box {
                    OutlinedTextField(
                        value = memberLvl,
                        onValueChange = { memberLvl = it },
                        placeholder = { androidx.compose.material.Text(text = "Choose The Member Level") },
                        enabled = false,
                        modifier = Modifier
                            .clickable {
                                isMemberLvlDropDownExpanded = true
                            }
                            .fillMaxWidth(0.8f)
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(3.dp)),
                        textStyle = TextStyle(color = Color.Black),
                        trailingIcon = { Icon(imageVector = Icons.Default.ArrowCircleDown, "") },
                    )

                    DropdownMenu(
                        expanded = isMemberLvlDropDownExpanded,
                        onDismissRequest = { isMemberLvlDropDownExpanded = false },
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                    ) {
                        memberLevelList.forEachIndexed { index, selectedItem ->
                            DropdownMenuItem(onClick = {
                                memberLvl = selectedItem
                                isMemberLvlDropDownExpanded = false
                            },
                                ) {
                                androidx.compose.material.Text(selectedItem)
                            }
                            if (index != memberLevelList.lastIndex)
                                Divider(Modifier.background(Color.Black))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = period,
                label = { Text(stringResource(id = R.string.period)) },
                onValueChange = {
                    period = it
                },
            )
            Spacer(modifier = Modifier.height(30.dp))
            CustomButton(stringResource(id = R.string.add_member)) {
                // Create the Member object
                if (memberName == "" || tglJoin == "" || memberLvl == "" || period == "") {
                    Toast.makeText(context, "Member Added Failed", Toast.LENGTH_SHORT).show()
                    Log.d("data db", "Data Gagal")
                } else {
                    val komunitas = Komunitas(memberName, tglJoin, memberLvl, period)
                    Log.d("data db", "Data Berhasil $komunitas")

                    // Update the Member to the database
                    komunitasViewModel.addCommunity(komunitas)
                    // Clear text fields
                    memberName = ""
                    tglJoin = ""
                    memberLvl = ""
                    period = ""
                    Toast.makeText(context, "Member added successfully", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }
}
