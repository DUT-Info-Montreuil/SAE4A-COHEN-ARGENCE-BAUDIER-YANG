<?php
header('Content-type: application/json; charset=utf-8');

function get_burgers()
{
    global $token;
    $burgers = Connexion::$bdd->prepare('select idBurger, nomBurger, description, prix from Burgers natural join Utilisateurs where idType = 2 or idUser = ?');
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
    $burgers = Connexion::$bdd->prepare('select idBurger, nomBurger, description, prix from Burgers natural join Utilisateurs where idType = 2');
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
    $burgers = Connexion::$bdd->prepare('select idBurger, nomBurger, description, prix from Burgers where idUser = ?');
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
    if (!isset($_GET['idBurger'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&action=get&idBurger=?");
        exit();
    }
    global $token;
    $burger = Connexion::$bdd->prepare('select idBurger, nomBurger, description, prix from Burgers natural join Utilisateurs where idBurger = ? and (idUser = ? or idType = 2)');
    $burger->execute(array($_GET['idBurger'], $token['idUser']));
    if ($burger->rowCount() > 0) {
        $burger = $burger->fetch();
        $ingredients = Connexion::$bdd->prepare('select idIngredient, nomIngredient, quantite, prix, (prix*quantite) as total from estCompose natural join Ingredients where idBurger = ?');
        $ingredients->execute(array($_GET['idBurger']));
        $burger['ingredients'] = $ingredients->fetchAll();
        http_response_code(200);
        echo json_encode($burger);
    } else {
        message(404, 'Burger pas trouve');
    }
}

function add_burger()
{
    if (!isset($_GET['nomBurger'], $_GET['description'], $_GET['ingredients'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : /http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&action=add&nomBurger=?&description=?&ingredients=?");
        exit();
    }

    Connexion::$bdd->beginTransaction();

    try {
        global $token;
        $sth = Connexion::$bdd->prepare('insert into Burgers Values(NULL, ?, ?, ?, 0)');
        $sth->execute(array($_GET['nomBurger'], $_GET['description'], $token['idUser']));

        $id = Connexion::$bdd->lastInsertId();
        add_ingredients_dans_burger($id);
        http_response_code(200);
        $burger = Connexion::$bdd->prepare('select idBurger, nomBurger, description, prix from Burgers natural join Utilisateurs where idBurger = ? and (idUser = ? or idType = 2)');
        $burger->execute(array($id, $token['idUser']));
        echo json_encode($burger->fetch());
        Connexion::$bdd->commit();
    } catch (PDOException $e) {
        Connexion::$bdd->rollBack();
        throw $e;
    }
}

function update_burger()
{
    if (!isset($_GET['idBurger'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&action=update&idBurger=?&(valeurs a modifier)");
        exit();
    }

    global $token;
    $sth = Connexion::$bdd->prepare('select * from Burgers where idBurger = ? and idUser = ?');
    $sth->execute(array($_GET['idBurger'], $token['idUser']));
    if ($sth->rowCount() == 0) {
        message(404, "Pas votre burger.");
        exit();
    }

    if (isset($_GET['nomBurger']) || isset($_GET['description'])) {
        $sql = 'update Burgers set ';
        if (isset($_GET['nomBurger'])) {
            $sql = $sql . 'nomBurger = :nomBurger';
        }
        if (isset($_GET['description'])) {
            if (isset($_GET['nomBurger'])) {
                $sql = $sql . ', ';
            }
            $sql = $sql . ' description = :description';
        }
        $sql = $sql . ' where idBurger = :idBurger';

        $sth = Connexion::$bdd->prepare($sql);
        if (isset($_GET['nomBurger']))
            $sth->bindParam(':nomBurger', $_GET['nomBurger'], PDO::PARAM_STR);
        if (isset($_GET['description']))
            $sth->bindParam(':description', $_GET['description'], PDO::PARAM_STR);
        $sth->bindParam(':idBurger', $_GET['idBurger'], PDO::PARAM_INT);
        $sth->execute();
    }

    if (isset($_GET['ingredients'])) {
        $sth = Connexion::$bdd->prepare('DELETE FROM estCompose WHERE idBurger = ?');
        $sth->execute(array($_GET['idBurger']));
        add_ingredients_dans_burger($_GET['idBurger']);
    }
    message(200, "Burger modifie.");
}

function add_ingredients_dans_burger($id)
{
    $array = explode(",", $_GET['ingredients']);
    $sql = 'insert into estCompose Values';

    $arr = array();
    foreach ($array as $key) {
        if (array_key_exists($key, $arr)) {
            $arr[$key]++;
        } else {
            $arr[$key] = 1;
        }
    }

    foreach ($arr as $idIngredient => $quantite) {
        $sql = $sql . '(' . $id . ', ' . $idIngredient . ', ' . $quantite . '),';
    }
    $sql = substr($sql, 0, strlen($sql) - 1);

    $sth = Connexion::$bdd->prepare($sql);
    $sth->execute();

    $ingredients = Connexion::$bdd->prepare('select idIngredient, nomIngredient, quantite, prix, (prix*quantite) as total from estCompose natural join Ingredients where idBurger = ?');
    $ingredients->execute(array($id));

    $prix = 0;
    foreach ($ingredients as $ingredient) {
        $prix += $ingredient['total'];
    }
    $sth = Connexion::$bdd->prepare('update Burgers set prix = ? where idBurger = ?');
    $sth->execute(array($prix, $id));
}

function delete_burger()
{
    if (!isset($_GET['idBurger'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&action=delete&idBurger=?");
        exit();
    }
    global $token;
    $burger = Connexion::$bdd->prepare('select * from Burgers where idBurger = ? and idUser = ?');
    $burger->execute(array($_GET['idBurger'], $token['idUser']));
    if ($burger->rowCount() > 0) {
        $sth = Connexion::$bdd->prepare('DELETE FROM estCompose WHERE idBurger = ?');
        $sth->execute(array($_GET['idBurger']));

        $sth = Connexion::$bdd->prepare('DELETE FROM Burgers WHERE idBurger = ?');
        $sth->execute(array($_GET['idBurger']));
        message(200, "Burger supprime.");
    } else {
        message(404, 'Burger pas trouve ou pas le votre');
    }
}
