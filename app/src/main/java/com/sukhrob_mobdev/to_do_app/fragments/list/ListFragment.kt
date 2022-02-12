package com.sukhrob_mobdev.to_do_app.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sukhrob_mobdev.to_do_app.R
import com.sukhrob_mobdev.to_do_app.fragments.SharedViewModel
import com.sukhrob_mobdev.to_do_app.viewmodel.ToDoViewModel

class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Set Menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false).also {
            it.findViewById<ImageView>(R.id.button_add).setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
            it.findViewById<ImageView>(R.id.no_data_imageView).setOnClickListener {
                findNavController().navigate(R.id.action_listFragment_to_updateFragment)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
//        recyclerView.layoutManager =
//            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkDatabaseState(data)
            adapter.setData(data)
        })

        mSharedViewModel.isDatabaseEmpty.observe(viewLifecycleOwner, Observer {
            if (it == true)
                view.findViewById<ImageView>(R.id.no_data_imageView).visibility = View.VISIBLE
            else
                view.findViewById<ImageView>(R.id.no_data_imageView).visibility = View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete All!")
        builder.setMessage("Are you sure you want to delete all items?")
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(), "All Items deleted successfully!", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()
    }
}