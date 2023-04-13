<?php
header('Content-type: application/json; charset=utf-8');


if (constant("lala") != "layn")
    die("wrong constant");

function mdp_correcte()
{
    $containsLowerCaseLetter  = preg_match('/[a-z]/', $_POST['mdp']);
    $containsUpperCaseLetter  = preg_match('/[A-Z]/', $_POST['mdp']);
    $containsDigit   = preg_match('/\d/', $_POST['mdp']);
    $correctSize = strlen($_POST['mdp']) >= 8;
    return $containsLowerCaseLetter && $containsUpperCaseLetter && $containsDigit && $correctSize;
}

function adresse_correcte()
{
    // Vérifier la validité de la syntaxe de l'adresse
    if (!preg_match('/^[a-zA-Z0-9\s-]+$/', $_POST['adresse'])) {
        return false;
    }

    // Vérifier la validité de la syntaxe de la ville
    if (!preg_match('/^[a-zA-Z\s-]+$/', $_POST['ville'])) {
        return false;
    }

    // Vérifier la validité du code postal
    if (!preg_match('/^(0[1-9]|[1-9][0-9])[0-9]{3}$/', $_POST['codePostale'])) {
        return false;
    }

    return true;
}

function inscription()
{
    if (!isset($_POST['login'], $_POST['nom'], $_POST['prenom'], $_POST['tel'], $_POST['email'], $_POST['mdp'], $_POST['conf_mdp'], $_POST['adresse'], $_POST['ville'], $_POST['codePostale'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/inscription&login=?&nom=?&prenom=?&tel=?&email=?&mdp=?conf_mdp=?&adresse=?&ville=?&codePostale=?");
        exit();
    }

    $login = htmlspecialchars($_POST['login']);
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

    if (!ctype_alpha($_POST['nom']) || !ctype_alpha($_POST['prenom'])) {
        message(404, 'Nom ou prenom invalide');
        exit();
    }

    if (strlen($_POST['tel']) != 10 || !is_numeric($_POST['tel'])) {
        message(404, 'Numero de telephone invalide');
        exit();
    }

    $verif_tel = Connexion::$bdd->prepare('select * from Utilisateurs where tel = ?');
    $verif_tel->execute(array($_POST['tel']));
    if ($verif_tel->rowCount() > 0) {
        message(404, 'Le numero de telephone est dejà utilisee');
        exit();
    }


    if (!filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)) {
        message(404, 'L\'adressse e-mail est invalide');
        exit();
    }

    $verif_email = Connexion::$bdd->prepare('select * from Utilisateurs where email = ?');
    $verif_email->execute(array($_POST['email']));
    if ($verif_email->rowCount() > 0) {
        message(404, 'L\'adresse e-mail est dejà utilisee');
        exit();
    }


    if (!mdp_correcte()) {
        message(404, '8 caractères minimum avec au moins une lettre minuscule, une lettre majuscule et un chiffre');
        exit();
    }


    if ($_POST['mdp'] != $_POST['conf_mdp']) {
        message(404, 'Les mots de passe ne correspondent pas');
        exit();
    }

    if (!adresse_correcte()) {
        message(404, 'L\'adresse est incorrecte');
        exit();
    }

    $mdp = password_hash($_POST['mdp'], PASSWORD_DEFAULT);
    $adresse = $_POST['adresse'] . ' ' . $_POST['ville'] . ' ' . $_POST['codePostale'];
    $statement = Connexion::$bdd->prepare('INSERT INTO Utilisateurs VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, 3)');
    $statement->execute(array($login, $_POST['nom'], $_POST['prenom'], $_POST['tel'], $_POST['email'], $mdp, $adresse));
    message(200, 'inscription validee');
}

function connexion()
{
    if (!isset($_POST['login'], $_POST['mdp'])) {
        message(400, "La requete n'est pas valide, vérifiez l'url. Exemple : http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/connexion&login=?&mdp=?");
        exit();
    }
    $login = htmlspecialchars($_POST['login']);
    $verif_login = Connexion::$bdd->prepare('select idUser, password, idType from Utilisateurs where login = :login or email = :login or tel = :login');
    $verif_login->bindParam(':login', $login);
    $verif_login->execute();
    $infos = $verif_login->fetch();
    if ($verif_login->rowCount() == 1 && password_verify($_POST['mdp'], $infos['password'])) {
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
