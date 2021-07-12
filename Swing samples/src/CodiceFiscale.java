import java.awt.*;
import java.awt.event.*;

public class CodiceFiscale
{
    private Frame f;
    private Panel pID, pArea, pBut;
    private Button bCalc, bCanc;
    private TextArea displayArea;
    private TextField tfNome, tfCognome, tfDataNascita, tfLuogoNascita, tfProvincia;
    private Label labNome, labCognome, labSesso, labDataNascita, labLuogoNascita, labProvincia;
    private Label labVuota, labVuota2, labVuota3, labVuota4, labVuota5;
    private CheckboxGroup cbg;
    private String data;

    static final String NEWLINE = System.getProperty("line.separator");

    public CodiceFiscale()
    {
        f = new Frame("Calcolo del codice fiscale");

        pID = new Panel();
        pArea = new Panel();
        pBut = new Panel();
        bCalc = new Button(" Calcola il codice ");
        bCanc = new Button(" Cancella ");

        tfNome = new TextField(30);
        tfCognome = new TextField(30);
        cbg = new CheckboxGroup();
        tfDataNascita = new TextField(10);
        tfLuogoNascita = new TextField(40);
        tfProvincia = new TextField(2);
        displayArea = new TextArea();

        labNome=new Label("Nome",Label.RIGHT);
        labCognome=new Label("Cognome",Label.RIGHT);
        labSesso=new Label("Sesso",Label.RIGHT);
        labDataNascita=new Label("Data di nascita",Label.RIGHT);
        labLuogoNascita=new Label("Luogo di nascita",Label.RIGHT);
        labProvincia=new Label("Provincia",Label.RIGHT);

        labVuota=new Label("",Label.LEFT);
        labVuota2=new Label("",Label.LEFT);
        labVuota3=new Label("",Label.LEFT);
        labVuota4=new Label("",Label.LEFT);
        labVuota5=new Label("",Label.LEFT);
    }

    public void setup()
    {
        f.setLayout(new BorderLayout());
        pID.setLayout(new GridLayout(6,3));

        f.setSize(450,230);
        f.setLocation(250, 200);

        f.setBackground(new Color(0,255,0));

        pID.add(labNome);
        pID.add(tfNome);
        pID.add(labVuota);
        pID.add(labCognome);
        pID.add(tfCognome);
        pID.add(labVuota2);
        pID.add(labSesso);
        pID.add(new Checkbox("M", cbg, true));
        pID.add(new Checkbox("F", cbg, false));
        tfDataNascita.addKeyListener(new KeyHandlerData(tfDataNascita, displayArea));
        pID.add(labDataNascita);
        pID.add(tfDataNascita);
        pID.add(labVuota3);
        pID.add(labLuogoNascita);
        pID.add(tfLuogoNascita).addKeyListener(new KeyHandlerNascita(tfLuogoNascita));
        pID.add(labVuota4);
        pID.add(labProvincia);
        pID.add(tfProvincia);
        pID.add(labVuota5);
        pArea.add(displayArea);

        bCalc.addActionListener(new ButtonHandlerCalc(tfNome,
                                                      tfCognome,
                                                      cbg,
                                                      tfDataNascita,
                                                      tfLuogoNascita,
                                                      tfProvincia,
                                                      displayArea));
        bCanc.addActionListener(new ButtonHandlerCanc(tfNome,
                                                      tfCognome,
                                                      cbg,
                                                      tfDataNascita,
                                                      tfLuogoNascita,
                                                      tfProvincia,
                                                      displayArea));
        pBut.add(bCalc);
        pBut.add(bCanc);
        
        f.add(pID, BorderLayout.NORTH);
        f.add(pArea, BorderLayout.CENTER);
        f.add(pBut, BorderLayout.SOUTH);

        f.setVisible(true);
    }

    public abstract class WindowAdapter implements
    WindowListener
    {
        public void windowClosing (WindowEvent ev)
        {
        }
        public void windowClosed (WindowEvent ev)
        {
        }
        public void windowOpened (WindowEvent ev)
        {
        }
        public void windowActivated (WindowEvent ev)
        {
        }
        public void windowDeactivated (WindowEvent ev)
        {
        }
        public void windowIconified (WindowEvent ev)
        {
        }
        public void windowDeiconified (WindowEvent ev)
        {
        }
    }

    // Nella stop gestisco la chiusura del programma con il click sul bottone X
    public void stop()
    {
        f.addWindowListener( new WindowAdapter()
        {
            public void windowClosing (WindowEvent ev)
            {
                System.exit(0);
            }
        } );
    }

    public static void main(String args[])
    {
        CodiceFiscale cf = new CodiceFiscale();
        cf.setup();

        cf.stop();
    }
}