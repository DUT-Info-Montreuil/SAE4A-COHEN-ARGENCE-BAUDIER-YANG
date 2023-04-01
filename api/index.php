<?php
session_start();

include_once('connexion.php');
include_once('utilisateur.php');
include_once('burger.php');
include_once('token.php');

header('Content-Type: application/json');

Connexion::initConnexion();
try {
    if (!empty($_GET['requete'])) {
        if (verif_token()) {
            switch ($_GET['requete']) {
                case "infos_utilisateur":
                    infos_utilisateur(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/infos_utilisateur
                    break;
                case "burgers":
                    if (isset($_GET['categorie']))
                        if ($_GET['categorie'] == 'personnalise')
                            get_burgers_personnalises(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burgers&categorie=personnalise
                        else
                            get_burgers_classiques(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burgers&categorie=classique
                    else
                        get_burgers();
                    break;
                case "burger":
                    if (isset($_GET['action'])) {
                        switch ($_GET['action']) {
                            case "get":
                                get_burger(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&action=get&idBurger=?
                                break;
                            default:
                                message(400, "La requete n'est pas valide, vérifiez l'url.");
                        }
                    } else {
                        message(400, "La requete n'est pas valide, vérifiez l'url. Manque la variable action.");
                    }
                    break;
                default:
                    message(400, "La requete n'est pas valide, vérifiez l'url.");
            }
        } else {
            switch ($_GET['requete']) {
                case "inscription":
                    inscription(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/inscription&login=?&nom=?&prenom=?&tel=?&email=?&mdp=?conf_mdp=?
                    break;
                case "connexion":
                    connexion(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/connexion&login=?&mdp=?
                    break;
                default:
                    message(400, "La requete n'est pas valide, vérifiez l'url. Vous devez vous connecter ou token invalide");
            }
        }
    } else {
        message(400, "La requete n'est pas valide, vérifiez l'url.");
    }
} catch (Exception $e) {
    message(400, $e->getMessage());
}

function message($code, $message)
{
    http_response_code($code);
    $reponse = array(
        'message' => $message
    );
    echo json_encode($reponse);
}
