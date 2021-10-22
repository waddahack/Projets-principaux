
namespace LifeGame
{
    partial class Form1
    {

        private System.ComponentModel.IContainer components = null;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        private void InitializeComponent()
        {
            this.button1 = new System.Windows.Forms.Button();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.randomColorsCheckBox = new System.Windows.Forms.CheckBox();
            this.resolutionInput = new System.Windows.Forms.NumericUpDown();
            this.label1 = new System.Windows.Forms.Label();
            this.centerXCheckBox = new System.Windows.Forms.CheckBox();
            this.centerYCheckBox = new System.Windows.Forms.CheckBox();
            this.rateInput = new System.Windows.Forms.NumericUpDown();
            this.label5 = new System.Windows.Forms.Label();
            this.RInput = new System.Windows.Forms.NumericUpDown();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.GInput = new System.Windows.Forms.NumericUpDown();
            this.label9 = new System.Windows.Forms.Label();
            this.BInput = new System.Windows.Forms.NumericUpDown();
            this.button2 = new System.Windows.Forms.Button();
            this.prefabNameList = new System.Windows.Forms.ComboBox();
            this.gridCheckBox = new System.Windows.Forms.CheckBox();
            this.button3 = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.pausedCheckBox = new System.Windows.Forms.CheckBox();
            this.label6 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.saveButton = new System.Windows.Forms.Button();
            this.saveNameInput = new System.Windows.Forms.TextBox();
            this.rollbackButton = new System.Windows.Forms.Button();
            this.mspfInput = new System.Windows.Forms.NumericUpDown();
            this.label3 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.resolutionInput)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.rateInput)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.RInput)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.GInput)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.BInput)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.mspfInput)).BeginInit();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(636, 175);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(150, 30);
            this.button1.TabIndex = 0;
            this.button1.Text = "Load";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click_1);
            // 
            // pictureBox1
            // 
            this.pictureBox1.Location = new System.Drawing.Point(12, 12);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(600, 600);
            this.pictureBox1.TabIndex = 1;
            this.pictureBox1.TabStop = false;
            this.pictureBox1.MouseDown += new System.Windows.Forms.MouseEventHandler(this.pictureBox1_MouseMove);
            this.pictureBox1.MouseMove += new System.Windows.Forms.MouseEventHandler(this.pictureBox1_MouseMove);
            this.pictureBox1.MouseUp += new System.Windows.Forms.MouseEventHandler(this.pictureBox1_MouseUp);
            // 
            // randomColorsCheckBox
            // 
            this.randomColorsCheckBox.AutoSize = true;
            this.randomColorsCheckBox.Location = new System.Drawing.Point(636, 561);
            this.randomColorsCheckBox.Name = "randomColorsCheckBox";
            this.randomColorsCheckBox.Size = new System.Drawing.Size(120, 19);
            this.randomColorsCheckBox.TabIndex = 3;
            this.randomColorsCheckBox.Text = "Randomize colors";
            this.randomColorsCheckBox.UseVisualStyleBackColor = true;
            this.randomColorsCheckBox.CheckedChanged += new System.EventHandler(this.randomColorsCheckBox_CheckedChanged);
            // 
            // resolutionInput
            // 
            this.resolutionInput.Location = new System.Drawing.Point(636, 427);
            this.resolutionInput.Maximum = new decimal(new int[] {
            300,
            0,
            0,
            0});
            this.resolutionInput.Minimum = new decimal(new int[] {
            50,
            0,
            0,
            0});
            this.resolutionInput.Name = "resolutionInput";
            this.resolutionInput.Size = new System.Drawing.Size(45, 23);
            this.resolutionInput.TabIndex = 4;
            this.resolutionInput.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.resolutionInput.Value = new decimal(new int[] {
            100,
            0,
            0,
            0});
            this.resolutionInput.ValueChanged += new System.EventHandler(this.resolutionInput_ValueChanged);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(687, 429);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(63, 15);
            this.label1.TabIndex = 5;
            this.label1.Text = "Resolution";
            // 
            // centerXCheckBox
            // 
            this.centerXCheckBox.AutoSize = true;
            this.centerXCheckBox.Checked = true;
            this.centerXCheckBox.CheckState = System.Windows.Forms.CheckState.Checked;
            this.centerXCheckBox.Location = new System.Drawing.Point(636, 307);
            this.centerXCheckBox.Name = "centerXCheckBox";
            this.centerXCheckBox.Size = new System.Drawing.Size(91, 19);
            this.centerXCheckBox.TabIndex = 8;
            this.centerXCheckBox.Text = "Center on X ";
            this.centerXCheckBox.UseVisualStyleBackColor = true;
            // 
            // centerYCheckBox
            // 
            this.centerYCheckBox.AutoSize = true;
            this.centerYCheckBox.Checked = true;
            this.centerYCheckBox.CheckState = System.Windows.Forms.CheckState.Checked;
            this.centerYCheckBox.Location = new System.Drawing.Point(636, 323);
            this.centerYCheckBox.Name = "centerYCheckBox";
            this.centerYCheckBox.Size = new System.Drawing.Size(88, 19);
            this.centerYCheckBox.TabIndex = 9;
            this.centerYCheckBox.Text = "Center on Y";
            this.centerYCheckBox.UseVisualStyleBackColor = true;
            this.centerYCheckBox.CheckedChanged += new System.EventHandler(this.centerYCheckBox_CheckedChanged);
            // 
            // rateInput
            // 
            this.rateInput.Location = new System.Drawing.Point(636, 485);
            this.rateInput.Maximum = new decimal(new int[] {
            90,
            0,
            0,
            0});
            this.rateInput.Minimum = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.rateInput.Name = "rateInput";
            this.rateInput.Size = new System.Drawing.Size(45, 23);
            this.rateInput.TabIndex = 14;
            this.rateInput.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.rateInput.Value = new decimal(new int[] {
            40,
            0,
            0,
            0});
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(687, 487);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(82, 15);
            this.label5.TabIndex = 15;
            this.label5.Text = "Pixel alive rate";
            // 
            // RInput
            // 
            this.RInput.Location = new System.Drawing.Point(636, 532);
            this.RInput.Maximum = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.RInput.Name = "RInput";
            this.RInput.Size = new System.Drawing.Size(45, 23);
            this.RInput.TabIndex = 18;
            this.RInput.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.RInput.Value = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.RInput.ValueChanged += new System.EventHandler(this.RInput_ValueChanged);
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(648, 514);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(14, 15);
            this.label7.TabIndex = 19;
            this.label7.Text = "R";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(699, 514);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(15, 15);
            this.label8.TabIndex = 21;
            this.label8.Text = "G";
            // 
            // GInput
            // 
            this.GInput.Location = new System.Drawing.Point(687, 532);
            this.GInput.Maximum = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.GInput.Name = "GInput";
            this.GInput.Size = new System.Drawing.Size(45, 23);
            this.GInput.TabIndex = 20;
            this.GInput.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.GInput.Value = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.GInput.ValueChanged += new System.EventHandler(this.GInput_ValueChanged);
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(750, 514);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(14, 15);
            this.label9.TabIndex = 23;
            this.label9.Text = "B";
            // 
            // BInput
            // 
            this.BInput.Location = new System.Drawing.Point(738, 532);
            this.BInput.Maximum = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.BInput.Name = "BInput";
            this.BInput.Size = new System.Drawing.Size(45, 23);
            this.BInput.TabIndex = 22;
            this.BInput.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.BInput.Value = new decimal(new int[] {
            255,
            0,
            0,
            0});
            this.BInput.ValueChanged += new System.EventHandler(this.BInput_ValueChanged);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(636, 348);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(150, 30);
            this.button2.TabIndex = 24;
            this.button2.Text = "Load";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click_1);
            // 
            // prefabNameList
            // 
            this.prefabNameList.AutoCompleteMode = System.Windows.Forms.AutoCompleteMode.Append;
            this.prefabNameList.AutoCompleteSource = System.Windows.Forms.AutoCompleteSource.ListItems;
            this.prefabNameList.FormattingEnabled = true;
            this.prefabNameList.Location = new System.Drawing.Point(636, 278);
            this.prefabNameList.Name = "prefabNameList";
            this.prefabNameList.Size = new System.Drawing.Size(118, 23);
            this.prefabNameList.TabIndex = 25;
            this.prefabNameList.Text = "Choose a prefab";
            this.prefabNameList.SelectedIndexChanged += new System.EventHandler(this.comboBox1_SelectedIndexChanged);
            // 
            // gridCheckBox
            // 
            this.gridCheckBox.AutoSize = true;
            this.gridCheckBox.Location = new System.Drawing.Point(636, 587);
            this.gridCheckBox.Name = "gridCheckBox";
            this.gridCheckBox.Size = new System.Drawing.Size(88, 19);
            this.gridCheckBox.TabIndex = 27;
            this.gridCheckBox.Text = "Display grid";
            this.gridCheckBox.UseVisualStyleBackColor = true;
            this.gridCheckBox.CheckedChanged += new System.EventHandler(this.gridCheckBox_CheckedChanged);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(636, 70);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(75, 23);
            this.button3.TabIndex = 28;
            this.button3.Text = "Clear";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(675, 208);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(0, 15);
            this.label4.TabIndex = 29;
            // 
            // pausedCheckBox
            // 
            this.pausedCheckBox.AutoSize = true;
            this.pausedCheckBox.Checked = true;
            this.pausedCheckBox.CheckState = System.Windows.Forms.CheckState.Checked;
            this.pausedCheckBox.Location = new System.Drawing.Point(636, 45);
            this.pausedCheckBox.Name = "pausedCheckBox";
            this.pausedCheckBox.Size = new System.Drawing.Size(64, 19);
            this.pausedCheckBox.TabIndex = 30;
            this.pausedCheckBox.Text = "Paused";
            this.pausedCheckBox.UseVisualStyleBackColor = true;
            this.pausedCheckBox.CheckedChanged += new System.EventHandler(this.pausedCheckBox_CheckedChanged);
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Segoe UI Semibold", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label6.Location = new System.Drawing.Point(636, 394);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(120, 30);
            this.label6.TabIndex = 31;
            this.label6.Text = "Parameters";
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Font = new System.Drawing.Font("Segoe UI Semibold", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label10.Location = new System.Drawing.Point(636, 142);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(106, 30);
            this.label10.TabIndex = 32;
            this.label10.Text = "Life game";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Segoe UI Semibold", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label2.Location = new System.Drawing.Point(636, 245);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(84, 30);
            this.label2.TabIndex = 33;
            this.label2.Text = "Prefabs";
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Font = new System.Drawing.Font("Segoe UI Semibold", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label11.Location = new System.Drawing.Point(636, 12);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(69, 30);
            this.label11.TabIndex = 34;
            this.label11.Text = "Game";
            // 
            // saveButton
            // 
            this.saveButton.Location = new System.Drawing.Point(717, 70);
            this.saveButton.Name = "saveButton";
            this.saveButton.Size = new System.Drawing.Size(75, 23);
            this.saveButton.TabIndex = 35;
            this.saveButton.Text = "Save";
            this.saveButton.UseVisualStyleBackColor = true;
            this.saveButton.Click += new System.EventHandler(this.saveButton_Click);
            // 
            // saveNameInput
            // 
            this.saveNameInput.Location = new System.Drawing.Point(717, 99);
            this.saveNameInput.Name = "saveNameInput";
            this.saveNameInput.Size = new System.Drawing.Size(75, 23);
            this.saveNameInput.TabIndex = 36;
            this.saveNameInput.Text = "MyPrefab";
            this.saveNameInput.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // rollbackButton
            // 
            this.rollbackButton.Location = new System.Drawing.Point(636, 99);
            this.rollbackButton.Name = "rollbackButton";
            this.rollbackButton.Size = new System.Drawing.Size(75, 23);
            this.rollbackButton.TabIndex = 37;
            this.rollbackButton.Text = "Rollback";
            this.rollbackButton.UseVisualStyleBackColor = true;
            this.rollbackButton.Click += new System.EventHandler(this.rollbackButton_Click);
            // 
            // mspfInput
            // 
            this.mspfInput.Increment = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.mspfInput.Location = new System.Drawing.Point(636, 456);
            this.mspfInput.Maximum = new decimal(new int[] {
            250,
            0,
            0,
            0});
            this.mspfInput.Minimum = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.mspfInput.Name = "mspfInput";
            this.mspfInput.Size = new System.Drawing.Size(45, 23);
            this.mspfInput.TabIndex = 38;
            this.mspfInput.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.mspfInput.Value = new decimal(new int[] {
            50,
            0,
            0,
            0});
            this.mspfInput.ValueChanged += new System.EventHandler(this.numericUpDown1_ValueChanged_1);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(687, 458);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(77, 15);
            this.label3.TabIndex = 39;
            this.label3.Text = "ms per frame";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(808, 628);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.mspfInput);
            this.Controls.Add(this.rollbackButton);
            this.Controls.Add(this.saveNameInput);
            this.Controls.Add(this.saveButton);
            this.Controls.Add(this.label11);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.pausedCheckBox);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.gridCheckBox);
            this.Controls.Add(this.prefabNameList);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.BInput);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.GInput);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.RInput);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.rateInput);
            this.Controls.Add(this.centerYCheckBox);
            this.Controls.Add(this.centerXCheckBox);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.resolutionInput);
            this.Controls.Add(this.randomColorsCheckBox);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.button1);
            this.Name = "Form1";
            this.Text = "Le jeu de la vie";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.resolutionInput)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.rateInput)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.RInput)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.GInput)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.BInput)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.mspfInput)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        public void numericUpDown1_ValueChanged(object sender, System.EventArgs e)
        {

        }

        private System.Windows.Forms.Button button1;
        public System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.CheckBox randomColorsCheckBox;
        public System.Windows.Forms.NumericUpDown resolutionInput;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.CheckBox centerXCheckBox;
        private System.Windows.Forms.CheckBox centerYCheckBox;
        public System.Windows.Forms.NumericUpDown rateInput;
        private System.Windows.Forms.Label label5;
        public System.Windows.Forms.NumericUpDown RInput;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        public System.Windows.Forms.NumericUpDown GInput;
        private System.Windows.Forms.Label label9;
        public System.Windows.Forms.NumericUpDown BInput;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.ComboBox prefabNameList;
        private System.Windows.Forms.CheckBox gridCheckBox;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Label label4;
        public System.Windows.Forms.CheckBox pausedCheckBox;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.TextBox saveNameInput;
        private System.Windows.Forms.Button rollbackButton;
        public System.Windows.Forms.NumericUpDown mspfInput;
        private System.Windows.Forms.Label label3;
    }
}

