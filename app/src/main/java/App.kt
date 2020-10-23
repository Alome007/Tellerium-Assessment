import android.app.Application
import com.chibatching.kotpref.Kotpref

public  class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
    }

}