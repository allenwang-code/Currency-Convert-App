package com.allenwang.currency.ui.supported_currency

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.allenwang.currency.CurrencyApplication
import com.allenwang.currency.R
import com.allenwang.currency.data.unity.SupportedCurrency
import com.allenwang.currency.util.ClickListener
import com.allenwang.currency.util.RecyclerViewTouchListener
import kotlinx.android.synthetic.main.supported_currency_item_list.*
import javax.inject.Inject


class SupportedCurrencyFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SupportedCurrenciesViewModel

    private var adapter: SupportedCurrencyRecyclerViewAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CurrencyApplication).appComponent?.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[SupportedCurrenciesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.supported_currency_item_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setupObservers()
        viewModel.getCurrencies()
    }

    private fun setUpRecyclerView() {
        currency_list.layoutManager = LinearLayoutManager(context)
        adapter = SupportedCurrencyRecyclerViewAdapter(emptyList())
        currency_list.adapter = adapter
        currency_list.addOnItemTouchListener(
            RecyclerViewTouchListener(
                context,
                currency_list,
                object : ClickListener {
                    override fun onClick(view: View?, position: Int) {
                        goBackToQuoteFragment(position)
                    }

                    private fun goBackToQuoteFragment(position: Int) {
                        val bundle = Bundle()
                        bundle.putSerializable("supportedCurrency",
                            viewModel.supportedCurrency.value?.get(position)
                        )
                        setFragmentResult("supportedCurrencyKey", bundle)
                        activity?.supportFragmentManager?.popBackStack();
                    }

                    override fun onLongClick(view: View?, position: Int) {}
                })
        )
    }

    private fun setupObservers() {
        val quotesObserver = Observer<List<SupportedCurrency>> { quotes ->
            adapter?.values = quotes
            adapter?.notifyDataSetChanged()
        }

        val errorObserver = Observer<Throwable> { error ->
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }

        viewModel.error.observe(viewLifecycleOwner, errorObserver)
        viewModel.supportedCurrency.observe(viewLifecycleOwner, quotesObserver)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SupportedCurrencyFragment()
    }
}