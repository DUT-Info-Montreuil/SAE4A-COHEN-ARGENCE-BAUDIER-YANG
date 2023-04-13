<?php
header('Content-type: application/json; charset=utf-8');

if (constant("lala") != "layn")
    die("wrong constant");

function supp_burger()
{
    if (!isset($_POST['idBurger'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&action=delete&idBurger=?");
        exit();
    }

    $burger = Connexion::$bdd->prepare('select * from Burgers where idBurger = ?');
    $burger->execute(array($_POST['idBurger']));
    if ($burger->rowCount() > 0) {
        $sth = Connexion::$bdd->prepare('DELETE FROM estCompose WHERE idBurger = ?');
        $sth->execute(array($_POST['idBurger']));

        $sth = Connexion::$bdd->prepare('DELETE FROM Burgers WHERE idBurger = ?');
        $sth->execute(array($_POST['idBurger']));
        message(200, "Burger supprime.");
    } else {
        message(404, 'Burger pas trouve ou pas le votre');
    }
}
