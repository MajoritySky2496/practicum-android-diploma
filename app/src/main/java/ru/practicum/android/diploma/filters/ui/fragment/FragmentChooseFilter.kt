package ru.practicum.android.diploma.filters.ui.fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterSelectionBinding
import ru.practicum.android.diploma.filters.domain.models.Areas
import ru.practicum.android.diploma.filters.domain.models.Industries
import ru.practicum.android.diploma.filters.domain.models.Industry
import ru.practicum.android.diploma.filters.domain.models.Region
import ru.practicum.android.diploma.filters.presentation.FiltersViewModel
import ru.practicum.android.diploma.filters.presentation.models.ScreenState
import ru.practicum.android.diploma.filters.ui.adapter.FilterSelectionClickListener
import ru.practicum.android.diploma.filters.ui.adapter.FiltersAdapter
import ru.practicum.android.diploma.filters.ui.fragment.FragmentSettingFilters.Companion.SCREEN
import ru.practicum.android.diploma.util.BindingFragment

class FragmentChooseFilter:BindingFragment<FragmentFilterSelectionBinding>() {

    private val viewModel by viewModel<FiltersViewModel>()
    private var adapter:FiltersAdapter? = null
    private var screen:String? =null
    private val areaList = mutableListOf<Region>()
    private val industryList = mutableListOf<Industries>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFilterSelectionBinding {
        return FragmentFilterSelectionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getScreenStateLiveData().observe(requireActivity()){chooseScreen(it)}
        screen = arguments?.getString(SCREEN)
        viewModel.setScreen(screen!!)
        initAdapter()
        back()
        binding.recyclerViewFilters.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFilters.adapter = adapter
        applyButtom()

    }

    private fun initAdapter(){
        adapter = FiltersAdapter(object: FilterSelectionClickListener {
            override fun onClickRegion(model: Region?, isChecked: Boolean) {
                when(isChecked){
                    true -> areaList.add(model!!)
                    false -> areaList.remove(model)
                }
                binding.buttonApply.visibility = View.GONE
                areaList.takeIf { it.isNotEmpty()}?.let{ binding.buttonApply.visibility = View.VISIBLE}
                Log.d("Area", "$areaList")
            }
            override fun onClickIndustries(model: Industries?, isChecked:Boolean) {
                when(isChecked){
                    true -> industryList.add(model!!)
                    false -> industryList.remove(model)
                }
                binding.buttonApply.visibility = View.GONE
                industryList.takeIf { it.isNotEmpty()}?.let{ binding.buttonApply.visibility = View.VISIBLE}

            }
            override fun onClickCountry(model: Areas?) {
                viewModel.addCountry(model!!)
                findNavController().navigateUp()

            }
        })
    }
    private fun applyButtom(){
        binding.buttonApply.setOnClickListener {
            areaList.takeIf { it.isNotEmpty() }?.let {
                viewModel.addArea(it)
            }
            industryList.takeIf { it.isNotEmpty() }?.let {
                viewModel.addIndustries(it)
            }
            findNavController().navigateUp()
        }
    }
    private fun back(){
        binding.arrowback.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun chooseScreen(state:ScreenState){
        when(state){
            is ScreenState.showIndustriesScreen -> showIndustriesScreen(state.industryList)
            is ScreenState.showAreasScreen -> showAreasScreen(state.areasList)
            is ScreenState.showCountriesScreen -> {
                showCountriesScreen(state.countriesList)
            }
        }
    }
    private fun showCountriesScreen(countriesList:List<Areas>){
        adapter?.setCountry(countriesList)
        binding.searchEditText.visibility = View.GONE
        binding.chooseTextview.text = requireActivity().getText(R.string.choose_of_country)

    }
    private fun showIndustriesScreen(industryList:List<Industries>){
        adapter?.setIndustrie(industryList)
        binding.recyclerViewFilters.visibility = View.VISIBLE
        binding.chooseTextview.text = requireActivity().getText(R.string.choose_of_industry)
        binding.searchEditText.setHint(requireActivity().getText(R.string.choose_of_industry))
        binding.searchEditText.visibility = View.VISIBLE
    }
    private fun showAreasScreen(areas:List<Region>){
        adapter?.setRegion(areas)
        binding.recyclerViewFilters.visibility = View.VISIBLE
        binding.chooseTextview.text = requireActivity().getText(R.string.choose_of_region)
        binding.searchEditText.setHint(requireActivity().getText(R.string.choose_of_region))
        binding.searchEditText.visibility = View.VISIBLE
    }
}