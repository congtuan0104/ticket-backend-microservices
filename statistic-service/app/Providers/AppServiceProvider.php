<?php

namespace App\Providers;

use Illuminate\Support\ServiceProvider;
use App\Services\EurekaService;

class AppServiceProvider extends ServiceProvider
{
    /**
     * Register any application services.
     */
    public function register(): void
    {
        //
    }

    /**
     * Bootstrap any application services.
     */
    public function boot(EurekaService $eurekaService): void
    {
        $eurekaService->register();
    }
}
