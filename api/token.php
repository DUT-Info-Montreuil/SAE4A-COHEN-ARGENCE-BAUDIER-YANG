<?php

declare(strict_types=1);

use Firebase\JWT\JWT;
use Firebase\JWT\Key;

require_once('../vendor/autoload.php');

$secret_Key  = '68V0zWFrS72GbpPreidkQFLfj4v9m3Ti+DXc8OB0gcM=';

$token;

function creer_token($idUser, $idType)
{
    //$date   = new DateTimeImmutable();
    //$expire_at     = $date->modify('+6 minutes')->getTimestamp();
    //$domainName = "your.domain.name";
    $request_data = [
        //'iat'  => $date->getTimestamp(),
        //'iss'  => $domainName,
        //'nbf'  => $date->getTimestamp(),
        //'exp'  => $expire_at,
        'idUser' => $idUser,
        'idType' => $idType,
    ];
    global $secret_Key;

    $reponse = array(
        'token' => JWT::encode(
            $request_data,
            $secret_Key,
            'HS512'
        )
    );
    echo json_encode($reponse);
}

function verif_token()
{
    $headers = apache_request_headers();
    if (!isset($headers['authorization']) || !preg_match('/Bearer\s(\S+)/', $headers['authorization'], $matches)) {
        header('HTTP/1.0 400 Bad Request');
        exit;
    } else {
        $jwt = $matches[1];
        if (!$jwt) {
            header('HTTP/1.0 400 Bad Request');
            //exit;
        } else {
            global $secret_Key;
            global $token;
            $token = (array) JWT::decode($jwt, new Key($secret_Key, 'HS512'));
            /*$now = new DateTimeImmutable();
            $serverName = "your.domain.name";

            if ($token->iss !== $serverName ||
                $token->nbf > $now->getTimestamp() ||
                $token->exp < $now->getTimestamp())
            {
               header('HTTP/1.1 401 Unauthorized');
                exit;
            }*/

            return true;
        }
    }
}
