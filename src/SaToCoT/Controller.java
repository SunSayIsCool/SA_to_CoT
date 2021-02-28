package SaToCoT;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class Controller {

    private ObservableList<harris_sa_list> harris_list = FXCollections.observableArrayList();
    private ObservableList<moto_sa_list> moto_list = FXCollections.observableArrayList();
    private ObservableList<String> team_list = FXCollections.observableArrayList("White", "Yellow", "Orange", "Magenta", "Red", "Maroon", "Purple", "Dark Blue", "Blue", "Cyan", "Teal", "Green", "Dark Green", "Brown");
    private ObservableList<String> role_list = FXCollections.observableArrayList("Team Member", "Team Lead", "HQ", "Sniper", "Medic", "Forward Observer", "RTO", "K9");

    @FXML
    private TableView<harris_sa_list> sign_role_team_tbl_view;

    @FXML
    private TableColumn<harris_sa_list, String> callsign_harris;

    @FXML
    private TableColumn<harris_sa_list, String> team_harris;

    @FXML
    private TableColumn<harris_sa_list, String> role_harris;

    @FXML
    private TableView<moto_sa_list> moto_sign_role_team_tbl_view;

    @FXML
    private TableColumn<moto_sa_list, String> radioid_moto;

    @FXML
    private TableColumn<moto_sa_list, String> team_moto;

    @FXML
    private TableColumn<moto_sa_list, String> role_moto;

    @FXML
    private TextField moto_radioid_txt;

    @FXML
    private Button moto_add_btn;

    @FXML
    private Button moto_del_btn;

    @FXML
    private ComboBox<String> moto_team_cb;

    @FXML
    private ComboBox<String> moto_role_cb;

    @FXML
    private TextField harris_sign_txt;

    @FXML
    private Button harris_add_btn;

    @FXML
    private Button harris_del_btn;

    @FXML
    private ComboBox<String> harris_team_cb;

    @FXML
    private ComboBox<String> harris_role_cb;

    @FXML
    private TextArea log_tx;

    @FXML
    private CheckBox local_fw_chkbx;

    @FXML
    private CheckBox mcast_fwd_chkbx;

    @FXML
    private TextField mcast_addr_txfld;

    @FXML
    private Button apply_btn;

    @FXML
    private Button start_stop_btn;

    @FXML
    private Label status_lbl;

    @FXML
    private Button log_save_btn;

    @FXML
    private Button log_clear_btn;

    @FXML
    private Label lbl_stale;

    @FXML
    private Slider sld_stale;

    Boolean l = false; //send local sa or not
    Boolean m = false; //send multicast sa or not

    @FXML
    void initialize() throws IOException,FileNotFoundException{
        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        String timestamp_file = new SimpleDateFormat("HHmm_dd-MM-yyyy").format(new Date());
        //Initializing tables and comboboxes
        initData();
        readCSV();

        callsign_harris.setCellValueFactory(new PropertyValueFactory<harris_sa_list, String>("callsign"));
        team_harris.setCellValueFactory(new PropertyValueFactory<harris_sa_list, String>("team"));
        role_harris.setCellValueFactory(new PropertyValueFactory<harris_sa_list, String>("role"));
        sign_role_team_tbl_view.setItems(harris_list);

        radioid_moto.setCellValueFactory(new PropertyValueFactory<moto_sa_list, String>("RadioId"));
        team_moto.setCellValueFactory(new PropertyValueFactory<moto_sa_list, String>("team"));
        role_moto.setCellValueFactory(new PropertyValueFactory<moto_sa_list, String>("role"));
        moto_sign_role_team_tbl_view.setItems(moto_list);

        // Stale slider listener
        sld_stale.valueProperty().addListener(new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue){
                int stale_int = newValue.intValue();
                lbl_stale.setText(String.valueOf(stale_int));
            }
        });

        //Setting choose of harris rows
        TableView.TableViewSelectionModel<harris_sa_list> selectionModel = sign_role_team_tbl_view.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<harris_sa_list>() {
            @Override
            public void changed(ObservableValue<? extends harris_sa_list> val, harris_sa_list oldVal, harris_sa_list newVal){
                if(newVal != null) {
                    harris_sign_txt.setText(newVal.getCallsign());
                    harris_team_cb.setValue(newVal.getTeam());
                    harris_role_cb.setValue(newVal.getRole());
                }

            }
        });
        //-------------------

        //Setting choose of harris rows
        TableView.TableViewSelectionModel<moto_sa_list> selectionModel_moto = moto_sign_role_team_tbl_view.getSelectionModel();
        selectionModel_moto.selectedItemProperty().addListener(new ChangeListener<moto_sa_list>() {
            @Override
            public void changed(ObservableValue<? extends moto_sa_list> val, moto_sa_list oldVal, moto_sa_list newVal){
                if(newVal != null) {
                    moto_radioid_txt.setText(newVal.getRadioId());
                    moto_team_cb.setValue(newVal.getTeam());
                    moto_role_cb.setValue(newVal.getRole());
                }

            }
        });
        //-------------------

        log_tx.appendText("[" + timestamp+  "] - App started\n"); //logging to textarea
        System.out.println("[" + timestamp+  "] - App started"); //logging to console

        //Add/modify Harris SA list
        harris_add_btn.setOnAction(ActionEvent -> {
            harris_list.add(new harris_sa_list(harris_sign_txt.getText(), harris_team_cb.getValue(), harris_role_cb.getValue()));
            try {
                save_db_harris();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        //-------------------

        //Add/modify MotoTRBO SA list
        moto_add_btn.setOnAction(ActionEvent -> {
            moto_list.add(new moto_sa_list(moto_radioid_txt.getText(), moto_team_cb.getValue(), moto_role_cb.getValue()));
            try {
                save_db_moto();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        //-------------------

        //Delete Harris SA item
        harris_del_btn.setOnAction(ActionEvent -> {
            sign_role_team_tbl_view.getItems().removeAll(sign_role_team_tbl_view.getSelectionModel().getSelectedItems());
            try {
                save_db_harris();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //-------------------

        //Delete MotoTRBO SA item
        moto_del_btn.setOnAction(ActionEvent -> {
            moto_sign_role_team_tbl_view.getItems().removeAll(moto_sign_role_team_tbl_view.getSelectionModel().getSelectedItems());
            try {
                save_db_moto();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //-------------------

        //Processing start/stop forwarder service
        start_stop_btn.setOnAction(actionEvent -> {

            if (start_stop_btn.getText().equals("Start")) {
                l = local_fw_chkbx.isSelected();
                m = mcast_fwd_chkbx.isSelected();
                start_stop_btn.setText("Stop");
                status_lbl.setText("Forwarder runned");
                status_lbl.setTextFill(Color.web("#808000"));
                System.out.println("[" + timestamp+  "] - Forwarder started");
                log_tx.appendText("[" + timestamp+  "] - Forwarder started\n");
            }
            else if(start_stop_btn.getText().equals("Stop")){
                l = false;
                m = false;
                start_stop_btn.setText("Start");
                status_lbl.setText("Forwarder stopped");
                status_lbl.setTextFill(Color.web("#FF0000"));
                System.out.println("[" + timestamp+  "] - Forwarder stopped (receiving last packets)");
                log_tx.appendText("[" + timestamp+  "] - Forwarder stopped (receiving last packets)\n");
            }

            try {
                recieve_UDP();
            }
            catch (IOException e){
                e.printStackTrace();
            }

        });
        //-------------------

        //Clearing log TextView
        log_clear_btn.setOnAction(ActionEvent -> {
            log_tx.clear();
        });
        //-------------------

        //Saving log to file
        log_save_btn.setOnAction(ActionEvent -> {
           try {
               ObservableList<CharSequence> paragraph = log_tx.getParagraphs();
               Iterator<CharSequence> iter = paragraph.iterator();
               BufferedWriter bf = new BufferedWriter(new FileWriter(new File("log_[" + timestamp_file+  "].log")));
               while(iter.hasNext())
               {
                   CharSequence seq = iter.next();
                   bf.append(seq);
                   bf.newLine();
               }
               bf.flush();
               bf.close();
           }
           catch (IOException e){
               e.printStackTrace();
           }
        });
        //-------------------
    }

    //Saving Harris SA list to harris_db.csv
    private void save_db_harris() throws Exception{
        Writer writer = null;
        try {
            File file = new File("harris_db.csv");
            writer = new BufferedWriter(new FileWriter(file));
            for (harris_sa_list harris_sa_list : harris_list) {

                String text = harris_sa_list.getCallsign() + ";" + harris_sa_list.getTeam() + ";" + harris_sa_list.getRole() + "\n";

                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            writer.flush();
            writer.close();
        }
    }
    private void save_db_moto() throws Exception{
        Writer writer = null;
        try {
            File file = new File("moto_db.csv");
            writer = new BufferedWriter(new FileWriter(file));
            for (moto_sa_list moto_sa_list : moto_list) {

                String text = moto_sa_list.getRadioId() + ";" + moto_sa_list.getTeam() + ";" + moto_sa_list.getRole() + "\n";

                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            writer.flush();
            writer.close();
        }
    }
    //-------------------

    private void initData() { //Initializing Harris SA table
        harris_team_cb.setItems(team_list);
        harris_role_cb.setItems(role_list);
        harris_team_cb.setValue("White");
        harris_role_cb.setValue("Team Member");
        moto_team_cb.setItems(team_list);
        moto_role_cb.setItems(role_list);
        moto_team_cb.setValue("White");
        moto_role_cb.setValue("Team Member");
        //harris_list.add(new harris_sa_list("HH1", "Red", "RTO")); //- list format
        //moto_list.add(new moto_sa_list("iiiiii","Red","jkjlj"));
    }
    //-------------------

    //Reading harris_db.csv
    private void readCSV() throws FileNotFoundException,IOException {

        String CsvFile = "harris_db.csv";
        String MotoCsvFile = "moto_db.csv";
        String FieldDelimiter = ";";


        BufferedReader br;
        BufferedReader moto_br;

        try {
            br = new BufferedReader(new FileReader(CsvFile));

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(FieldDelimiter, -1);
                harris_list.add(new harris_sa_list(fields[0], fields[1], fields[2]));
            }

            moto_br = new BufferedReader(new FileReader(MotoCsvFile));

            String moto_line;
            while ((moto_line = moto_br.readLine()) != null) {
                String[] fields = moto_line.split(FieldDelimiter, -1);
                moto_list.add(new moto_sa_list(fields[0], fields[1], fields[2]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //-------------------

    @FXML
    private void recieve_UDP() throws IOException { //Forwarder for Harris SA service

        ScheduledService<Boolean> ss = new ScheduledService<Boolean>()
        {
            @Override
            protected Task<Boolean> createTask()
            {
                Task<Boolean> task = new Task<Boolean>()
                {
                    @Override
                    protected Boolean call() throws IOException
                    {
                        HarrisSAsendCoT();
                        return true;
                    };
                };
                return task;
            }
        };
        ss.start();
    }
    //-------------------

    //HarrisSAsendCoT("10011", "239.2.3.1", "6969", true, true);

    // Harris SA converter code
    @FXML
    public void HarrisSAsendCoT() throws IOException {


            // Create a socket to listen at port
            DatagramSocket ds = new DatagramSocket(10011);
            byte[] receive = new byte[65535];

            DatagramPacket DpReceive = null;
            while (true) {
                Boolean localSA = l;
                Boolean multiSA = m;

                String team = "White";
                String role = "Team Member";
                if (status_lbl.getText().equals("Forwarder runned")) {

                    String multicast_addr = mcast_addr_txfld.getText().toString();

                    // Create a DatgramPacket to receive the data.
                    DpReceive = new DatagramPacket(receive, receive.length);

                    // Step 3 : revieve the data in byte buffer.
                    ds.receive(DpReceive);
                    String str = new String(receive);
                    str = str.replaceAll("[\\u0000-\u0009]", "");
                    int indexGPRMC = str.indexOf("$GPRMC");
                    int indexCHKSUM = str.indexOf("*");

                    if (indexGPRMC > 0 & indexCHKSUM > 0) {

                        // loop for searching callsigns

                        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                        String In = str;

                        System.out.println("[" + timestamp+  "] - Received: from "+RMC_callsign(In)+": " + In);
                        log_tx.appendText("[" + timestamp+  "] - Received: from "+RMC_callsign(In)+": " + In + "\n");
                        String date = RMC_date(In);
                        String time = RMC_time(In);

                        for (Integer i=0; i<harris_list.size(); i++) {
                            if (RMC_callsign(In).equals(harris_list.get(i).getCallsign())) {
                                team = harris_list.get(i).getTeam();
                                role = harris_list.get(i).getRole();
                            }
                        }

                        System.out.println(role);
                        System.out.println(team);

                        String out_str = "<event version=\"2.0\" uid=\"HarrisSA-to-CoT-v-1-0-"+RMC_callsign(In)+"\" type=\"a-f-G-U-C\" time=\"" + convertDT_CoT(date, time) + "\" start=\"" + convertDT_CoT(date, time) + "\" stale=\"" + staleDT_CoT(date, time, Integer.parseInt(lbl_stale.getText())) + "\" how=\"m-g\">" +
                                "<point lat=\"" + RMC_latitude(In) + "\" lon=\"" + RMC_longtitude(In) + "\" hae=\"25.0\" ce=\"5.0\" le=\"0.0\" />" +
                                "<detail>" +
                                "<uid Droid=\"harris_sa-RF78XX-to-CoT-" + RMC_callsign(In) + "\" />" +
                                "<contact callsign=\"" + RMC_callsign(In) + "\" />" +
                                "<__group name=\""+team+"\" role=\""+role+"\" />" +
                                "<precisionlocation geopointsrc=\"Radio\" altsrc=\"Harris GPS\" />" +
                                "<takv device=\"Radio\" platform=\"Harris\" version=\"RF 78XX\" />" +
                                "<track speed=\"" + RMC_speed(In) + "\" course=\"" + RMC_course(In) + "\" />" +
                                "</detail>" +
                                "</event>";
                        if (localSA == true){
                            send_udp(out_str, "127.0.0.1", "6969");
                            System.out.println("[" + timestamp+  "] -> 127.0.0.1 port:6969");
                            log_tx.appendText("[" + timestamp+  "] -> 127.0.0.1 port:6969\n");
                        }
                        if (multiSA == true && multicast_addr != null){
                            send_ms_udp(out_str, multicast_addr, "6969");
                            System.out.println("[" + timestamp+  "] -> "+multicast_addr+" port:6969");
                            log_tx.appendText("[" + timestamp+  "] -> "+multicast_addr+" port:6969\n");
                        }
                    }
                    receive = new byte[65535]; // Clear the buffer after every message.
                } else {
                    ds.close();
                    System.out.print("local "+ localSA.toString());
                    System.out.println(" Multicast "+ multiSA.toString());
                    break;
                }
            }
    }

    public String checksumm(String input_str) {
        int checksum = 0;
        for (int i = 0; i < input_str.length(); i++) {
            checksum = checksum ^ input_str.charAt(i);
        }
        return Integer.toHexString(checksum).toUpperCase();

    }

    public String RMC_string(String strMain) {

        String[] arrSplit = strMain.split(",");
        return strMain.split("\\$|\\*")[1];

    }

    public String RMC_callsign(String strMain) {

        String[] arrSplit = strMain.split(",");
        String gprmc_str = strMain.split("\\$|\\*")[1];
        String callsign_gprmc = arrSplit[0].split("\\$")[0];
        return callsign_gprmc.substring(1, callsign_gprmc.length() - 1);

    }

    public String RMC_time(String strMain) {

        String[] arrSplit = strMain.split(",");

        return arrSplit[1];

    }

    public char RMC_valid(String strMain) {

        String[] arrSplit = strMain.split(",");
        return arrSplit[2].charAt(0);

    }

    public String RMC_latitude(String strMain) {

        String[] arrSplit = strMain.split(",");
        float dd = Float.parseFloat(arrSplit[3].substring(0, 2));
        float mm = Float.parseFloat(arrSplit[3].substring(2, 4));
        float ss = Float.parseFloat("0" + arrSplit[3].substring(4, arrSplit[3].length()));
        float dddd = dd + mm / 60 + ss * 60 / 3600;
        switch (arrSplit[4].charAt(0)) {
            case 'N':
                dddd = dddd;
                break;
            case 'S':
                dddd = 0 - dddd;
                break;
        }
        return Float.toString(dddd);

    }

    public String RMC_longtitude(String strMain) {

        String[] arrSplit = strMain.split(",");
        float dd = Float.parseFloat(arrSplit[5].substring(0, 3));
        float mm = Float.parseFloat(arrSplit[5].substring(3, 5));
        float ss = Float.parseFloat("0" + arrSplit[5].substring(5, arrSplit[5].length()));
        float dddd = dd + mm / 60 + ss * 60 / 3600;
        switch (arrSplit[6].charAt(0)) {
            case 'E':
                dddd = dddd;
                break;
            case 'W':
                dddd = 0 - dddd;
                break;
        }
        return Float.toString(dddd);

    }

    public String RMC_speed(String strMain) {

        String[] arrSplit = strMain.split(",");
        float ms = Float.parseFloat(arrSplit[7]);
        ms = ms * 0.514444F;
        return Float.toString(ms);

    }

    public String RMC_course(String strMain) {

        String[] arrSplit = strMain.split(",");
        return arrSplit[8];

    }

    public String RMC_date(String strMain) {

        String[] arrSplit = strMain.split(",");
        return arrSplit[9];
    }

    public String chksum_callsign(String strMain) {

        String[] arrSplit = strMain.split(",");
        String callsign_chksum = arrSplit[11].split("\\*")[1];
        return callsign_chksum.substring(2, callsign_chksum.length());

    }

    public String RMC_chksum(String strMain) {

        String[] arrSplit = strMain.split(",");
        String callsign_chksum = arrSplit[11].split("\\*")[1];
        String chksum = callsign_chksum.substring(0, 2);
        return callsign_chksum.substring(0, 2);

    }

    public String convertDT_CoT(String inputDate, String inputTime) {

        String DateTime = inputDate + inputTime;
        Calendar c = Calendar.getInstance();
        try {
            Date date = new SimpleDateFormat("ddMMyyHHmmss").parse(DateTime);
            DateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return DateTime;
    }

    public String staleDT_CoT(String inputDate, String inputTime, Integer inputStale) {

        String DateTime = inputDate + inputTime;
        Calendar c = Calendar.getInstance();

        try {
            Date date = new SimpleDateFormat("ddMMyyHHmmss").parse(DateTime);
            c.setTime(date);
            c.add(Calendar.MINUTE, inputStale);
            Date currentDatePlusOne = c.getTime();
            DateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(currentDatePlusOne);
        }  catch (ParseException e) {
            e.printStackTrace();
        }

        return DateTime;
    }

    public void send_udp(String input_str, String ip_addr, String port) throws IOException {

        DatagramSocket ds = new DatagramSocket();

        InetAddress ip = InetAddress.getByName(ip_addr);
        int udp_port = Integer.parseInt(port);
        byte buf[] = null;
        buf = input_str.getBytes();
        DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, udp_port);
        ds.send(DpSend);
    }

    public void send_ms_udp(String input_str, String ip_addr, String port) throws IOException {

        InetAddress ip = InetAddress.getByName(ip_addr);
        int udp_port = Integer.parseInt(port);
        int ttl = 64;
        MulticastSocket s = new MulticastSocket();
        byte buf[] = null;
        buf = input_str.getBytes();
        DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, udp_port);
        s.send(DpSend,(byte)ttl);
        s.close();
    }
    // Harris SA converter code
}
