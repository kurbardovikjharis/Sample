package com.kurbardovikjharis.interactors

import com.kurbardovikjharis.community.TabItem
import com.kurbardovikjharis.community.TabRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class GetTabs @Inject constructor(private val tabRepository: TabRepository) :
    SubjectInteractor<Unit, List<TabItem>>() {
    override fun createObservable(params: Unit): Flow<List<TabItem>> {
        return flow {
            emit(tabRepository.getTabs())
        }
    }
}