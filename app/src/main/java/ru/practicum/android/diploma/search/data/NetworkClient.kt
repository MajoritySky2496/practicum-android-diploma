package ru.practicum.android.diploma.search.data

import ru.practicum.android.diploma.filters.data.dto.models.AreasDto
import ru.practicum.android.diploma.search.data.dto.Response

interface
NetworkClient {
    suspend fun doRequest(dto: Any): Response
    suspend fun getAres(dto:Any):Response

}