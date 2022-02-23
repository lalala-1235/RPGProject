package com.lalala1235.rpgproject.entity

import com.lalala1235.rpgproject.damage.DamageInfo
import com.lalala1235.rpgproject.utils.PDCManipulation
import org.bukkit.Location
import org.bukkit.entity.EntityType

class TestZombie constructor(location: Location) : Monster() {

    override val type: EntityType = EntityType.ZOMBIE
    override var hp: Double = 20.0
    override var name: String = "Test"

    override lateinit var damageInfo: DamageInfo

    init {
        me = spawn(location)

        damageInfo = DamageInfo(1.0, me, null)

        PDCManipulation.setTagString(me, "isCustomMob", "true")
        PDCManipulation.setTagDouble(me, "infoDamage", damageInfo.damage)
    }
}