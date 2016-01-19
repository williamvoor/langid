package com.github.williamvoor.langid.dictionary;

import java.util.List;

import com.github.williamvoor.langid.Language;
import com.skjegstad.utils.BloomFilter;

/**
 * This dictionary uses a space and time-efficient probabilistic data structure, a bloom filter, which can, most
 * notably, answer queries in constant time, at the expense of accuracy. The dictionary will have a small probability of
 * false positives, i.e. indicating that a word is contained in the set while it is not.
 * 
 */
public class BloomFilterDictionary implements Dictionary {

  private Language language;

  private BloomFilter<String> bloomFilter;

  public BloomFilterDictionary(Language language) {
    this.language = language;
    // preset filter with 10% probability false-positives and 10000 words
    this.bloomFilter = new BloomFilter<String>(0.1, 10000);
  }

  @Override
  public Language getLanguage() {
    return language;
  }

  @Override
  public void addToken(String token) {
    bloomFilter.add(token);
  }

  @Override
  public boolean matches(String word) {
    return bloomFilter.contains(word);
  }

  @Override
  public void addAllTokens(List<String> tokens) {
    for (String t : tokens) {
      addToken(t);
    }
  }

  @Override
  public boolean isEmpty() {
    return bloomFilter.count() <= 0;
  }
}