<?php
header('Content-type: application/json; charset=utf-8');


if (constant("lala") != "layn")
    die("wrong constant");

function get_commandes_a_faire()
{
    $commandes = Connexion::$bdd->prepare('select idCommande, finit, dateCommande, prix from Commande where finit = 0 order by dateCommande');
    $commandes->execute();
    if ($commandes->rowCount() > 0) {
        http_response_code(200);
        $reponse = $commandes->fetchAll();
        echo json_encode($reponse);
    } else {
        message(404, 'Erreur dans la base de donnee');
    }
}

function contenu_commande()
{
    if (!isset($_POST['idCommande'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/contenu_commande&idCommande=?");
        exit();
    }
    $sth = Connexion::$bdd->prepare('select idCommande, finit, dateCommande, prix from Commande where idCommande = ?');
    $sth->execute(array($_POST['idCommande']));
    if ($sth->rowCount() > 0) {
        $commande = $sth->fetch();
        $sth = Connexion::$bdd->prepare('select idBurger, nomBurger, prix, quantite, (prix * quantite) as total from Contient natural join Burgers where idCommande = ?');
        $sth->execute(array($_POST['idCommande']));
        $commande['burgers'] = $sth->fetchAll();
        http_response_code(200);
        echo json_encode($commande);
    } else {
        message(404, 'Commande pas trouve');
    }
}

function contenu_burger()
{
    if (!isset($_POST['idBurger'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/contenu_burger&idBurger=?");
        exit();
    }
    global $token;
    $burger = Connexion::$bdd->prepare('select idBurger, nomBurger, description, prix from Burgers natural join Utilisateurs where idBurger = ? and (idUser = ? or idType = 2)');
    $burger->execute(array($_POST['idBurger'], $token['idUser']));
    if ($burger->rowCount() > 0) {
        $burger = $burger->fetch();
        $ingredients = Connexion::$bdd->prepare('select idIngredient, nomIngredient, quantite, prix, (prix*quantite) as total from estCompose natural join Ingredients where idBurger = ?');
        $ingredients->execute(array($_POST['idBurger']));
        $burger['ingredients'] = $ingredients->fetchAll();
        http_response_code(200);
        echo json_encode($burger);
    } else {
        message(404, 'Burger pas trouve');
    }
}

function valider_commande()
{
    if (!isset($_POST['idCommande'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/valider_commande&idCommande=?");
        exit();
    }
    $commande = Connexion::$bdd->prepare('select * from Commande where idCommande = ?');
    $commande->execute(array($_POST['idCommande']));
    if ($commande->rowCount() > 0) {
        $sth = Connexion::$bdd->prepare('Update Commande set finit = 1 where idCommande = ?');
        $sth->execute(array($_POST['idCommande']));
        message(200, 'Commande terminee.');
    } else {
        message(404, 'Commande pas trouve.');
    }
}
