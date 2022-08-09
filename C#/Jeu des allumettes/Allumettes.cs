using System;
using System.Collections.Generic;
using System.Text;

namespace CnamCours
{
    class Allumettes
    {
        public static void Jeu()
        {
            int nbAllumettes;
            int nbAllumettesRestantes;

            int choixJoueur;
            int choixOrdinateur;
            Random rand = new Random();

            bool joueur = true;

            Console.WriteLine("+--------------------------------------------------------+");
            Console.WriteLine("+------------------ Jeu des allumettes ------------------+");
            Console.WriteLine("+--------------------------------------------------------+");

            Console.Write("Avec combien d'allumettes comptez vous jouer ? : ");
            nbAllumettesRestantes = nbAllumettes = int.Parse(Console.ReadLine());
            AfficherAllumettes(true,nbAllumettes,nbAllumettesRestantes);
            
            while(nbAllumettesRestantes > 0)
            {
                if(joueur)
                {
                    Console.WriteLine("Combien d'allumettes souhaitez vous prendre ? (1, 2 ou 3)");
                    do
                    {
                        choixJoueur = int.Parse(Console.ReadLine());
                        if (choixJoueur > 0 && choixJoueur < 4 && choixJoueur < nbAllumettesRestantes) break;
                           
                        Console.WriteLine("Veuillez chosir un nombre d'allumette valide. (1, 2 ou 3)");
                    }
                    while (choixJoueur <= 0 || choixJoueur >= 4 || choixJoueur > nbAllumettesRestantes);

                    nbAllumettesRestantes -= choixJoueur;
                    Console.WriteLine("Vous avez retiré {0} allumettes.", choixJoueur);
                    AfficherAllumettes(false, nbAllumettes, nbAllumettesRestantes);
                    joueur = !joueur;
                }
                else
                {
                    if (nbAllumettesRestantes <= 4 && nbAllumettesRestantes > 1) choixOrdinateur = nbAllumettesRestantes - 1;
                    else if (nbAllumettesRestantes == 1) choixOrdinateur = 1;
                    else choixOrdinateur = rand.Next(2)+1;

                    nbAllumettesRestantes -= choixOrdinateur;
                    Console.WriteLine("L'ordinateur a retiré {0} allumettes.", choixOrdinateur);
                    AfficherAllumettes(false, nbAllumettes, nbAllumettesRestantes);
                    joueur = !joueur;
                }
            }

            if (!joueur) Console.WriteLine("Vous avez perdu...");
            else Console.WriteLine("Vous avez gagné !");
        }

        static void AfficherAllumettes(bool debut, int nbAllumettes, int nbAllumettesRestantes)
        {
            string visuAllumettes = "";

            if (debut) for (int i = 0; i < nbAllumettes; i++) visuAllumettes += "|";
            else
            {
                int diffAllumettes = nbAllumettes - nbAllumettesRestantes;
                for (int i = 0; i < diffAllumettes; i++) visuAllumettes += " ";
                for (int i = diffAllumettes; i < nbAllumettes; i++) visuAllumettes += "|";
            }

            Console.WriteLine(visuAllumettes);
        }

        public static void Main(string[] args)
        {
            Jeu();
        }
    }
}
