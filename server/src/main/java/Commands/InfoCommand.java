package Commands;

import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class InfoCommand implements Command{
    private CommandKeeper commandKeeper;

    public InfoCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("info", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.info(arguments, objectArg);
    }
}