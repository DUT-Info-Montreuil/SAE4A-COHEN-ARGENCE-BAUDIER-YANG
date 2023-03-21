<?php
    class Connexion {
        static public $bdd;
        
        static private $id = "dutinfopw201652";
        static private $dbname = "dutinfopw201652";
        static private $mdp = "suzasasa";
        static private $adress = "database-etudiants.iut.univ-paris8.fr";
            
        public static function initConnexion() {
            self :: $bdd = new PDO ('mysql:host='.self::$adress.';dbname='.self::$dbname.'', self::$id, self::$mdp);
        }
    }
?>