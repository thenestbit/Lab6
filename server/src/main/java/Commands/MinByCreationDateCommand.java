package Commands;

import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class MinByCreationDateCommand implements Command{
    private CommandKeeper commandKeeper;

    public MinByCreationDateCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("min_by_creation_date", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.minByCreationDate(arguments, objectArg);
    }
}