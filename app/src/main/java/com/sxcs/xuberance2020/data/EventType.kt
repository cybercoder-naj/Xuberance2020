package com.sxcs.xuberance2020.data

enum class EventType {
    ENTRY, RECORDED, LIVE, GROUP;

    override fun toString() = when (this) {
        ENTRY -> "Entry-based"
        RECORDED -> "Broadcast"
        LIVE -> "Live"
        GROUP -> "Group"
    }
}