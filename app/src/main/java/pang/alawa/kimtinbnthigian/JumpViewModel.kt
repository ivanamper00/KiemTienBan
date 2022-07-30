package pang.alawa.kimtinbnthigian

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pang.alawa.kimtinbnthigian.retrofit.JumpServiceImp
import pang.alawa.kimtinbnthigian.retrofit.RequestModel
import pang.alawa.kimtinbnthigian.retrofit.ResponseModel
import pang.alawa.kimtinbnthigian.utils.UiState

class JumpViewModel: ViewModel() {

    private val repo = JumpServiceImp()

    private val _urlResponse = MutableLiveData<UiState<ResponseModel>>()
    val urlResponse : LiveData<UiState<ResponseModel>>
        get() = _urlResponse

    fun getJumpUrl(packageName: String){
        val param = RequestModel(
            packageName
        )
        viewModelScope.launch {
            repo.getJumpCodeUrl(param)
                .catch { err -> _urlResponse.value = UiState.Error(err) }
                .collectLatest {
                    _urlResponse.value = UiState.Success(it)
                }
        }
    }
}