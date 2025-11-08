package ngoding.modol.mufi.ui.navtype

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class ListNavType<T>(
    private val serializer: KSerializer<T>
) : NavType<List<T>>(isNullableAllowed = false) {

    override fun put(bundle: Bundle, key: String, value: List<T>) {
        bundle.putString(key, Json.encodeToString(ListSerializer(serializer), value))
    }

    override fun get(bundle: Bundle, key: String): List<T>? {
        val json = bundle.getString(key) ?: return null
        return Json.decodeFromString(ListSerializer(serializer), json)
    }

    override fun parseValue(value: String): List<T> {
        val decoded = Uri.decode(value)
        return Json.decodeFromString(ListSerializer(serializer), decoded)
    }

    override fun serializeAsValue(value: List<T>): String {
        val json = Json.encodeToString(ListSerializer(serializer), value)
        return Uri.encode(json)
    }
}

inline fun <reified T> listNavType(): ListNavType<T> {
    return ListNavType(serializer = kotlinx.serialization.serializer())
}
