package com.game.aristocrat.internet

data class GetGistDataDto (
    val allow_access: Boolean,
    val connection_url: String?
)