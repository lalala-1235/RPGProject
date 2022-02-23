package com.lalala1235.rpgproject.entity

import com.lalala1235.rpgproject.damage.DamageInfo
import org.bukkit.Location
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity

abstract class Monster {
    abstract val type: EntityType
    abstract var hp: Double
    abstract var name: String

    lateinit var me: LivingEntity

    abstract var damageInfo: DamageInfo

    open fun spawn(loc: Location): LivingEntity {
        if(type.isAlive) me = loc.world!!.spawnEntity(loc, type) as LivingEntity
        else throw Exception("Spawning non-alive monster is not allowed")

        return me
    }
}