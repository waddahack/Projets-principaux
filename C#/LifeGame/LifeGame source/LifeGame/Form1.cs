using LifeGame.Controller;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace LifeGame
{
    public partial class Form1 : Form
    {

        private bool onPause;
        private Timer MyTimer;
        public Form1()
        {
            InitializeComponent();
            FetchFiles();
            onPause = pausedCheckBox.Checked;
            Program.WindSize = pictureBox1.Width;
            pictureBox1.Paint += new PaintEventHandler(Form1_Paint);

            MyTimer = new Timer();
            MyTimer.Interval = (50);
            MyTimer.Tick += new EventHandler(UpdateGame);
            MyTimer.Start();
        }

        private void FetchFiles()
        {
            string path = "..\\..\\..\\Prefabs\\";
            foreach (string file in Directory.EnumerateFiles(path, "*.txt"))
            {
                string name = file.Substring(path.Length, file.Length-path.Length-4);
                if (name[0].ToString() == "_")
                    name = name.Substring(1, name.Length - 1);
                prefabNameList.Items.Insert(0, name);
            }
                
        }

        private void UpdateGame(Object sender, EventArgs e)
        {
            if (Program.Game != null && !onPause)
                Program.Game.Update();
            Refresh();
        }

        private void Form1_Paint(Object sender, PaintEventArgs e)
        {
                Graphics g = e.Graphics;
                g.Clear(Color.FromArgb(0, 0, 0));
                if (Program.Game != null)
                    Program.Game.Paint(g);
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            Program.Resolution = (int)resolutionInput.Value;
            Program.Game = new Game(false, randomColorsCheckBox.Checked, gridCheckBox.Checked);
            Program.Game.Presave();
        }

        private void pictureBox1_MouseMove(object sender, MouseEventArgs e)
        {
            if(e.Button != MouseButtons.None)
            {
                int width = Program.WindSize / Program.Resolution;
                int j = (int)MathF.Floor(e.X / width), i = (int)MathF.Floor(e.Y / width);
                if(i >= 0 && i < Program.Resolution && j >= 0 && j < Program.Resolution)
                {
                    if (e.Button == MouseButtons.Left && !Program.Game.PixelMap[i][j].On)
                        Program.Game.PixelMap[i][j].On = true;
                    else if (e.Button == MouseButtons.Right && Program.Game.PixelMap[i][j].On)
                        Program.Game.PixelMap[i][j].On = false;
                }
            }
        }

        private void pictureBox1_MouseUp(object sender, MouseEventArgs e)
        {
            //MessageBox.Show("Presaving");
            Program.Game.Presave();
        }

        private void resolutionInput_ValueChanged(object sender, EventArgs e)
        {
            int width = pictureBox1.Width / (int)resolutionInput.Value;
            resolutionInput.Value = pictureBox1.Width / width;
            Program.Resolution = (int)resolutionInput.Value;
            Program.Game = new Game(true, randomColorsCheckBox.Checked, gridCheckBox.Checked);
        }

        private void randomColorsCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            if (randomColorsCheckBox.Checked)
                Program.Game.SetRandomColors();
            else
                RInput_ValueChanged(sender, e);
            RInput.Enabled = !randomColorsCheckBox.Checked;
            GInput.Enabled = !randomColorsCheckBox.Checked;
            BInput.Enabled = !randomColorsCheckBox.Checked;
        }

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void button2_Click_1(object sender, EventArgs e)
        {
            string name = prefabNameList.Text;
            Program.Game.Clear();
            Program.Game.SetCenter(centerXCheckBox.Checked, centerYCheckBox.Checked);
            Program.Game.SetPrefab(name);
        }

        private void centerYCheckBox_CheckedChanged(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        private void button3_Click(object sender, EventArgs e)
        {
            Program.Game.Clear();
        }

        private void gridCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            Program.Game.SetDisplayGrid(gridCheckBox.Checked);
        }

        private void pausedCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            onPause = pausedCheckBox.Checked;
        }

        private void RInput_ValueChanged(object sender, EventArgs e)
        {
            if(!randomColorsCheckBox.Checked)
                Program.Game.SetRGB((int)RInput.Value, (int)GInput.Value, (int)BInput.Value);
        }

        private void GInput_ValueChanged(object sender, EventArgs e)
        {
            RInput_ValueChanged(sender, e);
        }

        private void BInput_ValueChanged(object sender, EventArgs e)
        {
            RInput_ValueChanged(sender, e);
        }

        private void saveButton_Click(object sender, EventArgs e)
        {
            string saveName = Program.Game.Save(saveNameInput.Text);
            if (saveName != "")
                prefabNameList.Items.Add(saveName);
            else
                MessageBox.Show("Nothing to save.");
        }

        private void rollbackButton_Click(object sender, EventArgs e)
        {
            Program.Game.Rollback();
        }

        private void numericUpDown1_ValueChanged_1(object sender, EventArgs e)
        {
            MyTimer.Interval = (int)mspfInput.Value;
        }
    }
}
