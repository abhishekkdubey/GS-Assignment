package com.goldmansanch.ui.home

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.goldmansanch.util.Constants
import com.goldmansanch.util.DateUtil
import com.goldmansanch.util.StringUtil
import com.golmansanch.R
import com.golmansanch.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


@AndroidEntryPoint
class HomeFragment : Fragment()
{

    private val homeViewModel by viewModels<HomeViewModel>()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.dateEdit.setOnClickListener { openDatePicker() }
        binding.contentPanel.favIcon.setOnClickListener {
            homeViewModel.markFavourite(
                !(homeViewModel.apod.value?.isFav ?: false)
            )
        }

        binding.swipeToRefresh.setColorSchemeResources(
            R.color.design_default_color_primary,
            R.color.design_default_color_primary_dark,
            R.color.design_default_color_secondary
        )
        binding.swipeToRefresh.setOnRefreshListener {
            homeViewModel.fetchAPOD(homeViewModel.getTodayDate())
            swipeToRefresh.isRefreshing = false
        }


        binding.dateEdit.isEnabled = false
        homeViewModel.apod.observe(viewLifecycleOwner, { apod ->
            binding.contentPanel.data = apod
            loadImage(apod.url, apod.mediaType)
            binding.displayDate.text = getString(R.string.apod_text, apod.date)
            binding.dateEdit.isEnabled = true
        })

        binding.contentPanel.play.setOnClickListener {
            val url = homeViewModel.apod.value?.url
            if (url != null) launchVideo(url) else Snackbar.make(
                binding.root,
                getString(R.string.video_load_error),
                Snackbar.LENGTH_SHORT
            ).show()
        }
        homeViewModel.error.observe(viewLifecycleOwner, {
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.hideShimmer()
            binding.shimmerFrameLayout.visibility = View.GONE
            binding.dateEdit.isEnabled = true
            when (it.first)
            {
                true ->
                {
                    val error = it.second
                    binding.contentPanel.cardView.visibility = View.GONE
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = error.message
                    binding.errorText.setCompoundDrawablesWithIntrinsicBounds(
                        0,
                        error.resIcon,
                        0,
                        0
                    )

                }
                false ->
                {
                    binding.contentPanel.cardView.visibility = View.VISIBLE
                    binding.errorText.visibility = View.GONE

                }

            }
        })


        return root
    }

    private fun launchVideo(videoURL: String)
    {

        try
        {
            val packageManager = activity!!.packageManager
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(videoURL))
            intent.putExtra("force_fullscreen", true)
            intent.setPackage("com.google.android.youtube")
            if (intent.resolveActivity(packageManager) != null)
            {
                startActivity(intent)
            } else
            {
                Snackbar.make(
                    binding.root,
                    getString(R.string.youtube_not_installed), Snackbar.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception)
        {
            Snackbar.make(
                binding.root,
                getString(R.string.youtube_not_installed), Snackbar.LENGTH_SHORT
            ).show()
        }

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

    private fun loadImage(url: String?, mediaType: String?)
    {
        val urlToLoad = if (mediaType == Constants.MEDIA_TYPE_VIDEO)
        {
            StringUtil.getYoutubeVideoThumbnailURL(url)
        } else
        {
            url
        }
        Glide.with(this).load(urlToLoad)
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder_error).centerInside()
            )
            .into(binding.contentPanel.imageThumbnail)
        binding.contentPanel.imageThumbnail.adjustViewBounds = true

    }

    private fun openDatePicker()
    {
        val cal = Calendar.getInstance()

        val datePicker = DatePickerDialog(
            requireContext(),
            dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.maxDate =
            Calendar.getInstance(TimeZone.getTimeZone(Constants.TIME_ZONE)).timeInMillis
        datePicker.datePicker.minDate = Constants.MIN_CALENDER_TIME
        datePicker.show()
    }

    private val dateSetListener =
        DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            val cal = Calendar.getInstance()

            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val date = DateUtil.getDate(cal)
            if (homeViewModel.isValidDate(cal.timeInMillis)){
                fetchAPODFor(date)
            }else{
                Snackbar.make(
                    binding.root,
                    getString(R.string.must_be_valid_date),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    private fun fetchAPODFor(date: String)
    {
        binding.dateEdit.isEnabled = false
        binding.displayDate.text = getString(R.string.apod_text, date)
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.shimmerFrameLayout.startShimmer()
        binding.contentPanel.cardView.visibility = View.GONE
        homeViewModel.fetchAPOD(date)
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}