package com.kurbardovikjharis.interactors

import com.kurbardovikjharis.community.CommunityRepository
import com.kurbardovikjharis.community.Tile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class GetCommunityData @Inject constructor(private val communityRepository: CommunityRepository) :
    SubjectInteractor<List<Int>, HashMap<Int, List<Tile>>>() {

    override fun createObservable(params: List<Int>): Flow<HashMap<Int, List<Tile>>> {
        return flow {
            emit(communityRepository.getTiles(params))
        }
    }
}