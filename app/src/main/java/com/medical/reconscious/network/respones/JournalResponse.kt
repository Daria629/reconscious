package com.medical.reconscious.network.respones

data class JournalResponse(
    var id: Int?,
    var created_at: String?,
    var feeling: String?,
    var is_locked: Boolean,
    var title: String?,
    var body: String?,
    var appointment: Int?
)
