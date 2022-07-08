package tang.inis.kimtinbnthigian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

class SplashActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[JumpViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.urlResponse.observe(this){
            if(it.code == "0"){
                Log.d("JumpCode", it.data?.jumpAddress ?: "")
                if(it.data?.jump == true){
                    startActivity(WebViewActivity.createIntent(this, it.data.jumpAddress ?: ""))
                    finish()
                }else {
                    startActivity(MainActivity.createIntent(this))
                    finish()
                }
            }else {
                Log.d("Error", it.msg ?: "")
            }
        }

        viewModel.errorResponse.observe(this){
            Log.d("Error", it.localizedMessage ?: "")
        }
        viewModel.getJumpUrl("123456")
    }
}