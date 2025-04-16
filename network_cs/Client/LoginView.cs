using Services;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Dtos;

namespace Client
{
    public partial class LoginView : Form
    {
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
        public IFestivalService Service { get; set; }
        
        public LoginView()
        {
            InitializeComponent();
        }

        private void loginBtn_Click(object sender, EventArgs e)
        {
            try
            {
                MainView mainView = new MainView();
                
                AngajatDto angajat = new AngajatDto(textBox1.Text, textBox2.Text);
                Service.Login(angajat, mainView);
                mainView.setService(Service);
                mainView.Angajat = angajat;
                mainView.LoginView = this;
                mainView.Text = $"Logged in as {angajat.Username}";
                mainView.Show();
                this.Hide();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Login Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
