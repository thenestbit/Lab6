package Commands;

import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class ShowCommand implements Command{
    private CommandKeeper commandKeeper;

    public ShowCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("show", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.show(arguments, objectArg);
    }
}