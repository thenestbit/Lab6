package Commands;

import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class RemoveByIdCommand implements Command{
    private CommandKeeper commandKeeper;

    public RemoveByIdCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("remove_by_id", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.removeById(arguments, objectArg);
    }
}