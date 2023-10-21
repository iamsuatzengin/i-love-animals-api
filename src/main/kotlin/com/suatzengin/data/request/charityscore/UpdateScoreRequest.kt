package com.suatzengin.data.request.charityscore

import java.util.UUID

data class UpdateScoreRequest(
    val userId: UUID,
    val point: Long,
)
