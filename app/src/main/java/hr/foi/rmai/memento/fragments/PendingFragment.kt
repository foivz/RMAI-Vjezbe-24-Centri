package hr.foi.rmai.memento.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hr.foi.rmai.memento.R
import hr.foi.rmai.memento.helpers.MockDataLoader

class PendingFragment : Fragment() {
    private val mockTasks = MockDataLoader.getDemoData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mockTasks.forEach { task ->
            Log.i("MOCK_PENDING_TASKS", task.name)
        }

        return inflater.inflate(R.layout.fragment_pending, container, false)
    }
}

