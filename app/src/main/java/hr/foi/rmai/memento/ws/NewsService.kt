package hr.foi.rmai.memento.ws

import retrofit2.Call
import retrofit2.http.GET

interface NewsService {
    @GET("news.php")
    fun getName() : Call<NewsResponse>
}