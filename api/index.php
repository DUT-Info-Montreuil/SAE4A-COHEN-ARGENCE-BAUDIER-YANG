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
                    if (isset($_GET['categorie']) && $_GET['categorie'] == 'personnalise')
                        get_burgers_personnalises(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burgers&categorie=personnalise
                    else
                        get_burgers_classiques(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burgers&categorie=classique
                    break;
                case "burger":
                    get_burger(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&idBurger=?
                    break;
                default:
                    http_response_code(400);
                    $reponse = array(
                        'message' => "La requete n'est pas valide, vérifiez l'url."
                    );
                    echo json_encode($reponse);
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
                    http_response_code(400);
                    $reponse = array(
                        'message' => "La requete n'est pas valide, vérifiez l'url. Vous devez vous connecter."
                    );
                    echo json_encode($reponse);
            }
        }
    } else {
        http_response_code(400);
        $reponse = array(
            'message' => "La requete n'est pas valide, vérifiez l'url."
        );
        echo json_encode($reponse);
    }
} catch (Exception $e) {
    http_response_code(400);
    $reponse = array(
        'message' => $e->getMessage()
    );
    echo json_encode($reponse);
}
