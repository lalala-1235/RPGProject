package com.lalala1235.rpgproject

import com.lalala1235.rpgproject.ability.Abilities
import com.lalala1235.rpgproject.ability.Ability
import com.lalala1235.rpgproject.commands.Test
import com.lalala1235.rpgproject.commands.TestItem
import com.lalala1235.rpgproject.event.listeners.EventListeners
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin




class Main: JavaPlugin() {
    companion object {
        private lateinit var plugin: JavaPlugin

        fun getPlugin(): JavaPlugin {
            return plugin
        }
    }

    override fun onEnable() {
        plugin = this

        val pm: PluginManager = Bukkit.getPluginManager()
        pm.registerEvents(EventListeners(), this)

        getCommand("test")!!.setExecutor(Test())
        getCommand("testitem")!!.setExecutor(TestItem())

        Ability.register(Abilities())

        println("RPGPlugin Enabled")
    }

    override fun onDisable() {
        println("RPGPlugin Disabled")
    }
}