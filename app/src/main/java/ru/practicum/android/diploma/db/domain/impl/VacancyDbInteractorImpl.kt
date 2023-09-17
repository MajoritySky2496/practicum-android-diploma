package ru.practicum.android.diploma.db.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.db.data.converter.VacancyDbConverter
import ru.practicum.android.diploma.db.data.entity.VacancyEntity
import ru.practicum.android.diploma.db.domain.api.VacancyDbInteractor
import ru.practicum.android.diploma.db.domain.api.VacancyDbRepository
import ru.practicum.android.diploma.search.domain.models.Vacancy

class VacancyDbInteractorImpl(
    private val vacancyDbRepository: VacancyDbRepository,
    private val vacancyDbConverter: VacancyDbConverter,
) :
    VacancyDbInteractor {
    override suspend fun insertVacancy(vacancy: Vacancy) {
        vacancyDbRepository.insertVacancy(vacancyDbConverter.map(vacancy))
    }

    override suspend fun deleteVacancy(vacancy: Vacancy) {
        vacancyDbRepository.deleteVacancy(vacancyDbConverter.map(vacancy))
    }

    override suspend fun getFavouriteVacancy(): Flow<List<VacancyEntity>> {
        return vacancyDbRepository.getFavouriteVacancy()
    }
}