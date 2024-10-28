package com.example.stockslist

import androidx.lifecycle.ViewModel
import com.example.stockslist.models.Stock
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

data class StockState(
    val stocks: ArrayList<Stock> = arrayListOf(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)

class StocksViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StockState())
    val uiState: StateFlow<StockState> = _uiState.asStateFlow()

    fun fetchStocks() {
        _uiState.value = StockState(isLoading = true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://financialmodelingprep.com/api/v3/symbol/available-euronext?apikey=LAPWcQAqBcOhURzMxylr571eyGz50gXa")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _uiState.value = StockState(errorMessage = e.message ?: "", isLoading = false)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        _uiState.value = StockState(errorMessage = "Failed to fetch data", isLoading = false)
                        return
                    }

                    val stocksResult = arrayListOf<Stock>()
                    val result = response.body!!.string()
                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val stockObject = jsonArray.getJSONObject(i)
                        val stock = Stock(
                            symbol = stockObject.getString("symbol"),
                            name = stockObject.getString("name"),
                            currency = stockObject.getString("currency"),
                            stockExchange = stockObject.getString("stockExchange"),
                            exchangeShortName = stockObject.getString("exchangeShortName")
                        )
                        stocksResult.add(stock)
                    }

                    _uiState.value = StockState(stocks = stocksResult, isLoading = false)
                }
            }
        })
    }
}
