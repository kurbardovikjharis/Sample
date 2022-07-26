package com.kurbardovikjharis.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurbardovikjharis.interactors.GetCommunityData
import com.kurbardovikjharis.interactors.GetTabs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CommunityViewModel @Inject constructor(
    private val getTabs: GetTabs,
    private val getCommunityData: GetCommunityData
) : ViewModel() {

    init {
        getTabs(Unit)
        viewModelScope.launch {
            getTabs.flow.collectLatest {
                getCommunityData(it.toIds())
            }
        }
    }

    val viewState: StateFlow<CommunityViewState> =
        combine(getTabs.flow, getCommunityData.flow) { tabs, data ->
            CommunityViewState(tabs = tabs, data = data)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CommunityViewState.Empty
        )
}