<?php

namespace App\Services;

use GuzzleHttp\Client;

class EurekaService
{
  protected $client;
  protected $eurekaUrl;

  public function __construct()
  {
    $this->client = new Client();
    $this->eurekaUrl = env('EUREKA_SERVER_URL');
  }

  // đăng ký thông tin service với Eureka server
  public function register()
  {
    $instanceId = gethostname();
    $data = [
      'instance' => [
        'instanceId' => $instanceId,
        'hostName' => $instanceId,
        'app' => strtoupper(config('app.name')),
        'ipAddr' => request()->ip(),
        'port' => ['@enabled' => true, '$' => env('SERVER_PORT', '8005')],
        'vipAddress' => config('app.name'),
        'dataCenterInfo' => [
          '@class' => 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
          'name' => 'MyOwn'
        ]
      ]
    ];

    $this->client->post("{$this->eurekaUrl}/eureka/apps/" . strtoupper(config('app.name')), [
      'json' => $data
    ]);
  }

  // lấy danh sách instance của các service khác từ Eureka server
  public function getServiceInstances($serviceName)
  {
    $response = $this->client->get("{$this->eurekaUrl}/eureka/apps/{$serviceName}", [
      'headers' => [
        'Accept' => 'application/json'
      ]
    ]);

    $body = json_decode($response->getBody(), true);

    if (isset($body['application']['instance'])) {
      return $body['application']['instance'];
    }

    return [];
  }
}
