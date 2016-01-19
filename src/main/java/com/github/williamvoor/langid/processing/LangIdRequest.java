package com.github.williamvoor.langid.processing;

import com.github.williamvoor.langid.Document;
import com.github.williamvoor.langid.Language;

/**
 * Contains information necessary to perform a request for language identification. When an instance of this class is
 * passed through the processing components, the value of the lanaguage field will be modified to contain the detected
 * language.
 */
public class LangIdRequest {

  private Language language = Language.UNKNOWN;

  private Document document;

  private int score;

  public LangIdRequest(Language language, Document document) {
    super();
    this.language = language;
    this.document = document;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public Document getDocument() {
    return document;
  }

  public void setDocument(Document document) {
    this.document = document;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
