using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO.Compression;
using Microsoft.Win32;

namespace Strike_Installer
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void pathButton_Click(object sender, EventArgs e)
        {
            // Show the FolderBrowserDialog.
            DialogResult result = folderBrowserDialog1.ShowDialog();
            if( result == DialogResult.OK )
         {
               var folderName = folderBrowserDialog1.SelectedPath;
               pathBox.Text = folderName;
          
         }

        }

        private void Decompress(string path)
        {
           
   
                string zipPath = "./data/Strike.zip";
           
                ZipFile.ExtractToDirectory(zipPath, path);
           
        }


        private void installButton_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(pathBox.Text))
            {
                MessageBox.Show("Please set a path");

                return;
            }
            if (Directory.Exists(pathBox.Text + "/Strike/"))
            {
                DialogResult confirmResult = MessageBox.Show("By clicking yes this installer will delete the currently installed version", "A copy of Strike exist here", MessageBoxButtons.YesNo);
                if (confirmResult == DialogResult.Yes)
                {
                    Directory.Delete(@pathBox.Text + "/Strike/", true);
                }
                else if (confirmResult == DialogResult.No)
                {
                    return;
                }

            
            }
            var startup = true;
            DialogResult dialogResult = MessageBox.Show("This is the best way to ensure your media server functions well.", "Would you like MPC/Strike to start on startup?", MessageBoxButtons.YesNo);
            if (dialogResult == DialogResult.Yes)
            {
                startup = true;
            }
            else if (dialogResult == DialogResult.No)
            {
                startup = false;
            }

            pictureBox1.Visible = true;
            Decompress(pathBox.Text);
            if (startup == true)
            {
                RegistryKey rk = Registry.CurrentUser.OpenSubKey
                ("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run", true);
                rk.SetValue("Strike", pathBox.Text + "/Strike/mpc-hc.exe");
            }

            pictureBox1.Visible = false;
            DialogResult runResults = MessageBox.Show("Strike has been installed to: " + pathBox.Text + " and will startup on windows: " +startup, "Would you run MPC/Strike now?", MessageBoxButtons.YesNo);
            if (runResults == DialogResult.Yes)
            {
                System.Diagnostics.Process.Start(@pathBox.Text + "/Strike/mpc-hc.exe");
                System.Diagnostics.Process.Start("http://localhost:13579");
            }
            else if (runResults == DialogResult.No)
            {
                startup = false;
            }
       
        }
    }
}
