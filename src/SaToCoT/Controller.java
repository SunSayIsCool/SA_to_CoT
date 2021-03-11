package SaToCoT;

import javafx.application.Platform;
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
    private ObservableList<String> domain_list = FXCollections.observableArrayList("AIR CIV","AIR MIL","GROUND SENSORS","GROUND VEHICLE","GROUND WEAPON","GROUND UNIT COMBAT","GROUND UNIT SERVICE SUPPORT","GROUND UNIT COMBAT SUPPORT","SURFACE","SUBSURFACE","SOF");
    private ObservableList<String> air_civ_list = FXCollections.observableArrayList("Air Civ", "Fixed", "Rotary", "Blimp");
    private ObservableList<String> air_mil_list = FXCollections.observableArrayList("Fixed","Fixed/Attack","Fixed/Bomber","Fixed/Transport","Fixed/C2","Fixed/Fighter","Fixed/Interceptor","Fixed/CSAR","Fixed/Jammer",
                                                                                           "Fixed/Tanker","Fixed/VSTOL","Fixed/SOF","Fixed/MEDEVAC","Fixed/Patrol","Fixed/UAV","Fixed/RECON","Fixed/Trainer","Fixed/Utility",
                                                                                            "Fixed/C3I","Rotor","Rotor/Attack","Rotor/Transport","Rotor/C2","Rotor/CSAR","Rotor/Jammer","Rotor/SOF","Rotor/MEDEVAC","Rotor/UAV","Rotor/RECON","Rotor/Utility","Blimp");
    private ObservableList<String> gnd_sens_list = FXCollections.observableArrayList("Sensor","Emplaced","Radar");
    private ObservableList<String> gnd_vech_list = FXCollections.observableArrayList("Vehicle","Armor/Gun","Apc","Apc/Recovery","C2V/ACV","Armor/Infantry",
                                                                                            "Armor/Light","Armor/Combat Service Support","Tank","Civilian","Engineer",
                                                                                            "Mine Clearing Vehicle","Utility","Bus","Cross Country Truck","Boat","Semi","Ambulance");
    private ObservableList<String> gnd_weap_list = FXCollections.observableArrayList("Weapon","Air Defense Gun","DirectFire","AnitTankGun","Howitzer","MissileLauncher",
                                                                                            "Mortar","Rifle","Automatic Weapon","Light Machine Gun","Heavy Machine Gun",
                                                                                            "RocketLauncher/Single","RocketLauncher/Multiple","RocketLauncher/AntiTank","Flame Thrower","Nbc Equipment");
    private ObservableList<String> gnd_unt_cbt_list = FXCollections.observableArrayList("Combat","ARMOR","ANTI ARMOR","ANTI ARMOR, ARMORED AIR ASSAULT","ANTI ARMOR, ARMORED TRACKED",
                                                                                                "ANTI ARMOR, ARMORED WHEELED","ANTI ARMOR, AIRBORNE","ANTI ARMOR, MOTORIZED","ARMOR TRACK","ARMOR TRACK AMPHIBIOUS",
                                                                                                "ARMOR, WHEELED","ARMOR, WHEELED AIRBORNE","ARMOR, WHEELED RECOVERY","ARMOR, WHEELED AMPHIBIOUS","AIR DEFENSE","AIR DEFENSE MISSILE",
                                                                                                "Engineer/ENGINEER","Artillery (Fixed)","MORTAR","ROCKET","SINGLE ROCKET SELF PROPELLED","MULTI ROCKET SELF PROPELLED","Infantry/Troops",
                                                                                                "Infantry/Airborne","Infantry/Fighting Vehicle","Infantry/Motorized","Infantry/Naval","Infantry/Air Assault","Infantry/Mountain","Infantry/Mechanized",
                                                                                                "MISSILE (SURF SURF)","Recon","Recon/AIRBORNE","Recon/MOUNTAIN","Recon/MARINE","Recon/AIR ASSAULT","INTERNAL SECURITY FORCES","Aviation/AVIATION",
                                                                                                "Aviation/COMPOSITE","Aviation/FIXED WING","Aviation/ROTARY WING");
    private ObservableList<String> gnd_unt_ss_list = FXCollections.observableArrayList("C2 HEADQUARTERS COMPONENT","MEDICAL","SUPPLY","TRANSPORTATION","MAINTENANCE","MAINTENANCE RECOVERY");
    private ObservableList<String> gnd_unt_cs_list = FXCollections.observableArrayList("COMBAT SUPPORT","COMBAT SUPPORT NBC","BIOLOGICAL","CHEMICAL","NUCLEAR","EXPLOSIVE ORDINANCE DISPOSAL",
                                                                                                "INFORMATION WARFARE UNIT","LAW ENFORCEMENT UNIT","CIVILIAN LAW ENFORCEMENT","MILITARY POLICE","MILITARY INTELLIGENCE",
                                                                                                "COUNTER INTELLIGENCE","SIGNAL INTELLIGENCE (SIGINT)","ELECTRONIC WARFARE","SIGNAL UNIT","COMMAND OPERATIONS","RADIO UNIT",
                                                                                                "TACTICAL SATELLITE","RELAY","TELEPHONE SWITCH");
    private ObservableList<String> sfc_list = FXCollections.observableArrayList("SEA SURFACE TRACK","COMBATANT","AMPHIBIOUS WARFARE SHIP","ASSAULT VESSEL","LANDING CRAFT",
                                                                                        "LANDING SHIP","HOVERCRAFT","LINE","BATTLESHIP","CRUISER","CARRIER","DESTROYER","FRIGATE/CORVETTE",
                                                                                        "MINE WARFARE VESSEL","MINEHUNTER","MINELAYER","PATROL","ANTISUBMARINE WARFARE","ANTISURFACE WARFARE",
                                                                                        "CONVOY","HOSPITAL SHIP","RESCUE","NON MILITARY","CARGO","PASSENGER","TANKER");
    private ObservableList<String> sub_sfc_list = FXCollections.observableArrayList("SUBSURFACE TRACK","DIVER","SUBMARINE","SUBMARINE CONVENTIONAL PROPULSION","SUBMARINE NUCLEAR PROPULSION","UNMANNED UNDERWATER VEHICLE (UUV)");
    private ObservableList<String> sof_list = FXCollections.observableArrayList("AVIATION Fixed","AVIATION Rotary","AVIATION CSAR","NAVAL","SPECIAL BOAT","SPECIAL SSNR","GROUND","RANGER","PSYOPS","SUPPORT");




    @FXML
    private TableView<harris_sa_list> harris_sa_tbl_view;

    @FXML
    private TableColumn<harris_sa_list, String> callsign_harris;

    @FXML
    private TableColumn<harris_sa_list, String> domain_harris;

    @FXML
    private TableColumn<harris_sa_list, String> unit_harris;

    @FXML
    private TableColumn<harris_sa_list, String> alias_harris;

    @FXML
    private TableView<moto_sa_list> moto_sa_tbl_view;

    @FXML
    private TableColumn<moto_sa_list, String> radioid_moto;

    @FXML
    private TableColumn<moto_sa_list, String> domain_moto;

    @FXML
    private TableColumn<moto_sa_list, String> unit_moto;

    @FXML
    private TableColumn<moto_sa_list, String> alias_moto;

    @FXML
    private TextField moto_radioid_txt;

    @FXML
    private Button moto_add_btn;

    @FXML
    private Button moto_del_btn;

    @FXML
    private ComboBox<String> moto_domain_cb;

    @FXML
    private ComboBox<String> moto_unit_cb;

    @FXML
    private TextField harris_sign_txt;

    @FXML
    private Button harris_add_btn;

    @FXML
    private Button harris_del_btn;

    @FXML
    private ComboBox<String> harris_domain_cb;

    @FXML
    private ComboBox<String> harris_unit_cb;

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

    @FXML
    private TextField harris_alias_txt;

    @FXML
    private TextField moto_alias_txt;

    Boolean l; //send local sa or not
    Boolean m; //send multicast sa or not
    String version = "1.1";

    @FXML
    void initialize() throws IOException, FileNotFoundException {

        String timestamp = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String timestamp_file = new SimpleDateFormat("HHmm_dd-MM-yyyy").format(new Date());

        //logging to textarea
        Platform.runLater(() -> {log_tx.appendText("-------------------------[" + timestamp + "]-------------------------");});
        addLog(" - App started"); //logging to console

        //Initializing tables and comboboxes
        initData();
        readCSV();

        // Stale slider listener
        sld_stale.valueProperty().addListener(new ChangeListener<Number>() {

            public void changed(ObservableValue<? extends Number> changed, Number oldValue, Number newValue) {
                int stale_int = newValue.intValue();
                lbl_stale.setText(String.valueOf(stale_int));
            }
        });

        //Setting choose of harris rows
        TableView.TableViewSelectionModel<harris_sa_list> selectionModel = harris_sa_tbl_view.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<harris_sa_list>() {
            @Override
            public void changed(ObservableValue<? extends harris_sa_list> val, harris_sa_list oldVal, harris_sa_list newVal) {
                if (newVal != null) {
                    harris_sign_txt.setText(newVal.getCallsign());
                    harris_domain_cb.setValue(newVal.getDomain());
                    harris_unit_cb.setValue(newVal.getItem());
                    harris_alias_txt.setText(newVal.getAlias());
                }

            }
        });
        //-------------------

        //Setting choose of harris rows
        TableView.TableViewSelectionModel<moto_sa_list> selectionModel_moto = moto_sa_tbl_view.getSelectionModel();
        selectionModel_moto.selectedItemProperty().addListener(new ChangeListener<moto_sa_list>() {
            @Override
            public void changed(ObservableValue<? extends moto_sa_list> val, moto_sa_list oldVal, moto_sa_list newVal) {
                if (newVal != null) {
                    moto_radioid_txt.setText(newVal.getRadioId());
                    moto_domain_cb.setValue(newVal.getDomain());
                    moto_unit_cb.setValue(newVal.getItem());
                    moto_alias_txt.setText(newVal.getAlias());
                }

            }
        });
        //-------------------

        //Add/modify Harris SA list
        harris_add_btn.setOnAction(ActionEvent -> {
            harris_list.add(new harris_sa_list(harris_sign_txt.getText(), harris_domain_cb.getValue(), harris_unit_cb.getValue(), harris_alias_txt.getText()));
            try {
                save_db_harris();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        //Add/modify MotoTRBO SA list
        moto_add_btn.setOnAction(ActionEvent -> {
            moto_list.add(new moto_sa_list(moto_radioid_txt.getText(), moto_domain_cb.getValue(), moto_unit_cb.getValue(), moto_alias_txt.getText()));
            try {
                save_db_moto();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        //-------------------

        //Delete Harris SA item
        harris_del_btn.setOnAction(ActionEvent -> {
            harris_sa_tbl_view.getItems().removeAll(harris_sa_tbl_view.getSelectionModel().getSelectedItems());
            try {
                save_db_harris();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //-------------------

        //Delete MotoTRBO SA item
        moto_del_btn.setOnAction(ActionEvent -> {
            moto_sa_tbl_view.getItems().removeAll(moto_sa_tbl_view.getSelectionModel().getSelectedItems());
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
                addLog(" - Forwarder started");

            } else if (start_stop_btn.getText().equals("Stop")) {
                l = false;
                m = false;
                start_stop_btn.setText("Start");
                status_lbl.setText("Forwarder stopped");
                status_lbl.setTextFill(Color.web("#FF0000"));
                addLog(" - Forwarder stopped (receiving last packets)");

            }

            try {
                recieve_UDP();
            } catch (IOException e) {
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
                BufferedWriter bf = new BufferedWriter(new FileWriter(new File("log_[" + timestamp_file + "].log")));
                while (iter.hasNext()) {
                    CharSequence seq = iter.next();
                    bf.append(seq);
                    bf.newLine();
                }
                bf.flush();
                bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //-------------------
    }

    private void initData() {
        //Initializing Harris SA table
        harris_domain_cb.setItems(domain_list);
        harris_unit_cb.setItems(air_civ_list);
        harris_domain_cb.setValue("AIR CIV");
        harris_unit_cb.setValue("Air Civ");
        moto_domain_cb.setItems(domain_list);
        moto_unit_cb.setItems(air_civ_list);
        moto_domain_cb.setValue("AIR CIV");
        moto_unit_cb.setValue("Air Civ");

        callsign_harris.setCellValueFactory(new PropertyValueFactory<harris_sa_list, String>("callsign"));
        domain_harris.setCellValueFactory(new PropertyValueFactory<harris_sa_list, String>("domain"));
        unit_harris.setCellValueFactory(new PropertyValueFactory<harris_sa_list, String>("item"));
        alias_harris.setCellValueFactory(new PropertyValueFactory<harris_sa_list, String>("alias"));
        harris_sa_tbl_view.setItems(harris_list);

        radioid_moto.setCellValueFactory(new PropertyValueFactory<moto_sa_list, String>("RadioId"));
        domain_moto.setCellValueFactory(new PropertyValueFactory<moto_sa_list, String>("domain"));
        unit_moto.setCellValueFactory(new PropertyValueFactory<moto_sa_list, String>("item"));
        alias_moto.setCellValueFactory(new PropertyValueFactory<moto_sa_list, String>("alias"));
        moto_sa_tbl_view.setItems(moto_list);

        //Change comboboxes for HarrisSA
        harris_domain_cb.setOnAction(event -> {
            switch (harris_domain_cb.getValue().toString()){
                case  ("AIR CIV"):
                    harris_unit_cb.setItems(air_civ_list);
                    harris_unit_cb.setValue("Air Civ");
                    break;
                case ("AIR MIL"):
                    harris_unit_cb.setItems(air_mil_list);
                    harris_unit_cb.setValue("Fixed");
                    break;
                case ("GROUND SENSORS"):
                    harris_unit_cb.setItems(gnd_sens_list);
                    harris_unit_cb.setValue("Sensor");
                    break;
                case ("GROUND VEHICLE"):
                    harris_unit_cb.setItems(gnd_vech_list);
                    harris_unit_cb.setValue("Vehicle");
                    break;
                case ("GROUND WEAPON"):
                    harris_unit_cb.setItems(gnd_weap_list);
                    harris_unit_cb.setValue("Weapon");
                    break;
                case ("GROUND UNIT COMBAT"):
                    harris_unit_cb.setItems(gnd_unt_cbt_list);
                    harris_unit_cb.setValue("Combat");
                    break;
                case ("GROUND UNIT SERVICE SUPPORT"):
                    harris_unit_cb.setItems(gnd_unt_ss_list);
                    harris_unit_cb.setValue("C2 HEADQUARTERS COMPONENT");
                    break;
                case ("GROUND UNIT COMBAT SUPPORT"):
                    harris_unit_cb.setItems(gnd_unt_cs_list);
                    harris_unit_cb.setValue("COMBAT SUPPORT");
                    break;
                case ("SURFACE"):
                    harris_unit_cb.setItems(sfc_list);
                    harris_unit_cb.setValue("SEA SURFACE TRACK");
                    break;
                case ("SUBSURFACE"):
                    harris_unit_cb.setItems(sub_sfc_list);
                    harris_unit_cb.setValue("SUBSURFACE TRACK");
                    break;
                case ("SOF"):
                    harris_unit_cb.setItems(sof_list);
                    harris_unit_cb.setValue("AVIATION Fixed");
                    break;
            }
        });
        //-------------------

        //Change comboboxes for HarrisSA
        moto_domain_cb.setOnAction(event -> {
            switch (moto_domain_cb.getValue().toString()){
                case  ("AIR CIV"):
                    moto_unit_cb.setItems(air_civ_list);
                    moto_unit_cb.setValue("Air Civ");
                    break;
                case ("AIR MIL"):
                    moto_unit_cb.setItems(air_mil_list);
                    moto_unit_cb.setValue("Fixed");
                    break;
                case ("GROUND SENSORS"):
                    moto_unit_cb.setItems(gnd_sens_list);
                    moto_unit_cb.setValue("Sensor");
                    break;
                case ("GROUND VEHICLE"):
                    moto_unit_cb.setItems(gnd_vech_list);
                    moto_unit_cb.setValue("Vehicle");
                    break;
                case ("GROUND WEAPON"):
                    moto_unit_cb.setItems(gnd_weap_list);
                    moto_unit_cb.setValue("Weapon");
                    break;
                case ("GROUND UNIT COMBAT"):
                    moto_unit_cb.setItems(gnd_unt_cbt_list);
                    moto_unit_cb.setValue("Combat");
                    break;
                case ("GROUND UNIT SERVICE SUPPORT"):
                    moto_unit_cb.setItems(gnd_unt_ss_list);
                    moto_unit_cb.setValue("C2 HEADQUARTERS COMPONENT");
                    break;
                case ("GROUND UNIT COMBAT SUPPORT"):
                    moto_unit_cb.setItems(gnd_unt_cs_list);
                    moto_unit_cb.setValue("COMBAT SUPPORT");
                    break;
                case ("SURFACE"):
                    moto_unit_cb.setItems(sfc_list);
                    moto_unit_cb.setValue("SEA SURFACE TRACK");
                    break;
                case ("SUBSURFACE"):
                    moto_unit_cb.setItems(sub_sfc_list);
                    moto_unit_cb.setValue("SUBSURFACE TRACK");
                    break;
                case ("SOF"):
                    moto_unit_cb.setItems(sof_list);
                    moto_unit_cb.setValue("AVIATION Fixed");
                    break;
            }
        });
        //-------------------
    }

    //Saving Harris SA list to harris_db.csv
    private void save_db_harris() throws Exception {
        Writer writer = null;
        try {
            File file = new File("harris_db.csv");
            writer = new BufferedWriter(new FileWriter(file));
            for (harris_sa_list harris_sa_list : harris_list) {

                String text = harris_sa_list.getCallsign() + ";" + harris_sa_list.getDomain() + ";" + harris_sa_list.getItem() + ";" + harris_sa_list.getAlias() + "\n";

                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }

    //Saving Moto SA list to moto_db.csv
    private void save_db_moto() throws Exception {
        Writer writer = null;
        try {
            File file = new File("moto_db.csv");
            writer = new BufferedWriter(new FileWriter(file));
            for (moto_sa_list moto_sa_list : moto_list) {

                String text = moto_sa_list.getRadioId() + ";" + moto_sa_list.getDomain() + ";" + moto_sa_list.getItem() + ";" + moto_sa_list.getAlias()+ "\n";

                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }
    //-------------------

    //Reading harris_db.csv
    private void readCSV() throws FileNotFoundException, IOException {

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
                harris_list.add(new harris_sa_list(fields[0],fields[1],fields[2],fields[3]));
            }

            moto_br = new BufferedReader(new FileReader(MotoCsvFile));

            String moto_line;
            while ((moto_line = moto_br.readLine()) != null) {
                String[] fields = moto_line.split(FieldDelimiter, -1);
                moto_list.add(new moto_sa_list(fields[0],fields[1],fields[2],fields[3]));
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

        ScheduledService<Boolean> ss = new ScheduledService<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                Task<Boolean> task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws IOException {
                        HarrisSAsendCoT();
                        return true;
                    }

                    ;
                };
                return task;
            }
        };
        ss.start();
    }
    //-------------------

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
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return DateTime;
    }

    public void send_udp(String input_str, String ip_addr, String port) {
        try {
            DatagramSocket ds = new DatagramSocket();

            InetAddress ip = InetAddress.getByName(ip_addr);
            int udp_port = Integer.parseInt(port);
            byte buf[] = null;
            buf = input_str.getBytes();
            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, udp_port);
            ds.send(DpSend);
            ds.close();
        } catch (IOException ex) {
            System.out.println("Local error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void send_ms_udp(String input_str, String ip_addr, String port, int ttl) {
        try{
            int udp_port = Integer.parseInt(port);
            Integer ttlByte = new Integer(ttl);
            MulticastSocket mcast_sock = new MulticastSocket();
            byte buf[] = null;
            buf = input_str.getBytes();
            DatagramPacket mc_Send = new DatagramPacket(buf, buf.length, InetAddress.getByName(ip_addr), udp_port);
            mcast_sock.send(mc_Send, ttlByte.byteValue());
            mcast_sock.close();
        } catch (IOException ex) {
            System.out.println("Multicast error: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public void addLog (String message) {
        String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        System.out.println("[" + timestamp + "]" + message);
        Platform.runLater(() -> {log_tx.appendText("\n[" + timestamp + "]" + message);});
    }

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

            String domain = " ";
            String item = " ";
            String alias = "N/A";
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

                    addLog(" - Received: from " + RMC_callsign(In) + ": " + In);
                    String date = RMC_date(In);
                    String time = RMC_time(In);

                    for (Integer i = 0; i < harris_list.size(); i++) {
                        if (RMC_callsign(In).equals(harris_list.get(i).getCallsign())) {
                            domain = harris_list.get(i).getDomain();
                            item = harris_list.get(i).getItem();
                            alias = harris_list.get(i).getAlias();
                        }
                    }

                    CoT_return type = new CoT_return(domain, item);

                    String out_str = "<event version=\"2.0\" uid=\"satocot-v-"+version+"-harrissa-combatid-" + RMC_callsign(In) +
                            "\" type=\"" + type + "\" time=\"" + convertDT_CoT(date, time) +
                            "\" start=\"" + convertDT_CoT(date, time) + "\" stale=\"" + staleDT_CoT(date, time, Integer.parseInt(lbl_stale.getText())) +
                            "\" how=\"m-g\">" +
                            "<point lat=\"" + RMC_latitude(In) + "\" lon=\"" + RMC_longtitude(In) + "\" hae=\"25.0\" ce=\"5.0\" le=\"0.0\" />" +
                            "<detail>" +
                            "<contact callsign=\"" + alias + "\" />" +
                            "<precisionlocation geopointsrc=\"Radio\" altsrc=\"Harris GPS\" />" +
                            "<track speed=\"" + RMC_speed(In) + "\" course=\"" + RMC_course(In) + "\" />" +
                            "</detail>" +
                            "</event>";
                    if (localSA == true) {
                        send_udp(out_str, "127.0.0.1", "6969");
                        addLog(" -> 127.0.0.1 port:6969");
                    }
                    if (multiSA == true && multicast_addr != null) {
                        send_ms_udp(out_str, multicast_addr, "6969", 64);

                        addLog(" -> " + multicast_addr + " port:6969");
                    }
                }
                receive = new byte[65535]; // Clear the buffer after every message.
            } else {
                ds.close();
                break;
            }
        }
    }
    // Harris SA converter code
}

