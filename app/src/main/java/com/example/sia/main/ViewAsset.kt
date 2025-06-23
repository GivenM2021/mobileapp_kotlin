import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sia.R

class ViewAsset : AppCompatActivity() {
//    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_assets)

//        recyclerView = findViewById(R.id.assetRecyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        // Pass both context and callback
//        ApiService.viewAssets(this) { assets: List<Asset> ->
//            recyclerView.adapter = AssetAdapter(assets)
//        }
    }
}
