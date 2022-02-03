package SaToCoT.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CoTinputController implements Initializable {

    @FXML
    private ScrollPane sp_sfap;

    @FXML
    private ScrollPane sp_sfapc;

    @FXML
    private ScrollPane sp_sfapm;

    @FXML
    private ScrollPane sp_sfapmf;

    @FXML
    private ScrollPane sp_sfapmfc;

    @FXML
    private ScrollPane sp_sfapmff;

    @FXML
    private ScrollPane sp_sfapmfk;

    @FXML
    private ScrollPane sp_sfapmfp;

    @FXML
    private ScrollPane sp_sfapmfq;

    @FXML
    private ScrollPane sp_sfapmfqr;

    @FXML
    private ScrollPane sp_sfapmfr;

    @FXML
    private ScrollPane sp_sfapmfu;

    @FXML
    private ScrollPane sp_sfapmh;

    @FXML
    private ScrollPane sp_sfapmhc;

    @FXML
    private ScrollPane sp_sfapmhu;

    @FXML
    private ScrollPane sp_sfapw;

    @FXML
    private ScrollPane sp_sfapwm;

    @FXML
    private ScrollPane sp_sfapwma;

    @FXML
    private ScrollPane sp_sfapwms;

    @FXML
    private ScrollPane sp_sfgp;

    @FXML
    private ScrollPane sp_sfgpe;

    @FXML
    private ScrollPane sp_sfgpev;

    @FXML
    private ScrollPane sp_sfgpeva;

    @FXML
    private ScrollPane sp_sfgpevaa;

    @FXML
    private ScrollPane sp_sfgpevat;

    @FXML
    private ScrollPane sp_sfgpevath;

    @FXML
    private ScrollPane sp_sfgpevatl;

    @FXML
    private ScrollPane sp_sfgpevatm;

    @FXML
    private ScrollPane sp_sfgpevc;


    public String CIDC;

    private Stage stage;


    public void setStage(Stage mainStage) {
        this.stage = mainStage;
    }


    public void back_cot() {
        if (sp_sfap.isVisible()) {
            sp_sfap.setVisible(false);
        }
        if (sp_sfapc.isVisible()) {
            sp_sfap.setVisible(true);
            sp_sfapc.setVisible(false);
        }
        if (sp_sfapm.isVisible()) {
            sp_sfap.setVisible(true);
            sp_sfapm.setVisible(false);
        }
        if (sp_sfapmf.isVisible()) {
            sp_sfapm.setVisible(true);
            sp_sfapmf.setVisible(false);
        }
        if (sp_sfapmfc.isVisible()) {
            sp_sfapmf.setVisible(true);
            sp_sfapmfc.setVisible(false);
        }
        if (sp_sfapmfq.isVisible()) {
            sp_sfapmf.setVisible(true);
            sp_sfapmfq.setVisible(false);
        }
        if (sp_sfapmfqr.isVisible()) {
            sp_sfapmfq.setVisible(true);
            sp_sfapmfqr.setVisible(false);
        }
        if (sp_sfapmff.isVisible()) {
            sp_sfapmf.setVisible(true);
            sp_sfapmff.setVisible(false);
        }
        if (sp_sfapmfp.isVisible()) {
            sp_sfapmf.setVisible(true);
            sp_sfapmfp.setVisible(false);
        }
        if (sp_sfapmfr.isVisible()) {
            sp_sfapmf.setVisible(true);
            sp_sfapmfr.setVisible(false);
        }
        if (sp_sfapmfk.isVisible()) {
            sp_sfapmf.setVisible(true);
            sp_sfapmfk.setVisible(false);
        }
        if (sp_sfapmfu.isVisible()) {
            sp_sfapmf.setVisible(true);
            sp_sfapmfu.setVisible(false);
        }
        if (sp_sfapmh.isVisible()) {
            sp_sfapm.setVisible(true);
            sp_sfapmh.setVisible(false);
        }
        if (sp_sfapmhc.isVisible()) {
            sp_sfapmh.setVisible(true);
            sp_sfapmhc.setVisible(false);
        }
        if (sp_sfapmhu.isVisible()) {
            sp_sfapmh.setVisible(true);
            sp_sfapmhu.setVisible(false);
        }
        if (sp_sfapw.isVisible()) {
            sp_sfap.setVisible(true);
            sp_sfapw.setVisible(false);
        }
        if (sp_sfapwm.isVisible()) {
            sp_sfapw.setVisible(true);
            sp_sfapwm.setVisible(false);
        }
        if (sp_sfapwma.isVisible()) {
            sp_sfapwm.setVisible(true);
            sp_sfapwma.setVisible(false);
        }
        if (sp_sfapwms.isVisible()) {
            sp_sfapwm.setVisible(true);
            sp_sfapwms.setVisible(false);
        }
        if (sp_sfgp.isVisible()) {
            sp_sfgp.setVisible(false);
        }
        if (sp_sfgpe.isVisible()) {
            sp_sfgp.setVisible(true);
            sp_sfgpe.setVisible(false);
        }
        if (sp_sfgpev.isVisible()) {
            sp_sfgpe.setVisible(true);
            sp_sfgpev.setVisible(false);
        }
        if (sp_sfgpeva.isVisible()) {
            sp_sfgpev.setVisible(true);
            sp_sfgpeva.setVisible(false);
        }
        if (sp_sfgpevaa.isVisible()) {
            sp_sfgpeva.setVisible(true);
            sp_sfgpevaa.setVisible(false);
        }
        if (sp_sfgpevat.isVisible()) {
            sp_sfgpeva.setVisible(true);
            sp_sfgpevat.setVisible(false);
        }
        if (sp_sfgpevatl.isVisible()) {
            sp_sfgpevat.setVisible(true);
            sp_sfgpevatl.setVisible(false);
        }
        if (sp_sfgpevatm.isVisible()) {
            sp_sfgpevat.setVisible(true);
            sp_sfgpevatm.setVisible(false);
        }
        if (sp_sfgpevath.isVisible()) {
            sp_sfgpevat.setVisible(true);
            sp_sfgpevath.setVisible(false);
        }
        if (sp_sfgpevc.isVisible()) {
            sp_sfgpev.setVisible(true);
            sp_sfgpevc.setVisible(false);
        }
    }

    public void cot_btn_pressed(ActionEvent actionEvent) {
        Button cotButtonChoose = (Button) actionEvent.getSource();
        switch (cotButtonChoose.getId()) {
            //Air track
            case ("btn_sfap"):
                sp_sfap.setVisible(true);
                break;

            //Air track->Civil
            case ("btn_sfapc"):
                sp_sfapc.setVisible(true);
                break;

            //Air track->Military
            case ("btn_sfapm"):
                sp_sfapm.setVisible(true);
                break;
            //Air track->Military->Fixed
            case ("btn_sfapmf"):
                sp_sfapmf.setVisible(true);
                break;
            //Air track->Military->Fixed->Cargo
            case ("btn_sfapmfc"):
                sp_sfapmfc.setVisible(true);
                break;
            //Air track->Military->Fixed->Drone
            case ("btn_sfapmfq"):
                sp_sfapmfq.setVisible(true);
                break;
            //Air track->Military->Fixed->Drone->Reconnaissance
            case ("btn_sfapmfqr"):
                sp_sfapmfqr.setVisible(true);
                break;
            //Air track->Military->Fixed->Fighter
            case ("btn_sfapmff"):
                sp_sfapmff.setVisible(true);
                break;
            //Air track->Military->Fixed->Patrol
            case ("btn_sfapmfp"):
                sp_sfapmfp.setVisible(true);
                break;
            //Air track->Military->Fixed->Reconnaissance
            case ("btn_sfapmfr"):
                sp_sfapmfr.setVisible(true);
                break;
            //Air track->Military->Fixed->Tanker
            case ("btn_sfapmfk"):
                sp_sfapmfk.setVisible(true);
                break;
            //Air track->Military->Fixed->Utility
            case ("btn_sfapmfu"):
                sp_sfapmfu.setVisible(true);
                break;
            //Air track->Military->Rotary
            case ("btn_sfapmh"):
                sp_sfapmh.setVisible(true);
                break;
            //Air track->Military->Rotary->Cargo
            case ("btn_sfapmhc"):
                sp_sfapmhc.setVisible(true);
                break;
            //Air track->Military->Rotary->Utility
            case ("btn_sfapmhu"):
                sp_sfapmhu.setVisible(true);
                break;


            //Air track->Military->Weapon
            case ("btn_sfapw"):
                sp_sfapw.setVisible(true);
                break;
            //Air track->Military->Weapon->Missle in flight
            case ("btn_sfapwm"):
                sp_sfapwm.setVisible(true);
                break;
            //Air track->Military->Weapon->Missle in flight->Air launched missile
            case ("btn_sfapwma"):
                sp_sfapwma.setVisible(true);
                break;
            //Air track->Military->Weapon->Surface launched missile
            case ("btn_sfapwms"):
                sp_sfapwms.setVisible(true);
                break;

            //Ground track
            case ("btn_sfgp"):
                sp_sfgp.setVisible(true);
                break;
            //Ground track->Equipment
            case ("btn_sfgpe"):
                sp_sfgpe.setVisible(true);
                break;

            //Ground track->Equipment->Vehicle
            case ("btn_sfgpev"):
                sp_sfgpev.setVisible(true);
                break;
            //Ground track->Equipment->Vehicle->Armored
            case ("btn_sfgpeva"):
                sp_sfgpeva.setVisible(true);
                break;
            //Ground track->Equipment->Vehicle->Armored->APC
            case ("btn_sfgpevaa"):
                sp_sfgpevaa.setVisible(true);
                break;
            //Ground track->Equipment->Vehicle->Armored->Tank
            case ("btn_sfgpevat"):
                sp_sfgpevat.setVisible(true);
                break;
            //Ground track->Equipment->Vehicle->Armored->Tank->Light
            case ("btn_sfgpevatl"):
                sp_sfgpevatl.setVisible(true);
                break;
            //Ground track->Equipment->Vehicle->Armored->Tank->Medium
            case ("btn_sfgpevatm"):
                sp_sfgpevatm.setVisible(true);
                break;
            //Ground track->Equipment->Vehicle->Armored->Tank->Heavy
            case ("btn_sfgpevath"):
                sp_sfgpevath.setVisible(true);
                break;
            //Ground track->Equipment->Vehicle->Civilian
            case ("btn_sfgpevc"):
                sp_sfgpevc.setVisible(true);
                break;

            //Sea surface track
            case ("btn_sfsp"):
                sp_sfap.setVisible(true);
                break;

            //Space track
            case ("btn_sfpp"):
                sp_sfap.setVisible(true);
                break;

            //SOF Unit
            case ("btn_sffp"):
                sp_sfap.setVisible(true);
                break;

            //Subsurface track
            case ("btn_sfup"):
                sp_sfap.setVisible(true);
                break;

        }
    }

    public void cot_out_pressed(ActionEvent actionEvent) {
        Button cotButton = (Button) actionEvent.getSource();
        System.out.println(cotButton.getAccessibleText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
