<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use DB;

use App\Http\Requests;
use App\Http\Controllers\Controller;
use App\User;
use App\Item;
use App\TradeRequest;
class ItemsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return Response
     */
    public function index()
    {
        //return DB::table('items')->orderBy('updated_at', 'desc')->get();
        return Item::orderBy('updated_at', 'desc')->get()->pluck('id');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  Request  $request
     * @return Response
     */
    public function store(Request $request)
    {
        $this->validate($request, [
            'title' => 'required',
            'number' => 'required',
            'user_id' => 'required',
            'price' => 'required',
            'description' => 'required',
            'status' => 'required'
        ]);

        $item = new Item;
        $item->title = Input::get('title');
        $item->number = Input::get('number');
        $item->user_id = Input::get('user_id');
        $item->price = Input::get('price');
        $item->description = Input::get('description');
        $item->status = Input::get('status');

        if ($item->save()) {
            return True;
        } else {
            return False;
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return Response
     */
    public function show($id)
    {
        return Item::find($id);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  Request  $request
     * @param  int  $id
     * @return Response
     */
    public function update(Request $request, $id)
    {
        $this->validate($request, [
            'title' => 'required',
            'number' => 'required',
            'user_id' => 'required',
            'price' => 'required',
            'description' => 'required',
            'status' => 'required'
        ]);

        $item = Item::find($id);
        $item->title = Input::get('title');
        $item->number = Input::get('number');
        $item->user_id = Input::get('user_id');
        $item->price = Input::get('price');
        $item->description = Input::get('description');
        $item->status = Input::get('status');

        if ($item->save()) {
            return True;
        } else {
            return False;
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return Response
     */
    public function destroy($id)
    {
        $item = Item::find($id);
        $item->delete();
        return True;
    }
}
