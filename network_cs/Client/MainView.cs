using Dtos;
using Services;
using System.Collections.ObjectModel;
using System.ComponentModel;

namespace Client
{
    public partial class MainView : Form, IFestivalObserver
    {

        [DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
        private IFestivalService Service;

        ConcertDto? selectat;
        Collection<ConcertDto> concerts = new Collection<ConcertDto>();
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
        public AngajatDto Angajat { get; set; }
        [DesignerSerializationVisibility(DesignerSerializationVisibility.Hidden)]
        public LoginView LoginView { get; set; }

        public MainView()
        {
            InitializeComponent();
            //concerts = Service.GetConcerts();
            //loadData();
            dataGridView1.DataBindingComplete += dataGridView1_DataBindingComplete;
            dateTimePicker1.ValueChanged += dateTimePicker1_ValueChanged;
            dataGridView1.SelectionChanged += dataGridView1_SelectionChanged;
        }

        public void setService(IFestivalService service)
        {
            Service = service;
            concerts = Service.GetConcerts();
            loadData();
        }

        private void loadData()
        {
            dataGridView1.DataSource = concerts;
        }

        private void updateLabel()
        {
            if (selectat != null)
            {
                concertLabel.Text = selectat.ToString();
            }
        }

        public void UpdateConcert(ConcertDto c)
        {
            for (int i = 0; i < concerts.Count; i++)
            {
                if (concerts[i].Id == c.Id)
                {
                    concerts[i] = c;
                    UpdateRowColor(dataGridView1.Rows[i]);
                    break;
                }
            }
            dataGridView1.Refresh();
            dataGridView2.Refresh();
        }

        private void vindeBtn_Click(object sender, EventArgs e)
        {
            VindeView vindeView = new VindeView(selectat);
            vindeView.setService(Service);
            vindeView.ShowDialog();
        }

        private void dataGridView1_SelectionChanged(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedCells.Count > 0)
            {
                selectat = dataGridView1.SelectedCells[0].OwningRow.DataBoundItem as ConcertDto;
                updateLabel();
            }
        }

        private void dateTimePicker1_ValueChanged(object sender, EventArgs e)
        {
            DateTime selectedDate = dateTimePicker1.Value;
            dataGridView2.DataSource = concerts.Where(c => c.date.Date == selectedDate.Date).ToList();
        }

        private void UpdateRowColor(DataGridViewRow row)
        {
                ConcertDto c = row.DataBoundItem as ConcertDto;
                if (c.Disponibile <= 0)
                {
                    row.DefaultCellStyle.BackColor = Color.Red;
                    row.DefaultCellStyle.ForeColor = Color.White; // Optional: make text readable
                }

        }

        private void HighlightRows()
        {
            foreach (DataGridViewRow row in dataGridView1.Rows)
            {
                UpdateRowColor(row);
            }
        }

        private void dataGridView1_DataBindingComplete(object sender, DataGridViewBindingCompleteEventArgs e)
        {
            HighlightRows();
        }


        private void logoutBtn_Click(object sender, EventArgs e)
        {
            try
            {
                BeginInvoke((MethodInvoker)delegate
                {
                    Service.Logout(Angajat);
                    LoginView.Show();
                    this.Close();
                });
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
    }
}
