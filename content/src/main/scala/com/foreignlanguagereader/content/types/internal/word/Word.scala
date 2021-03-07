package com.foreignlanguagereader.content.types.internal.word

import com.foreignlanguagereader.content.types.Language.Language
import com.foreignlanguagereader.content.types.internal.word.Count.Count
import com.foreignlanguagereader.content.types.internal.word.GrammaticalGender.GrammaticalGender
import com.foreignlanguagereader.content.types.internal.word.PartOfSpeech.PartOfSpeech
import com.foreignlanguagereader.content.types.internal.word.WordTense.WordTense
import com.foreignlanguagereader.dto.v1.word.WordDTO

case class Word(
    language: Language,
    token: String,
    tag: PartOfSpeech,
    lemma: String,
    gender: Option[GrammaticalGender],
    number: Option[Count],
    proper: Option[Boolean],
    tense: Option[WordTense],
    processedToken: String
) {
  lazy val toDTO: WordDTO =
    new WordDTO(
      token,
      processedToken,
      tag.toString,
      lemma
    )
}
object Word {
  def fromToken(token: String, language: Language): Word =
    Word(
      token = token,
      language = language,
      tag = PartOfSpeech.UNKNOWN,
      lemma = token,
      gender = None,
      number = None,
      proper = None,
      tense = None,
      processedToken = Word.processToken(token)
    )

  def processToken(token: String): String = token.toLowerCase
}
