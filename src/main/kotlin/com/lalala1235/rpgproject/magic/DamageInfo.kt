package com.lalala1235.rpgproject.magic

import org.bukkit.entity.Entity
import javax.annotation.Nullable

data class DamageInfo(
    val damage: Double,
    val damager: Entity,
    @Nullable val damageTag: ArrayList<String>?
)
