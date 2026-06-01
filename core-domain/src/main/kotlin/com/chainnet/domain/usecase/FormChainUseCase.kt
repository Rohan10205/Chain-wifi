package com.chainnet.domain.usecase

import com.chainnet.domain.repository.NetworkRepository

class FormChainUseCase(private val networkRepository: NetworkRepository) {
    suspend operator fun invoke() {
        networkRepository.startChain()
    }
}
