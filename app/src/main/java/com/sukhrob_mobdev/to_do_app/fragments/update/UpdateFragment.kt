package com.sukhrob_mobdev.to_do_app.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sukhrob_mobdev.to_do_app.R
import com.sukhrob_mobdev.to_do_app.data.models.ToDoData
import com.sukhrob_mobdev.to_do_app.databinding.FragmentUpdateBinding
import com.sukhrob_mobdev.to_do_app.viewmodel.SharedViewModel
import com.sukhrob_mobdev.to_do_app.viewmodel.ToDoViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        // Set Menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.currentItem = args.currentItem
        binding.currentPrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener
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
            Toast.makeText(requireContext(), "Item deleted successfully!", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete ${args.currentItem.title}")
        builder.setMessage("Are you sure you want to delete ${args.currentItem.title}")
        builder.create().show()
    }

    private fun updateItem() {
        val title = binding.currentTitleEt.text.toString()
        val description = binding.currentDescriptionEt.text.toString()
        val priority = binding.currentPrioritiesSpinner.selectedItem.toString()

        //val validation = mSharedViewModel.verifyDataFromUser(title, description)
        when {
            title.isEmpty() and description.isEmpty() -> {
                binding.currentTitleEt.error = "This is empty"
                binding.currentDescriptionEt.error = "This is empty"
            }
            description.isEmpty() -> {
                binding.currentDescriptionEt.error = "This is empty"
            }
            title.isEmpty() -> {
                binding.currentTitleEt.error = "This is empty"
            }
            else -> {
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
            }
        }

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}