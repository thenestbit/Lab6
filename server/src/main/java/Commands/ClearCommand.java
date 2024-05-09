package Commands;

import CollectionObject.City;
import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class ClearCommand implements Command{
    private CommandKeeper commandKeeper;

    public ClearCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("clear", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.clear(arguments, objectArg);
    }
}