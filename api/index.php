<?php
    session_start();
    
    include_once('connexion.php');
    include_once('utilisateur.php');

	Connexion::initConnexion();
    try{
        if(!empty($_GET['requete'])){
            switch($_GET['requete']){
                case "inscription" : inscription(); break;
                default : throw new Exception ("La requete n'est pas valide, vérifiez l'url");
            }
        } else {
            throw new Exception ("La requete n'est pas valide, vérifiez l'url");
        }
    } catch(Exception $e){
        print_r($e->getMessage());
    }
?>