using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using LifeGame.Controller;

namespace LifeGame
{
    static class Program
    {
        public static Form1 Window;
        public static int WindSize { get; set; }
        public static int Resolution { get; set; } = 100;
        public static Random Rand => new Random();

        public static Game Game { get; set; }
        [STAThread]
        static void Main()
        {
            Application.SetHighDpiMode(HighDpiMode.SystemAware);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Window = new Form1();
            Game = new Game(true, false, false);
            Application.Run(Window);
        }
    }
}
