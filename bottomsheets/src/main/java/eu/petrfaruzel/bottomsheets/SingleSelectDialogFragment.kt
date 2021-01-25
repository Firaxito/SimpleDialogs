package eu.petrfaruzel.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SingleSelectDialogFragment private constructor() : BottomSheetDialogFragment() {

    private var title: String = ""
    private var options: List<String> = listOf()
    private var selectedOptionIndex: Int = -1
    private var onSelectedListener: OnSelectedListener? = null
    private var itemHeight = 48.px

    private lateinit var mTitle: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SingleSelectDialogAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root = inflater.inflate(R.layout.dialog_simple, container, false)

        mTitle = root.findViewById(R.id.dialog_simple_title)
        mTitle.text = title

        recyclerView = root.findViewById(R.id.dialog_simple_recyclerview)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapter = SingleSelectDialogAdapter(object : SingleSelectDialogAdapter.OnStatusChangedListener {
                override fun onSelected(item: Pair<String, Int>) {
                    selectedOptionIndex = item.second
                    onSelectedListener?.onSelected(item)
                }
            })

        adapter.swapData(options, selectedOptionIndex)
        adapter.setItemHeight(itemHeight)

        recyclerView.adapter = adapter

        return root
    }

    private fun setDataFromBuilder(builder: Builder) {
        this.title = builder.title
        this.options = builder.options
        this.selectedOptionIndex = builder.selectedOptionIndex
        this.onSelectedListener = builder.onSelectedListener
        this.itemHeight = builder.itemHeightDp
    }

    companion object {

        class Builder {
            var title: String = ""
                private set
            var options: List<String> = listOf()
                private set
            var selectedOptionIndex: Int = -1
                private set
            var onSelectedListener: OnSelectedListener? = null
                private set
            var itemHeightDp: Int = 48.px
                private set


            fun title(title: String) = apply { this.title = title }
            fun options(options: List<String>) = apply { this.options = options }
            fun selectedOptionIndex(optionIndex: Int) = apply { this.selectedOptionIndex = selectedOptionIndex }
            fun onSelectedLister(listener: OnSelectedListener) = apply { this.onSelectedListener = onSelectedListener }
            fun itemHeight(heightInDp: Int) = apply { this.itemHeightDp = heightInDp.px }

            fun build() = SingleSelectDialogFragment().apply { setDataFromBuilder(this@Builder) }
        }

        interface OnSelectedListener {
            fun onSelected(item: Pair<String, Int>)
        }
    }
}