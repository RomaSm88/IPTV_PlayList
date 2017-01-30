package ru.home.tv.iptv;

/**
 * Главный класс приложения. Реализует генерацию Multicast-адресов и их последующее сохранение
 * в файле плейлиста.
 * Адрес сохранения плейлиста и его имя задает пользователь класса.
 * Created by Roman on 04.01.2017.
 */

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;


public class Playlist {

    private static final String HEADER_PLAYLIST = "header.properties";


    @Min(value = 0, message = "min value actet #3 = 0")
    @Max(value = 254, message = "max value actet #3 = 254")
    private final Integer maxValueActet_3;


    @Min(value = 1, message = "min value actet #4 = 1")
    @Max(value = 254, message = "max value actet #4 = 254")
    private final Integer maxValueActet_4;

    @NotNull (message = "properties not null!")
    private  Properties properties;

    @NotNull(message = "pathPlayList not null!")
    private String pathPlayList;


    private void setProperties() {

        Class aClass = getClass();

        try(InputStream file_resources = aClass.getClassLoader().getResourceAsStream(HEADER_PLAYLIST)) {

            properties = new Properties();
            properties.load(file_resources);

        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void setPathPlayList(String path) {
        pathPlayList = path;
    }


    public void createPlayList() {

        try (BufferedWriter write_file = Files.newBufferedWriter(Paths.get(pathPlayList))) {

            write_file.write(properties.getProperty("START") + '\n');

            for (int ac3 = 0; ac3 <= maxValueActet_3; ac3++) {
                for (int ac4 = 1; ac4 <= maxValueActet_4; ac4++ ) {

                    String titleChannel = properties.getProperty("TITLE") + properties.getProperty("NAME") + ac3 + '.' + ac4;
                    write_file.write( titleChannel + '\n');

                    String multicastAddress = properties.getProperty("ADDR") + ac3 + '.' + ac4 + properties.getProperty("PORT");
                    write_file.write( multicastAddress + '\n');

                }
            }

        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public Playlist(Integer actet3, Integer actet4) {

            maxValueActet_3 = actet3;
            maxValueActet_4 = actet4;

            setProperties();
    }

    public String getPathPlayList() {
        return pathPlayList;
    }

}