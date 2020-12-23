package com.weylar.soloadaptersample


import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.weylar.soloadapter.SoloAdapter
import com.weylar.soloadaptersample.databinding.ActivityMainBinding
import com.weylar.soloadaptersample.databinding.ListItemBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter = generateBindingAdapter()
        //val adapter = generateLayoutAdapter()


        adapter.submitList(
            listOf(
                Student("Alberto", 45),
                Student("Idris", 22),
                Student("Tony", 30)
            )
        )
        binding.recyclerView.adapter = adapter

        binding.button.setOnClickListener {
            adapter.notifyItemChanged(2, mutableListOf(Student("John", 100)))
        }


    }

    private fun generateBindingAdapter(): SoloAdapter<ListItemBinding, Student> {
        return SoloAdapter.bind(ListItemBinding::inflate) { data, _, payload ->
            if (payload.isNullOrEmpty()) {
                name.text = data.name
                age.text = data.age.toString()
            } else {
                age.text = (payload.first() as ArrayList<Student>)[0].age.toString()
            }
        }
    }

    private fun generateLayoutAdapter(): SoloAdapter<ViewBinding, Student> {
        return SoloAdapter.bind(R.layout.list_item) { data, position, _ ->
            findViewById<TextView>(R.id.name).text = data.name
            findViewById<TextView>(R.id.age).text = data.age.toString()
            Log.i("Aminu", position.toString())
        }
    }

}

data class Student(val name: String, val age: Int)

