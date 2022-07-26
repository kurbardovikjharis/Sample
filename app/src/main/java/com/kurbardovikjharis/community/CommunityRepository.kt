package com.kurbardovikjharis.community

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CommunityRepository @Inject constructor() {

    private val data = hashMapOf<Int, List<Tile>>()

    suspend fun getTiles(params: List<Int>): HashMap<Int, List<Tile>> {
        delay(200)
        params.forEach {
            when (it) {
                0 -> data[0] = listOf(Tile("tab 1 tile1"))
                1 -> data[1] = listOf(Tile("tab 2 tile1"), Tile("tab 2 tile2"))
                else -> data[2] =
                    listOf(Tile("tab 3 tile1"), Tile("tab 3 tile2"), Tile("tab 3 tile3"))
            }
        }

        return data
    }
}

data class Tile(val title: String)