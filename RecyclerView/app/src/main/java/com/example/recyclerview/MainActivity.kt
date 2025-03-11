package com.example.recyclerview

import android.R
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: ShoppingListAdapter? = null
    private var shoppingList: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)

        recyclerView = findViewById(R.id.redo)
        extracted()

        shoppingList = ArrayList(mutableListOf("Хлеб", "Молоко", "Яйца"))
        adapter = ShoppingListAdapter(shoppingList as ArrayList<String>)
        recyclerView()?.setAdapter(adapter)
    }

    private fun MainActivity.extracted() {
        recyclerView?.setLayoutManager(LinearLayoutManager(this))
    }

    private fun MainActivity.recyclerView() = recyclerView

    private class ShoppingListAdapter(private val shoppingList: List<String>) :
        RecyclerView.Adapter<ShoppingListAdapter.ViewHolder?>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.list_content, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

        @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = shoppingList[position]
            holder.itemName.text = item
            holder.deleteButton.setOnClickListener { v: View? ->
                shoppingList.forEach(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, shoppingList.size)
            }
        }

        val itemCount: Int
            get() = shoppingList.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var itemName: TextView = itemView.findViewById<TextView>(R.id.cut)
            @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            var deleteButton: Button = itemView.findViewById<Button>(R.id.bold)
        }
    }
}

private fun <E> List<E>.forEach(action: Int) {

}
