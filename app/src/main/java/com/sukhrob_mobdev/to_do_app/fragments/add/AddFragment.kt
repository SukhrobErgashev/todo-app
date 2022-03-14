package com.sukhrob_mobdev.to_do_app.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sukhrob_mobdev.to_do_app.R
import com.sukhrob_mobdev.to_do_app.data.models.ToDoData
import com.sukhrob_mobdev.to_do_app.databinding.FragmentAddBinding
import com.sukhrob_mobdev.to_do_app.viewmodel.SharedViewModel
import com.sukhrob_mobdev.to_do_app.viewmodel.ToDoViewModel

class AddFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Binding
        _binding = FragmentAddBinding.inflate(inflater, container, false)

        // Set Menu
        setHasOptionsMenu(true)

        binding.prioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.prioritiesSpinner.selectedItem.toString()
        val mDescription = binding.descriptionEt.text.toString()

        when {
            mTitle.isEmpty() and mDescription.isEmpty() -> {
                binding.titleEt.error = "This is empty"
                binding.descriptionEt.error = "This is empty"
            }
            mTitle.isEmpty() -> {
                binding.titleEt.error = "This is empty"
            }
            mDescription.isEmpty() -> {
                binding.descriptionEt.error = "This is empty"
            }
            else -> {
                // Insert Data to Database
                val newData = ToDoData(
                    id = 0,
                    title = mTitle,
                    priority = mSharedViewModel.parsePriority(mPriority),
                    description = mDescription
                )
                mToDoViewModel.insertData(newData)
                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
                // Navigate back
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}