package UI;

import com.sun.javafx.tools.packager.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Coordinator {

    private Parent windowSceneContent(String fxml,Stage rootStage, String title,double width, double height) throws Exception{
        System.out.println("WindowSceneContent");
        Parent page=(Parent) FXMLLoader.load(getClass().getResource(fxml),null, new JavaFXBuilderFactory());
        Stage stage=new Stage();
        stage.setScene(new Scene(page));
        stage.setTitle(title);
        stage.setWidth(width);
        stage.setHeight(height);
        double midX=rootStage.getX() + rootStage.getWidth() / 2;
        double midY=rootStage.getY() + rootStage.getHeight() / 2;
        stage.setX( midX- stage.getWidth() / 2);
        stage.setY( midY- stage.getHeight() / 2);
        stage.initModality(Modality.APPLICATION_MODAL);
        /*stage.initStyle(StageStyle.UNDECORATED);
        stage.getScene().getRoot().setEffect(new DropShadow());
        stage.getScene().setFill(Color.TRANSPARENT);*/
        stage.show();
        return page;
    }

    private Parent replaceSceneContent(String fxml,Stage rootStage) throws Exception{
        System.out.println("replaceSceneContent");
        //Parent page=(Parent) FXMLLoader.load(UI.Mains.class.getResource(fxml),null, new JavaFXBuilderFactory());
        Parent page=FXMLLoader.load(getClass().getResource(fxml));
        Scene scene=rootStage.getScene();
        if(scene==null){
            scene=new Scene(page,1366,1000);
            rootStage.setScene(scene);
        }else{
            rootStage.getScene().setRoot(page);
        }
        rootStage.sizeToScene();
        return page;
    }

    public void goToParametersWindow(Stage stage, double width, double height){
       try{
           windowSceneContent("Parameter/parameters.fxml",stage,"Параметры", width, height);
       }catch (Exception ex){
           System.out.println("Exception goToParametersWindow "+ex);
       }
    }

    public void replaceWithParametersScene(Stage stage){
        try{
            replaceSceneContent("Parameter/parameters.fxml",stage);
        }catch (Exception ex){
            System.out.println("Exception replaceWithParametersScene "+ex);
        }
    }

    public void replaceWithMainScene(Stage stage) {
        try{
            replaceSceneContent("sample.fxml",stage);
        }catch (Exception ex){
            System.out.println("Exception replaceWithMainScene " +ex);
        }
    }

    public void goToAddParameterWindow(Stage stage, double width, double height){
        try{
            windowSceneContent("Parameter/addParameter.fxml",stage, "Новый параметр", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToAddParameterWindow "+ex);
        }
    }

    public void replaceWithAddParameterScene(Stage stage){
        try{
            replaceSceneContent("Parameter/addParameter.fxml",stage);
        }catch (Exception ex){
            System.out.println("Exception replaceWithAddParameterScene "+ex);
        }
    }

    public void goToEditParameterWindow(Stage stage, double width, double height){
        try{
            windowSceneContent("Parameter/editParameter.fxml",stage, "Параметр", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToEditParameterWindow");
        }
    }

    public void goToStatesWindow(Stage stage, double width, double height){
        try{
            windowSceneContent("State/states.fxml", stage, "Состояние", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToStatesWindow "+ex);
        }
    }

    public void replaceWithStatesScene(Stage stage){
        try{
            replaceSceneContent("State/states.fxml", stage);
        }catch (Exception ex){
            System.out.println("Exception replaceWithStatesScene "+ex);
        }
    }

    public void goToAddStateWindow(Stage stage, double width, double height){
        try{
            windowSceneContent("State/addState.fxml", stage, "Новое состояние", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToAddStateWindow "+ex );
        }
    }

    public void goToAreaWindow(Stage stage, double width, double height){
        try{
            windowSceneContent("Area/areas.fxml",stage,"Районы", width, height);
        }catch(Exception ex){
            System.out.println("Exception goToAreaWindow " +ex);
        }
    }

    public void goToAddAreaWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Area/addArea.fxml",stage,"Новый район", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToAddAreaWindow "+ex);
        }
    }

    public void goToWorkersWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Worker/workers.fxml",stage,"Сотрудники", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToWorkersWindow "+ex);
        }
    }

    public void goToAddWorkerWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Worker/addWorker.fxml",stage,"Новый сотрудник", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToAddWorkerWindow "+ex);
        }
    }

    public void goToTypesWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Type/types.fxml",stage,"Типы", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToTypeWindow "+ex);
        }
    }

    public void goToAddTypeWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Type/addType.fxml",stage,"Новый тип", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToAddTypeWindow "+ex);
        }
    }

    public void goToEditTypeWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Type/editType.fxml",stage,"Тип", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToEditType "+ex);
        }
    }

    public void goToSupplysWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Supply/supplys.fxml",stage,"Поставки", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToSupplysWindow "+ex);
        }
    }

    public void goToAddSupplyWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Supply/addSupply.fxml",stage,"Новая поставка", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToAddSupplyWindow "+ex);
        }
    }

    public void goToEditSupplyWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Supply/editSupply.fxml",stage,"Редактирование", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToEditSupplyWindow "+ex);
        }
    }

    public void goToEquipmentsWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Equipment/equipments.fxml",stage,"Оборудование", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToEquipmentsWindow "+ex);
        }
    }

    public void goToAddEquipmentWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Equipment/addEquipment.fxml",stage,"Новое оборудование", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToAddEquipmentWindow "+ex);
        }
    }

    public void goToEditEquipmentWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Equipment/editEquipment.fxml",stage,"оборудование", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToEditEquipmentWindow "+ex);
        }
    }

    public void goToInventoryNumbersWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Inventory_number/inventoryNumbers.fxml", stage,"нвентарные номера", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToInventoryNumberWindow "+ex);
        }
    }

    public void goToAddInventoryNumberWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Inventory_number/addInventoryNumber.fxml",stage,"Новый номер", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToAddInventoryNumber "+ex);
        }
    }

    public void goToEditInventoryNumber(Stage stage, double width, double height){
        try{
            windowSceneContent("Inventory_number/editInventoryNumber.fxml",stage,"Редактировать", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToEditInventoryNumber "+ex);
        }
    }

    public void goToEquipentInventoryWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Equipment/Equipment_inventory/equipmentInventory.fxml",stage,"Конкретное оборудование", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToEquipmentInventiryWindow "+ex);
        }
    }

    public void goToAddEquipmentInventoryWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Equipment/Equipment_inventory/addEquipmentInventory.fxml",stage,"Новое оборудование", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToAddEquipmentInventoryWindow "+ex);
        }
    }

    public void goToAddEquipmentStateWindow(Stage stage, double width, double height){
        try{
            windowSceneContent("Equipment/Equipment_inventory/Equipment_state/addEquipmentState.fxml",stage,"Новое состояние", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToAddEquipmnetWindow "+ex);
        }
    }

    public void goToDepartmentsWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Department/departments.fxml",stage,"Отделы", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToDepartmentsWindow "+ex);
        }
    }

    public void goToAddDepartmentWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Department/addDepartment.fxml",stage,"Новый отдел", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToAddDepartmentWindow" +ex );
        }
    }

    public void goToEditDepartmentWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Department/editDepartment.fxml",stage,"Отдел", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToEditDepartmentWindow "+ex);
        }
    }

    public void goToFilesDepartmentWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Department/DepartmentFiles/filesDepartment.fxml",stage,"файлы", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToFilesDepartmentWindow "+ex);
        }
    }

    public void goToPhotoDepartmentWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Department/DepartmentFiles/photoDepartment.fxml",stage,"Фото", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToPhotoDepartmentWindow "+ex);
        }
    }

    public void goToAddWorkerDepartmentWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Department/addWorker.fxml",stage,"Добавить сотрудника", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToAddWorkerDepartmentWindow "+ex);
        }
    }

    public void goToAddPurchaseWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Department/addPurchase.fxml",stage,"Добавить закупку", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToAddPurchaseWindow "+ex);
        }
    }

    public void goToAddMovementWindow(Stage stage, double width, double height){
        try{
            windowSceneContent("Movement/addMovement.fxml",stage,"Новое перемещение", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToAddMovementWindow "+ex);
        }
    }

    public void goToMovementsWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Movement/movements.fxml",stage,"Перемещения", width, height);
        }catch (Exception ex){
            System.out.println("Exeption goToMovementsWindow "+ex);
        }
    }

    public void goToProvidersWindows(Stage stage, double width, double height){
        try{
            windowSceneContent("Provider/providers.fxml", stage, "Поставщики", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToProviderWindow "+ex);
        }
    }

    public void goToAddProviderWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Provider/addProvider.fxml", stage, "Новый поставщик", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToAddProviderWindow "+ex);
        }
    }

    public void goToAddLocationWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Department/addLocation.fxml", stage, "Новый адрес", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToAddProviderWindow "+ex);
        }
    }

    public void goToMoveEquipmentInventoryWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Equipment/Equipment_inventory/moveEquipmentInventory.fxml", stage, "Перемещение", width, height);
        }catch (Throwable ex){
            System.out.println("Exception goToMoveEquipmentInventoryWindow "+ex);
        }
    }



}