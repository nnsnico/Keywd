package net.nns.keywd.datasource.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import arrow.core.Either
import net.nns.keywd.model.Diary
import net.nns.keywd.model.Title

@Entity
data class DiaryEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val content: String,
) {
    suspend fun toDiary(): Either<Throwable, Diary> =
        Title.fromString(title).map {
            Diary(
                title = it,
                content = content,
            )
        }

}
