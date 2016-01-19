package com.github.williamvoor.langid.processing;

import com.github.williamvoor.langid.dictionary.LanguageDictionaries;

/**
 * Base language identification stage. Concrete implementations of this class will form a chain of responsibility meant
 * to identify the language of a {@link LangIdRequest}. Concrete classes can modify the request so that the chain stops
 * when the language is identified.
 * 
 */
public abstract class Stage {

  private Stage nextStage;

  public void setNextStage(Stage nextStage) {
    this.nextStage = nextStage;
  }

  protected Stage getNextStage() {
    return nextStage;
  }

  protected boolean hasNextStage() {
    return nextStage != null;
  }

  abstract public void processRequest(LangIdRequest request, LanguageDictionaries dictionaries);

}