package uz.ssd.bookzumda.ui.profile

import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.item_search_book.view.*
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.entity.MyBookEntity
import uz.ssd.bookzumda.ui.global.formattedMoney
import uz.ssd.bookzumda.ui.global.inflate
import uz.ssd.bookzumda.ui.global.visible
import uz.ssd.bookzumda.util.Animation

class MyBooksList(
    private val vendorClickListener: (MyBookEntity) -> Unit
) : RecyclerView.Adapter<MyBooksList.VendorCategoryView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorCategoryView {
        return VendorCategoryView(parent.inflate(R.layout.item_search_book))
    }

    private var items: ArrayList<MyBookEntity> = ArrayList()
    private var filteredList: ArrayList<MyBookEntity> = ArrayList()

    init {
        filteredList = items
    }

    fun setData(books: List<MyBookEntity>) {
        val oldData = items.toList()

        items.clear()
        items.addAll(books)
        notifyDataSetChanged()
        //yes, on main thread...
        DiffUtil
            .calculateDiff(DiffUtilCallback(items, oldData), false)
            .dispatchUpdatesTo(this)

    }

    fun deleteBook(bookEntity: MyBookEntity) {
        items.remove(bookEntity)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VendorCategoryView, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            Animation().Pulse(it).start()
            Handler().postDelayed({
                vendorClickListener(items[position])
            }, 200)
        }
    }

    class VendorCategoryView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MyBookEntity) {
            itemView.tvAuthor.text = item.author
            itemView.tvNameBook.text = item.name
            itemView.tvPrice.text = (item.price * 100L).formattedMoney() + "\t"
            itemView.context.resources.getString(R.string.price)
            itemView.tvType.text = item.janr_type

            Glide.with(itemView)
                .load(item.photo)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.fullscreenProgressView.visible(false)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                }).into(itemView.imgBook)
        }
    }

    class DiffUtilCallback(
        private val oldList: List<MyBookEntity>,
        private val newList: List<MyBookEntity>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.id == newItem.id
//                    && TextUtils.equals(oldItem.value.toString(), newItem.value.toString())

        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }
}