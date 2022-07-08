package tang.inis.kimtinbnthigian

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import tang.inis.kimtinbnthigian.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {
    
    private lateinit var binding : ActivityWebviewBinding

    private val url by lazy {
        intent.getStringExtra(EXTRA_URL_CONTENT)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupViews()
    }

    private fun setupViews() {
        with(binding.webView){
            with(settings){
                javaScriptEnabled = true
                defaultTextEncodingName = "UTF-8"
                cacheMode = WebSettings.LOAD_NO_CACHE
                useWideViewPort = true
                domStorageEnabled = true
                builtInZoomControls = false
                layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                loadWithOverviewMode = true
                blockNetworkImage = true
                loadsImagesAutomatically = true
                setSupportZoom(false)
                setSupportMultipleWindows(true)
            }
            requestFocusFromTouch()
            scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        }

        val webSetting: WebSettings = binding.webView.settings

        with(webSetting){
            domStorageEnabled = true
            allowFileAccess = true
            cacheMode = WebSettings.LOAD_DEFAULT
        }

        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {

                if (newProgress == 100) {
                    binding.webView.settings.blockNetworkImage = false
                    binding.progressBar.visibility = View.GONE
                }else {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }

            override fun onCreateWindow(
                view: WebView,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message
            ): Boolean {
                val newWebView = WebView(this@WebViewActivity)
                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                newWebView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        binding.webView.loadUrl(url)
                        return true
                    }
                }
                return true
            }
        }

        binding.webView.loadUrl(url ?: "")
    }

    companion object {
        private const val EXTRA_URL_CONTENT = "url_content"


        /**
         * use createIntent function to call this activity's intent.
         * parameters - context (Context) - context of the current activity.
         * url (String) - the default link that the activity will load in the webview.
         */
        fun createIntent(context: Context, url: String) = Intent(context, WebViewActivity::class.java).apply {
            putExtra(EXTRA_URL_CONTENT, url)
        }
    }
}