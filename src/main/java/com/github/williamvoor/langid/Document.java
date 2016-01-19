package com.github.williamvoor.langid;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardTokenizer;

import com.github.williamvoor.langid.util.TextUtils;

/**
 * Stores a text as a list of tokens.
 */
public class Document {

  /*
   * Tokens (or words) contained in the text.
   */
  private List<String> tokens;

  /**
   * Constructs the document with a preset collection of tokens
   * 
   * @param tokens
   */
  public Document(List<String> tokens) {
    this.tokens = tokens;
  }

  /**
   * Constructs the document with an empty collection of tokens
   */
  public Document() {
    this.tokens = new ArrayList<String>();
  }

  /*
   * Returns the list of tokens store in the document
   */
  public List<String> getTokens() {
    return tokens;
  }

  /**
   * Adds or more tokens to this document
   * 
   * @param tokens
   */
  public void addTokens(String... tokens) {
    for (String t : tokens) {
      this.tokens.add(t);
    }
  }

  /**
   * Obtains the number of tokens
   * 
   * @return number of tokens in the document
   */
  public int getTokenCount() {
    return tokens.size();
  }

  /**
   * Factory method that constructs a {@link Document} from a text input stream, by feeding the stream through a
   * {@link StandardTokenizer}
   * 
   * @param s
   *          input stream containing text
   * @return instance of {@link Document}
   * @throws IOException
   *           if the stream cannot be read
   */
  public static Document fromInputStream(InputStream s) throws IOException {
    return new Document(TextUtils.tokenize(s));
  }
}