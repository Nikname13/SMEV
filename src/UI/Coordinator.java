package UI;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Coordinator {

    private Parent windowSceneContent(String fxml, Stage rootStage, String title, double width, double height) throws Exception {
        System.out.println("WindowSceneContent");
        Parent page = FXMLLoader.load(getClass().getResource(fxml), null, new JavaFXBuilderFactory());
        Stage stage=new Stage();
        stage.setScene(new Scene(page));
        stage.setTitle(title);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setMinHeight(height);
        stage.setMinWidth(width);
        double midX=rootStage.getX() + rootStage.getWidth() / 2;
        double midY=rootStage.getY() + rootStage.getHeight() / 2;
        stage.setX( midX- stage.getWidth() / 2);
        stage.setY( midY- stage.getHeight() / 2);
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initStyle(StageStyle.UNDECORATED);
        /*stage.getScene().getRoot().setEffect(new DropShadow());
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
            scene = new Scene(page, 100, 100);
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

    public void goToAddParameterWindow(Stage stage){
        try{
            windowSceneContent("Parameter/addParameter.fxml",stage, "Новый параметр", 250, 170);
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

    public void goToAddStateWindow(Stage stage){
        try{
            windowSceneContent("State/addState.fxml", stage, "Новое состояние", 250, 170);
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

    public void goToAddAreaWindow(Stage stage){
        try {
            windowSceneContent("Area/addArea.fxml",stage,"Новый район",300, 170);
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

    public void goToAddWorkerWindow(Stage stage){
        try {
            windowSceneContent("Worker/addWorker.fxml",stage,"Новый сотрудник",260.0, 300.0);
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

    public void goToAddTypeWindow(Stage stage){
        try {
            windowSceneContent("Type/addType.fxml",stage,"Новый тип",300, 330);
        }catch (Exception ex){
            System.out.println("Exception goToAddTypeWindow " + ex);
        }
    }

    public void goToEditTypeWindow(Stage stage){
        try {
            windowSceneContent("Type/editType.fxml",stage,"Тип", 250, 170);
        }catch (Exception ex){
            System.out.println("Exception goToEditType " + ex);
        }
    }

    public void goToSupplysWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Supply/supplys.fxml",stage,"Поставки", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToSupplysWindow " + ex);
        }
    }

    public void goToAddSupplyWindow(Stage stage) {
        try {
            windowSceneContent("Supply/addSupply.fxml", stage, "Новая поставка", 298, 535);
        }catch (Exception ex){
            System.out.println("Exception goToAddSupplyWindow " + ex);
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
            System.out.println("Exception goToEquipmentsWindow " + ex);
        }
    }

    public void goToAddEquipmentWindow(Stage stage) {
        try {
            windowSceneContent("Equipment/addEquipment.fxml", stage, "Новое оборудование", 410, 485);
        }catch (Exception ex){
            System.out.println("Exception goToAddEquipmentWindow " + ex);
        }
    }

    public void goToEditEquipmentWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Equipment/editEquipment.fxml",stage,"оборудование", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToEditEquipmentWindow " + ex);
        }
    }

    public void goToInventoryNumbersWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Inventory_number/inventoryNumbers.fxml", stage,"нвентарные номера", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToInventoryNumberWindow "+ex);
        }
    }

    public void goToAddInventoryNumberWindow(Stage stage) {
        try {
            windowSceneContent("Inventory_number/addInventoryNumber.fxml", stage, "Новый номер", 300, 305);
        }catch (Exception ex){
            System.out.println("Exception goToAddInventoryNumber " + ex);
        }
    }

    public void goToEditInventoryNumber(Stage stage, double width, double height){
        try{
            windowSceneContent("Inventory_number/editInventoryNumber.fxml",stage,"Редактировать", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToEditInventoryNumber "+ex);
        }
    }


    public void goToInventoryNumberLog(Stage stage){
        try{
            windowSceneContent("Inventory_number/inventoryNumberLog.fxml",stage,"Журнал изменений номера", 560.0, 355.0);
        }catch (Exception ex){
            System.out.println("Exception goToEditInventoryNumber "+ex);
        }
    }

    public void goToEquipentInventoryWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Equipment/Equipment_inventory/equipmentInventory.fxml",stage,"Конкретное оборудование", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToEquipmentInventoryWindow " + ex);
        }
    }

    public void goToAddEquipmentInventoryWindow(Stage stage) {
        try {
            windowSceneContent("Equipment/Equipment_inventory/addEquipmentInventory.fxml", stage, "Новое оборудование", 305.0, 395);
        }catch (Exception ex){
            System.out.println("Exception goToAddEquipmentInventoryWindow "+ex);
        }
    }

    public void goToAddEquipmentStateLog(Stage stage) {
        try{
            windowSceneContent("Equipment/Equipment_inventory/Equipment_state/addEquipmentStateLog.fxml", stage, "Новое состояние", 360.0, 355.0);
        }catch (Exception ex){
            System.out.println("Exception goToAddEquipmnetWindow " + ex);
        }
    }

    public void goToEquipmentStateLog(Stage stage) {
        try {
            windowSceneContent("Equipment/Equipment_inventory/Equipment_state/equipmentStateLog.fxml", stage, "Журнаял изменения состояния", 560.0, 355.0);
        } catch (Exception ex) {
            System.out.println("Exception goToEquipmentStateLog " + ex);
        }
    }

    public void goToAddInventoryNumberLog(Stage stage) {
        try {
            windowSceneContent("Equipment/Equipment_inventory/Equipment_number/addInventoryNumberLog.fxml", stage, "Новый номер", 360.0, 355.0);
        } catch (Throwable ex) {
            System.out.println("Exception goToAddInventoryNumberLog " + ex);
        }
    }

    public void goToInventoryNumberEquipmentLog(Stage stage) {
        try {
            windowSceneContent("Equipment/Equipment_inventory/Equipment_number/inventoryLog.fxml", stage, "Журнал изменений номера", 560.0, 355.0);
        } catch (Throwable ex) {
            System.out.println("Exception goToAddInventoryNumberLog " + ex);
        }
    }

    public void goToDepartmentsWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Department/departments.fxml",stage,"Отделы", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToDepartmentsWindow " + ex);
        }
    }

    public void goToAddDepartmentWindow(Stage stage) {
        try {
            windowSceneContent("Department/addDepartment.fxml", stage, "Новый отдел", 310.0, 540.0);
        }catch (Exception ex){
            System.out.println("Exception goToAddDepartmentWindow" +ex );
        }
    }

    public void goToEditDepartmentWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Department/editDepartment.fxml",stage,"Отдел", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToEditDepartmentWindow " + ex);
        }
    }

    public void goToFilesDepartmentWindow(Stage stage) {
        try {
            windowSceneContent("Department/DepartmentFiles/filesDepartment.fxml", stage, "файлы", 100.0, 200.0);
        }catch (Exception ex){
            System.out.println("Exception goToFilesDepartmentWindow " + ex);
        }
    }

    public void goToPhotoDepartmentWindow(Stage stage) {
        try {
            windowSceneContent("Department/DepartmentFiles/photoDepartment.fxml", stage, "Фото", 100.0, 200.0);
        }catch (Exception ex){
            System.out.println("Exception goToPhotoDepartmentWindow " + ex);
        }
    }

    public void goToAddWorkerDepartmentWindow(Stage stage) {
        try {
            windowSceneContent("Worker/addWorker.fxml", stage, "Добавить сотрудника", 260.0, 300.0);
        }catch (Exception ex){
            System.out.println("Exception goToAddWorkerDepartmentWindow " + ex);
        }
    }

    public void goToEditWorkerWindow(Stage stage) {
        try {
            windowSceneContent("Worker/editWorker.fxml", stage, "Сотрудник", 260.0, 300.0);
        } catch (Exception ex) {
            System.out.println("Exception goToEditWorkerWindow " + ex);
        }
    }

    public void goToAddPurchaseWindow(Stage stage) {
        try {
            windowSceneContent("Department/addPurchase.fxml", stage, "Добавить закупку", 365.0, 360.0);
        }catch (Exception ex){
            System.out.println("Exception goToAddPurchaseWindow " + ex);
        }
    }

    public void goToPurchasesWindow(Stage stage) {
        try {
            windowSceneContent("Department/purchases.fxml", stage, "Закупки", 365.0, 360.0);
        } catch (Exception ex) {
            System.out.println("Exception goToPurchasesWindow " + ex);
        }
    }

    public void goToAddMovementWindow(Stage stage) {
        try{
            windowSceneContent("Movement/addMovement.fxml", stage, "Новое перемещение", 480, 510);
        }catch (Exception ex){
            System.out.println("Exception goToAddMovementWindow " + ex);
        }
    }

    public void goToMovementsWindow(Stage stage, double width, double height){
        try {
            windowSceneContent("Movement/movements.fxml",stage,"Перемещения", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToMovementsWindow " + ex);
        }
    }

    public void goToProvidersWindows(Stage stage, double width, double height){
        try{
            windowSceneContent("Provider/providers.fxml", stage, "Поставщики", width, height);
        }catch (Exception ex){
            System.out.println("Exception goToProviderWindow "+ex);
        }
    }

    public void goToAddProviderWindow(Stage stage){
        try {
            windowSceneContent("Provider/addProvider.fxml", stage, "Новый поставщик", 295, 240);
        }catch (Exception ex){
            System.out.println("Exception goToAddProviderWindow "+ex);
        }
    }

    public void goToAddLocationWindow(Stage stage) {
        try {
            windowSceneContent("Department/addLocation.fxml", stage, "Новый адрес", 275.0, 170.0);
        }catch (Exception ex){
            System.out.println("Exception goToAddProviderWindow "+ex);
        }
    }

    public void goToMoveEquipmentInventoryWindow(Stage stage) {
        try {
            windowSceneContent("Equipment/Equipment_inventory/Equipment_move/moveEquipmentInventory.fxml", stage, "Перемещение", 480.0, 375.0);
        }catch (Throwable ex){
            System.out.println("Exception goToMoveEquipmentInventoryWindow "+ex);
        }
    }

    public void goToMovementsEquipmentInventoryLog(Stage stage) {
        try {
            windowSceneContent("Equipment/Equipment_inventory/Equipment_move/movementsEquipmentLog.fxml", stage, "Журнал перемещений", 560.0, 355.0);
        } catch (Throwable ex) {
            System.out.println("Exception goToMovementsEquipmentInventoryLog " + ex);
        }
    }



}
