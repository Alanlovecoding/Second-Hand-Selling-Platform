<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateRequestsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
      Schema::create('requests', function (Blueprint $table) {
          $table->increments('id');
          $table->integer('user_id');
          $table->integer('item_id');
          $table->integer('number');
          $table->enum('status', ['unreviewed', 'reviewed', 'rejected']);

          $table->timestamps();
      });
      
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('requests');
    }
}
