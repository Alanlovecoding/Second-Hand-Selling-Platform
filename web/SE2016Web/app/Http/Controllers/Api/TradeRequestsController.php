<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;

use App\Http\Requests;
use App\Http\Controllers\Controller;
use App\TradeRequest;
class TradeRequestsController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return Response
     */
    public function index()
    {
        return Request::all();
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
            'user_id' => 'required',
            'item_id' => 'required',
            'number' => 'required',
            'status' => 'required'
        ]);
        $trade_request = new TradeRequest;
        $trade_request->user_id = Input::get('user_id');
        $trade_request->item_id = Input::get('item_id');
        $trade_request->number = Input::get('number');
        $trade_request->status = Input::get('status');

        if ($trade_request->save()) {
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
        return TradeRequest::find($id);
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
            'user_id' => 'required',
            'item_id' => 'required',
            'number' => 'required',
            'status' => 'required'
        ]);
        $trade_request = TradeRequest::find($id);
        $trade_request->user_id = Input::get('user_id');
        $trade_request->item_id = Input::get('item_id');
        $trade_request->number = Input::get('number');
        $trade_request->status = Input::get('status');

        if ($trade_request->save()) {
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
        $trade_request = TradeRequest::find($id);
        $trade_request->delete();
        return True;
    }
}
