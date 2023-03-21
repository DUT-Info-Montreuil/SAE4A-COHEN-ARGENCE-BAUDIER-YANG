<?php
    include_once('connexion.php');
    header('Content-type: application/json; charset=utf-8');
    function inscription() {
        $reponse = array(
            'message' => sous_fonction_inscription()
        );
        echo json_encode($reponse);
    }

    function sous_fonction_inscription()
    {
        $login = htmlspecialchars($_GET['login']);
        if (strlen($login) == 0)
            return 'Nom d\'utilisateur indisponible';
        $verif_login = Connexion::$bdd->prepare('select * from Utilisateurs where login = ?');
        $verif_login->execute(array($login));
        if ($verif_login->rowCount() > 0)
            return 'Nom d\'utilisateur indisponible';
    
        if (!ctype_alpha($_GET['nom']) || !ctype_alpha($_GET['prenom']))
            return 'Nom ou prenom invalide';
        
        if (strlen($_GET['tel']) != 10 || !is_numeric($_GET['tel']))
            return 'Numero de telephone invalide';
            
        $verif_tel = Connexion::$bdd->prepare('select * from Utilisateurs where numeroDeTel = ?');
        $verif_tel->execute(array($_GET['tel']));
        if ($verif_tel->rowCount() > 0)
            return 'Le numero de telephone est dejà utilisee';

        $email = $_GET['email'];
        if (!filter_var($email, FILTER_VALIDATE_EMAIL))
            return 'L\'adressse e-mail est invalide';
        $verif_email = Connexion::$bdd->prepare('select * from Utilisateurs where email = ?');
        $verif_email->execute(array($email));
        if ($verif_email->rowCount() > 0)
            return 'L\'adresse e-mail est dejà utilisee';

        if (!mdp_correcte())
            return '8 caractères minimum avec au moins une lettre minuscule, une lettre majuscule et un chiffre';
    
        if ($_GET['mdp'] != $_GET['conf_mdp'])
            return 'Les mots de passe ne correspondent pas';

        $mdp = password_hash($_GET['mdp'], PASSWORD_DEFAULT);
        $sql = 'INSERT INTO Utilisateurs VALUES(NULL, ?, ?, ?, ?, ?, ?, 1)';
        $statement = Connexion::$bdd->prepare($sql);
        $statement->execute(array($login, $_GET['nom'], $_GET['prenom'], $_GET['tel'], $email, $mdp));
        return 'inscription validee';
    }

    function mdp_correcte()
    {
        $containsLowerCaseLetter  = preg_match('/[a-z]/', $_GET['mdp']);
        $containsUpperCaseLetter  = preg_match('/[A-Z]/', $_GET['mdp']);
        $containsDigit   = preg_match('/\d/', $_GET['mdp']);
        $correctSize = strlen($_GET['mdp']) >= 8;
        return $containsLowerCaseLetter && $containsUpperCaseLetter && $containsDigit && $correctSize;
    }
?>