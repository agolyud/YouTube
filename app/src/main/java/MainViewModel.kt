package app.hdrezka.youtubesun

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _appVersion = MutableLiveData<String>()
    val appVersion: LiveData<String> get() = _appVersion
    fun setAppVersion(version: String) {
        _appVersion.value = version
    }
}