using Dtos;
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

namespace Client
{
    public partial class VindeView : Form
    {
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
        private IFestivalService Service;

        ConcertDto concert;

        public void setService(IFestivalService service)
        {
            Service = service;
        }
        public VindeView(ConcertDto concert)
        {
            InitializeComponent();
            this.concert = concert;
            concertLabel.Text = concert.ToString();
            numericUpDown1.Minimum = 1;
            numericUpDown1.Maximum = concert.Disponibile;
            locuriLabel.Text = $"Locuri disponibile: {concert.Disponibile.ToString()}" ;
        }

        private void vindeBtn_Click(object sender, EventArgs e)
        {
            BeginInvoke((MethodInvoker)delegate
            {
                int locuri = (int)numericUpDown1.Value;
                if (locuri > concert.Disponibile)
                {
                    MessageBox.Show("Nu sunt suficiente locuri disponibile!");
                    return;
                }
                if (string.IsNullOrWhiteSpace(textBox1.Text))
                {
                    MessageBox.Show("Introduceti numele clientului!");
                    return;
                }
                try
                {
                    Service.SellTickets(concert, locuri);
                    MessageBox.Show($"Locurile au fost vandute cu succes catre {textBox1.Text}!");
                    this.Close();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }
            });
        }
    }
}
