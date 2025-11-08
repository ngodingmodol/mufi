package ngoding.modol.mufi.tmdbapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditResponse(
    @SerialName("cast")
    val casts: List<Cast>
)

@Serializable
data class Cast(
    @SerialName("original_name")
    val originalName: String?,
    @SerialName("profile_path")
    val profilePath: String?
)