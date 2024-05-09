package Commands;

import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class AddCommand implements Command {
    private CommandKeeper commandKeeper;

    public AddCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("add", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.add(arguments,objectArg);
    }
}