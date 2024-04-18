package hr.foi.rmai.memento.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rmai.memento.R
import hr.foi.rmai.memento.ws.NewsItem
import hr.foi.rmai.memento.ws.WsNews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class NewsAdapter(val newsList: List<NewsItem>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var title: TextView
        private var text: TextView
        private var dateTime: TextView
        private var imageView: ImageView

        init {
            title = view.findViewById(R.id.tv_news_list_item_title)
            text = view.findViewById(R.id.tv_news_list_item_text)
            dateTime = view.findViewById(R.id.tv_news_list_item_date)
            imageView = view.findViewById(R.id.iv_news_list_item_image)
        }

        fun bind(newsItem: NewsItem) {
            title.text = newsItem.title
            text.text = newsItem.text
            dateTime.text = newsItem.date

            CoroutineScope(Dispatchers.IO).launch {
                val image : Bitmap
                val imageURL = URL(WsNews.BASE_URL + newsItem.imagePath)
                image = BitmapFactory.decodeStream(imageURL.openStream())

                withContext(Dispatchers.Main) {
                    imageView.setImageBitmap(image)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).
                    inflate(R.layout.news_list_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }
}