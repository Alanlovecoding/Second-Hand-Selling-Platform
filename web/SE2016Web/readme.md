# To start

After you download this, there are still several things to do to make it work.

First, you need to install composer:
	curl -sS https://getcomposer.org/installer | php
	mv composer.phar /usr/local/bin/composer

Then cd to this folder, install all the packages needed:
	composer install

Then you should check your .env, make sure that all settings are corroct. If this file does not exist, you can copy .env.example to .env

Next, type:	
	php artisan key:generate
This gonna generate a key for the application