package net.nns.keywd.datasource.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import arrow.core.Option
import arrow.core.getOrElse
import net.nns.keywd.core.NonEmptyString
import net.nns.keywd.model.Diary
import net.nns.keywd.model.Keyword

@Entity
data class DiaryKeywordEntity(
    @PrimaryKey val id: String,
    val diaryId: Int,
    val keyword: String,
) {
    fun toKeyword(): Option<Keyword> = NonEmptyString.init(keyword).map { nes ->
        Keyword(
            id = id,
            value = nes,
        )
    }

    companion object {
        fun fromDiary(diary: Diary): List<DiaryKeywordEntity> = diary.keywords.map { keyword ->
            DiaryKeywordEntity(
                id = keyword.id,
                diaryId = diary.id.getOrElse { 0 },
                keyword = keyword.value.value,
            )
        }

        fun create(diaryId: Int, keywords: List<Keyword>): List<DiaryKeywordEntity> =
            keywords.map { keyword ->
                DiaryKeywordEntity(
                    id = keyword.id,
                    diaryId = diaryId,
                    keyword = keyword.value.value,
                )
            }
    }
}
