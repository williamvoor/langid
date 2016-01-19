package com.github.williamvoor.langid;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.williamvoor.langid.dictionary.LanguageDictionaries;
import com.github.williamvoor.langid.processing.LangIdRequest;
import com.github.williamvoor.langid.processing.SimpleScoreIdentificationStage;
import com.github.williamvoor.langid.processing.TrainingStage;

public class Main {

  public static void main(String[] args) {

    if (args.length <= 0) {
      System.err.println("This application requires a file name argument");
      System.exit(1);
    }

    File file = new File(args[0]);
    if (!file.exists()) {
      System.err.println("File " + args[0] + " does not exist");
      System.exit(1);
    }

    try (InputStream textStream = new BufferedInputStream(new FileInputStream(file))) {

      System.out.println("Reading input file from " + file.getAbsolutePath());

      // build chain of responsibility
      // TODO building the chain should be done via configuration
      TrainingStage stage1 = new TrainingStage();
      SimpleScoreIdentificationStage stage2 = new SimpleScoreIdentificationStage();
      stage1.setNextStage(stage2);

      LangIdRequest request1 = new LangIdRequest(Language.UNKNOWN, Document.fromInputStream(textStream));
      stage1.processRequest(request1, new LanguageDictionaries());

      System.out.println("Detected language of text " + args[0] + " is " + request1.getLanguage());
    } catch (IOException e) {
      System.err.println("Error reading input file: " + e.getMessage());
    }
  }
}
