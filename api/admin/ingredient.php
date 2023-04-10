<?php
header('Content-type: application/json; charset=utf-8');

function add_ingredient()
{
    if (!isset($_GET['nomIngredient'], $_GET['prix'], $_GET['idType'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/admin_ingredient&action=add&nomIngredient=?&prix=?&idType=?");
        exit();
    }

    $sth = Connexion::$bdd->prepare('select * from Ingredients where nomIngredient = ?');
    $sth->execute(array($_GET['nomIngredient']));
    if ($sth->rowCount() > 0) {
        message(404, 'L\'ingredient existe deja');
        exit();
    }

    if (!ctype_alpha($_GET['nomIngredient'])) {
        message(404, 'Nom d\'ingredient invalide');
        exit();
    }

    if (!is_numeric($_GET['prix'])) {
        message(404, 'Prix invalide');
        exit();
    }

    $sth = Connexion::$bdd->prepare('select * from TypesIngredient where idType = ?');
    $sth->execute(array($_GET['idType']));
    if ($sth->rowCount() == 0) {
        message(404, 'L\'idType n\'existe pas');
        exit();
    }

    $sth = Connexion::$bdd->prepare('insert into Ingredients Values(NULL, ?, ?, 0, ?)');
    $sth->execute(array($_GET['nomIngredient'], $_GET['prix'], $_GET['idType']));
    message(200, "Ingredient ajoute.");
}

function ajouter_stock()
{
    gestion_stock('+');
}

function retirer_stock()
{
    gestion_stock('-');
}


function gestion_stock($operation)
{
    if (!isset($_GET['idIngredient'], $_GET['quantite'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/admin_ingredient&action=ajouter_stock&idIngredient=?&quantite=?");
        exit();
    }

    $sth = Connexion::$bdd->prepare('select stock from Ingredients where idIngredient = ?');
    $sth->execute(array($_GET['idIngredient']));
    if ($sth->rowCount() < 0) {
        message(404, 'L\'ingredient n\'existe pas');
        exit();
    }

    if (!is_numeric($_GET['quantite']) || intval($_GET['quantite']) <= 0) {
        message(404, 'Quantite invalide');
        exit();
    }

    $stock = intval($sth->fetch()['stock']);
    if ($operation == '+')
        $stock += intval($_GET['quantite']);
    else
        $stock -= intval($_GET['quantite']);

    $sth = Connexion::$bdd->prepare('update Ingredients set stock = ? where idIngredient = ?');
    $sth->execute(array($stock, $_GET['idIngredient']));
    message(200, "Quantite mise a jour.");
}
