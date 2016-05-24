<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Item extends Model
{
    public function user()
    {
        return $this->belongsTo('App\User');
    }

    public function trade_requests()
    {
        return $this->hasMany('App\TradeRequest');
    }
}
