package com.lalala1235.rpgproject.event.listeners

import com.lalala1235.rpgproject.Main
import com.lalala1235.rpgproject.event.customevent.CustomDamageEvent
import com.lalala1235.rpgproject.magic.DamageInfo
import com.lalala1235.rpgproject.utils.NBTManipulation
import com.lalala1235.rpgproject.utils.PDCManipulation
import org.bukkit.ChatColor
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class EventListeners: Listener {
    @EventHandler
    fun onDamage(e: EntityDamageByEntityEvent) {
        if(PDCManipulation.getTagString(e.damager, "isCustomMob")=="true") {
            Main.getPlugin().server.pluginManager.callEvent(
                CustomDamageEvent(
                    "Custom Damage",
                    DamageInfo(PDCManipulation.getTagDouble(e.damager, "infoDamage")!!, e.damager, null),
                    e.damager as LivingEntity,
                    e.entity as LivingEntity)
                )

            e.isCancelled = true
        }
    }

    @EventHandler
    fun onCustomDamage(e: CustomDamageEvent) {
        val damageInfo = e.damageInfo

        val scheduler = Main.getPlugin().server.scheduler

        e.entity.damage(1.0)
        e.entity.damage(e.damageInfo.damage, e.damageInfo.damager)

        val damageStand = e.entity.location.world!!.spawnEntity(e.entity.location, EntityType.ARMOR_STAND)

        damageStand.setGravity(false)
        damageStand.isInvulnerable = true
        damageStand.isCustomNameVisible = true
        damageStand.customName = ChatColor.GRAY.toString() + damageInfo.damage
        (damageStand as ArmorStand).isVisible = false

        scheduler.scheduleSyncDelayedTask(Main.getPlugin(), {
            damageStand.remove()
        }, 30L)
    }
}