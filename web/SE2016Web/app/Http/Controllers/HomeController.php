<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use App\Item;
class HomeController extends Controller
{
    public function index()
    {
        return view('Home');
    }

    public function haha()
    {
        return view('wangyaoxieqianduan');
    }
}
