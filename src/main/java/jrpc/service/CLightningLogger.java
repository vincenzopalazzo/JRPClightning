package jrpc.service;

import java.util.HashMap;
import java.util.Map;
import jrpc.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLightningLogger {

  private static final String CLASS_TAG = CLightningLogger.class.getCanonicalName();
  private static CLightningLogger SINGLETON;

  public static CLightningLogger getInstance() {
    if (SINGLETON == null) {
      SINGLETON = new CLightningLogger();
    }
    return SINGLETON;
  }

  private Map<String, Logger> loggersCache;

  private CLightningLogger() {
    this.loggersCache = new HashMap<>();
  }

  public void debug(Class clazz, String message) {
    try {
      printMessage(LevelLogger.DEBUG, clazz, message);
    } catch (ServiceException e) {
      throw new IllegalArgumentException(e.getCause());
    }
  }

  public void error(Class clazz, String message) {
    try {
      printMessage(LevelLogger.ERROR, clazz, message);
    } catch (ServiceException e) {
      throw new IllegalArgumentException(e.getCause());
    }
  }

  public void info(Class clazz, String message) {
    try {
      printMessage(LevelLogger.INFO, clazz, message);
    } catch (ServiceException e) {
      throw new IllegalArgumentException(e.getCause());
    }
  }

  protected void printMessage(LevelLogger levelLogger, Class clazz, String message)
      throws ServiceException {
    if (clazz == null || (message == null || message.isEmpty())) {
      String errorMessage = this.getMessageException();
      if (clazz == null) {
        errorMessage += "- ";
        errorMessage += "Class null\n";
      }
      if (message == null) {
        errorMessage += "- ";
        errorMessage += "Log message null";
      } else {
        errorMessage += "- ";
        errorMessage += "Log message empty";
      }
      throw new ServiceException(errorMessage);
    }

    Logger logger = this.getLoggerByClass(clazz);

    if (logger == null) {
      String errorMessage = this.getMessageException() + "- Logger null, error irreversible";
      throw new ServiceException(errorMessage);
    }

    switch (levelLogger) {
      case DEBUG:
        logger.debug(message);
        break;
      case ERROR:
        logger.error(message);
        break;
      default:
        logger.info(message);
    }
  }

  protected Logger getLoggerByClass(Class clazz) throws ServiceException {
    if (clazz == null) {
      String message = this.getMessageException();
      message += "- Class inside methods getLoggerByClass null";
      throw new ServiceException(message);
    }
    String classpath = clazz.getCanonicalName();
    if (loggersCache.containsKey(classpath)) {
      return loggersCache.get(classpath);
    }
    Logger newLogger = LoggerFactory.getLogger(classpath);
    loggersCache.put(classpath, newLogger);
    return newLogger;
  }

  private String getMessageException() {
    return "Exception generated inside class " + CLASS_TAG + " Errors List:\n";
  }

  protected enum LevelLogger {
    ERROR,
    DEBUG,
    INFO,
  }
}
