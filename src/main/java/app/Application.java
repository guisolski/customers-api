package app;
import static  spark.Spark.*;

public class Application {
    public static void main(String[] args) {
        Router routes = new Router();
        routes.routes();
    }
}
