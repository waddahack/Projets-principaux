using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;

namespace LifeGame.Controller
{
    class Pixel
    {
        private int i, j, x, y, size;
        public SolidBrush Brush { get; set; }
        public bool On { get; set; }
        public bool DisplayGrid { get; set; }
        private bool nextStateIs;
        public Pixel(int i, int j, bool displayGrid, bool randomColors, bool random, bool state)
        {
            build(i, j, randomColors, displayGrid, random, state);
        }

        public Pixel(int i, int j, bool displayGrid, bool randomColors)
        {
            build(i, j, randomColors, displayGrid, true, false);
        }

        public Pixel(int i, int j, bool displayGrid, bool random, bool state)
        {
            build(i, j, false, displayGrid, random, state);
        }

        public Pixel(Pixel p)
        {
            build(p.i, p.j, false, false, false, false);
        }

        private void build(int i, int j, bool displayGrid, bool randomColors, bool random, bool state)
        {
            this.DisplayGrid = displayGrid;
            size = Program.WindSize / Program.Resolution;
            this.i = i;
            this.j = j;
            x = j * size;
            y = i * size;
            if(randomColors)
                Brush = new SolidBrush(Color.FromArgb(Program.Rand.Next(100, 255), Program.Rand.Next(100, 255), Program.Rand.Next(100, 255)));
            else
                Brush = new SolidBrush(Color.FromArgb((int)Program.Window.RInput.Value, (int)Program.Window.GInput.Value, (int)Program.Window.BInput.Value));
            if (!random)
                On = state;
            else
            {
                On = Program.Rand.Next(1, 101) <= Program.Window.rateInput.Value;
                nextStateIs = On;
            }
        }

        public void CheckState()
        {
            int nbNeighboorOn = NbNeighboorOn();
            if (On && nbNeighboorOn != 2 && nbNeighboorOn != 3)
                nextStateIs = false;
            else if (!On && nbNeighboorOn == 3)
                nextStateIs = true;
            else
                nextStateIs = On;
        }

        public void Update()
        {
            On = nextStateIs;
        }

        public void Paint(Graphics g)
        {
            if (DisplayGrid && ((i % 2 != 0 && j % 2 != 0) || (i % 2 == 0 && j % 2 == 0)))
                g.DrawRectangle(new Pen(Color.FromArgb(100, 100, 100), 1f), x, y, size, size);
            if (On)
                g.FillRectangle(Brush, x, y, size, size);
        }

        private int NbNeighboorOn()
        {
            int nb = 0;
            List<List<Pixel>> pixelMap = Program.Game.PixelMap;
            if(i - 1 > 0)
            {
                if (j - 1 > 0 && pixelMap[i - 1][j - 1].On)
                    nb++;
                if (pixelMap[i - 1][j].On)
                    nb++;
                if (j + 1 < pixelMap[i - 1].Count && pixelMap[i - 1][j + 1].On)
                    nb++;
            }
            if (j - 1 > 0 && pixelMap[i][j - 1].On)
                nb++;
            if (j + 1 < pixelMap[i].Count && pixelMap[i][j + 1].On)
                nb++;
            if (i + 1 < pixelMap.Count)
            {
                if (j - 1 > 0 && pixelMap[i + 1][j - 1].On)
                    nb++;
                if (pixelMap[i + 1][j].On)
                    nb++;
                if (j + 1 < pixelMap[i + 1].Count && pixelMap[i + 1][j + 1].On)
                    nb++;
            }
            return nb;
        }
    }
}
