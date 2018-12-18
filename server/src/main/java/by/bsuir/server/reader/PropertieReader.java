package by.bsuir.server.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertieReader {
    private static final String PATH_TO_PROPERTIES = "/prop/properties.properties";
    private static PropertieReader instance = new PropertieReader();
    private String login;
    private String password;
    private String baseUrl;
    private String driver;
    private String portNumber;

    private PropertieReader() {
        InputStream fileInputStream;
        Properties prop = new Properties();
        try {
            fileInputStream = getClass().getResourceAsStream(PATH_TO_PROPERTIES);
            prop.load(fileInputStream);
            login = prop.getProperty("login");
            password = prop.getProperty("password");
            baseUrl = prop.getProperty("baseUrl");
            driver = prop.getProperty("driver");
            portNumber = prop.getProperty("portNumber");
        } catch (IOException e) {
            System.out.println("Ошибка в программе: файл " + PATH_TO_PROPERTIES + " не обнаружено");
            e.printStackTrace();////////log
        }

    }

    public static PropertieReader getInstance() {
        return instance;
    }

    public int getPortNumber() {
        return  new Integer(portNumber);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getDriver() {
        return driver;
    }
}
