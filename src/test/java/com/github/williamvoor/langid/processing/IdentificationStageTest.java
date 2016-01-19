package com.github.williamvoor.langid.processing;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.williamvoor.langid.Document;
import com.github.williamvoor.langid.Language;
import com.github.williamvoor.langid.dictionary.LanguageDictionaries;

public class IdentificationStageTest {

  private static Logger logger = LoggerFactory.getLogger(IdentificationStageTest.class);

  private static String TEST_ENGLISH_1 = "ENGLISH.TEXT";
  private static String TEST_ENGLISH_2 = "ENGLISH.TWEET";
  private static String TEST_GERMAN_1 = "GERMAN.TEXT";
  private static String TEST_ITALIAN_1 = "ITALIAN.TEXT";
  private static String TEST_PORT_1 = "PORTUGUESE.TEXT";
  private static String TEST_POLISH_1 = "POLISH.TEXT";

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  private TrainingStage stage1;

  @Before
  public void setUp() throws Exception {
    // build chain of responsibility
    stage1 = new TrainingStage();
    SimpleScoreIdentificationStage stage2 = new SimpleScoreIdentificationStage();
    stage1.setNextStage(stage2);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testLargeTextEnglish() throws Exception {

    try (InputStream textStream = IdentificationStageTest.class.getResourceAsStream(TEST_ENGLISH_1)) {
      LangIdRequest request1 = new LangIdRequest(Language.UNKNOWN, Document.fromInputStream(textStream));

      stage1.processRequest(request1, new LanguageDictionaries());

      logger.debug("Score {}, Token count {}", request1.getScore(), request1.getDocument().getTokenCount());
      logger.debug("Language of " + TEST_ENGLISH_1 + " is " + request1.getLanguage());

      assertEquals(Language.ENGLISH, request1.getLanguage());
    }
  }

  @Test
  public void testLargeTextGerman() throws Exception {

    try (InputStream textStream = IdentificationStageTest.class.getResourceAsStream(TEST_GERMAN_1)) {
      LangIdRequest request1 = new LangIdRequest(Language.UNKNOWN, Document.fromInputStream(textStream));

      stage1.processRequest(request1, new LanguageDictionaries());

      logger.debug("Score {}, Token count {}", request1.getScore(), request1.getDocument().getTokenCount());
      logger.debug("Language of " + TEST_GERMAN_1 + " is " + request1.getLanguage());

      assertEquals(Language.GERMAN, request1.getLanguage());
    }
  }

  @Test
  public void testLargeTextItalian() throws Exception {

    try (InputStream textStream = IdentificationStageTest.class.getResourceAsStream(TEST_ITALIAN_1)) {
      LangIdRequest request1 = new LangIdRequest(Language.UNKNOWN, Document.fromInputStream(textStream));

      stage1.processRequest(request1, new LanguageDictionaries());

      logger.debug("Score {}, Token count {}", request1.getScore(), request1.getDocument().getTokenCount());
      logger.debug("Language of " + TEST_ITALIAN_1 + " is " + request1.getLanguage());

      assertEquals(Language.ITALIAN, request1.getLanguage());
    }
  }

  @Test
  public void testNoMatchPortuguese() throws Exception {

    try (InputStream textStream = IdentificationStageTest.class.getResourceAsStream(TEST_PORT_1)) {
      LangIdRequest request1 = new LangIdRequest(Language.UNKNOWN, Document.fromInputStream(textStream));

      stage1.processRequest(request1, new LanguageDictionaries());

      logger.debug("Score {}, Token count {}", request1.getScore(), request1.getDocument().getTokenCount());
      logger.debug("Language of " + TEST_PORT_1 + " is " + request1.getLanguage());

      assertEquals(Language.UNKNOWN, request1.getLanguage());
    }
  }

  @Test
  public void testNoMatchPolish() throws Exception {

    try (InputStream textStream = IdentificationStageTest.class.getResourceAsStream(TEST_POLISH_1)) {
      LangIdRequest request1 = new LangIdRequest(Language.UNKNOWN, Document.fromInputStream(textStream));

      stage1.processRequest(request1, new LanguageDictionaries());

      logger.debug("Score {}, Token count {}", request1.getScore(), request1.getDocument().getTokenCount());
      logger.debug("Language of " + TEST_POLISH_1 + " is " + request1.getLanguage());

      assertEquals(Language.UNKNOWN, request1.getLanguage());
    }
  }

  @Test
  public void testEnglishTweet() throws Exception {

    try (InputStream textStream = IdentificationStageTest.class.getResourceAsStream(TEST_ENGLISH_2)) {
      LangIdRequest request1 = new LangIdRequest(Language.UNKNOWN, Document.fromInputStream(textStream));

      stage1.processRequest(request1, new LanguageDictionaries());

      logger.debug("Score {}, Token count {}", request1.getScore(), request1.getDocument().getTokenCount());
      logger.debug("Language of " + TEST_ENGLISH_2 + " is " + request1.getLanguage());

      assertEquals(Language.ENGLISH, request1.getLanguage());
    }
  }
}