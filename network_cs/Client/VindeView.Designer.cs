namespace Client
{
    partial class VindeView
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            vindeBtn = new Button();
            concertLabel = new Label();
            numericUpDown1 = new NumericUpDown();
            textBox1 = new TextBox();
            locuriLabel = new Label();
            ((System.ComponentModel.ISupportInitialize)numericUpDown1).BeginInit();
            SuspendLayout();
            // 
            // vindeBtn
            // 
            vindeBtn.Location = new Point(336, 354);
            vindeBtn.Name = "vindeBtn";
            vindeBtn.Size = new Size(94, 29);
            vindeBtn.TabIndex = 0;
            vindeBtn.Text = "Vinde";
            vindeBtn.UseVisualStyleBackColor = true;
            vindeBtn.Click += vindeBtn_Click;
            // 
            // concertLabel
            // 
            concertLabel.AutoSize = true;
            concertLabel.Location = new Point(153, 122);
            concertLabel.Name = "concertLabel";
            concertLabel.Size = new Size(50, 20);
            concertLabel.TabIndex = 1;
            concertLabel.Text = "label1";
            // 
            // numericUpDown1
            // 
            numericUpDown1.Location = new Point(309, 296);
            numericUpDown1.Name = "numericUpDown1";
            numericUpDown1.Size = new Size(150, 27);
            numericUpDown1.TabIndex = 2;
            // 
            // textBox1
            // 
            textBox1.Location = new Point(309, 263);
            textBox1.Name = "textBox1";
            textBox1.Size = new Size(150, 27);
            textBox1.TabIndex = 3;
            // 
            // locuriLabel
            // 
            locuriLabel.AutoSize = true;
            locuriLabel.Location = new Point(355, 218);
            locuriLabel.Name = "locuriLabel";
            locuriLabel.Size = new Size(50, 20);
            locuriLabel.TabIndex = 4;
            locuriLabel.Text = "label1";
            // 
            // VindeView
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(800, 450);
            Controls.Add(locuriLabel);
            Controls.Add(textBox1);
            Controls.Add(numericUpDown1);
            Controls.Add(concertLabel);
            Controls.Add(vindeBtn);
            Name = "VindeView";
            Text = "Vinde";
            ((System.ComponentModel.ISupportInitialize)numericUpDown1).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private Button vindeBtn;
        private Label concertLabel;
        private NumericUpDown numericUpDown1;
        private TextBox textBox1;
        private Label locuriLabel;
    }
}