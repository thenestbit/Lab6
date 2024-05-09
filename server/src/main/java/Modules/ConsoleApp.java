package Modules;

import CollectionObject.CityModel;
import Commands.Command;
import Network.Response;

import java.util.HashMap;

public class ConsoleApp {
    // command hashmap. K - command name; V - command class
    public static HashMap<String, Command> commandList = new HashMap<>();
    private Command help;
    private Command info;
    private Command show;
    private Command add;
    private Command update;
    private Command removeById;
    private Command clear;
    private Command addIfMin;
    private Command removeLower;
    private Command history;
    private Command minByCreationDate;
    private Command groupCountingByArea;
    private Command filterByStandardOfLiving;

    public ConsoleApp(Command... commands) {
        this.help = commands[0];
        this.info = commands[1];
        this.show = commands[2];
        this.add = commands[3];
        this.update = commands[4];
        this.removeById = commands[5];
        this.clear = commands[6];
        this.addIfMin = commands[7];
        this.removeLower = commands[8];
        this.history = commands[9];
        this.minByCreationDate = commands[10];
        this.groupCountingByArea = commands[11];
        this.filterByStandardOfLiving = commands[12];
    }

    public Response help(String arguments, CityModel objectArg){
        return help.execute(arguments,objectArg);
    }

    public Response info(String arguments, CityModel objectArg){
        return info.execute(arguments,objectArg);
    }

    public Response show(String arguments, CityModel objectArg){
        return show.execute(arguments,objectArg);
    }

    public Response add(String arguments, CityModel objectArg){
        return add.execute(arguments,objectArg);
    }

    public Response update(String arguments, CityModel objectArg){
        return update.execute(arguments,objectArg);
    }

    public Response removeById(String arguments, CityModel objectArg){
        return removeById.execute(arguments,objectArg);
    }

    public Response clear(String arguments, CityModel objectArg){
        return clear.execute(arguments,objectArg);
    }

    public Response addIfMin(String arguments, CityModel objectArg){
        return addIfMin.execute(arguments,objectArg);
    }

    public Response removeLower(String arguments, CityModel objectArg){
        return removeLower.execute(arguments,objectArg);
    }

    public Response history(String arguments, CityModel objectArg){
        return history.execute(arguments,objectArg);
    }

    public Response minByCreationDate(String arguments, CityModel objectArg){
        return minByCreationDate.execute(arguments,objectArg);
    }

    public Response groupCountingByArea(String arguments, CityModel objectArg){
        return groupCountingByArea.execute(arguments,objectArg);
    }

    public Response filterByStandardOfLiving(String arguments, CityModel objectArg){
        return filterByStandardOfLiving.execute(arguments,objectArg);
    }

}
