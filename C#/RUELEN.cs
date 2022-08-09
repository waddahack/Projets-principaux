using System;
using System.Linq;

namespace Jeu_des_allumettes
{
    class Program
    {
        static void Main(string[] args)
        {
            int nbAllumettes = 0, nbAllumettesRestantes = 0, choix = 0;
            bool ordiImbattable = false, joueurCommence = true;

            nbAllumettes = ChoisirNbAllumettes(10);
            nbAllumettesRestantes = nbAllumettes;
            joueurCommence = ChoisirJoueurCommence();
            ordiImbattable = ChoisirOrdiImbattable();

            do
            {
                Console.Clear();
                Console.WriteLine(String.Concat(Enumerable.Repeat("| ", nbAllumettesRestantes)));
                if (choix > 0 && joueurCommence) Console.WriteLine("L'ordi a prit {0} allumette{1}.", choix, choix > 1 ? "s" : "");
                choix = TourDeJeu(joueurCommence, nbAllumettes, nbAllumettesRestantes, ordiImbattable);
                nbAllumettesRestantes -= choix;
                if (EndGame(joueurCommence, nbAllumettesRestantes))
                    break;
                Console.Clear();
                Console.WriteLine(String.Concat(Enumerable.Repeat("| ", nbAllumettesRestantes)));
                if (!joueurCommence) Console.WriteLine("L'ordi a prit {0} allumette{1}.", choix, choix > 1 ? "s" : "");
                choix = TourDeJeu(!joueurCommence, nbAllumettes, nbAllumettesRestantes, ordiImbattable);
                nbAllumettesRestantes -= choix;
                EndGame(!joueurCommence, nbAllumettesRestantes);
            } while (nbAllumettesRestantes > 0);
        }

        static int TourDeJeu(bool tourJoueur, int nbAllumettes, int nbAllumettesRestantes, bool ordiImbattable)
        {
            int choix;
            if (tourJoueur)
                choix = TourJoueur(nbAllumettesRestantes);
            else
                choix = TourOrdi(nbAllumettes, nbAllumettesRestantes, ordiImbattable);
            return choix;
        }

        static int TourJoueur(int nbAllumettesRestantes)
        {
            int choix = 0;
            Console.Write("Nombre d'allumettes que tu veux prendre : ");
            do
            {
                try
                {
                    choix = Convert.ToInt32(Console.ReadLine());
                }
                catch (Exception e) { }
            } while (choix < 1 || choix > 3 || choix > nbAllumettesRestantes);
            return choix;
        }

        static int TourOrdi(int nbAllumettes, int nbAllumettesRestantes, bool ordiImbattable)
        {
            int choix = 1;
            if (ordiImbattable)
            {
                int index = (nbAllumettes - 1) % 4; // 4 = nb max qu'on peut en prendre + 1
                while (nbAllumettes - nbAllumettesRestantes >= index)
                    index += 4;
                choix = index - (nbAllumettes - nbAllumettesRestantes);
                if (choix > 3) // 3 = nb max qu'on peut en prendre
                    choix = 1;
            }
            else
            {
                if (nbAllumettesRestantes <= 4 && nbAllumettesRestantes > 1)
                    choix = nbAllumettesRestantes - 1;
                else if (nbAllumettesRestantes == 1)
                    choix = 1;
                else
                    choix = new Random().Next(1, 3);
            }
            Console.Write(".");
            System.Threading.Thread.Sleep(333);
            Console.Write(".");
            System.Threading.Thread.Sleep(333);
            Console.Write(".");
            System.Threading.Thread.Sleep(333);
            return choix;
        }

        static bool EndGame(bool joueurCommence, int nbAllumettesRestantes)
        {
            if (nbAllumettesRestantes <= 0)
            {
                Console.Clear();
                if (joueurCommence)
                    Console.WriteLine("Perdu...");
                else
                    Console.WriteLine("Gagné !");
                return true;
            }
            return false;
        }

        static bool ChoisirJoueurCommence()
        {
            string reponse;
            bool joueurCommence = true, repondu = true;
            Console.WriteLine("Voulez-vous commencer ?");
            do
            {
                reponse = Console.ReadLine();
                reponse.ToLower();
                repondu = true;
                if (reponse == "o" || reponse == "oui")
                    joueurCommence = true;
                else if ((reponse == "n" || reponse == "non"))
                    joueurCommence = false;
                else
                    repondu = false;
            } while (repondu == false);
            return joueurCommence;
        }

        static int ChoisirNbAllumettes(int min = 0, int max = int.MaxValue)
        {
            int nbAllumettes = -1;
            Console.Write("Nombre d'allumettes en jeu (min 10) : ");
            do
            {
                try
                {
                    nbAllumettes = Convert.ToInt32(Console.ReadLine());
                }
                catch (Exception e) { }
            } while (nbAllumettes < min || nbAllumettes > max);
            return nbAllumettes;
        }

        static bool ChoisirOrdiImbattable()
        {
            string reponse;
            bool ordiImbattable = false, repondu = true;
            Console.WriteLine("Voulez-vous jouer contre l'Imbattable ?");
            do
            {
                reponse = Console.ReadLine();
                reponse.ToLower();
                repondu = true;
                if (reponse == "o" || reponse == "oui")
                    ordiImbattable = true;
                else if ((reponse == "n" || reponse == "non"))
                    ordiImbattable = false;
                else
                    repondu = false;
            } while (repondu == false);
            return ordiImbattable;
        }
    }
}
