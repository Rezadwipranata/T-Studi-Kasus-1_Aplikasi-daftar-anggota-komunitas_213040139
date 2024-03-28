package com.application.komunitas_app.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.komunitas_app.data.database.Komunitas
import com.application.komunitas_app.data.database.KomunitasDao
import com.application.komunitas_app.data.database.KomunitasDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class KomunitasViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: KomunitasDao
    private val _allCommunities = MutableLiveData<List<Komunitas>>()
    private val _getCommunity = MutableLiveData<Komunitas>()

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val allCommunities: LiveData<List<Komunitas>> = _allCommunities
    val getCommunity: LiveData<Komunitas> = _getCommunity

    init {
        dao = KomunitasDatabase.getInstance(application).communitiesDao()
    }

    fun getCommunities() {
        viewModelScope.launch(Dispatchers.IO) {
            _allCommunities.postValue(dao.getAllCommunities())
        }
    }

    fun getCommunity(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _getCommunity.postValue(dao.getCommunity(id))
        }
    }

    fun addCommunity(komunitas: Komunitas) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {dao.insertCommunity(komunitas)}
        }
        getCommunities()
    }

    fun deleteCommunity(komunitas: Komunitas) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {dao.deleteCommunity(komunitas)}
        }
        getCommunities()
    }

    fun updateCommunity(id: Int, name: String, tglJoin: String, memberLvl: String, period: String) {
        viewModelScope.launch(Dispatchers.IO) {
            executorService.execute {
                dao.updateCommunity(id, name, tglJoin, memberLvl, period)
            }
        }
        getCommunities()
    }
}