package ru.practicum.android.diploma.filters.domain
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.models.Areas
import ru.practicum.android.diploma.filters.domain.models.Filters
import ru.practicum.android.diploma.filters.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface FiltersRepository {
    suspend fun getAreas(): Flow<Resource<List<Areas>>>
    suspend fun getFilters():Flow<Filters>
    suspend fun writeFilters(filters: Filters)
    suspend fun getIndustries():Flow<Resource<List<Industry>>>
}