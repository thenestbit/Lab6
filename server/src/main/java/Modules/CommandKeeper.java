package Modules;


import CollectionObject.CityModel;
import Exceptions.NonExistingElementException;
import Exceptions.ScriptRecursionException;
import Network.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class CommandKeeper {
    private CollectionService collectionService;
    private static XMLProvider xmlProvider;
    private static LinkedList<String> commandHistory = new LinkedList<>();
    private static Set<Path> scriptsNames = new TreeSet<>();

    public CommandKeeper() {
        this.collectionService = new CollectionService();
        xmlProvider = new XMLProvider(XMLProvider.FILE_PATH);
    }

    public Response help(String strArgument, CityModel objArgument){
        if (!strArgument.isBlank()){
            return new Response("Wrong command arguments", "");// illegal args exception
        } else {
            String message =
                    """
                    Список доступных команд:
                    ================================================================================================
                    help : вывести справку по доступным командам
                    info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                    show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                    add {element} : добавить новый элемент в коллекцию
                    update id {element} : обновить значение элемента коллекции, id которого равен заданному
                    remove_by_id id : удалить элемент из коллекции по его id
                    clear : очистить коллекцию
                    save : сохранить коллекцию в файл
                    execute_script file_name : считать и исполнить скрипт из указанного файла. В Wrong command arguments в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                    exit : завершить программу (без сохранения в файл)
                    add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
                    remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный
                    history : вывести последние 13 команд (без их аргументов)
                    min_by_creation_date : вывести любой объект из коллекции, значение поля creationDate которого является минимальным
                    group_counting_by_area : сгруппировать элементы коллекции по значению поля area, вывести количество элементов в каждой группе
                    filter_by_standard_of_living standardOfLiving : вывести элементы, значение поля standardOfLiving которых равно заданному
                    ================================================================================================
                    """;
            return new Response(message,"");
        }
    }

    public Response info(String strArgument, CityModel objArgument){
        if (!strArgument.isBlank()){
            return new Response("Wrong command arguments","");
        } else {
            var message = collectionService.info();
            return new Response(message, "");
        }
    }

    public Response show(String strArgument, CityModel objArgument){
        if (!strArgument.isBlank()){
            return new Response("Wrong command arguments",""); // illegal args exception
        } else {
            var collection = collectionService.show();

            if (collection.isEmpty()){
                return new Response("В коллекции пока нету ни одного элемента", "");
            } else{
                return new Response("Коллекция успешно распечатана", collection.toString());
            }
        }
    }

    public Response add(String strArgument, CityModel objArgument){
        if (!strArgument.isBlank()){
            return new Response("Wrong command arguments",""); // illegal args exception
        } else {
            var collection = collectionService.addElement(objArgument);
            return new Response("Элемент успешно добавлен", collection.toString());
        }
    }

    public Response update(String strArgument, CityModel objArgument){  //args required
        if (strArgument.isBlank() && objArgument == null){
            return new Response("Неверные аргументы команды", "" );

        } else {
            try {
                long current_id = Long.parseLong(strArgument);

                if (current_id > 0){
                    var collection = collectionService.update(current_id, objArgument);
                    return new Response("элемент c id " + current_id + " успешно обновлён", collection.toString());

                } else {
                    return new Response("id не может быть отрицательным", "" );
                }

            } catch (NumberFormatException e){
                return new Response("Неверный формат аргументов", "" );
            } catch (NonExistingElementException e) {
                return new Response(e.getMessage(), "");
            }
        }
    }

    public Response removeById(String strArgument, CityModel objArgument) { //args required
        if (strArgument.isBlank()){
            return new Response("Wrong command arguments",""); // illegal args exception
        } else {
            try {
                long id = Long.parseLong(strArgument);
                if (id > 0){
                    var collection = collectionService.removeById(id);
                    return new Response("Элемент с id " + id + " успешно удален", collection.toString());
                } else {
                    return new Response("id не может быть отрицательным", "" );
                }
            } catch (NumberFormatException e){
                return new Response("Неверный формат аргументов", "" );
            } catch (NonExistingElementException e){
                return new Response(e.getMessage(),"");
            }
        }
    }

    public Response clear(String strArgument, CityModel objArgument){
        if (!strArgument.isBlank()){
            return new Response("Wrong command arguments",""); // illegal args exception
        } else {
            var collection = collectionService.clear();
            return new Response("Коллекция очищена успешно", collection.toString());
        }
    }

    public static void save(){
        xmlProvider.save(CollectionService.collection);
    }

    public Response removeLower(String strArgument, CityModel objArgument){
        if (strArgument.isBlank() || objArgument != null){
            return new Response("Неверные аргументы команды", "" );

        } else {
            try {
                long startId = Long.parseLong(strArgument) + 1;

                if (startId > 0) {
                    var collection = collectionService.removeLower(startId);
                    return new Response("элементы успешно удалены", collection.toString());

                } else {
                    return new Response("id не может быть отрицательным", "");
                }

            } catch (NumberFormatException e){
                return new Response("Неверный формат аргументов", "" );
            } catch (NonExistingElementException e) {
                return new Response(e.getMessage(), "");
            }
        }
    }

    public Response history(String strArgument, CityModel objArgument){
        StringBuilder historyList = new StringBuilder();

        if (!strArgument.isBlank() || objArgument != null){
            return new Response("Неверные аргументы команды", "" );

        } else {
            for (String command : commandHistory) {
                historyList.append(command).append("\n");
            }
        }
        return new Response("Последние 13 команд, введённые пользователем: \n" + historyList, "" );
    }

    public Response filterByStandardOfLiving(String strArgument, CityModel objArgument) { //args required
        if (strArgument.isBlank()){
            return new Response("Wrong command arguments",""); // illegal args exception
        } else {
            try {
                var collection = collectionService.filterByStandardOfLiving(strArgument.toUpperCase());
                return new Response("Элементы с указанным уровнем жизни: ", collection.toString());
            } catch (NonExistingElementException e){
                return new Response(e.getMessage(),"");
            }
        }

    }

    public Response groupCountingByArea(String strArgument, CityModel objArgument) {
        if (strArgument.isBlank() || objArgument != null){
            return new Response("Неверные аргументы команды", "" );

        } else {
            try {
                int area = Integer.parseInt(strArgument);
                if (area > 0) {
                    var count = collectionService.groupCountingByArea(area);
                    return new Response("Количество городов с area " + area + ":   " + count, "" );

                } else {
                    return new Response("Area не может быть отрицательной", "" );
                }

            } catch (NumberFormatException e){
                return new Response("Неверный формат аргументов", "" );
            }
        }
    }

    public Response minByCreationDate(String strArgument, CityModel objArgument) {
        if (!strArgument.isBlank()){
            return new Response("Wrong command arguments","");
        }
        else {
            var collection = collectionService.minByCreationDate();
            return new Response("Минимальный элемент по дате создания: ", collection.toString());
        }
    }

    public Response addIfMin(String strArgument, CityModel objArgument) {
        if (!strArgument.isBlank()) {
            return new Response("Wrong command arguments","");
        }
        else{
            var collection = collectionService.addIfMin(objArgument);
            return new Response("Элемент успешно добавлен", collection.toString());
        }
    }

    public static void addCommand(String command){
        if (commandHistory.size() == 13){
            commandHistory.removeFirst();
        }
        commandHistory.addLast(command);
    }
}