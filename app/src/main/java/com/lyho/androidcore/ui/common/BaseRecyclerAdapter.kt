import android.content.Context
import androidx.recyclerview.widget.RecyclerView

/**
 * Base Adapter.
 */
abstract class BaseRecyclerAdapter<T, VH : BaseViewHolder?>(protected val context: Context) :
    RecyclerView.Adapter<VH>() {
    private var mElements: MutableList<T>? = null
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder?.onBind()
    }

    fun addElements(elements: List<T>?) {
        if (mElements == null) {
            mElements = ArrayList()
        }
        if (elements?.isNotEmpty() == true) {
            mElements?.addAll(elements)
            notifyItemRangeInserted(itemCount, elements.size)
        }
    }

    fun setElements(elements: List<T>?) {
        if (mElements == null) {
            mElements = ArrayList()
        }
        elements?.let {
            mElements?.clear()
            mElements?.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun getElement(position: Int): T? {
        return try {
            mElements!![position]
        } catch (e: Exception) {
            null
        }
    }

    fun removeItem(position: Int) {
        mElements?.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getElements(): MutableList<T> {
        return mElements ?: ArrayList()
    }

    override fun getItemCount(): Int {
        return mElements?.size ?: 0
    }
}
