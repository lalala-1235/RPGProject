package com.lalala1235.rpgproject.event.customevent

import com.lalala1235.rpgproject.magic.DamageInfo
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class CustomDamageEvent(msg: String, val damageInfo: DamageInfo, val damager: LivingEntity, val entity: LivingEntity): Event() {
    companion object {
        @JvmStatic private val handlers: HandlerList = HandlerList()

        @JvmStatic fun getHandlerList(): HandlerList {
            return handlers
        }
    }
    private val message: String

    init {
        message = msg
    }

    fun getMessage(): String {
        return message
    }

    override fun getHandlers(): HandlerList {
        return getHandlerList()
    }
}