package com.lalala1235.rpgproject.commands

import com.lalala1235.rpgproject.utils.PDCManipulation
import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class TestItem: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player) return false

        val item = ItemStack(Material.NETHERITE_SWORD)
        val meta = item.itemMeta

        meta!!.lore = listOf(ChatColor.GRAY.toString() + "Damage: 10.0", ChatColor.GRAY.toString() + "Strength: +100.0")

        item.itemMeta = meta

        item.itemMeta = PDCManipulation.setTagString(item, "isCustomItem", "true")
        item.itemMeta = PDCManipulation.setTagDouble(item, "weaponStr", 100.0)
        item.itemMeta = PDCManipulation.setTagDouble(item, "weaponDmg", 10.0)

        sender.inventory.addItem(item)


        val item2 = ItemStack(Material.NETHERITE_CHESTPLATE)
        val meta2 = item2.itemMeta

        meta2!!.lore = listOf(ChatColor.GRAY.toString() + "Defense: 100.0")

        item2.itemMeta = meta2

        item2.itemMeta = PDCManipulation.setTagString(item2, "isCustomItem", "true")
        item2.itemMeta = PDCManipulation.setTagDouble(item2, "armorDef", 100.0)

        sender.inventory.addItem(item2)

        return true
    }
}