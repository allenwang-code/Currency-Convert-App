package com.allenwang.currency.ui.convert

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.allenwang.currency.CurrencyApplication
import com.allenwang.currency.R
import com.allenwang.currency.ui.convert.dummy.ConvertCurrencyViewModel
import com.allenwang.currency.ui.convert.dummy.DummyContent
import com.allenwang.currency.ui.supported_currency.SupportedCurrencyFragment
import kotlinx.android.synthetic.main.fragment_convert_currency_list.*
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class ConvertCurrencyFragment : Fragment() {

    @Inject
    lateinit var viewModel: ConvertCurrencyViewModel

    private var columnCount = 1


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CurrencyApplication).appComponent?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_convert_currency_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyconvertCurrencyRecyclerViewAdapter(DummyContent.ITEMS)
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chosen_currency_button.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.addToBackStack("ConvertCurrencyFragment")
                ?.add(R.id.root_container, SupportedCurrencyFragment.newInstance(1))
                ?.commitAllowingStateLoss()
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ConvertCurrencyFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}