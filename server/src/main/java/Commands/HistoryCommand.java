package Commands;

import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class HistoryCommand implements Command{
    private CommandKeeper commandKeeper;

    public HistoryCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("history", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.history(arguments, objectArg);
    }
}