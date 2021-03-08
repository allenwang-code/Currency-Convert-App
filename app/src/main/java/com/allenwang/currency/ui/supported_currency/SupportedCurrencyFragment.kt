package com.allenwang.currency.ui.supported_currency

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.allenwang.currency.CurrencyApplication
import com.allenwang.currency.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.supported_currency_item_list.*
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class SupportedCurrencyFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SupportedCurrenciesViewModel

    private var adapter: MySupportedCurrencyRecyclerViewAdapter? = null

    private var columnCount = 1


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CurrencyApplication).appComponent?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[SupportedCurrenciesViewModel::class.java]
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.supported_currency_item_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currency_list. layoutManager = LinearLayoutManager(context)
        adapter = MySupportedCurrencyRecyclerViewAdapter(emptyList())
        currency_list.adapter = adapter
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getCurrencies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter?.values = it
                adapter?.notifyDataSetChanged()
            }, {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            })
    }

        companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            SupportedCurrencyFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}