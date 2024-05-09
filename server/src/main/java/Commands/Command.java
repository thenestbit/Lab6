package Commands;

import CollectionObject.CityModel;
import Network.Response;

public interface Command {
    Response execute(String arguments, CityModel objArgument);
}