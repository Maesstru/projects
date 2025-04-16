namespace Client
{
    partial class MainView
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            dataGridView1 = new DataGridView();
            dataGridView2 = new DataGridView();
            concertLabel = new Label();
            dateTimePicker1 = new DateTimePicker();
            vindeBtn = new Button();
            logoutBtn = new Button();
            ((System.ComponentModel.ISupportInitialize)dataGridView1).BeginInit();
            ((System.ComponentModel.ISupportInitialize)dataGridView2).BeginInit();
            SuspendLayout();
            // 
            // dataGridView1
            // 
            dataGridView1.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridView1.Location = new Point(14, 51);
            dataGridView1.Margin = new Padding(3, 4, 3, 4);
            dataGridView1.Name = "dataGridView1";
            dataGridView1.RowHeadersWidth = 51;
            dataGridView1.Size = new Size(887, 200);
            dataGridView1.TabIndex = 0;
            // 
            // dataGridView2
            // 
            dataGridView2.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridView2.Location = new Point(14, 435);
            dataGridView2.Margin = new Padding(3, 4, 3, 4);
            dataGridView2.Name = "dataGridView2";
            dataGridView2.RowHeadersWidth = 51;
            dataGridView2.Size = new Size(887, 149);
            dataGridView2.TabIndex = 1;
            // 
            // concertLabel
            // 
            concertLabel.AutoSize = true;
            concertLabel.Location = new Point(14, 280);
            concertLabel.Name = "concertLabel";
            concertLabel.Size = new Size(268, 20);
            concertLabel.TabIndex = 2;
            concertLabel.Text = "Momentan nu ai selectat niciun concert";
            // 
            // dateTimePicker1
            // 
            dateTimePicker1.Location = new Point(14, 396);
            dateTimePicker1.Margin = new Padding(3, 4, 3, 4);
            dateTimePicker1.Name = "dateTimePicker1";
            dateTimePicker1.Size = new Size(228, 27);
            dateTimePicker1.TabIndex = 3;
            // 
            // vindeBtn
            // 
            vindeBtn.Location = new Point(467, 331);
            vindeBtn.Margin = new Padding(3, 4, 3, 4);
            vindeBtn.Name = "vindeBtn";
            vindeBtn.Size = new Size(86, 31);
            vindeBtn.TabIndex = 4;
            vindeBtn.Text = "Vinde";
            vindeBtn.UseVisualStyleBackColor = true;
            vindeBtn.Click += vindeBtn_Click;
            // 
            // logoutBtn
            // 
            logoutBtn.Location = new Point(800, 12);
            logoutBtn.Margin = new Padding(3, 4, 3, 4);
            logoutBtn.Name = "logoutBtn";
            logoutBtn.Size = new Size(86, 31);
            logoutBtn.TabIndex = 5;
            logoutBtn.Text = "Logout";
            logoutBtn.UseVisualStyleBackColor = true;
            logoutBtn.Click += logoutBtn_Click;
            // 
            // MainView
            // 
            AutoScaleDimensions = new SizeF(8F, 20F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(914, 600);
            Controls.Add(logoutBtn);
            Controls.Add(vindeBtn);
            Controls.Add(dateTimePicker1);
            Controls.Add(concertLabel);
            Controls.Add(dataGridView2);
            Controls.Add(dataGridView1);
            Margin = new Padding(3, 4, 3, 4);
            Name = "MainView";
            Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)dataGridView1).EndInit();
            ((System.ComponentModel.ISupportInitialize)dataGridView2).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private DataGridView dataGridView1;
        private DataGridView dataGridView2;
        private Label concertLabel;
        private DateTimePicker dateTimePicker1;
        private Button vindeBtn;
        private Button logoutBtn;
    }
}
