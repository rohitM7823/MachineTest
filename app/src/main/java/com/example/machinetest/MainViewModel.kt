package com.example.machinetest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.machinetest.models.Country
import com.example.machinetest.models.Data
import com.example.machinetest.models.DataX
import com.example.machinetest.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import retrofit2.http.Query
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    var showData by mutableStateOf(false)
    var bookingID by mutableStateOf("")
    var query by mutableStateOf("")
    var submittionStatus by mutableStateOf("")
    var enableSubmit by mutableStateOf(false)
    var specialRequest by mutableStateOf("")
    var expandRoomPreferenceDropDown by mutableStateOf(false)
    var selectedRooPref by mutableStateOf("")
    val roomPreferences = MutableStateFlow<List<String>>(emptyList())
    val kidsCounts = MutableStateFlow<List<String>>(emptyList())
    var kidsCount by mutableStateOf("")
    var expandKidsDropDown by mutableStateOf(false)
    var expandAdultDropDown by mutableStateOf(false)
    var adultCount by mutableStateOf("")
    val adultsCount = MutableStateFlow<List<String>>(emptyList())
    var checkInDate by mutableStateOf("")
    var checkOutDate by mutableStateOf("")
    var pincode by mutableStateOf("")
    var selectedCountry by mutableStateOf("")
    var expandDropDown by mutableStateOf(false)
    var address by mutableStateOf("")
    var email by mutableStateOf("")
    var phone by mutableStateOf("")
    var lname by mutableStateOf("")
    var fname by mutableStateOf("")

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries.asStateFlow()

    private val _allData = MutableStateFlow<List<Data>>(emptyList())
    val allData: StateFlow<List<Data>> = _allData.asStateFlow()

    private val _listOfDataX = MutableStateFlow<List<DataX>>(emptyList())
    val listOfDataX: StateFlow<List<DataX>> = _listOfDataX.asStateFlow()

    init {
        getCountries()
        getAdultCounts()
        getKidsCounts()
        getRoomPreference()
    }

    fun onUpdatefName(input: String) {
        fname = input
    }

    fun onUpdatelName(input: String) {
        lname = input
    }

    fun onUpdateEmail(input: String) {
        email = input
    }

    fun onUpdatePhoneNum(input: String) {
        phone = input
    }

    fun onUpdateAddress(input: String) {
        address = input
    }

    fun onSelectCountry(country: Country) {
        selectedCountry = country.name
        expandDropDown = false
    }

    fun onUpdatePincode(it: String) {
        pincode = it
    }

    fun onSelectAdultCount(count: String) {
        adultCount = count
        expandAdultDropDown = false
    }

    fun onSelectKidsCount(count: String) {
        kidsCount = count
        expandKidsDropDown = false
    }

    fun onSelectRoomPref(pref: String) {
        selectedRooPref = pref
        expandRoomPreferenceDropDown = false
        validateInputs()
    }

    fun onUpdateSpecialRequest(it: String) {
        specialRequest = it
        validateInputs()
    }

    private fun validateInputs() {
        when {
            fname.isEmpty() -> {
                enableSubmit = false
            }
            lname.isEmpty() -> {
                enableSubmit = false
            }
            email.isEmpty() || !email.matches(Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+\$")) -> {
                enableSubmit = false
            }
            phone.isEmpty() || !phone.matches(Regex("[0-9]{10}")) -> {
                enableSubmit = false
            }
            address.isEmpty() -> {
                enableSubmit = false
            }
            selectedCountry.isEmpty() -> {
                enableSubmit = false
            }
            pincode.isEmpty() -> {
                enableSubmit = false
            }
            checkInDate.isEmpty() -> {
                enableSubmit = false
            }
            checkOutDate.isEmpty() -> {
                enableSubmit = false
            }
            selectedRooPref.isEmpty() -> {
                enableSubmit = false
            }
            else -> {
                showData = false
                enableSubmit = true
            }
        }
    }

    fun submitAll() {
        mainRepository.submitAllData(
            fname,
            lname,
            email,
            phone,
            pincode,
            address,
            selectedCountry,
            checkInDate,
            checkOutDate,
            selectedRooPref,
            adultCount,
            kidsCount
        ).onEach {
            if ("Submitted Successfully" == it) {
                fname = ""
                lname = ""
                email = ""
                phone = ""
                pincode = ""
                address = ""
                selectedCountry = countries.value.first().name
                checkInDate = ""
                checkOutDate = ""
                selectedRooPref = ""
                adultCount = adultsCount.value.first()
                kidsCount = kidsCounts.value.first()
                specialRequest = ""
                showData = true
                bookingID = Random(10000).nextInt().toString()
            }
            submittionStatus = it
        }.launchIn(viewModelScope)
    }

    private fun getCountries() {
        mainRepository.getCountries().onEach {
            selectedCountry = it.first().name
            _countries.emit(it)
        }.launchIn(viewModelScope)
    }

    private fun getAdultCounts() {
        mainRepository.getAdultsCount().onEach {
            adultCount = it.first()
            adultsCount.emit(it)
        }.launchIn(viewModelScope)
    }

    private fun getKidsCounts() {
        mainRepository.getKidsCount().onEach {
            kidsCount = it.first()
            kidsCounts.emit(it)
        }.launchIn(viewModelScope)
    }

    private fun getRoomPreference() {
        mainRepository.getRoomReference().onEach {
            selectedRooPref = it.first()
            roomPreferences.emit(it)
        }.launchIn(viewModelScope)
    }

    fun onShowAllDataClicked() {
        mainRepository.showAllData().onEach {
            _allData.emit(it)
        }.launchIn(viewModelScope)
    }

    fun onSearch(query: String) {
        mainRepository.getSearchableData(query = query).onEach {
            if(it != null) {
                _listOfDataX.emit(it)
            } else {
                _listOfDataX.emit(emptyList())
            }
        }.launchIn(viewModelScope)
    }

    fun onUpdateQuery(it: String) {
        query = it
        onSearch(query)
    }
}