<?php
session_start();

include_once('connexion.php');
include_once('utilisateur.php');
include_once('burger.php');
include_once('ingredient.php');
include_once('commande.php');
include_once('token.php');

header('Content-Type: application/json');

Connexion::initConnexion();
try {
    if (!empty($_GET['requete'])) {
        if (verif_token()) {
            $requete_trouve = false;
            global $token;
            switch ($token['idType']) {
                case 1:
                    if (admin()) $requete_trouve = true;
                case 2:
                case 3:
                    if (!$requete_trouve) utilisateur();
            }
        } else {
            switch ($_GET['requete']) {
                case "inscription":
                    inscription(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/inscription&login=?&nom=?&prenom=?&tel=?&email=?&mdp=?conf_mdp=?&adresse=?&ville=?&codePostale=?
                    break;
                case "connexion":
                    connexion(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/connexion&login=?&mdp=?
                    break;
                default:
                    message(400, "La requete n'est pas valide, verifiez l'url. Vous devez vous connecter ou token invalide");
            }
        }
    } else {
        message(400, "La requete n'est pas valide, verifiez l'url.");
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

function admin()
{
    include_once('admin/ingredient.php');
    switch ($_GET['requete']) {
        case "admin_ingredient":
            if (isset($_GET['action'])) {
                switch ($_GET['action']) {
                    case "add":
                        add_ingredient(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/admin_ingredient&action=add&nomIngredient=?&prix=?&idType=?
                        return true;
                    case "ajouter_stock":
                        ajouter_stock(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/admin_ingredient&action=ajouter_stock&idIngredient=?&quantite=?
                        return true;
                    case "retirer_stock":
                        retirer_stock(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/admin_ingredient&action=retirer_stock&idIngredient=?&quantite=?
                        return true;
                    case "delete":
                        delete_burger(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&action=delete&idBurger=?
                        return true;
                    default:
                        message(400, "La requete n'est pas valide, verifiez l'url.");
                }
            } else {
                message(400, "La requete n'est pas valide, verifiez l'url. Manque la variable action.");
            }
    }
}

function utilisateur()
{
    switch ($_GET['requete']) {
        case "infos_utilisateur":
            infos_utilisateur(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/infos_utilisateur
            break;
        case "ingredients":
            get_ingredients(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/ingredients
            break;
        case "ingredient":
            get_ingredient(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/ingredient&idIngredient=?
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
                    case "add":
                        add_burger(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&action=add&nomBurger=?&description=?&ingredients=?
                        break;
                    case "update":
                        update_burger(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&action=update&idBurger=?&(valeurs a modifier)
                        break;
                    case "delete":
                        delete_burger(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/burger&action=delete&idBurger=?
                        break;
                    default:
                        message(400, "La requete n'est pas valide, verifiez l'url.");
                }
            } else {
                message(400, "La requete n'est pas valide, verifiez l'url. Manque la variable action.");
            }
            break;
        case "commandes":
            get_commandes(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/commandes
            break;
        case "commande":
            if (isset($_GET['action'])) {
                switch ($_GET['action']) {
                    case "get":
                        get_commande(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/commande&action=get&idCommande=?
                        break;
                    case "add":
                        add_commande(); //http://localhost/SAE4A-COHEN-ARGENCE-BAUDIER-YANG/api/commande&action=add&idBurgers=?,?,?
                        break;
                    default:
                        message(400, "La requete n'est pas valide, verifiez l'url.");
                }
            } else {
                message(400, "La requete n'est pas valide, verifiez l'url. Manque la variable action.");
            }
            break;
        default:
            message(400, "La requete n'est pas valide, verifiez l'url.");
    }
}
