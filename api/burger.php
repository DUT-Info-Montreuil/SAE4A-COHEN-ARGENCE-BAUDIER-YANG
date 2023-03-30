<?php
include_once('connexion.php');
header('Content-type: application/json; charset=utf-8');

function get_burgers()
{
    $burgers = Connexion::$bdd->prepare('select * from Burgers');
    $burgers->execute();
    if ($burgers->rowCount() > 0) {
        http_response_code(200);
        $reponse = $burgers->fetchAll();
    } else {
        http_response_code(404);
        $reponse = array(
            'message' => "Erreur dans la base de donnee"
        );
    }

    echo json_encode($reponse);
}
