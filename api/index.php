<?php
session_start();
    
include_once('connexion.php');
include_once('utilisateur.php');

Connexion::initConnexion();
try{
    if(!empty($_GET['requete'])){
        switch($_GET['requete']){
            case "inscription" : inscription(); break; //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/inscription&login=?&nom=?&prenom=?&tel=?&email=?&mdp=?conf_mdp=?
            case "connexion" : connexion(); break; //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/connexion&login=?&mdp=?
            case "infos_utilisateur" : connexion(); break; //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/infos_utilisateur&idUser=?
            default : throw new Exception ("La requete n'est pas valide, vérifiez l'url");
        }
    } else {
        throw new Exception ("La requete n'est pas valide, vérifiez l'url");
    }
} catch(Exception $e){
    print_r($e->getMessage());
}
?>