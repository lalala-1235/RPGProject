package com.lalala1235.rpgproject.commands

import com.lalala1235.rpgproject.entity.TestZombie
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Test: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player) return false

        TestZombie(sender.location)

        return true
    }
}