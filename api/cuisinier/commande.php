<?php
header('Content-type: application/json; charset=utf-8');


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

function valider_commande()
{
    if (!isset($_GET['idCommande'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/valider_commande&idCommande=?");
        exit();
    }
    $commande = Connexion::$bdd->prepare('select * from Commande where idCommande = ?');
    $commande->execute(array($_GET['idCommande']));
    if ($commande->rowCount() > 0) {
        $sth = Connexion::$bdd->prepare('Update Commande set finit = 1 where idCommande = ?');
        $sth->execute(array($_GET['idCommande']));
        message(200, 'Commande terminee.');
    } else {
        message(404, 'Commande pas trouve.');
    }
}
