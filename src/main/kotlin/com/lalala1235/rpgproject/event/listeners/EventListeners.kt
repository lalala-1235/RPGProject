package com.lalala1235.rpgproject.event.listeners

import com.lalala1235.rpgproject.Main
import com.lalala1235.rpgproject.event.customevent.CustomDamageEvent
import com.lalala1235.rpgproject.magic.DamageInfo
import com.lalala1235.rpgproject.utils.DamageCalculator
import com.lalala1235.rpgproject.utils.PDCManipulation
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerJoinEvent

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

        if(PDCManipulation.getTagString(e.damager, "isCustomPlayer")=="true") {
            Main.getPlugin().server.pluginManager.callEvent(
                CustomDamageEvent(
                    "Custom Damage",
                    DamageInfo(DamageCalculator.getDamage(PDCManipulation.getTagDouble(e.damager, "infoBaseDamage")!!, PDCManipulation.getTagDouble(e.damager, "infoStr")!!), e.damager, null),
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

        e.entity.damage(e.damageInfo.damage)

        val newLoc = Location(e.entity.location.world, e.entity.location.x, e.entity.location.y - 0.7, e.entity.location.z)

        val damageStand = e.entity.location.world!!.spawnEntity(newLoc, EntityType.ARMOR_STAND)

        damageStand.setGravity(false)
        damageStand.isInvulnerable = true
        damageStand.isCustomNameVisible = true
        damageStand.customName = ChatColor.GRAY.toString() + damageInfo.damage
        (damageStand as ArmorStand).isVisible = false

        scheduler.scheduleSyncDelayedTask(Main.getPlugin(), {
            damageStand.remove()
        }, 30L)
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if(PDCManipulation.getTagString(e.player, "isCustomPlayer")=="true") return

        PDCManipulation.setTagString(e.player, "isCustomPlayer", "true")
        PDCManipulation.setTagDouble(e.player, "infoBaseDamage", 1.0)
        PDCManipulation.setTagDouble(e.player, "infoStr", 100.0)
    }
}