namespace Strike_Installer
{
    partial class Form1
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.greetingLabel = new System.Windows.Forms.Label();
            this.pathBox = new System.Windows.Forms.TextBox();
            this.pathButton = new System.Windows.Forms.Button();
            this.folderBrowserDialog1 = new System.Windows.Forms.FolderBrowserDialog();
            this.installButton = new System.Windows.Forms.Button();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // greetingLabel
            // 
            this.greetingLabel.AutoSize = true;
            this.greetingLabel.Font = new System.Drawing.Font("Open Sans", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.greetingLabel.Location = new System.Drawing.Point(34, 9);
            this.greetingLabel.Name = "greetingLabel";
            this.greetingLabel.Size = new System.Drawing.Size(417, 26);
            this.greetingLabel.TabIndex = 1;
            this.greetingLabel.Text = "Welcome to the Strike installer, select a path.";
            // 
            // pathBox
            // 
            this.pathBox.BackColor = System.Drawing.SystemColors.Window;
            this.pathBox.Location = new System.Drawing.Point(39, 53);
            this.pathBox.Name = "pathBox";
            this.pathBox.ReadOnly = true;
            this.pathBox.Size = new System.Drawing.Size(335, 20);
            this.pathBox.TabIndex = 2;
            // 
            // pathButton
            // 
            this.pathButton.Location = new System.Drawing.Point(381, 53);
            this.pathButton.Name = "pathButton";
            this.pathButton.Size = new System.Drawing.Size(70, 23);
            this.pathButton.TabIndex = 3;
            this.pathButton.Text = "Set Path";
            this.pathButton.UseVisualStyleBackColor = true;
            this.pathButton.Click += new System.EventHandler(this.pathButton_Click);
            // 
            // installButton
            // 
            this.installButton.Location = new System.Drawing.Point(39, 115);
            this.installButton.Name = "installButton";
            this.installButton.Size = new System.Drawing.Size(412, 23);
            this.installButton.TabIndex = 4;
            this.installButton.Text = "Install";
            this.installButton.UseVisualStyleBackColor = true;
            this.installButton.Click += new System.EventHandler(this.installButton_Click);
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackColor = System.Drawing.SystemColors.Control;
            this.pictureBox1.ImageLocation = "http://i.imgur.com/NBA6aEL.gif";
            this.pictureBox1.Location = new System.Drawing.Point(1, -3);
            this.pictureBox1.MaximumSize = new System.Drawing.Size(536, 224);
            this.pictureBox1.MinimumSize = new System.Drawing.Size(536, 224);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(536, 224);
            this.pictureBox1.TabIndex = 0;
            this.pictureBox1.TabStop = false;
            this.pictureBox1.Visible = false;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(520, 185);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.installButton);
            this.Controls.Add(this.pathButton);
            this.Controls.Add(this.pathBox);
            this.Controls.Add(this.greetingLabel);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximumSize = new System.Drawing.Size(536, 224);
            this.MinimumSize = new System.Drawing.Size(536, 224);
            this.Name = "Form1";
            this.Text = "Strike Installer";
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        internal System.Windows.Forms.Label greetingLabel;
        private System.Windows.Forms.TextBox pathBox;
        private System.Windows.Forms.Button pathButton;
        private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog1;
        private System.Windows.Forms.Button installButton;
        private System.Windows.Forms.PictureBox pictureBox1;
    }
}

