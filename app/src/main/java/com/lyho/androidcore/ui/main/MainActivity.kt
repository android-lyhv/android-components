package com.lyho.androidcore.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lyho.androidcore.R
import com.lyho.androidcore.ui.common.RecyclerDiffUtilCallBack
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userAdapter = UserAdapter(diffUser)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
        userAdapter.setItems(getUser())
    }
}

data class User(val id: Int, val name: String)

fun getUser(): List<User> {
    return (1..10).map {
        User(id = it, name = "Ho Van Ly $it")
    }
}

object diffUser : RecyclerDiffUtilCallBack<User>() {
    override fun sameContent(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (getOldItemPosition(oldItemPosition).name == getNewItemPosition(newItemPosition).name)
    }

    override fun sameItem(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (getOldItemPosition(oldItemPosition).id == getNewItemPosition(newItemPosition).id)
    }
}