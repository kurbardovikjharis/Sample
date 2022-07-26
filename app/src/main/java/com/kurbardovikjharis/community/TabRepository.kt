package com.kurbardovikjharis.community

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TabRepository @Inject constructor() {

    suspend fun getTabs(): List<TabItem> {
        delay(200)
        return listOf(TabItem("tab1", 0), TabItem("tab2", 1), TabItem("tab3", 2))
    }
}

data class TabItem(val name: String, val id: Int)

fun List<TabItem>.toIds(): List<Int> {
    val list = mutableListOf<Int>()

    forEach {
        list.add(it.id)
    }

    return list
}
