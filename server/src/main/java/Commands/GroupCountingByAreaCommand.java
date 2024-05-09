package Commands;

import CollectionObject.CityModel;
import Modules.*;
import Network.Response;

public class GroupCountingByAreaCommand implements Command{
    private CommandKeeper commandKeeper;

    public GroupCountingByAreaCommand(CommandKeeper commandKeeper) {
        this.commandKeeper = commandKeeper;
        ConsoleApp.commandList.put("group_counting_by_area", this);
    }

    @Override
    public Response execute(String arguments, CityModel objectArg) {
        return commandKeeper.groupCountingByArea(arguments, objectArg);
    }
}