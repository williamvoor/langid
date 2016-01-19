package com.github.williamvoor.langid.dictionary;

import java.util.HashMap;
import java.util.Map;

import com.github.williamvoor.langid.Language;

/**
 * Wraps several dictionaries of multiple languages
 */
public class LanguageDictionaries {

  private Map<Language, Dictionary> dictionaries;

  public LanguageDictionaries() {
    this.dictionaries = new HashMap<Language, Dictionary>();
  }

  public void addDictionary(Dictionary dictionary) {
    dictionaries.put(dictionary.getLanguage(), dictionary);
  }

  public Dictionary getDictionary(Language language) {
    return dictionaries.get(language);
  }

  public boolean hasDictionary(Language language) {
    return dictionaries.containsKey(language);
  }
}