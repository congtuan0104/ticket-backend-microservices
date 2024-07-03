import { Eureka } from "eureka-js-client";

export const eurekaClient = new Eureka({
  instance: {
    app: "organization-service",
    hostName: "localhost",
    ipAddr: "127.0.0.1",
    statusPageUrl: `http://localhost:${process.env.PORT}`,
    port: {
      $: 8007,
      "@enabled": true,
    },
    vipAddress: "organization-service",
    dataCenterInfo: {
      "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
      name: "MyOwn",
    },
  },
  eureka: {
    host: "localhost",
    port: 8761,
    servicePath: "/eureka/apps/",
  },
});
