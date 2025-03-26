import java.util.Scanner;

public class Repl {
    private final Menus client;

    public Repl(String serverUrl) {
        client = new Menus(serverUrl);
    }

    public void run() {
        System.out.println("\u265e Welcome to CS240 Chess! type HELP to get started");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";

        while (!result.equals("quit")) {

            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(result);
                System.out.println();
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }

        }
        System.out.println();

    }

}
