package Commands;

import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class RemoveLowerCommand implements Command{
    private CommandKeeper commandKeeper;

    public RemoveLowerCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("remove_lower", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.removeLower(arguments, objectArg);
    }
}