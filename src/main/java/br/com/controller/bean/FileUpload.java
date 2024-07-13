package br.com.controller.bean;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import br.com.controller.util.ValidaCertificado;
import br.com.controller.domain.Certificados;

@Named
@RequestScoped
public class FileUpload {

    private UploadedFile file;
    private String password; // Campo para armazenar a senha do usuário
    private ValidaCertificado valida = new ValidaCertificado(); // Inicializa a valida

    public void handleFileUpload(FileUploadEvent event) {
        this.file = event.getFile();
        String fileName = file.getFileName();

        try (InputStream inputStream = file.getInputStream()) {
            // Lê o certificado usando InputStream e senha
            Certificados certificado = valida.lerCertificado(inputStream, password);

            // Aqui você pode adicionar lógica para exibir as informações do certificado, se necessário
            FacesMessage message = new FacesMessage("Successful", fileName + " is selected and read.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException e) {
            FacesMessage errorMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Upload failed", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, errorMessage);
        }
    }

    public void submitPassword() {
        // Lógica para processar a senha
    }

    // Getters e Setters
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ValidaCertificado getValida() {
        return valida;
    }

    public void setValida(ValidaCertificado valida) {
        this.valida = valida;
    }
}
