import dotenv from "dotenv";
import App from "./app";
import OrganizationController from "./controllers/organization-controller";

dotenv.config();

const port = process.env.PORT || 8007;
const app = new App([new OrganizationController()], port);

app.listen();
