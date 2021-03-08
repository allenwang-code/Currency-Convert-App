package com.allenwang.currency.ui.quotes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.allenwang.currency.CurrencyApplication
import com.allenwang.currency.R
import com.allenwang.currency.data.unity.CurrencyQuote
import com.allenwang.currency.data.unity.SupportedCurrency
import com.allenwang.currency.ui.supported_currency.SupportedCurrencyFragment
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_convert_currency_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * A fragment representing a list of Items.
 */
class CurrencyQuotesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CurrencyQuotesViewModel

    private var adapter: CurrencyQuotesRecyclerViewAdapter? = null

    var list: List<CurrencyQuote>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as CurrencyApplication).appComponent?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[CurrencyQuotesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_convert_currency_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        setupObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quotes_list.layoutManager = LinearLayoutManager(context)
        adapter = CurrencyQuotesRecyclerViewAdapter(emptyList(), 1)
        quotes_list.adapter = adapter
        setupObservers()

        amount_editText.textChangeEvents()
            .debounce(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter?.amountToConvert = it.text.toString().toInt()
                adapter?.notifyDataSetChanged()
            }

        chosen_currency_button.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.addToBackStack("ConvertCurrencyFragment")
                ?.add(R.id.root_container, SupportedCurrencyFragment.newInstance())
                ?.commitAllowingStateLoss()
        }

        setFragmentResultListener("supportedCurrencyKey") { key, bundle ->
            val result = bundle.getSerializable("supportedCurrency") as SupportedCurrency
            chosen_currency_button.text = result.currencyKey
        }

    }

    private fun setupObservers() {
        viewModel.getCurrencyQuotes(chosen_currency_button.text.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter?.values = it
                adapter?.notifyDataSetChanged()
                list = it
            }, {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            })
    }

    companion object {
        @JvmStatic
        fun newInstance() = CurrencyQuotesFragment()
    }
}