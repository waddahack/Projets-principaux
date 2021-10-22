using System.IO;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace LifeGame.Controller
{
    class Game
    {
        public List<List<Pixel>> PixelMap { get; set; }
        private List<List<Pixel>> PixelMapSave;
        private bool debug = false;
        private bool centerX, centerY;
        public Game(bool empty, bool randomColors, bool displayGrid, bool debug = false)
        {
            this.debug = debug;
            centerX = false;
            centerY = false;
            PixelMap = new List<List<Pixel>>();
            for(int i = 0; i < Program.Resolution; i++)
            {
                List<Pixel> row = new List<Pixel>();
                for (int j = 0; j < Program.Resolution; j++)
                {
                    row.Add(new Pixel(i, j, randomColors, displayGrid, !empty, false));
                }
                PixelMap.Add(row);
            }
        }

        public void Update()
        {
            if (!debug)
            {
                foreach (List<Pixel> row in PixelMap)
                    foreach (Pixel p in row)
                        p.CheckState();
                foreach (List<Pixel> row in PixelMap)
                    foreach (Pixel p in row)
                        p.Update();
            }
        }

        public void Paint(Graphics g)
        {
            foreach (List<Pixel> row in PixelMap)
                foreach (Pixel p in row)
                    p.Paint(g);
        }

        public void SetCenter(bool centerX, bool centerY)
        {
            this.centerX = centerX;
            this.centerY = centerY;
        }

        public void SetPrefab(string prefabName)
        {
            string path = "..\\..\\..\\Prefabs\\" + prefabName + ".txt";
            int xStart = 0, yStart = 0;
            if (File.Exists(path))
            {
                string[] lines = File.ReadAllLines(path);
                if(centerY) yStart = Program.Resolution / 2 - lines.Length / 2;
                if(centerX) xStart = Program.Resolution / 2 - lines[0].Length / 2;
                if (yStart < 0) yStart = 0;
                if (xStart < 0) xStart = 0;
                for (int i = 0; i < lines.Length; i++)
                    for (int j = 0; j < lines[i].Length; j++)
                        if(i < Program.Resolution && j < Program.Resolution)
                            PixelMap[yStart+i][xStart+j].On = lines[i][j].ToString() == "0" ? true : false;
            }
        }

        public void Presave()
        {
            PixelMapSave = new List<List<Pixel>>();
            for (int i = 0; i < PixelMap.Count; i++)
            {
                List<Pixel> row = new List<Pixel>();
                for (int j = 0; j < PixelMap[i].Count; j++)
                {
                    Pixel p = new Pixel(i, j, false, false);
                    p.On = PixelMap[i][j].On;
                    row.Add(p);
                }
                    
                PixelMapSave.Add(row);
            }
        }

        public string Save(string saveName)
        {
            if(PixelMapSave == null)
                return "";
            int i = 0;
            string path = "..\\..\\..\\Prefabs\\" + saveName + ".txt";
            while (File.Exists(path)){
                i++;
                if (i > 1)
                    saveName = saveName.Substring(0, saveName.Length-4);
                saveName += " ("+i+")";
                path = "..\\..\\..\\Prefabs\\" + saveName + ".txt";
            }
            string fileContent = "";
            foreach (List<Pixel> row in PixelMapSave)
            {
                foreach (Pixel p in row)
                    fileContent += p.On ? "0" : "x";
                fileContent += "\n";
            }
            File.WriteAllText(path, fileContent);
            fileContent = CleanUp(path);
            File.WriteAllText(path, fileContent);
            return saveName;
        }

        private string CleanUp(string path)
        {
            int nbEmptyRow = 0, nbEmptyPixel = 0, lineLength = 0, xStart = Program.Resolution, yStart = 0;
            string[] lines = File.ReadAllLines(path);
            string content = "";
            bool emptyLine;
            bool drawStarted = false;
            foreach(string line in lines)
            {
                // Fetching necessary variables 
                emptyLine = true;
                nbEmptyPixel = 0;
                for (int i = 0; i < line.Length; i++)
                {
                    if (line[i].ToString() == "0")
                    {
                        if (emptyLine)
                        {
                            emptyLine = false;
                            if (xStart > i)
                                xStart = i;
                        }
                        nbEmptyPixel = 0;
                    }
                    else
                        nbEmptyPixel++;
                }
                
                if (lineLength < line.Length - nbEmptyPixel - xStart)
                    lineLength = line.Length - nbEmptyPixel - xStart;

                if (!emptyLine)
                {
                    if (!drawStarted)
                        drawStarted = true;
                    nbEmptyRow = 0;
                }
                else if (drawStarted)
                    nbEmptyRow++;
                else
                    yStart++;
            }
            // Rewritting content
            for(int i = yStart; i < lines.Length-nbEmptyRow; i++)
                content += lines[i].Substring(xStart, lineLength) + "\n"; 
            return content;
        }

        public void Rollback()
        {
            if(PixelMapSave != null)
            {
                for (int i = 0; i < PixelMapSave.Count; i++)
                    for (int j = 0; j < PixelMapSave[i].Count; j++)
                        if(i < Program.Resolution && j < Program.Resolution)
                            PixelMap[i][j].On = PixelMapSave[i][j].On;
            }
        }

        public void SetDisplayGrid(bool b)
        {
            foreach (List<Pixel> row in PixelMap)
                foreach (Pixel p in row)
                    p.DisplayGrid = b;
        }

        public void Clear()
        {
            foreach (List<Pixel> row in PixelMap)
                foreach (Pixel p in row)
                    p.On = false;
        }

        public void SetRandomColors()
        {
            foreach (List<Pixel> row in PixelMap)
                foreach (Pixel p in row)
                    p.Brush.Color = Color.FromArgb(Program.Rand.Next(100, 256), Program.Rand.Next(100, 256), Program.Rand.Next(100, 256));
        }

        public void SetRGB(int r, int g, int b)
        {
            foreach (List<Pixel> row in PixelMap)
                foreach (Pixel p in row)
                    p.Brush.Color = Color.FromArgb(r, g, b);
        }
    }
}
