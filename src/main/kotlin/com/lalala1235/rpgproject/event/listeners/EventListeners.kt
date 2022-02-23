package com.lalala1235.rpgproject.event.listeners

import org.bukkit.Material

import com.lalala1235.rpgproject.Main
import com.lalala1235.rpgproject.ability.Ability
import com.lalala1235.rpgproject.ability.AbilityParameter
import com.lalala1235.rpgproject.event.customevent.CustomDamageEvent
import com.lalala1235.rpgproject.damage.DamageInfo
import com.lalala1235.rpgproject.damage.DamageCalculator
import com.lalala1235.rpgproject.utils.PDCManipulation
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.inventory.*
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerJoinEvent

class EventListeners: Listener {
    @EventHandler
    fun onDamage(e: EntityDamageByEntityEvent) {
        if(PDCManipulation.getTagString(e.damager, "isCustomMob")=="true") {
            if(PDCManipulation.getTagString(e.entity, "isCustomPlayer")!="true") return

            Main.getPlugin().server.pluginManager.callEvent(
                CustomDamageEvent(
                    "Custom Damage",
                    DamageInfo(DamageCalculator.getDamage(PDCManipulation.getTagDouble(e.damager, "infoDamage")!!,0.0, PDCManipulation.getTagDouble(e.entity, "infoDef")!!), e.damager, null),
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
        PDCManipulation.setTagDouble(e.player, "infoDef", 0.0)
        println(PDCManipulation.getTagDouble(e.player, "infoDef"))

        if(PDCManipulation.getTagString(e.player, "isCustomPlayer")=="true") return

        PDCManipulation.setTagString(e.player, "isCustomPlayer", "true")
        PDCManipulation.setTagDouble(e.player, "infoBaseDamage", 1.0)
        PDCManipulation.setTagDouble(e.player, "infoStr", 100.0)
        PDCManipulation.setTagDouble(e.player, "infoDef", 0.0)
    }





    @EventHandler
    fun onPlayerChangeItem(e: PlayerItemHeldEvent) {
        val item = e.player.inventory.getItem(e.newSlot)

        if(item==null) {
            PDCManipulation.setTagDouble(e.player, "infoBaseDamage", 1.0)
            PDCManipulation.setTagDouble(e.player, "infoStr", 100.0)
            return
        }

        if(PDCManipulation.getTagString(item, "isCustomItem")!="true") {
            PDCManipulation.setTagDouble(e.player, "infoBaseDamage", 1.0)
            PDCManipulation.setTagDouble(e.player, "infoStr", 100.0)
        } else {
            val str = PDCManipulation.getTagDouble(item, "weaponStr") ?: return
            val dmg = PDCManipulation.getTagDouble(item, "weaponDmg") ?: return

            PDCManipulation.setTagDouble(e.player, "infoBaseDamage", dmg)
            PDCManipulation.setTagDouble(e.player, "infoStr", 100.0 + str)
        }

    }





    @EventHandler
    fun onPlayerInventoryDragEvent(e: InventoryDragEvent) {
        if(e.inventory.type != InventoryType.PLAYER) return

        val p = e.viewers[0] ?: return

        val item = p.inventory.itemInMainHand

        if(PDCManipulation.getTagString(item, "isCustomItem")!="true") {
            PDCManipulation.setTagDouble(p, "infoBaseDamage", 1.0)
            PDCManipulation.setTagDouble(p, "infoStr", 100.0)
        } else {
            println(PDCManipulation.getTagDouble(item, "weaponStr"))
            println(PDCManipulation.getTagDouble(item, "weaponDmg"))
            val str = PDCManipulation.getTagDouble(item, "weaponStr") ?: return
            val dmg = PDCManipulation.getTagDouble(item, "weaponDmg") ?: return

            PDCManipulation.setTagDouble(p, "infoBaseDamage", dmg)
            PDCManipulation.setTagDouble(p, "infoStr", 100.0 + str)
        }
    }





    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        if(e.clickedInventory==null) return

        if(e.clickedInventory!!.type != InventoryType.PLAYER) return

        //마우스로 클릭해서 가져오기
        if(e.action==InventoryAction.PICKUP_ALL || e.action==InventoryAction.PICKUP_ONE || e.action==InventoryAction.PICKUP_HALF || e.action==InventoryAction.PICKUP_SOME) {
            if(e.clickedInventory!!.viewers.isEmpty()) return
            if(e.clickedInventory!!.viewers[0].type!=EntityType.PLAYER) return

            val p = e.clickedInventory!!.viewers[0]

            if(e.slotType!= InventoryType.SlotType.ARMOR) return

            if(p.inventory.getItem(e.slot)==null) return
            if(p.inventory.getItem(e.slot)!!.type==Material.AIR) return

            if(!p.inventory.getItem(e.slot)!!.hasItemMeta()) return

            if (PDCManipulation.getTagString(p.inventory.getItem(e.slot)!!, "isCustomItem")!="true") return

            PDCManipulation.setTagDouble(p, "infoDef",
                (PDCManipulation.getTagDouble(p, "infoDef") ?: return)
                - (PDCManipulation.getTagDouble(p.inventory.getItem(e.slot)!!, "armorDef") ?: return))

            println(PDCManipulation.getTagDouble(p, "infoDef"))
        }

        //마우스로 클릭해서 내려놓기
        if(e.action==InventoryAction.PLACE_ALL || e.action==InventoryAction.PLACE_ONE || e.action==InventoryAction.PLACE_SOME) {
            if(e.clickedInventory!!.viewers.isEmpty()) return
            if(e.clickedInventory!!.viewers[0].type!=EntityType.PLAYER) return

            val p = e.clickedInventory!!.viewers[0]

            if(p.itemOnCursor.type==Material.AIR) return
            if(e.slotType!= InventoryType.SlotType.ARMOR) return
            if(!p.itemOnCursor.hasItemMeta()) return

            if (PDCManipulation.getTagString(p.itemOnCursor, "isCustomItem")!="true") return

            PDCManipulation.setTagDouble(p, "infoDef",
                (PDCManipulation.getTagDouble(p, "infoDef") ?: return)
                        + (PDCManipulation.getTagDouble(p.itemOnCursor, "armorDef") ?: return))
            println(PDCManipulation.getTagDouble(p, "infoDef"))
        }

        //숫자 키를 이용해서 핫바와 스왑
        /* if(e.action==InventoryAction.HOTBAR_SWAP) {

        } */

    }




    @EventHandler
    fun onRightClick(e: PlayerInteractEvent) {
        if(e.action != Action.RIGHT_CLICK_AIR && e.action != Action.RIGHT_CLICK_BLOCK) return

        val abilityName = PDCManipulation.getTagString(e.player.inventory.itemInMainHand, "ability") ?: return
        val paramInfo = Ability.getParams(abilityName) ?: return

        when(paramInfo) {
            AbilityParameter.EFFECTED_ENTITY_ONE -> {
                Ability.executeAbility(abilityName, e.player)
            }
        }
    }


}