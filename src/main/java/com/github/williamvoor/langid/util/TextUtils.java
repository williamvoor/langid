package com.github.williamvoor.langid.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Utilities for parsing/tokenizing text
 */
public final class TextUtils {

  public static List<String> tokenize(InputStream f) throws IOException {
    try (StandardTokenizer tokenizer = new StandardTokenizer()) {

      BufferedReader reader = new BufferedReader(new InputStreamReader(f));
      List<String> result = new ArrayList<String>();

      tokenizer.setReader(reader);
      tokenizer.reset();
      try {
        while (tokenizer.incrementToken()) {
          result.add(tokenizer.getAttribute(CharTermAttribute.class).toString());
        }
      } catch (IOException e) {
        throw new IllegalStateException();
      }
      return result;
    }
  }
}