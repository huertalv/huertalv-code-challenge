package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.util.FileCopyUtils;

import com.example.exception.CodeChallengeRuntimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class TestUtils {

  public static <T> T convertFileToClass(String filePath, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    InputStream fileInputStream = TestUtils.class.getClassLoader().getResourceAsStream(filePath);
    try {
      return mapper.readValue(fileInputStream, clazz);
    } catch (Exception e) {
      throw new CodeChallengeRuntimeException("Error parsing file " + filePath, e);
    }
  }

  public static String asString(String filePath) {
    InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream(filePath);
    try (Reader reader = new InputStreamReader(inputStream)) {
      return FileCopyUtils.copyToString(reader);
    } catch (IOException e) {
      throw new CodeChallengeRuntimeException("Error parsing file " + filePath, e);
    }
  }

  public static String getFilePath(String folder, String testCase, String fileName) {
    StringBuilder builder = new StringBuilder();
    return builder.append(folder).append("/")
        .append(testCase).append("/")
        .append(fileName)
        .toString();
  }
}
