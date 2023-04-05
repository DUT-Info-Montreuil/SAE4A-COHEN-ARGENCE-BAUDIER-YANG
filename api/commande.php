<?php
include_once('connexion.php');
header('Content-type: application/json; charset=utf-8');


function get_commandes()
{
    global $token;
    $burgers = Connexion::$bdd->prepare('select idCommande, finit, dateCommande, prix from Commande where idUser = ?');
    $burgers->execute(array($token['idUser']));
    if ($burgers->rowCount() > 0) {
        http_response_code(200);
        $reponse = $burgers->fetchAll();
        echo json_encode($reponse);
    } else {
        message(404, 'Erreur dans la base de donnee');
    }
}

function get_commande()
{
    if (!isset($_GET['idCommande'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Example : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/commande&action=get&idCommande=?");
        exit();
    }
    global $token;
    $sth = Connexion::$bdd->prepare('select idCommande, finit, dateCommande, prix from Commande where idCommande = ? and idUser = ?');
    $sth->execute(array($_GET['idCommande'], $token['idUser']));
    if ($sth->rowCount() > 0) {
        $commande = $sth->fetch();
        $sth = Connexion::$bdd->prepare('select idBurger, nomBurger, prix, quantite, (prix * quantite) as total from Contient natural join Burgers where idCommande = ?');
        $sth->execute(array($_GET['idCommande']));
        $commande['burgers'] = $sth->fetchAll();
        http_response_code(200);
        echo json_encode($commande);
    } else {
        message(404, 'Commande pas trouve');
    }
}

function add_commande()
{
    if (!isset($_GET['idBurgers'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Example : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/commande&action=add&idBurgers=?,?,?");
        exit();
    }

    Connexion::$bdd->beginTransaction();

    try {
        global $token;
        $sth = Connexion::$bdd->prepare('insert into Commande(idUser) Values(?)');
        $sth->execute(array($token['idUser']));

        $id = Connexion::$bdd->lastInsertId();
        $array = explode(",", $_GET['idBurgers']);
        $sql = 'insert into Contient Values';

        $arr = array();
        foreach ($array as $key) {
            if (array_key_exists($key, $arr)) {
                $arr[$key]++;
            } else {
                $arr[$key] = 1;
            }
        }

        foreach ($arr as $idBurger => $quantite) {
            $sql = $sql . '(' . $id . ', ' . $idBurger . ', ' . $quantite . '),';
        }
        $sql = substr($sql, 0, strlen($sql) - 1);

        $sth = Connexion::$bdd->prepare($sql);
        $sth->execute();

        $burgers = Connexion::$bdd->prepare('select idBurger, nomBurger, prix, quantite, (prix * quantite) as total from Contient natural join Burgers where idCommande = ?');
        $burgers->execute(array($id));

        $prix = 0;
        foreach ($burgers as $burger) {
            $prix += $burger['total'];
        }
        $sth = Connexion::$bdd->prepare('update Commande set prix = ? where idCommande = ?');
        $sth->execute(array($prix, $id));

        Connexion::$bdd->commit();
    } catch (PDOException $e) {
        Connexion::$bdd->rollBack();
        throw $e;
    }

    message(200, "Commande ajoute.");
}
