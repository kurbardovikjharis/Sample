package com.kurbardovikjharis.community

import javax.annotation.concurrent.Immutable

@Immutable
data class CommunityViewState(
    val tabs: List<TabItem> = emptyList(),
    val data: HashMap<Int, List<Tile>> = hashMapOf()
) {
    companion object {
        val Empty = CommunityViewState()
    }
}