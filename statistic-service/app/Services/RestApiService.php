<?php

namespace App\Services;

use GuzzleHttp\Client;

class RestApiService
{
  protected $eurekaClient;
  protected $httpClient;

  public function __construct(EurekaService $eurekaClient)
  {
    $this->eurekaClient = $eurekaClient;
    $this->httpClient = new Client();
  }

  public function callApi($serviceName, $endpoint, $method = 'GET', $options = [])
  {
    // Lấy danh sách instance của service từ Eureka
    $instances = $this->eurekaClient->getServiceInstances($serviceName);

    if (empty($instances)) {
      throw new \Exception("Service not found: {$serviceName}");
    }

    // Chọn instance đầu tiên (có thể thêm logic chọn instance khác nếu cần)
    $instance = $instances[0];
    $baseUrl = $instance['homePageUrl'];

    // Gọi API từ service
    try {
      $response = $this->httpClient->request($method, "{$baseUrl}{$endpoint}", $options);

      return json_decode($response->getBody(), true);
    } catch (\Exception $e) {
      throw $e;
    }
  }
}
