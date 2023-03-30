<?php
session_start();

include_once('connexion.php');
include_once('utilisateur.php');
include_once('burger.php');

Connexion::initConnexion();
try {
    if (!empty($_GET['requete'])) {
        switch ($_GET['requete']) {
            case "inscription":
                inscription(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/inscription&login=?&nom=?&prenom=?&tel=?&email=?&mdp=?conf_mdp=?
                break; 
            case "connexion":
                connexion(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/connexion&login=?&mdp=?
                break; 
            case "deconnexion":
                deconnexion(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/deconnexion
                break;
            case "infos_utilisateur":
                infos_utilisateur(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/infos_utilisateur
                break; 
            case "burgers":
                if($_GET['categorie'] == 'personnalise')
                    get_burgers_par_idUser(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burgers&categorie=personalise
                else
                    get_burgers_classiques(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burgers&categorie=classique
            break; 
            case "burger":
                get_burger(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&idBurger=?
            break; 
            default:
                throw new Exception("La requete n'est pas valide, vérifiez l'url");
        }
    } else {
        throw new Exception("La requete n'est pas valide, vérifiez l'url");
    }
} catch (Exception $e) {
    print_r($e->getMessage());
}
