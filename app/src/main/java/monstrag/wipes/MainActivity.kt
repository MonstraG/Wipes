package monstrag.wipes

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val myDataset = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        // FAB
//        findViewById<FloatingActionButton>(R.id.add_item).setOnClickListener { view ->
//            myDataset.add("new item")
//            recyclerView.swapAdapter(ListAdapter(myDataset), false)
//            Snackbar.make(view, "Item added", Snackbar.LENGTH_SHORT)
//                .setAction("Action", null).show()
//        }

        // init list
        viewManager = LinearLayoutManager(this)
        viewAdapter = ListAdapter(myDataset)
        recyclerView = findViewById<RecyclerView>(R.id.list).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val plusIcon = findViewById<ImageView>(R.id.plus_sign)
        val sendBtnIcon = findViewById<ImageView>(R.id.confirm)

        // Icon-button animations
        sendBtnIcon.visibility = View.GONE
        val newItemField = findViewById<EditText>(R.id.new_item_field)
        newItemField.setOnFocusChangeListener { view, focused ->
            Debug.log(view, focused)
            if (focused) {
                plusIcon.visibility = View.GONE
                sendBtnIcon.visibility = View.VISIBLE
            } else {
                plusIcon.visibility = View.VISIBLE
                sendBtnIcon.visibility = View.GONE
            }
        }

        //todo: remove focus when touched outside
        // https://stackoverflow.com/questions/6677969/tap-outside-edittext-to-lose-focus/36411427

        // if clicks on plus, focus on input
        plusIcon.setOnClickListener {
            // Focus
            newItemField.requestFocus()
            // Show keyboard
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(newItemField, InputMethodManager.SHOW_IMPLICIT)
        }

        //todo: recolor send when not empty
        sendBtnIcon.setOnClickListener {
            val text = newItemField.text
            if (text.isNotEmpty()) {
                myDataset.add(text.toString())
                text.clear()
                viewAdapter.notifyItemInserted(myDataset.size - 1)
            }
        }

        //todo: on click back (or click away), remove focus from input
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}