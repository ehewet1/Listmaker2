package com.raywenderlich.listmaker.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.listmaker.R
import com.raywenderlich.listmaker.TaskList
import com.raywenderlich.listmaker.databinding.MainFragmentBinding

class MainFragment : Fragment(),
ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListner {
    private lateinit var binding: MainFragmentBinding
    lateinit var clickListener: MainFragmentInteractionListner

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    interface MainFragmentInteractionListner{
        fun listItemTapped(list: TaskList)
    }

    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View {
        binding= MainFragmentBinding.inflate(inflater, container,
            false)

        //1
        binding.listRecyclerView.layoutManager=LinearLayoutManager(requireContext())

        //2
        //binding.listRecyclerView.adapter=ListSelectionRecyclerViewAdapter()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),
        MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(requireActivity())))
            .get(MainViewModel::class.java)

        val recyclerViewAdapter=ListSelectionRecyclerViewAdapter(viewModel.lists,this)

        binding.listRecyclerView.adapter=recyclerViewAdapter

        viewModel.onListAdded={
            recyclerViewAdapter.listsUpdated()
        }
    }

    override fun listItemClicked(list: TaskList) {
        clickListener.listItemTapped(list)
    }
}