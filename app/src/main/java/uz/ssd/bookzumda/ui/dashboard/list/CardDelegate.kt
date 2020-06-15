package uz.ssd.bookzumda.ui.dashboard.list

import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_dashboard.*
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.model.entity.BooksEntity
import uz.ssd.bookzumda.ui.global.inflate

/**
 * Created by MrShoxruxbek on 22,May,2020
 */

class CardDelegate(
    private val clickListener: (BooksEntity, Int) -> Unit
) : AdapterDelegate<MutableList<Any>>() {

    override fun isForViewType(items: MutableList<Any>, position: Int) =
        items[position] is BooksEntity

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_dashboard))

    override fun onBindViewHolder(
        items: MutableList<Any>,
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) =
        (viewHolder as ViewHolder).bind(items[position] as BooksEntity, position)

    private inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        private lateinit var BooksEntity: BooksEntity

        init {
            val displayMetrics = containerView.context.resources.displayMetrics
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                screenWidthDp - 60,
                displayMetrics
            ).toInt()

            cardView.layoutParams.width = px
            containerView.setOnClickListener { clickListener(BooksEntity, adapterPosition) }
        }

        fun bind(book: BooksEntity, position: Int) {
            this.BooksEntity = book

            Glide.with(containerView.context)
                .load(book.photo)
                .placeholder(R.drawable.no_book_img)
                .centerInside()
                .fitCenter()
                .into(ivBankLogo)

        }
    }
}