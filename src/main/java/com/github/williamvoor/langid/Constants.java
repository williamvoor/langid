package com.github.williamvoor.langid;

/**
 * Constants that affect the behaviour of language identification process
 * 
 */
public final class Constants {

  /**
   * Language matching scores are defined as they ration between the number of hits against a training set (dictionary)
   * and the total number of tokens in a document; therefore producing a number between 0 and 1. This threshold defines
   * a level under which a score is determined to be to precise enough.
   */
  public static double UNKNOWN_THRESHOLD = 0.2;

  private Constants() {
  }
}
