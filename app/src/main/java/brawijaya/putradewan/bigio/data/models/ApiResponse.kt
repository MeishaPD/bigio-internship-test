package brawijaya.putradewan.bigio.data.models

data class ApiResponse<T>(
    val info: Info,
    val results: List<T>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)