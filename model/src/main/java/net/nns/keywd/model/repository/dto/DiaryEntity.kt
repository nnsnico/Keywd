package net.nns.keywd.model.repository.dto

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
                id = id,
                title = it,
                content = content,
            )
        }

    companion object {
        fun fromDiary(diary: Diary): DiaryEntity = DiaryEntity(
            id = diary.id,
            title = diary.title.value,
            content = diary.content,
        )
    }
}
