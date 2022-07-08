package tang.inis.kimtinbnthigian

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tang.inis.kimtinbnthigian.retrofit.JumpServiceImp
import tang.inis.kimtinbnthigian.retrofit.RequestModel
import tang.inis.kimtinbnthigian.retrofit.ResponseModel

class JumpViewModel: ViewModel() {

    private val repo = JumpServiceImp()

    private val _urlResponse = MutableLiveData<ResponseModel>()
    val urlResponse : LiveData<ResponseModel>
        get() = _urlResponse

    private val _errorResponse = MutableLiveData<Throwable>()
    val errorResponse : LiveData<Throwable>
        get() = _errorResponse

    fun getJumpUrl(packageName: String){
        val param = RequestModel(
            packageName
        )
        viewModelScope.launch {
            repo.getJumpCodeUrl(param)
                .catch { err -> _errorResponse.value = err }
                .collectLatest {
                    _urlResponse.value = it
                }
        }
    }
}