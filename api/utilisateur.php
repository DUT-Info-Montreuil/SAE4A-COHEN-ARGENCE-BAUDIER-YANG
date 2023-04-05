<?php
include_once('connexion.php');
header('Content-type: application/json; charset=utf-8');

function mdp_correcte()
{
    $containsLowerCaseLetter  = preg_match('/[a-z]/', $_GET['mdp']);
    $containsUpperCaseLetter  = preg_match('/[A-Z]/', $_GET['mdp']);
    $containsDigit   = preg_match('/\d/', $_GET['mdp']);
    $correctSize = strlen($_GET['mdp']) >= 8;
    return $containsLowerCaseLetter && $containsUpperCaseLetter && $containsDigit && $correctSize;
}

function adresse_correcte() {
    // Vérifier la validité de la syntaxe de l'adresse
    if (!preg_match('/^[a-zA-Z0-9\s-]+$/', $_GET['adresse'])) {
        return false;
    }

    // Vérifier la validité de la syntaxe de la ville
    if (!preg_match('/^[a-zA-Z\s-]+$/', $_GET['ville'])) {
        return false;
    }

    // Vérifier la validité du code postal
    if (!preg_match('/^(0[1-9]|[1-9][0-9])[0-9]{3}$/', $_GET['codePostale'])) {
        return false;
    }

    return true;
}

function inscription()
{
    if (!isset($_GET['login'], $_GET['nom'], $_GET['prenom'], $_GET['tel'], $_GET['email'], $_GET['mdp'], $_GET['conf_mdp'], $_GET['adresse'], $_GET['ville'], $_GET['codePostale'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/inscription&login=?&nom=?&prenom=?&tel=?&email=?&mdp=?conf_mdp=?&adresse=?&ville=?&codePostale=?");
        exit();
    }

    $login = htmlspecialchars($_GET['login']);
    if (strlen($login) == 0 || is_numeric($login)) {
        message(404, 'Nom d\'utilisateur indisponible');
        exit();
    }
    $verif_login = Connexion::$bdd->prepare('select * from Utilisateurs where login = ?');
    $verif_login->execute(array($login));
    if ($verif_login->rowCount() > 0) {
        message(404, 'Nom d\'utilisateur indisponible');
        exit();
    }

    if (!ctype_alpha($_GET['nom']) || !ctype_alpha($_GET['prenom'])) {
        message(404, 'Nom ou prenom invalide');
        exit();
    }

    if (strlen($_GET['tel']) != 10 || !is_numeric($_GET['tel'])) {
        message(404, 'Numero de telephone invalide');
        exit();
    }

    $verif_tel = Connexion::$bdd->prepare('select * from Utilisateurs where tel = ?');
    $verif_tel->execute(array($_GET['tel']));
    if ($verif_tel->rowCount() > 0) {
        message(404, 'Le numero de telephone est dejà utilisee');
        exit();
    }


    if (!filter_var($_GET['email'], FILTER_VALIDATE_EMAIL)) {
        message(404, 'L\'adressse e-mail est invalide');
        exit();
    }

    $verif_email = Connexion::$bdd->prepare('select * from Utilisateurs where email = ?');
    $verif_email->execute(array($_GET['email']));
    if ($verif_email->rowCount() > 0) {
        message(404, 'L\'adresse e-mail est dejà utilisee');
        exit();
    }


    if (!mdp_correcte()) {
        message(404, '8 caractères minimum avec au moins une lettre minuscule, une lettre majuscule et un chiffre');
        exit();
    }


    if ($_GET['mdp'] != $_GET['conf_mdp']) {
        message(404, 'Les mots de passe ne correspondent pas');
        exit();
    }

    if (!adresse_correcte()) {
        message(404, 'L\'adresse est incorrecte');
        exit();
    }

    $mdp = password_hash($_GET['mdp'], PASSWORD_DEFAULT);
    $adresse = $_GET['adresse'] . ' ' . $_GET['ville'] . ' ' . $_GET['codePostale'];
    $statement = Connexion::$bdd->prepare('INSERT INTO Utilisateurs VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, 3)');
    $statement->execute(array($login, $_GET['nom'], $_GET['prenom'], $_GET['tel'], $_GET['email'], $mdp, $adresse));
    message(200, 'inscription validee');
}

function connexion()
{
    if (!isset($_GET['login'], $_GET['mdp'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/connexion&login=?&mdp=?");
        exit();
    }
    $login = htmlspecialchars($_GET['login']);
    $verif_login = Connexion::$bdd->prepare('select idUser, password, idType from Utilisateurs where login = :login or email = :login or tel = :login');
    $verif_login->bindParam(':login', $login);
    $verif_login->execute();
    $infos = $verif_login->fetch();
    if ($verif_login->rowCount() == 1 && password_verify($_GET['mdp'], $infos['password'])) {
        creer_token($infos['idUser'], $infos['idType']);
    } else {
        message(404, "Login ou mot de passe incorrect");
    }
}

function infos_utilisateur()
{
    global $token;
    $infos = Connexion::$bdd->prepare('select idUser, login, nom, prenom, tel, email, adresse, idType from Utilisateurs where idUser = ?');
    $infos->execute(array($token['idUser']));
    if ($infos->rowCount() == 0) {
        message(404, "Utilisateur pas trouve");
    } else {
        http_response_code(200);
        $reponse = $infos->fetch();
        echo json_encode($reponse);
    }
}
