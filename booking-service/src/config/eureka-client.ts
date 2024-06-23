import { Eureka } from "eureka-js-client";

export const eurekaClient = new Eureka({
  instance: {
    app: "booking-service",
    hostName: "localhost",
    ipAddr: "127.0.0.1",
    statusPageUrl: `http://localhost:8008`,
    port: {
      $: 8008,
      "@enabled": true,
    },
    vipAddress: "booking-service",
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
