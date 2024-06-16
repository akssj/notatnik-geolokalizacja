package com.example.notatnik_geolokalizacja.ui.fragment

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.notatnik_geolokalizacja.R
import com.example.notatnik_geolokalizacja.databinding.FragmentCreateNoteBinding
import com.example.notatnik_geolokalizacja.database.model.Notes
import com.example.notatnik_geolokalizacja.viewmodel.NotesViewModel
import java.util.Date
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.notatnik_geolokalizacja.worker.NotificationWorker
import java.util.concurrent.TimeUnit

/**
 * Fragment for creating a new note.
 */
class CreateNoteFragment : Fragment() {

    private lateinit var binding: FragmentCreateNoteBinding
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double? = null
    private var longitude: Double? = null

    /**
     * Create fragment user interface view.
     * @param inflater object that can be used to inflate views in the fragment.
     * @param container parent view.
     * @param savedInstanceState
     * @return returns the View for the fragments ui.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLocationPermission()

        binding.saveNote.setOnClickListener {
            createNotes(it)
        }

        return binding.root
    }

    /**
     * Creates a new note and saves it to the database.
     * @param view current view.
     */
    private fun createNotes(view: View) {
        val notes = binding.editTextNotes.text.toString()
        val currentDate = DateFormat.format("dd MM yyyy", Date()).toString()

        val data = Notes(
            null,
            notes = notes,
            date = currentDate,
            latitude = latitude,
            longitude = longitude
        )

        viewModel.addNotes(data)

        scheduleNotification()
        Navigation.findNavController(view).navigate(R.id.action_createNoteFragment_to_homeFragment)
    }
    /**
     * Checks for location permission and requests it if not already granted.
     */
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            getLastLocation()
        }
    }

    /**
     * Gets the last known location of the device.
     */
    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                }
            }
    }
    /**
     * Schedules a notification to be shown after a delay.
     */
    private fun scheduleNotification() {
        val notificationWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .build()

        WorkManager.getInstance(requireContext()).enqueue(notificationWorkRequest)
    }
}
