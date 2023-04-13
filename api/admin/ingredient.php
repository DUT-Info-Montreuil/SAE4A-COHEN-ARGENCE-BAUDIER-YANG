<?php
header('Content-type: application/json; charset=utf-8');

function add_ingredient()
{
    if (!isset($_POST['nomIngredient'], $_POST['prix'], $_POST['idType'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/admin_ingredient&action=add&nomIngredient=?&prix=?&idType=?");
        exit();
    }

    $sth = Connexion::$bdd->prepare('select * from Ingredients where nomIngredient = ?');
    $sth->execute(array($_POST['nomIngredient']));
    if ($sth->rowCount() > 0) {
        message(404, 'L\'ingredient existe deja');
        exit();
    }

    if (!ctype_alpha($_POST['nomIngredient'])) {
        message(404, 'Nom d\'ingredient invalide');
        exit();
    }

    if (!is_numeric($_POST['prix'])) {
        message(404, 'Prix invalide');
        exit();
    }

    $sth = Connexion::$bdd->prepare('select * from TypesIngredient where idType = ?');
    $sth->execute(array($_POST['idType']));
    if ($sth->rowCount() == 0) {
        message(404, 'L\'idType n\'existe pas');
        exit();
    }

    $sth = Connexion::$bdd->prepare('insert into Ingredients Values(NULL, ?, ?, 0, ?)');
    $sth->execute(array($_POST['nomIngredient'], $_POST['prix'], $_POST['idType']));
    message(200, "Ingredient ajoute.");
}

function delete_ingredient()
{
    if (!isset($_POST['idIngredient'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/admin_ingredient&action=delete&idIngredient=?");
        exit();
    }

    $sth = Connexion::$bdd->prepare('select * from Ingredients where idIngredient =  ?');
    $sth->execute(array($_POST['idIngredient']));
    if ($sth->rowCount() > 0) {
        $sth = Connexion::$bdd->prepare('DELETE FROM Ingredients WHERE idIngredient = ?');
        $sth->execute(array($_POST['idIngredient']));
        message(200, "Ingredient supprime.");
    } else {
        message(404, 'Ingredient pas trouve');
    }
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
    if (!isset($_POST['idIngredient'], $_POST['quantite'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/admin_ingredient&action=ajouter_stock&idIngredient=?&quantite=?");
        exit();
    }

    $sth = Connexion::$bdd->prepare('select stock from Ingredients where idIngredient = ?');
    $sth->execute(array($_POST['idIngredient']));
    if ($sth->rowCount() < 0) {
        message(404, 'L\'ingredient n\'existe pas');
        exit();
    }

    if (!is_numeric($_POST['quantite']) || intval($_POST['quantite']) <= 0) {
        message(404, 'Quantite invalide');
        exit();
    }

    $stock = intval($sth->fetch()['stock']);
    if ($operation == '+')
        $stock += intval($_POST['quantite']);
    else
        $stock -= intval($_POST['quantite']);

    $sth = Connexion::$bdd->prepare('update Ingredients set stock = ? where idIngredient = ?');
    $sth->execute(array($stock, $_POST['idIngredient']));
    message(200, "Quantite mise a jour.");
}
