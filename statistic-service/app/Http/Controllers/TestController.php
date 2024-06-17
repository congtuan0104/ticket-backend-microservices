<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Services\RestApiService;

class TestController extends Controller
{
    protected $apiService;

    public function __construct(RestApiService $apiService)
    {
        $this->apiService = $apiService;
    }

    public function getExternalData(Request $request)
    {
        try {
            $images = $this->apiService->callApi('image-service', 'images');
            return response()->json($images);
        } catch (\Exception $e) {
            return response()->json(['error' => 'Service call failed'], 500);
        }
    }
}
