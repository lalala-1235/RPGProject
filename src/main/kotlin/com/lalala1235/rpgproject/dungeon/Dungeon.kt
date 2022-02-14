package com.lalala1235.rpgproject.dungeon

import org.bukkit.Location

abstract class Dungeon {
    abstract val name: String
    abstract val desc: ArrayList<String>
    abstract val location: ArrayList<Location>

    abstract fun start()
}