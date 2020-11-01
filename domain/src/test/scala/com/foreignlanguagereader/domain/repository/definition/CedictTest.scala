package com.foreignlanguagereader.domain.repository.definition

import com.foreignlanguagereader.content.types.Language
import com.foreignlanguagereader.content.types.internal.word.Word
import org.scalatest.funspec.AnyFunSpec

class CedictTest extends AnyFunSpec {
  it("can parse CEDICT") {
    assert(Cedict.definitions.nonEmpty)
  }

  it("can get definitions") {
    val library = Cedict.getDefinition(Word.fromToken("圖書館", Language.CHINESE))
    assert(library.isDefined)
    val libraryDefinition = library.get.head
    assert(libraryDefinition.pinyin == "tu2 shu1 guan3")
    assert(libraryDefinition.traditional == "圖書館")
    assert(libraryDefinition.simplified == "图书馆")
    assert(libraryDefinition.subdefinitions.size == 2)
    assert(libraryDefinition.subdefinitions.head == "library")
    assert(libraryDefinition.subdefinitions(1) == "CL:家[jia1],個|个[ge4]")
  }
}