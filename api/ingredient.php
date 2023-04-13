<?php
header('Content-type: application/json; charset=utf-8');


function get_ingredients()
{
    $sth = Connexion::$bdd->prepare('select * from Ingredients');
    $sth->execute();
    if ($sth->rowCount() > 0) {
        http_response_code(200);
        echo json_encode($sth->fetchAll());
    } else {
        message(404, 'Erreur dans la base de donnee');
    }
}

function get_ingredient()
{
    if (!isset($_POST['idIngredient'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/ingredient&idIngredient=?");
        exit();
    }
    $sth = Connexion::$bdd->prepare('select * from Ingredients where idIngredient = ?');
    $sth->execute(array($_POST['idIngredient']));
    if ($sth->rowCount() > 0) {
        http_response_code(200);
        echo json_encode($sth->fetch());
    } else {
        message(404, 'Ingredient pas trouve.');
    }
}
