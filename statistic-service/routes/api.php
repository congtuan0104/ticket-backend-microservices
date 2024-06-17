<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\TestController;

Route::get('/hello', function () {
    return response()->json(['message' => 'Hello World!']);
});

Route::post('/hello', function () {
    return response()->json(['message' => 'Hello World! POST']);
});

Route::get('/test', [TestController::class, 'getExternalData']);
