package Commands;

import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class AddIfMinCommand implements Command{
    private CommandKeeper commandKeeper;

    public AddIfMinCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("add_if_min", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.addIfMin(arguments, objectArg);
    }
}