package com.example.demo

import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineSortingRepository

interface DemoRepository : CoroutineSortingRepository<DemoEntity, Int> {
    @Query("SELECT id, data, 'auto' AS tag FROM demo_entity")
    fun getAutoProjections(): Flow<AutoDto>

    @Query("SELECT id, data, 'custom' AS tag FROM demo_entity")
    fun getCustomProjections(): Flow<CustomDto>
}
