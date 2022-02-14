package com.lalala1235.rpgproject.entity

import org.bukkit.entity.Player

abstract class CustomPlayer {
    abstract var hp: Double
    abstract var name: String

    abstract val me: Player
}