package xyz.ronella.crypto.symmetric.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.slf4j.LoggerFactory;
import xyz.ronella.crypto.symmetric.CryptoSym;
import xyz.ronella.crypto.symmetric.generator.SecretMgr;
import xyz.ronella.crypto.symmetric.gui.common.Images;
import xyz.ronella.crypto.symmetric.gui.function.ApplicationTitle;
import xyz.ronella.crypto.symmetric.util.KeyResolver;
import xyz.ronella.crypto.symmetric.util.impl.EnvAsKey;
import xyz.ronella.crypto.symmetric.util.impl.EnvAsKeyByProp;
import xyz.ronella.crypto.symmetric.util.impl.StringAsKey;
import xyz.ronella.logging.LoggerPlus;
import xyz.ronella.trivial.command.Invoker;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class CryptoSymController implements Initializable {

    private final static LoggerPlus LOG = new LoggerPlus(LoggerFactory.getLogger(CryptoSymController.class));
    private static final String COLOR_LIGHT_GREEN = "#90EE90";
    private static final String COLOR_LIGHT_RED = "#F08080";
    private static final String NO_COLOR = "#FFFFFF";

    @FXML
    private RadioButton radDefaultEnv;

    @FXML
    private RadioButton radKeyHolder;

    @FXML
    private RadioButton radActualKey;

    @FXML
    private ToggleGroup radKeyMode;

    @FXML
    private ToggleGroup radOperation;

    @FXML
    private Button btnGenerateKey;

    @FXML
    private TextField txtKey;

    @FXML
    private RadioButton radEncrypt;

    @FXML
    private RadioButton radDecrypt;

    @FXML
    private TextField txtInput;

    @FXML
    private TextField txtOutput;

    @FXML
    private Button btnExecute;

    @FXML
    private MenuBar mainMenuBar;

    @FXML
    private void mnuAboutAction(ActionEvent event) {
        try {
            Stage parentStage = (Stage) mainMenuBar.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("about.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.getIcons().add(Images.ICON);
            stage.initOwner(parentStage);
            stage.setTitle(Invoker.generate(new ApplicationTitle()));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @FXML
    private void btnGenerateKeyAction(ActionEvent event) throws NoSuchAlgorithmException {
        txtKey.textProperty().set(SecretMgr.generateKeyAsString());
    }

    @FXML
    private void btnClearAction(ActionEvent event) {
        txtInput.textProperty().set("");
        txtInput.setStyle(calcTextBackground(NO_COLOR));
        txtOutput.textProperty().set("");
    }

    @FXML
    private void btnExecuteAction(ActionEvent event) {
        final var key = txtKey.textProperty().get();
        final var input = txtInput.textProperty().get();
        final var operation = (RadioButton) radOperation.getSelectedToggle();
        final var keyMode = (RadioButton) radKeyMode.getSelectedToggle();
        final var output = txtOutput.textProperty();
        final var shouldEncrypt = operation.getText().equalsIgnoreCase("Encrypt");

        final KeyResolver keyResolver = switch (keyMode.getText()) {
            case "Key Holder" -> new EnvAsKey(key);
            case "Actual Key" -> new StringAsKey(key);
            default -> new EnvAsKey("CRYPTOSYM_KEY");
        };

        try {
            output.set(shouldEncrypt ? CryptoSym.encrypt(keyResolver, input) : CryptoSym.decrypt(keyResolver, input));
            txtInput.setStyle(calcTextBackground(COLOR_LIGHT_GREEN));
        }
        catch (Exception exp) {
            LOG.error(LOG.getStackTraceAsString(exp));
            txtInput.setStyle(calcTextBackground(COLOR_LIGHT_RED));
        }
    }

    private String calcTextBackground(final String color) {
        return String.format("-fx-control-inner-background: %s", color);
    }

    private void updateKeyComponents(boolean disableKeyText, boolean disableGenerateKey, final String promptText) {
        txtKey.textProperty().set("");
        txtKey.promptTextProperty().set(promptText);
        txtKey.promptTextProperty().set(promptText);
        txtKey.disableProperty().set(disableKeyText);
        btnGenerateKey.disableProperty().set(disableGenerateKey);
    }

    private void updateBtnExecuteText(final String text) {
        btnExecute.textProperty().set(text);
        txtInput.promptTextProperty().set(text.equalsIgnoreCase("Encrypt") ? "Plain Text" : "Cypher Text");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.debug("Initialize");

        radDefaultEnv.selectedProperty().addListener((___observable, ___oldValue, ___newValue) -> {
            if (___newValue) {
                updateKeyComponents(true, true, "");
            }
        });

        radKeyHolder.selectedProperty().addListener((___observable, ___oldValue, ___newValue) -> {
            if (___newValue) {
                updateKeyComponents(false, true, "Key Holder");
            }
        });

        radActualKey.selectedProperty().addListener((___observable, ___oldValue, ___newValue) -> {
            if (___newValue) {
                updateKeyComponents(false, false, "Actual Key");
            }
        });

        radEncrypt.selectedProperty().addListener((___observable, ___oldValue, ___newValue) -> {
            if (___newValue) {
                updateBtnExecuteText(radEncrypt.getText());
            }
        });

        radDecrypt.selectedProperty().addListener((___observable, ___oldValue, ___newValue) -> {
            if (___newValue) {
                updateBtnExecuteText(radDecrypt.getText());
            }
        });

        txtInput.textProperty().addListener((___observable, ___oldValue, ___newValue) -> {
            btnExecute.disableProperty().set(___newValue.isEmpty());
        });

    }

}
