package net.nns.keywd.datasource.dto

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.getOrElse
import arrow.core.some
import net.nns.keywd.core.ext.NonEmptyList.toNonEmptyList
import net.nns.keywd.core.ext.NonEmptyList.traverse
import net.nns.keywd.model.Diary
import net.nns.keywd.model.Title

@Entity
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val title: String,
) {
    companion object {
        fun fromDiary(diary: Diary): DiaryEntity = DiaryEntity(
            id = diary.id.getOrElse { 0 },
            title = diary.title.value,
        )
    }
}

@Entity
data class DiaryWithKeywordsEntity(
    @Embedded val diary: DiaryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "diaryId",
    ) val keywords: List<DiaryKeywordEntity>,
) {
    suspend fun toDiary(): Either<Throwable, Diary> = either {
        val title = Title.fromString(diary.title).bind()
        val maybeKeywords = keywords.toNonEmptyList().bind {
            Exception("Keyword is empty.")
        }
        val keywords = maybeKeywords.traverse { it.toKeyword() }.bind {
            Exception("Failed to instantiate Keyword.")
        }
        Diary(
            id = diary.id.some(),
            title = title,
            keywords = keywords,
        )
    }
}
