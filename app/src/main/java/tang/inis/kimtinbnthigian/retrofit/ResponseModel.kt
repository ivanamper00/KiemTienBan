package tang.inis.kimtinbnthigian.retrofit

import androidx.annotation.Keep

@Keep
data class ResponseModel(
    val code: String,
    val msg: String,
    val data: DataModel?
){
    data class DataModel(
        val appName: String?,
        val packageName: String?,
        val jump: Boolean,
        val jumpAddress: String?
    )
}
