package com.github.williamvoor.langid.processing;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.williamvoor.langid.Language;
import com.github.williamvoor.langid.dictionary.BloomFilterDictionary;
import com.github.williamvoor.langid.dictionary.Dictionary;
import com.github.williamvoor.langid.dictionary.LanguageDictionaries;
import com.github.williamvoor.langid.util.TextUtils;

/**
 * Identification stage that trains on a set of documents known to be of a certain language. Uses bloom filters and
 * dictionaries.
 * 
 */
public class TrainingStage extends Stage {

  private static Logger logger = LoggerFactory.getLogger(TrainingStage.class);

  public TrainingStage() {
  }

  @Override
  public void processRequest(LangIdRequest request, LanguageDictionaries dictionaries) {

    // Searches for training files for all languages
    for (Language l : Language.values()) {
      Dictionary dict = getDictionary(l, dictionaries);
      URL trainingFile = null;
      int i = 1;
      do {
        String fName = l.toString() + "." + i++;
        trainingFile = TrainingStage.class.getResource("/" + fName);
        if (trainingFile != null) {
          try (InputStream stream = trainingFile.openStream()) {
            List<String> tokens = TextUtils.tokenize(stream);
            logger.debug("Adding {} tokens of file {} to training set {}", tokens.size(), fName, l);
            dict.addAllTokens(tokens);
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        }
      } while (trainingFile != null);

      if (dict.isEmpty()) {
        logger.warn("No training files were found for language {}. Dictionary will be empty", l);
      }
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