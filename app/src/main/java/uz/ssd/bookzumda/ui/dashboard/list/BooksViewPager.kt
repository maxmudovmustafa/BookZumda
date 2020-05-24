package uz.ssd.bookzumda.ui.dashboard.list

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import uz.ssd.bookzumda.entity.BooksEntity

/**
 * Created by MrShoxruxbek on 22,May,2020
 */

class BooksViewPager(
    clickListener: (BooksEntity, Int) -> Unit,
    clickAddListener: () -> Unit
) : ListDelegationAdapter<MutableList<Any>>() {

    init {
        items = mutableListOf()
        delegatesManager.addDelegate(CardDelegate(clickListener))
    }

    fun setData(cardsList: List<BooksEntity>) {
        val oldData = items.toList()

        items.clear()
        items.addAll(cardsList)

        //yes, on main thread...
        DiffUtil
            .calculateDiff(DiffCallback(items, oldData), false)
            .dispatchUpdatesTo(this)
    }

    private inner class DiffCallback(
        private val newItems: List<Any>,
        private val oldItems: List<Any>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size
        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return if (newItem is BooksEntity && oldItem is BooksEntity) {
                newItem.id == oldItem.id
            } else {
                newItem is BooksEntity && oldItem is BooksEntity
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return if (newItem is BooksEntity && oldItem is BooksEntity) {
                newItem.price == oldItem.price
                        && newItem.name == oldItem.name
            } else {
                newItem is BooksEntity && oldItem is BooksEntity
            }
        }
    }
}