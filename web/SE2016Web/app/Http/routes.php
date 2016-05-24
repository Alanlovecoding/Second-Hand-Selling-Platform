<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', 'HomeController@index');

Route::controllers([
	'auth' => 'Auth\AuthController',
	'password' => 'Auth\PasswordController',
]);

Route::group(['prefix' => 'api'], function () {
	Route::resource('users', 'Api\UsersController', ['except' => ['create', 'store', 'edit']]);
	Route::resource('items', 'Api\ItemsController', ['except' => ['create', 'edit']]);
	Route::resource('trade_requests', 'Api\TradeRequestsController', ['except' => ['create', 'edit']]);

	Route::group(['prefix' => 'users/{user_id}'], function()
	{
        Route::get('favorites', 'Api\FavoritessController@index');
        Route::post('favorites', 'Api\FavoritessController@store');
        Route::delete('favorites/{item_id}', 'Api\FavoritessController@destroy');
        
		Route::get('items', 'Api\UsersController@getItems');
		Route::get('trade_requests/sent', 'Api\UsersController@getSentRequests');
		Route::get('trade_requests/received', 'Api\UsersController@getReceivedRequests');
	});
});
