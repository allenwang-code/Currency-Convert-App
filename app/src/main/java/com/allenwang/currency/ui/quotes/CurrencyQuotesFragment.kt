package com.allenwang.currency.ui.quotes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.allenwang.currency.CurrencyApplication
import com.allenwang.currency.R
import com.allenwang.currency.data.unity.CurrencyQuote
import com.allenwang.currency.data.unity.SupportedCurrency
import com.allenwang.currency.ui.supported_currency.SupportedCurrencyFragment
import com.allenwang.currency.ui.supported_currency.SupportedCurrencyFragment.Companion.SUPPORTED_CURRENCY_REQUEST_KEY
import com.allenwang.currency.ui.supported_currency.SupportedCurrencyFragment.Companion.SUPPORTED_CURRENCY_SERIALIZABLE_KEY
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observable.just
import kotlinx.android.synthetic.main.fragment_convert_currency_list.*
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyQuotesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CurrencyQuotesViewModel
    private var adapter: CurrencyQuotesRecyclerViewAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CurrencyApplication).appComponent?.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[CurrencyQuotesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_convert_currency_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setupObservers()
        setUpUIEventsListener()

        viewModel.getCurrencyQuotes(chosen_currency_button.text.toString())
        viewModel.updateCurrencyQuotes(chosen_currency_button.text.toString(), 30 * 60)
    }

    private fun setUpUIEventsListener() {
        amount_editText.textChangeEvents()
            .debounce(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                adapter?.amountToConvert = it.text.toString().toInt()
                adapter?.notifyDataSetChanged()
            }, {
                amount_editText.setText("1")
                val errorMsg = context?.getString(R.string.inputError)
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
            })

        chosen_currency_button.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack("ConvertCurrencyFragment")
                .add(R.id.root_container, SupportedCurrencyFragment.newInstance())
                .commitAllowingStateLoss()
        }

        setFragmentResultListener(SUPPORTED_CURRENCY_REQUEST_KEY) { _, bundle ->
            val result = bundle.getSerializable(SUPPORTED_CURRENCY_SERIALIZABLE_KEY) as SupportedCurrency
            chosen_currency_button.text = result.currencyKey
            viewModel.updateDisposable?.dispose()
            viewModel.getCurrencyQuotes(chosen_currency_button.text.toString())
            viewModel.updateCurrencyQuotes(chosen_currency_button.text.toString(), 30 * 60)
        }
    }

    private fun setUpRecyclerView() {
        quotes_list.layoutManager = LinearLayoutManager(context)
        adapter = CurrencyQuotesRecyclerViewAdapter(emptyList(), 1)
        quotes_list.adapter = adapter
    }

    private fun setupObservers() {
        val quotesObserver = Observer<List<CurrencyQuote>> { quotes ->
            adapter?.values = quotes
            adapter?.notifyDataSetChanged()
        }

        val errorObserver = Observer<Throwable> { error ->
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }

        viewModel.error.observe(viewLifecycleOwner, errorObserver)
        viewModel.quotes.observe(viewLifecycleOwner, quotesObserver)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CurrencyQuotesFragment()
    }
}