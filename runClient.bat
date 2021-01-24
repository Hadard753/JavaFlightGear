@echo off
java -Xms512M -Xmx512M --module-path "C:\Users\isako\Desktop\javafx-sdk-11.0.2\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web -jar out/artifacts/Client_jar/FinalProject.jar -o true
PAUSE