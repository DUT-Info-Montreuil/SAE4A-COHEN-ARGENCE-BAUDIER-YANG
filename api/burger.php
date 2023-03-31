<?php
include_once('connexion.php');
header('Content-type: application/json; charset=utf-8');

function get_burgers_classiques()
{
    $burgers = Connexion::$bdd->prepare('select idBurger, nomBurger, description from Burgers natural join Utilisateurs where idType = 2');
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

function get_burgers_personnalises()
{
    global $token;
    echo $token['idUser'];
    $burgers = Connexion::$bdd->prepare('select * from Burgers where idUser = ?');
    $burgers->execute(array($token['idUser']));
    if ($burgers->rowCount() > 0) {
        http_response_code(200);
        $reponse = $burgers->fetchAll();
    } else {
        http_response_code(404);
        $reponse = array(
            'message' => "Aucun burgers ou idUser incorrecte"
        );
    }

    echo json_encode($reponse);
}

function get_burger()
{
    $burger = Connexion::$bdd->prepare('select * from Burgers where idBurger = ?');
    $burger->execute(array($_GET['idBurger']));
    if ($burger->rowCount() > 0) {
        $burger = $burger->fetch();
        $ingredients = Connexion::$bdd->prepare('select idIngredient, nomIngredient from estCompose natural join Ingredients where idBurger = ?');
        $ingredients->execute(array($_GET['idBurger']));
        $burger['ingredients'] = $ingredients->fetchAll();
        http_response_code(200);
        $reponse = $burger;
    } else {
        http_response_code(404);
        $reponse = array(
            'message' => "Burger pas trouve"
        );
    }

    echo json_encode($reponse);
}
