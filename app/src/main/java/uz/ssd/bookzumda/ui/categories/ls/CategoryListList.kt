package uz.ssd.bookzumda.ui.categories.ls

import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_category.view.*
import uz.ssd.bookzumda.R
import uz.ssd.bookzumda.model.entity.Tuple5
import uz.ssd.bookzumda.ui.global.inflate
import uz.ssd.bookzumda.util.Animation
import java.util.*
import kotlin.collections.ArrayList

class CategoryListList(
    private val vendorClickListener: (Tuple5) -> Unit
) : RecyclerView.Adapter<CategoryListList.VendorCategoryView>(), Filterable {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorCategoryView {
        return VendorCategoryView(parent.inflate(R.layout.item_category))
    }

    private var items: ArrayList<Tuple5> = ArrayList()
    private var filteredList = ArrayList(items)


    init {
        filteredList = items
    }

    fun setData(item: List<Tuple5>) {
        val oldData = items.toList()

        items.clear()
        items.addAll(item)

        //yes, on main thread...
        DiffUtil
            .calculateDiff(DiffUtilCallback(items, oldData), false)
            .dispatchUpdatesTo(this)
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
        fun bind(item: Tuple5) {
            itemView.fmBackground.background = itemView.resources.getDrawable(item.drawable)
            itemView.imgBackground.background = itemView.resources.getDrawable(item.imageBackground)
            itemView.tvTitle.text = item.title
            itemView.tvDetail.text = item.detail
            itemView.imgBackground.background.alpha = 75
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2 * 2
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSearch: CharSequence): FilterResults {
                if (charSearch.isEmpty()) {
                    items = filteredList
                } else {
                    val resultList = ArrayList<Tuple5>()
                    for (row in filteredList) {
                        if (row.detail.toLowerCase(Locale.ROOT).contains(
                                charSearch.toString().toLowerCase(Locale.ROOT)
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    items = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = items
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items = results?.values as ArrayList<Tuple5>
                notifyDataSetChanged()
            }

        }
    }

    class DiffUtilCallback(
        private val oldList: List<Tuple5>,
        private val newList: List<Tuple5>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return oldItem.detail == newItem.detail
//                    && TextUtils.equals(oldItem.value.toString(), newItem.value.toString())

        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }
}