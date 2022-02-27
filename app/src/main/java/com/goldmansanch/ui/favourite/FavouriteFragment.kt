package com.goldmansanch.ui.favourite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goldmansanch.data.APODItem
import com.goldmansanch.ui.favourite.adapter.APODListAdapter
import com.golmansanch.R
import com.golmansanch.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.IndexOutOfBoundsException

@AndroidEntryPoint
class FavouriteFragment : Fragment()
{
    private val TAG = "FavouriteFragment"

    private val favouritesViewModel by viewModels<FavouritesViewModel>()

    private var _binding: FragmentFavouritesBinding? = null


    private val binding get() = _binding!!


    private lateinit var apodListAdapter: APODListAdapter

    private var adapterListData : MutableList<APODItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)


        apodListAdapter = APODListAdapter(adapterListData, favClick= this::favClick)
        val layoutManager = object :LinearLayoutManager(requireActivity()) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams
            {
                return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

            }

            override fun onLayoutChildren(
                recycler: RecyclerView.Recycler?,
                state: RecyclerView.State?
            )
            {
                try
                {
                    super.onLayoutChildren(recycler, state)
                } catch (e: IndexOutOfBoundsException)
                {
                    Log.e(TAG, "meet a IOOBE in RecyclerView")
                }
            }
        }
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = apodListAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favouritesViewModel.getFavouriteAPODLive().observe(viewLifecycleOwner, {
                    binding.shimmerFrameLayout.stopShimmer()
                    binding.shimmerFrameLayout.hideShimmer()
                    binding.shimmerFrameLayout.visibility = View.GONE

                    if (!it.isNullOrEmpty())
                    {

                        val list= it
                        adapterListData.clear()
                        adapterListData.addAll(list)
                        binding.recyclerView.adapter?.notifyItemRangeChanged(0, adapterListData.size)
                    }else{
                        binding.recyclerView.visibility = View.GONE
                        binding.errorText.visibility = View.VISIBLE
                        binding.errorText.text = getString(R.string.no_favoutire)
                    }
                })
            }
        }
    }

    private fun favClick(apodItem: APODItem){

        favouritesViewModel.removeFavouriteAPOD(apodItem)
    }


    override fun onResume()
    {
        super.onResume()
        binding.shimmerFrameLayout.startShimmer()
    }

    override fun onPause()
    {
        binding.shimmerFrameLayout.stopShimmer()
        super.onPause()
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

}