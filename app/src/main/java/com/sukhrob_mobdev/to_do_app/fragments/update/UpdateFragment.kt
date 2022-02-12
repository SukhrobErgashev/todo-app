package com.sukhrob_mobdev.to_do_app.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sukhrob_mobdev.to_do_app.R
import com.sukhrob_mobdev.to_do_app.data.models.ToDoData
import com.sukhrob_mobdev.to_do_app.fragments.SharedViewModel
import com.sukhrob_mobdev.to_do_app.viewmodel.ToDoViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Set Menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<EditText>(R.id.current_title_et).setText(args.currentItem.title)
        view.findViewById<EditText>(R.id.current_description_et)
            .setText(args.currentItem.description)
        view.findViewById<Spinner>(R.id.current_priorities_spinner)
            .setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        view.findViewById<Spinner>(R.id.current_priorities_spinner).onItemSelectedListener =
            mSharedViewModel.listener

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteItem(args.currentItem)
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            Toast.makeText(requireContext(), "Item deleted successfully!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") {_, _ ->

        }
        builder.setTitle("Delete ${args.currentItem.title}")
        builder.setMessage("Are you sure you want to delete ${args.currentItem.title}")
        builder.create().show()
    }

    private fun updateItem() {
        val title = view?.findViewById<EditText>(R.id.current_title_et)?.text.toString()
        val description = view?.findViewById<EditText>(R.id.current_description_et)?.text.toString()
        val priority =
            view?.findViewById<Spinner>(R.id.current_priorities_spinner)?.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            // Update Data
            val currentData = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(priority),
                description
            )

            mToDoViewModel.updateData(currentData)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT)
                .show()
        }
    }

}