package model

@kotlinx.serialization.Serializable
data class ResponseNaverIndex(
    val my: My
) {
    @kotlinx.serialization.Serializable
    data class My(
        val bookmarkSync: BookmarkSync,
        val folderSync: FolderSync
    ) {
        @kotlinx.serialization.Serializable
        data class BookmarkSync(
            val bookmarks: List<Bookmark>,
            val count: Int
        ) {
            @kotlinx.serialization.Serializable
            data class Bookmark(
                val bookmark: BookmarkX,
                val folderMappings: List<FolderMapping>
            ) {
                @kotlinx.serialization.Serializable
                data class BookmarkX(
                    val address: String,
                    val bookmarkId: Int,
                    val creationTime: Long,
                    val displayName: String,
                    val folderMappings: String?,
                    val isIndoor: Boolean,
                    val lastUpdateTime: Long,
                    val memo: String?,
                    val name: String,
                    val order: Int,
                    val px: Double,
                    val py: Double,
                    val sid: String,
                    val type: String,
                    val url: String?,
                    val useTime: Long
                ) {
                    fun toRestaurant() = Restaurant(
                        address, isIndoor, name, px, py, sid, url
                    )
                }

                @kotlinx.serialization.Serializable
                data class FolderMapping(
                    val creationTime: Long,
                    val folderId: Int
                )
            }
        }

        @kotlinx.serialization.Serializable
        data class FolderSync(
            val count: Int,
            val folders: List<Folder>
        ) {
            @kotlinx.serialization.Serializable
            data class Folder(
                val bookmarkCount: Int,
                val colorCode: String,
                val creationTime: Long,
                val externalLink: String?,
                val folderId: Int,
                val followCount: Int,
                val iconId: String,
                val isDefaultFolder: Boolean,
                val lastUseTime: Long,
                val markerColor: String,
                val memo: String?,
                val name: String,
                val publicationStatus: String,
                val shareId: String,
                val shouldOverlay: Boolean,
                val userId: String,
                val viewCount: Int
            )
        }
    }

    fun toRestaurantList() = this.my.bookmarkSync.bookmarks.map { it.bookmark.toRestaurant() }
}
