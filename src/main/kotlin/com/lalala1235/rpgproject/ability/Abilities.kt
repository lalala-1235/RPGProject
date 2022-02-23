package com.lalala1235.rpgproject.ability

import org.bukkit.entity.Entity

class Abilities: AbilityExecutors {
    @AbilityExecutor("tpup5", AbilityParameter.EFFECTED_ENTITY_ONE)
    fun teleport(en: Entity) {
        val newLoc = en.location
        newLoc.y = en.location.y + 5

        en.teleport(newLoc)
    }
}