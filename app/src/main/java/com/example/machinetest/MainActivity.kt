package com.example.machinetest

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.machinetest.models.Data
import com.example.machinetest.models.DataX
import com.example.machinetest.ui.theme.MachineTestTheme
import com.google.accompanist.flowlayout.FlowColumn
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MachineTestTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF3FBFF)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    ScreenMainContent(viewModel)
                }
            }
        }
    }
}

@Composable
private fun ScreenMainContent(viewModel: MainViewModel) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth(.9f)
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Personal Details",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        PersonalDetailsInputSection(viewModel)

        Spacer(modifier = Modifier.height(12.dp))

        PersonalDetailsCommunicationSection(viewModel)

        Spacer(modifier = Modifier.height(12.dp))

        PersonalDetailsAddressSection(viewModel)

        Spacer(modifier = Modifier.height(12.dp))

        PersonalDetailsCountrySection(viewModel)

        Spacer(modifier = Modifier.height(12.dp))

        PersonalDetailsPincodeSection(viewModel)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Make a Reservation",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        CheckInCalenderSection(viewModel)

        Spacer(modifier = Modifier.height(12.dp))

        CheckOutCalenderSection(viewModel)

        Spacer(modifier = Modifier.height(12.dp))

        AdultSection(viewModel)

        Spacer(modifier = Modifier.height(12.dp))

        KidsCount(viewModel)

        Spacer(modifier = Modifier.height(12.dp))

        RoomPreference(viewModel)

        Spacer(modifier = Modifier.height(12.dp))

        SpecialRequestSection(viewModel)

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { viewModel.submitAll() }, enabled = viewModel.enableSubmit) {
            Text(
                text = "Submit",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = viewModel.submittionStatus)

        Spacer(modifier = Modifier.height(12.dp))

        if (viewModel.bookingID.isNotEmpty()) {
            Text(text = "Booking id: ID${viewModel.bookingID}")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Divider(modifier = Modifier.fillMaxWidth(), color = Color.LightGray)

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { viewModel.onShowAllDataClicked() }, enabled = viewModel.showData) {
            Text(
                text = "Show all Data", fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        val datas = viewModel.allData.collectAsState()

        FlowColumn(modifier = Modifier.fillMaxWidth()) {
            datas.value.forEach {
                DataItem(it)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        SearchDataContentSection(viewModel)

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
private fun SearchDataContentSection(viewModel: MainViewModel) {
    val datas = viewModel.listOfDataX.collectAsState()

    OutlinedTextField(
        value = viewModel.query, onValueChange = { viewModel.onUpdateQuery(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.Black
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Type 1 or 2 or 3", color = Color.LightGray) }
    )

    Spacer(modifier = Modifier.height(12.dp))

    FlowColumn(modifier = Modifier.fillMaxWidth()) {
        datas.value.forEach {
            SearchItemContent(it)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun SearchItemContent(it: DataX) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageModel = it.avatar, modifier = Modifier
                .width(50.dp)
                .height(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "${it.firstName}")
    }
}

@Composable
private fun DataItem(it: Data) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 6.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "First Name: ${it.fname}")
                Text(text = "Last Name: ${it.lname}")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Check In: ${it.checkInDate}")
                Text(text = "Check Out: ${it.checkOutDate}")
            }
        }
    }
}

@Composable
private fun SpecialRequestSection(viewModel: MainViewModel) {
    OutlinedTextField(
        value = viewModel.specialRequest,
        onValueChange = { viewModel.onUpdateSpecialRequest(it) },
        placeholder = {
            Text(
                text = "Any Special Request", color = Color.LightGray
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.Black
        )
    )
}

@Composable
private fun RoomPreference(viewModel: MainViewModel) {

    val roomPreferences = viewModel.roomPreferences.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Room Preference: ",
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.fillMaxWidth()) {

            TextButton(onClick = { viewModel.expandRoomPreferenceDropDown = true }) {
                Text(text = viewModel.selectedRooPref)
            }

            DropdownMenu(
                expanded = viewModel.expandRoomPreferenceDropDown,
                onDismissRequest = { viewModel.expandRoomPreferenceDropDown = false },
            ) {
                roomPreferences.value.forEachIndexed { index, pref ->
                    DropdownMenuItem(onClick = { viewModel.onSelectRoomPref(pref) }) {
                        Text(
                            text = pref,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun KidsCount(viewModel: MainViewModel) {
    val kidsCount = viewModel.kidsCounts.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Kids: ",
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.fillMaxWidth()) {

            TextButton(onClick = { viewModel.expandKidsDropDown = true }) {
                Text(text = viewModel.kidsCount)
            }

            DropdownMenu(
                expanded = viewModel.expandKidsDropDown,
                onDismissRequest = { viewModel.expandKidsDropDown = false },
            ) {
                kidsCount.value.forEachIndexed { index, count ->
                    DropdownMenuItem(onClick = { viewModel.onSelectKidsCount(count) }) {
                        Text(
                            text = count,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun AdultSection(viewModel: MainViewModel) {

    val adultsCount = viewModel.adultsCount.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Adult: ",
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.fillMaxWidth()) {

            TextButton(onClick = { viewModel.expandAdultDropDown = true }) {
                Text(text = viewModel.adultCount)
            }

            DropdownMenu(
                expanded = viewModel.expandAdultDropDown,
                onDismissRequest = { viewModel.expandAdultDropDown = false },
            ) {
                adultsCount.value.forEachIndexed { index, count ->
                    DropdownMenuItem(onClick = { viewModel.onSelectAdultCount(count) }) {
                        Text(
                            text = count,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun CheckOutCalenderSection(viewModel: MainViewModel) {
    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()


    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()


    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            viewModel.checkOutDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )


    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Check Out: ", color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_calendar_today_24),
            contentDescription = null,
            modifier = Modifier.clickable {
                mDatePickerDialog.show()
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = viewModel.checkOutDate, color = Color.Blue,
            fontFamily = FontFamily.SansSerif,
            fontSize = 15.sp
        )

    }
}

@Composable
private fun CheckInCalenderSection(viewModel: MainViewModel) {
    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()


    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()


    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            viewModel.checkInDate = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )


    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Check In: ", color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_calendar_today_24),
            contentDescription = null,
            modifier = Modifier.clickable {
                mDatePickerDialog.show()
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = viewModel.checkInDate, color = Color.Blue,
            fontFamily = FontFamily.SansSerif,
            fontSize = 15.sp
        )

    }


}

@Composable
private fun PersonalDetailsPincodeSection(viewModel: MainViewModel) {
    OutlinedTextField(
        value = viewModel.pincode,
        onValueChange = { viewModel.onUpdatePincode(it) },
        placeholder = {
            Text(
                text = "Pincode", color = Color.LightGray
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.Black
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PersonalDetailsCountrySection(viewModel: MainViewModel) {

    val countries = viewModel.countries.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Select Country: ",
            color = Color.Black,
            fontFamily = FontFamily.SansSerif,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.fillMaxWidth()) {

            TextButton(onClick = { viewModel.expandDropDown = true }) {
                Text(text = viewModel.selectedCountry)
            }

            DropdownMenu(
                expanded = viewModel.expandDropDown,
                onDismissRequest = { viewModel.expandDropDown = false },
            ) {
                countries.value.forEachIndexed { index, country ->
                    DropdownMenuItem(onClick = { viewModel.onSelectCountry(country) }) {
                        Text(
                            text = country.name,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

    }

}

@Composable
private fun PersonalDetailsAddressSection(viewModel: MainViewModel) {
    OutlinedTextField(
        value = viewModel.address,
        onValueChange = { viewModel.onUpdateAddress(it) },
        placeholder = {
            Text(
                text = "Address", color = Color.LightGray
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.Black
        )
    )
}

@Composable
private fun PersonalDetailsCommunicationSection(viewModel: MainViewModel) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.onUpdateEmail(it) },
            placeholder = {
                Text(
                    text = "Email", color = Color.LightGray
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.width(10.dp))

        OutlinedTextField(
            value = viewModel.phone,
            onValueChange = { viewModel.onUpdatePhoneNum(it) },
            placeholder = {
                Text(
                    text = "Phone Number", color = Color.LightGray
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.Black
            )
        )
    }
}

@Composable
private fun PersonalDetailsInputSection(viewModel: MainViewModel) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

        OutlinedTextField(
            value = viewModel.fname,
            onValueChange = { viewModel.onUpdatefName(it) },
            placeholder = {
                Text(
                    text = "First Name", color = Color.LightGray
                )
            },
            singleLine = true,
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.width(10.dp))

        OutlinedTextField(
            value = viewModel.lname,
            onValueChange = { viewModel.onUpdatelName(it) },
            placeholder = {
                Text(
                    text = "Last Name", color = Color.LightGray
                )
            },
            singleLine = true,
            modifier = Modifier.weight(1f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.Black
            )
        )
    }
}
