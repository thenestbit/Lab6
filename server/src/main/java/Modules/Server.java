package Modules;

import CollectionObject.CityModel;
import Commands.*;
import Network.Request;
import Network.Response;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Server {
    private InetSocketAddress address;
    private Selector selector;
    private ConsoleApp consoleApp;
    private Response response;
    private Request request;

    public Server(InetSocketAddress address) {
        this.address = address;
        this.consoleApp = createConsoleApp();
    }

    public void run(String[] args){

        try {
            var pathToCollection = System.getenv("PATH_TO_JSON"); //"collection.xml"
            XMLProvider xmlProvider = new XMLProvider(String.valueOf(pathToCollection));
            xmlProvider.load();

            selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(address);
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()){
                    SelectionKey key = keys.next();

                    try {
                        if (key.isValid()){
                            if (key.isAcceptable()){
                                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                                SocketChannel clientChannel = serverSocketChannel.accept();
                                clientChannel.configureBlocking(false);
                                clientChannel.register(selector,SelectionKey.OP_READ);
                            }

                            if (key.isReadable()){
                                SocketChannel clientChannel = (SocketChannel) key.channel();
                                clientChannel.configureBlocking(false);

                                ByteBuffer clientData = ByteBuffer.allocate(2048);
                                clientChannel.read(clientData);

                                try(ObjectInputStream clientDataIn = new ObjectInputStream(new ByteArrayInputStream(clientData.array()))){
                                    request = (Request) clientDataIn.readObject();
                                } catch (StreamCorruptedException e){
                                    key.cancel();
                                };

                                var commandName = request.getCommandName();
                                var commandStrArg = request.getCommandStrArg();
                                var commandObjArg = (CityModel) request.getCommandObjArg();

                                if (ConsoleApp.commandList.containsKey(commandName)) {
                                    response = ConsoleApp.commandList.get(commandName).execute(commandStrArg, commandObjArg);
                                    CommandKeeper.addCommand(commandName);
                                } else {
                                    response = new Response("Команда не найдена. Используйте help для справки", "");
                                }

                                clientChannel.register(selector, SelectionKey.OP_WRITE);
                            }

                            if (key.isWritable()){
                                SocketChannel clientChannel = (SocketChannel) key.channel();
                                clientChannel.configureBlocking(false);

                                try(ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                    ObjectOutputStream clientDataOut = new ObjectOutputStream(bytes)){
                                    clientDataOut.writeObject(response);

                                    ByteBuffer clientData = ByteBuffer.wrap(bytes.toByteArray());
                                    ByteBuffer dataLength = ByteBuffer.allocate(32).putInt(clientData.limit());
                                    dataLength.flip();

                                    clientChannel.write(dataLength); // пишем длину ответа клиенту
                                    clientChannel.write(clientData); // шлём клиенту ответ
                                    clientData.clear();
                                }

                                clientChannel.register(selector, SelectionKey.OP_READ);
                            }
                        }
                    } catch (SocketException | CancelledKeyException e){
                        CommandKeeper.save();
                        key.cancel();
                    }
                    keys.remove();
                }
            }
        } catch (ArrayIndexOutOfBoundsException ignored){
        } catch (NoSuchElementException e) {
            CommandKeeper.save();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static ConsoleApp createConsoleApp() {
        CommandKeeper commandKeeper = new CommandKeeper();
        return new ConsoleApp(
                new HelpCommand(commandKeeper),
                new InfoCommand(commandKeeper),
                new ShowCommand(commandKeeper),
                new AddCommand(commandKeeper),
                new UpdateCommand(commandKeeper),
                new RemoveByIdCommand(commandKeeper),
                new ClearCommand(commandKeeper),
                new AddIfMinCommand(commandKeeper),
                new RemoveLowerCommand(commandKeeper),
                new HistoryCommand(commandKeeper),
                new MinByCreationDateCommand(commandKeeper),
                new GroupCountingByAreaCommand(commandKeeper),
                new FilterBySOLCommand(commandKeeper)
        );
    }

}