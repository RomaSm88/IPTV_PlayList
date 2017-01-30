package ru.home.tv;


/**
 * Запускаемый класс программы.
 * Создает основной объект приложения {@link ru.home.tv.iptv.Playlist}
 * Реализует командный интерфес пользователя, для взаимодействия пользователя с основным объектом программы.
 * В данном классе реализована валидация значений в заданных поля класса {@link ru.home.tv.iptv.Playlist}
 * по алгоритму объекта {@link javax.validation.Validator}
 * Created by Roman on 04.01.2017.
 */

import ru.home.tv.iptv.Playlist;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Scanner;
import java.util.Set;

public class StartProgram {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private static Playlist playlist = null;

    // Пример ввода адреса сохранения плейлиста
    private static final String EXAMLE_PATH_PLAYLIST = "Examle: path playlist save c:\\playlist_tv.m3u";
    // Сообщение перед закрытием программы.
    private static final String MESSAGE_BY = "Program close. Bye!";

    // Сообщения об ошибка ввода актетов
    private static final String ERROR_MES_ACTET = "ERROR: actet #3 and actet #4 - number value.";

    private static void CreateValidatorFactory() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }


    private static void CloseValidator() {
        if (validatorFactory != null) {

            validatorFactory.close();
        }
    }

    // Валидация введенных данных.
    private static boolean validationProcess() {

        if (playlist == null) {
            return  false;
        }

        CreateValidatorFactory();

        Set<ConstraintViolation<Playlist>> violations = validator.validate(playlist);

        if (violations.size() != 0) {
            System.out.println(violations.iterator().next().getMessage());
            playlist = null;

            return false;
        }

        CloseValidator();

        return true;
    }

    public static void main(String[] args) {

        String tmp_input;

        try (Scanner input = new Scanner(System.in)) {

            while (!validationProcess()) {
                System.out.print("Enter values y/n: ");
                String value_enter = input.next();

                if (!value_enter.equals("y")) {
                    System.out.println(MESSAGE_BY);
                    break;
                }

                System.out.print("Enter number actet #3: ");
                tmp_input = input.next();

                Integer actet3 = Integer.parseInt(tmp_input);

                System.out.print("Enter number actet #4: ");
                tmp_input = input.next();

                Integer actet4 = Integer.parseInt(tmp_input);

                playlist = new Playlist(actet3, actet4);

                System.out.println(EXAMLE_PATH_PLAYLIST);
                System.out.print("Enter path playlist save: ");
                playlist.setPathPlayList(input.next());

            }
        } catch (NumberFormatException e) {

            System.out.println(ERROR_MES_ACTET);
            System.exit(-1);

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
            System.exit(-1);
        }


        if (playlist != null) {
            playlist.createPlayList();

            System.out.println("Established playlist: " + playlist.getPathPlayList());
            System.out.println(MESSAGE_BY);
        }

    }

}
