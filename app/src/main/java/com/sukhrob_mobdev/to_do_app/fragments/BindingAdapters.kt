package com.sukhrob_mobdev.to_do_app.fragments

import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.sukhrob_mobdev.to_do_app.R
import com.sukhrob_mobdev.to_do_app.data.models.Priority
import com.sukhrob_mobdev.to_do_app.data.models.ToDoData
import com.sukhrob_mobdev.to_do_app.fragments.list.ListFragmentDirections

object BindingAdapters {
    @BindingAdapter("android:navigateToAddFragment")
    @JvmStatic
    fun navigateToAddFragment(imageView: ImageView, navigate: Boolean) {
        imageView.setOnClickListener {
            if (navigate) {
                imageView.findNavController().navigate(R.id.action_listFragment_to_addFragment)
            }
        }
    }

    @BindingAdapter("android:navigateToUpdateFragment")
    @JvmStatic
    fun navigateToUpdateFragment(view: View, toDoData: ToDoData) {
        view.setOnClickListener {
            view.findNavController().navigate(ListFragmentDirections.actionListFragmentToUpdateFragment(toDoData))
        }
    }

    @BindingAdapter("android:emptyDatabase")
    @JvmStatic
    fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
        if (emptyDatabase.value == true) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    @BindingAdapter("android:parsePriorityToInt")
    @JvmStatic
    fun parsePriorityToInt(spinner: Spinner, priority: Priority) {
        return when (priority) {
            Priority.HIGH -> spinner.setSelection(0)
            Priority.MEDIUM -> spinner.setSelection(1)
            Priority.LOW -> spinner.setSelection(2)
        }
    }

    @BindingAdapter("android:parsePriorityColor")
    @JvmStatic
    fun indicatorColor(cardView: CardView, priority: Priority) {
        when (priority) {
            Priority.HIGH -> cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    cardView.context,
                    R.color.red
                )
            )
            Priority.MEDIUM -> cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    cardView.context,
                    R.color.yellow
                )
            )
            Priority.LOW -> cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    cardView.context,
                    R.color.green
                )
            )
        }
    }

}