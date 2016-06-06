<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Storage;
use App\Http\Controllers\Controller;
use App\Http\Requests;

class ImagesController extends Controller
{
    public function show($image_file)
    {
        $file = Storage::get('images/' . $image_file);
        return (new Response($file, 200))->header('Content-Type', 'image/jpeg');
    }

    public function destroy($image_file)
    {
        Storage::delete('images/' . $image_file);
        return 1;
    }
}
