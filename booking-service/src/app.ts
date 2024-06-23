import express from "express";
import { BaseController } from "./controllers/abstractions/base-controller";
import errorMiddleware from "./middlewares/error.middleware";
import Eureka from "eureka-js-client";
import { eurekaClient } from "./config/eureka-client";

class App {
  public app: express.Application;
  public port: number | string;

  constructor(controllers: BaseController[], port: number | string) {
    this.app = express();
    this.port = port;

    this.initializeMiddlewares();
    this.initializeControllers(controllers);
    this.initializeErrorHandling();
    this.registryEurekaServer();
  }

  private initializeMiddlewares() {
    this.app.use(express.json());
  }

  private initializeErrorHandling() {
    this.app.use(errorMiddleware);
  }

  private initializeControllers(controllers: BaseController[]) {
    this.app.get("/api/express", (request, response) => {
      response.send("Application is running");
    });
    controllers.forEach((controller) => {
      this.app.use("/api/express/", controller.router);
    });
  }

  private registryEurekaServer() {
    // Khởi chạy Eureka Client
    eurekaClient.start((error: any) => {
      console.log(error || "Eureka client started");
    });
  }

  public listen() {
    this.app.listen(this.port, () => {
      console.log(`App listening on the port ${this.port}`);
    });
  }
}

export default App;
