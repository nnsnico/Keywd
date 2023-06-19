package net.nns.keywd.datasource.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.some
import net.nns.keywd.model.Diary
import net.nns.keywd.model.Title

@Entity
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val title: String,
    @ColumnInfo val content: String,
) {
    suspend fun toDiary(): Either<Throwable, Diary> =
        Title.fromString(title).map {
            Diary(
                id = id.some(),
                title = it,
                content = content,
            )
        }

    companion object {
        fun fromDiary(diary: Diary): DiaryEntity = DiaryEntity(
            id = diary.id.getOrElse { 0 },
            title = diary.title.value,
            content = diary.content,
        )
    }
}
