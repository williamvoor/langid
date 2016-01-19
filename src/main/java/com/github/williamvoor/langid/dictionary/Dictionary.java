package com.github.williamvoor.langid.dictionary;

import java.util.List;

import com.github.williamvoor.langid.Language;

/**
 * Standard interface of a dictionary used in language detection.
 * 
 */
public interface Dictionary {

  /**
   * Gets the language this dictionary represents
   * 
   * @return the language
   */
  Language getLanguage();

  /**
   * Adds a token to the dictionary
   * 
   * @param token
   */
  void addToken(String token);

  /**
   * Queries the dictionary for a word match
   * 
   * @param word
   * @return
   */
  boolean matches(String word);

  /**
   * Adds a list a tokens to the dictionary
   * 
   * @param tokens
   */
  void addAllTokens(List<String> tokens);

  /**
   * Whether the dictionary contains any token
   * 
   * @return
   */
  boolean isEmpty();
}