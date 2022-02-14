package com.lalala1235.rpgproject.utils

import com.lalala1235.rpgproject.Main
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.persistence.PersistentDataType

class PDCManipulation {
    companion object {
        fun getTagString(e: Entity, key: String): String? {
            val namespacedKey = NamespacedKey(Main.getPlugin(), key)

            val container = e.persistentDataContainer

            return if(container.has(namespacedKey, PersistentDataType.STRING))
                e.persistentDataContainer.get(namespacedKey, PersistentDataType.STRING)
            else null
        }

        fun getTagDouble(e: Entity, key: String): Double? {
            val namespacedKey = NamespacedKey(Main.getPlugin(), key)

            val container = e.persistentDataContainer

            return if(container.has(namespacedKey, PersistentDataType.DOUBLE))
                e.persistentDataContainer.get(namespacedKey, PersistentDataType.DOUBLE)
            else null
        }

        fun setTagString(e: Entity, key: String, value: String) {
            val namespacedKey = NamespacedKey(Main.getPlugin(), key)

            e.persistentDataContainer.set(namespacedKey, PersistentDataType.STRING, value)
        }

        fun setTagDouble(e: Entity, key: String, value: Double) {
            val namespacedKey = NamespacedKey(Main.getPlugin(), key)

            e.persistentDataContainer.set(namespacedKey, PersistentDataType.DOUBLE, value)
        }
    }
}