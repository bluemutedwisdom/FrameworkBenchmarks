#!/bin/bash
export PHP_HOME=${IROOT}/php-5.5.17
export COMPOSER_HOME=${IROOT}/php-composer
export PHP_FPM=${PHP_HOME}/sbin/php-fpm
export NGINX_HOME=${IROOT}/nginx

sed -i 's|localhost|'"${DBHOST}"'|g' index.php
sed -i 's|root .*/FrameworkBenchmarks/limonade|root '"${TROOT}"'|g' deploy/nginx.conf
sed -i 's|/usr/local/nginx/|'"${IROOT}"'/nginx/|g' deploy/nginx.conf

$PHP_FPM --fpm-config $FWROOT/config/php-fpm.conf -g $TROOT/deploy/php-fpm.pid
$NGINX_HOME/sbin/nginx -c $TROOT/deploy/nginx.conf
