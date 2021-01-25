package eu.petrfaruzel.bottomsheets


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*

internal class SingleSelectDialogAdapter(
    val outsideListener : OnStatusChangedListener? = null
) : RecyclerView.Adapter<SingleSelectDialogAdapter.SingleSelectDialogViewHolder>() {

    private var itemHeight = 48.px
    private var data: List<String> = ArrayList()
    private var selectedId = -1
    private var listener = object : OnStatusChangedListener {
        override fun onSelected(item: Pair<String, Int>) {
            val oldId = selectedId
            selectedId = item.second
            notifyItemChanged(oldId)
            notifyItemChanged(selectedId)
            outsideListener?.onSelected(item)
        }
    }


    interface OnStatusChangedListener {
        fun onSelected(item : Pair<String, Int>)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SingleSelectDialogViewHolder {
        return SingleSelectDialogViewHolder(listener,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_dialog_single_select, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SingleSelectDialogViewHolder, position: Int){
        holder.itemView.layoutParams.height = itemHeight
        holder.bind(data[position], selectedId)
    }

    fun swapData(data: List<String>, selectedId : Int) {
        this.data = data
        this.selectedId = selectedId
        notifyDataSetChanged()
    }

    fun setItemHeight(height : Int){
        this.itemHeight = height
    }

    internal class SingleSelectDialogViewHolder(private val listener : SingleSelectDialogAdapter.OnStatusChangedListener, itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String, selectedId : Int) = with(itemView) {

            findViewById<TextView>(R.id.item_dialog_single_text).text = item
            val icon = findViewById<ImageView>(R.id.item_dialog_single_image)
            if (selectedId == adapterPosition) {
                icon.setImageDrawable(ContextCompat.getDrawable(icon.context, R.drawable.ic_dialog_checked))
                // todo -> Theme later
                //this.setBackgroundColor(ContextCompat.getColor(icon.context, R.color.colorThemeDarker))
            } else {
                icon.setImageDrawable(ContextCompat.getDrawable(icon.context, R.drawable.ic_dialog_unchecked))
                //this.setBackgroundColor(ContextCompat.getColor(icon.context, R.color.colorTheme))
            }
            setOnClickListener {
                listener.onSelected(Pair(item, adapterPosition))
            }
        }
    }
}