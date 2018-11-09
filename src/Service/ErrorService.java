package Service;

public class ErrorService {

    private static ErrorService sErrorService;

    private ErrorService() {
    }

    public static ErrorService get() {
        if (sErrorService == null) sErrorService = new ErrorService();
        return sErrorService;
    }


    public void overrideError(String methodName, Class currentClass) {
        System.out.println("Метод " + methodName + " в \"" + currentClass + "\" не переопредлен");
    }
}
