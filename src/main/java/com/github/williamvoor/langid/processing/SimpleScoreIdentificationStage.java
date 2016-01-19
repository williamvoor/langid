package com.github.williamvoor.langid.processing;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.williamvoor.langid.Constants;
import com.github.williamvoor.langid.Language;
import com.github.williamvoor.langid.dictionary.BloomFilterDictionary;
import com.github.williamvoor.langid.dictionary.Dictionary;
import com.github.williamvoor.langid.dictionary.LanguageDictionaries;

/**
 * Identification stage that uses dictionaries populated by previous stages to compute hit scores.
 * 
 */
public class SimpleScoreIdentificationStage extends Stage {

  private static Logger logger = LoggerFactory.getLogger(SimpleScoreIdentificationStage.class);

  public SimpleScoreIdentificationStage() {
  }

  @Override
  public void processRequest(LangIdRequest request, LanguageDictionaries dictionaries) {

    if (request.getDocument().getTokenCount() <= 0) {
      throw new IllegalArgumentException("Document in request has no tokens");
    }

    Map<Language, AtomicInteger> scores = new HashMap<Language, AtomicInteger>();

    for (Language l : Language.values()) {
      AtomicInteger score = scores.get(l);
      if (score == null) {
        score = new AtomicInteger();
        scores.put(l, score);
      }

      Dictionary trainingSet = getDictionary(l, dictionaries);
      for (String t : request.getDocument().getTokens()) {
        if (trainingSet.matches(t)) {
          score.incrementAndGet();
        }
      }
    }

    int maxScore = Integer.MIN_VALUE;
    Language bestMatch = Language.UNKNOWN;
    Set<Entry<Language, AtomicInteger>> entrySet = scores.entrySet();
    for (Entry<Language, AtomicInteger> entry : entrySet) {
      if (entry.getValue().get() > maxScore) {
        maxScore = entry.getValue().get();
        bestMatch = entry.getKey();
      }
    }

    double hitRatio = ((double) maxScore / (double) request.getDocument().getTokenCount());

    logger.debug("Hit ratio for language {}: {}", bestMatch, hitRatio);

    request.setScore(maxScore);

    if (hitRatio >= Constants.UNKNOWN_THRESHOLD) {
      request.setLanguage(bestMatch);
    }

    if (hasNextStage()) {
      getNextStage().processRequest(request, dictionaries);
    }
  }

  private Dictionary getDictionary(Language language, LanguageDictionaries dictionaries) {

    if (!dictionaries.hasDictionary(language)) {
      BloomFilterDictionary bloomFilterDictionary = new BloomFilterDictionary(language);
      dictionaries.addDictionary(bloomFilterDictionary);
      return bloomFilterDictionary;
    }

    return dictionaries.getDictionary(language);
  }
}