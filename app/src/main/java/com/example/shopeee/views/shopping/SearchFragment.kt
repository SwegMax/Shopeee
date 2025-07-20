package com.example.shopeee.views.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shopeee.R
import com.example.shopeee.repository.Constants.FLUTTER_SEARCH_ENGINE_ID
import com.example.shopeee.repository.Product
import com.example.shopeee.repository.Resource
import com.example.shopeee.viewmodelMVVM.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val flutterSearchEngineId = FLUTTER_SEARCH_ENGINE_ID
    private var flutterFragment: FlutterFragment? = null
    private var methodChannel: MethodChannel? = null
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val flutterEngine = FlutterEngineCache.getInstance().get(flutterSearchEngineId)

        methodChannel = MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, "com.shopeee.app.search")


        // Crucial: Check if the FlutterEngine is already cached. It MUST be if pre-warming is set up.
        if (FlutterEngineCache.getInstance().contains(flutterSearchEngineId)) {
            flutterFragment = FlutterFragment.withCachedEngine(flutterSearchEngineId).build()

            // Add the FlutterFragment to the container in your Android layout
            childFragmentManager.beginTransaction()
                .replace(R.id.flutter_container, flutterFragment!!, "flutter_search_fragment_tag")
                .commit()
        } else {
            Log.e("SearchTabFragment", "FlutterEngine not found in cache for ID: $flutterSearchEngineId. This indicates a problem with pre-warming!")
            //Remove this log message
        }

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        methodChannel?.setMethodCallHandler { call, result ->
            when (call.method) {
                "startSearch" -> {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val resource = withContext(Dispatchers.IO) {
                            viewModel.startSearch(call.argument<String>("query"))
                        }
                        when (resource) {
                            is Resource.Success -> {
                                val productList = resource.data?.map { it.toMap() }
                                result.success(mapOf("status" to "success", "data" to productList))
                            }

                            is Resource.Error -> {
                                result.success(mapOf("status" to "error", "message" to resource.message))
                            }

                            else -> Unit
                        }
                    }
                }

                "loadNextPage" -> {
                    result.success(mapOf("status" to "loading"))
                    viewLifecycleOwner.lifecycleScope.launch {
                        val resource = withContext(Dispatchers.IO) {
                            viewModel.loadNextPage()
                        }
                        when (resource) {
                            is Resource.Success -> {
                                val productList = resource.data?.map { it.toMap() }
                                result.success(mapOf("status" to "success", "data" to productList))
                            }

                            is Resource.Error -> {
                                result.success(mapOf("status" to "error", "message" to resource.message))
                            }

                            else -> result.notImplemented()
                        }
                    }
                }
                "navigateToProduct" -> {
                    val product = call.argument<Map<String, Any?>>("product")
                    navigateToProduct(product!!)
                    result.success(true)
                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }

    private fun navigateToProduct(productMap: Map<String, Any?>) {
        val product = Product.fromMap(productMap)
        val b = Bundle().apply { putParcelable("product", product) }
        findNavController().navigate(R.id.action_searchFragment_to_productDetailsFragment, b)
    }
}