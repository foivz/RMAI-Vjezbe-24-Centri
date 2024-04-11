package hr.foi.rmai.memento.ws

import com.google.gson.annotations.SerializedName

data class NewsItem(
    val title: String?,
    val text: String?,
    val date: String?,
    @SerializedName("image_path") var imagePath : String?
)
