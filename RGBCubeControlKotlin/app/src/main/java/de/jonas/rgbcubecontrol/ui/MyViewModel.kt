package de.jonas.rgbcubecontrol.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import de.jonas.rgbcubecontrol.domain.animations.Animation

class MyViewModel : ViewModel() {
    private lateinit var users: MutableLiveData<List<Animation>>

    fun getUsers(): LiveData<List<Animation>> {
        if (!::users.isInitialized) {
            users = MutableLiveData()
            loadUsers()
        }
        return users
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}