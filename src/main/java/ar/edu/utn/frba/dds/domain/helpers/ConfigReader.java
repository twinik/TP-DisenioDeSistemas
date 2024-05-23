package ar.edu.utn.frba.dds.domain.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader class permite leer un archivo de configuracion.
 */
public class ConfigReader {
  private String filePath;

  public ConfigReader(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Lee un archivo de configuracion.
   */
  public Properties getProperties() throws IOException {
    Properties prop = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(filePath)) {
      prop.load(input);
    }
    return prop;
  }

  public String getProperty(String key) throws IOException {
    Properties prop = getProperties();
    return prop.getProperty(key);
  }
}