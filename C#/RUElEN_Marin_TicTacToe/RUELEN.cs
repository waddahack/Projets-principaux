using System;
using System.Collections.Generic;
using System.Linq;

namespace TicTacToeGroupe
{
    class Program
    {
        static void Main(string[] args)
        {
            Dictionary<string, string> mapOriginal = new Dictionary<string, string>();
            for (int i = 0; i < 3; i++)
                for (int j = 1; j <= 3; j++)
                    mapOriginal.Add(Convert.ToChar(65 + i) + Convert.ToString(j), " ");
            Dictionary<string, string> map = new Dictionary<string, string>();

            List<String> remainingCoord;
            Random random;
            string choice;
            bool rejouer;

            do
            {
                map.Clear();
                foreach (KeyValuePair<string, string> coord in mapOriginal)
                    map.Add(coord.Key, coord.Value);
                remainingCoord = map.Keys.ToList();
                random = new Random();
                choice = "";

                while (remainingCoord.Any())
                {
                    DisplayMap(map);
                    Console.WriteLine("Tappez une coordonnée :");
                    do
                    {
                        choice = Console.ReadLine();
                        choice = choice.ToUpperInvariant();
                    } while (!remainingCoord.Contains(choice));

                    map[choice] = "x";
                    remainingCoord.Remove(choice);

                    if (IsWon(map, choice))
                    {
                        DisplayMap(map);
                        Console.WriteLine("Vous avez gagné !");
                        break;
                    }
                    if (remainingCoord.Count <= 0)
                    {
                        DisplayMap(map);
                        Console.WriteLine("Egalité");
                        break;
                    }

                    Console.Write(".");
                    System.Threading.Thread.Sleep(333);
                    Console.Write(".");
                    System.Threading.Thread.Sleep(333);
                    Console.Write(".");
                    System.Threading.Thread.Sleep(333);

                    int index = random.Next(remainingCoord.Count());
                    choice = remainingCoord[index];
                    map[choice] = "o";
                    remainingCoord.Remove(choice);

                    if (IsWon(map, choice))
                    {
                        DisplayMap(map);
                        Console.WriteLine("\nVous avez perdu...");
                        break;
                    }
                }
                string r;
                do
                {
                    Console.Write("\nVoulez-vous rejouer ?\no / n\n");
                    r = Console.ReadLine();
                } while (r != "o" && r != "n");
                if (r == "o")
                    rejouer = true;
                else
                    rejouer = false;
            } while (rejouer);
        }

        public static void DisplayMap(Dictionary<string, string> map)
        {
            Console.Clear();

            string separator = "  -------------", letter;

            Console.Write("  ");
            for (int i = 1; i <= 3; i++)
                Console.Write("  " + i + " ");
            Console.WriteLine();

            for (int i = 0; i < 3; i++)
            {
                letter = Convert.ToChar(65 + i).ToString();
                Console.WriteLine(separator);
                Console.Write(letter + " |");
                for (int j = 1; j <= 3; j++)
                    Console.Write(" " + map[letter + j] + " |");
                Console.WriteLine();
            }
            Console.WriteLine(separator);
        }

        private static bool IsWon(Dictionary<string, string> map, string choice)
        {
            string symbole = map[choice];
            char l = choice[0], c = choice[1];

            if (map[l + "1"] == symbole && map[l + "2"] == symbole && map[l + "3"] == symbole)
                return true;
            else if (map["A" + c] == symbole && map["B" + c] == symbole && map["C" + c] == symbole)
                return true;
            else if (map["A1"] == symbole && map["B2"] == symbole && map["C3"] == symbole)
                return true;
            else if (map["A3"] == symbole && map["B2"] == symbole && map["C1"] == symbole)
                return true;

            return false;
        }
    }
}