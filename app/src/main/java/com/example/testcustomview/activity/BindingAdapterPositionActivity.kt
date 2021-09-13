package com.example.testcustomview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testcustomview.R
import com.example.testcustomview.databinding.ItemBindingAdapterPositionBinding
import java.util.*

class BindingAdapterPositionActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null
    private var adapter: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binding_adapter_position)
        findViewById<TextView>(R.id.tv_remove).setOnClickListener {
            adapter?.removeItem(2)
        }
        findViewById<TextView>(R.id.tv_insert).setOnClickListener {
            adapter?.insertItem(1, "${(adapter?.items?.size ?: 0)}")
        }
        recyclerView = findViewById(R.id.recyclerView)
        adapter = Adapter()
        recyclerView?.adapter = adapter
        val data = ArrayList<String>()
        data.add("0")
        data.add("1")
        data.add("2")
        data.add("3")
        data.add("4")
        data.add("5")
        data.add("6")
        data.add("7")
        data.add("8")
        data.add("9")
        data.add("10")
        data.add("11")
        data.add("12")
        data.add("13")
        data.add("14")
        adapter?.setData(data)
    }

    class Adapter : RecyclerView.Adapter<Adapter.ItemHolder>() {
        val items = ArrayList<String>()

        fun setData(items: ArrayList<String>) {
            this.items.clear()
            this.items.addAll(items)
            notifyDataSetChanged()
        }

        fun removeItem(position: Int) {
            this.items.removeAt(position)
            notifyItemRemoved(position)
            // remove item不能直接使用notifyItemRangeChanged，否则会崩溃java.lang.IndexOutOfBoundsException: Inconsistency detected.可以先notifyItemRemoved再notifyItemRangeChanged
//            notifyItemRangeChanged(position, items.size - position)
        }

        fun insertItem(position: Int, string: String) {
            this.items.add(position, string)
            notifyItemInserted(position)
            // insert可以直接使用notifyItemRangeChanged刷新
//            notifyItemRangeChanged(position, itemCount - position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            return ItemHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_binding_adapter_position, parent, false))
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val item = items[position]
            holder.binding.tvPosition.text = item
            holder.itemView.setOnClickListener {
                // 点击事件里不要使用position，遇到notifyItemRemove,insert(没有notifyItemRangeChange)会有错误，推荐使用bindingAdapterPosition,或者使用外部得到的item
                Log.d("testposition", "position: $position")
                Log.d("testposition", "position item: ${items[position]}")
                Log.d("testposition", "bindingAdapterPosition: ${holder.bindingAdapterPosition}")
                Log.d("testposition", "bindingAdapterPosition item: ${items[holder.bindingAdapterPosition]}")
                Log.d("testposition", "layoutPosition: ${holder.layoutPosition}")
                Log.d("testposition", "layoutPosition item: ${items[holder.layoutPosition]}")
                Log.d("testposition", "external item: $item")
            }
        }

        class ItemHolder(val binding: ItemBindingAdapterPositionBinding) : RecyclerView.ViewHolder(binding.root)
    }
}