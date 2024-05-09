package Commands;

import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class UpdateCommand implements Command{
    private CommandKeeper commandKeeper;

    public UpdateCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("update", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.update(arguments, objectArg);
    }
}