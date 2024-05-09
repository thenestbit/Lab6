package Commands;

import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class HelpCommand implements Command {
    CommandKeeper commandKeeper;

    public HelpCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("help", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.help(arguments, objectArg);
    }
}