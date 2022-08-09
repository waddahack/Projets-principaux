using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;

namespace BlackJack
{
    class Program
    {
        public static Dictionary<string, int> cartes = new Dictionary<string, int>();
        static void Main(string[] args)
        {
            for (int j = 2; j <= 10; j++)
                cartes.Add(Convert.ToString(j), j);
            cartes.Add("Valet", 10);
            cartes.Add("Dame", 10);
            cartes.Add("Roi", 10);
            cartes.Add("As", 0);

            bool rejouer = true;
            string choix;
            Random random;
            List<string> mainJoueur = new List<string>();
            List<string> mainCroupier = new List<string>();
            List<string> paquet = new List<string>();
            for (int i = 0; i < 4; i++)
                foreach (KeyValuePair<string, int> carte in cartes)
                    paquet.Add(carte.Key);
            int cartesTirees;

            do
            {
                rejouer = false;
                mainJoueur.Clear();
                mainCroupier.Clear();
                random = new Random();
                paquet = paquet.OrderBy(x => random.Next()).ToList();
                cartesTirees = 0;

                mainJoueur.Add(paquet[cartesTirees]);
                cartesTirees++;

                for (int i = 0; i < 2; i++)
                {
                    mainCroupier.Add(paquet[cartesTirees]);
                    cartesTirees++;
                }

                do
                {
                    mainJoueur.Add(paquet[cartesTirees]);
                    cartesTirees++;
                    AfficherMains(mainJoueur, mainCroupier);
                    if (CanDraw(mainJoueur))
                    {
                        Console.WriteLine("\nVoulez-vous piocher ? o / n");
                        do
                        {
                            choix = Console.ReadLine();
                        } while (choix != "o" && choix != "n");
                    }
                    else
                        choix = "n";
                    
                } while (choix == "o");

                while (CanDraw(mainCroupier) && GetValue(mainCroupier) < 15)
                {
                    mainCroupier.Add(paquet[cartesTirees]);
                    cartesTirees++;
                }

                AfficherMains(mainJoueur, mainCroupier, false);

                Console.WriteLine();
                if (IsOut(mainJoueur))
                    Console.WriteLine("Vous êtes out.\nPerdu...");
                else if (IsOut(mainCroupier))
                    Console.WriteLine("Le croupier est out.\nGagné !");
                else if (GetValue(mainJoueur) > GetValue(mainCroupier))
                    Console.WriteLine("Votre main est supérieur à celle du croupier.\nGagné !");
                else if (GetValue(mainJoueur) < GetValue(mainCroupier))
                    Console.WriteLine("Votre main est inférieur à celle du croupier.\nPerdu...");
                else
                    Console.WriteLine("Égalité.\nVous perdez...");

                Console.WriteLine("\nVoulez-vous rejouer ? o / n");
                do
                {
                    choix = Console.ReadLine();
                } while (choix != "o" && choix != "n");
                if (choix == "o")
                    rejouer = true;
            } while (rejouer);
        }

        public static int GetValue(List<string> main, bool countWithAHighAs = false)
        {
            int total = 0;
            foreach (string keyCarte in main)
            {
                if(countWithAHighAs && keyCarte == "As")
                {
                    total += 11;
                }
                else if (keyCarte == "As")
                {
                    if (IsOut(main, true))
                        total += 1;
                    else
                        total += 11;
                }
                else
                    total += cartes[keyCarte];
            }
            return total;
        }

        public static bool IsOut(List<string> main, bool countWithAHighAs = false)
        {
            return (GetValue(main, countWithAHighAs) > 21);
        }

        public static bool CanDraw(List<string> main)
        {
            return (GetValue(main) < 21);
        }

        public static void AfficherMains(List<string> mainJoueur, List<string> mainCroupier, bool mainCroupierHidden = true)
        {
            Console.Clear();
            for (int i = 0; i < mainJoueur.Count; i++)
                Console.Write("{0}{1}{2}", i == 0 ? "Votre main : " : "", mainJoueur[i], i != mainJoueur.Count - 1 ? " | " : "\n");
            if(mainCroupierHidden)
                Console.WriteLine("Croupier : " + mainCroupier[0] + " | ?");
            else
                for (int i = 0; i < mainCroupier.Count; i++)
                    Console.Write("{0}{1}{2}", i == 0 ? "Croupier : " : "", mainCroupier[i], i != mainCroupier.Count - 1 ? " | " : "\n");
        }
    }
}
