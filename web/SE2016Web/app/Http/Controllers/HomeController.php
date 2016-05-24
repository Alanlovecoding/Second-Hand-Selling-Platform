<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use App\Item;
class HomeController extends Controller
{
    public function index()
    {
        return view('Home')->withItems(Item::all()->sortByDesc(function($item){
    		return $item->created_at;
		}));
    }
}
