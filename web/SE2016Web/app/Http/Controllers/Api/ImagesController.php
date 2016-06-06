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
            'image_file' => 'image',
        ]);
        if ($request->hasFile('image_file')) {
            $file_name = Crypt::encrypt($item->title . strval($item->user_id)) . '.jpg';
            Storage::put('images/' . $file_name, $request->file('image_file'));
            $item->image_file = $file_name;
            return $file_name;
        }
        else {
            $item->image_file = '';
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
