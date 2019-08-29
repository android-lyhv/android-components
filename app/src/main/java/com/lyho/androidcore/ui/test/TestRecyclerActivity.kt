package com.lyho.androidcore.ui.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lyho.androidcore.R
import com.lyho.androidcore.model.entities.User
import com.lyho.androidcore.ui.common.recyclerview.DividerSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main_recycler.*

@SuppressLint("Registered")
class TestRecyclerActivity : AppCompatActivity(), TestActivityViewListener {
    override fun onNewUser(user: User?) {
        // TODO update ui here
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_recycler)
        // Create View Model
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TestRecyclerActivity)
            addItemDecoration(
                DividerSpacingItemDecoration(
                    this@TestRecyclerActivity,
                    DividerItemDecoration.VERTICAL,
                    resources.getDimensionPixelSize(R.dimen._10sdp),
                    R.drawable.bg_divder
                )
            )
            adapter = TestRecyclerAdapter(this@TestRecyclerActivity)
        }
    }
}
