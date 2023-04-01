<?php
include_once('connexion.php');
header('Content-type: application/json; charset=utf-8');

function get_burgers()
{
    global $token;
    $burgers = Connexion::$bdd->prepare('select idBurger, nomBurger, description from Burgers natural join Utilisateurs where idType = 2 or idUser = ?');
    $burgers->execute(array($token['idUser']));
    if ($burgers->rowCount() > 0) {
        http_response_code(200);
        $reponse = $burgers->fetchAll();
        echo json_encode($reponse);
    } else {
        message(404, 'Erreur dans la base de donnee');
    }
}

function get_burgers_classiques()
{
    $burgers = Connexion::$bdd->prepare('select idBurger, nomBurger, description from Burgers natural join Utilisateurs where idType = 2');
    $burgers->execute();
    if ($burgers->rowCount() > 0) {
        http_response_code(200);
        $reponse = $burgers->fetchAll();
        echo json_encode($reponse);
    } else {
        message(404, 'Erreur dans la base de donnee');
    }
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
        echo json_encode($reponse);
    } else {
        message(404, 'Aucun burgers ou idUser incorrecte');
    }
}

function get_burger()
{
    if (isset($_GET['idBurger'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Example : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&action=get&idBurger=?");
        exit();
    }
    global $token;
    $burger = Connexion::$bdd->prepare('select idBurger, nomBurger, description from Burgers natural join Utilisateurs where idBurger = ? and (idUser = ? or idType = 2)');
    $burger->execute(array($_GET['idBurger'], $token['idUser']));
    if ($burger->rowCount() > 0) {
        $burger = $burger->fetch();
        $ingredients = Connexion::$bdd->prepare('select idIngredient, nomIngredient from estCompose natural join Ingredients where idBurger = ?');
        $ingredients->execute(array($_GET['idBurger']));
        $burger['ingredients'] = $ingredients->fetchAll();
        http_response_code(200);
        echo json_encode($burger);
    } else {
        message(404, 'Burger pas trouve');
    }
}
