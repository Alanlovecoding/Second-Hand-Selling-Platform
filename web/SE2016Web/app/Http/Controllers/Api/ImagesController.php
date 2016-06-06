<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Storage;
use App\Http\Controllers\Controller;
use App\Http\Requests;

class ImagesController extends Controller
{
    public function store(Request $request)
    {
        $this->validate($request, [
            'item_id' => 'required',
            'image_file' => 'required|image'
        ]);
        return $request->all();
        if ($request->hasFile('image_file')) {
            $file_name = Crypt::encrypt($item->title . strval($item->user_id)) . '.jpg';
            Storage::put('images/' . $file_name, $request->file('image_file'));
            return $file_name;
        }
        else {
            return 0;
        }
    }

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
