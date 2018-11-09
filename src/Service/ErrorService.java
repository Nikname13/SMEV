package Service;

public class ErrorService {

    private static ErrorService sErrorService;
    private IErrorMessage mErrorListener;

    private ErrorService() {
    }

    public static ErrorService get() {
        if (sErrorService == null) sErrorService = new ErrorService();
        return sErrorService;
    }


    public IErrorMessage getErrorListener() {
        return mErrorListener;
    }

    public void setErrorListener(IErrorMessage errorListener) {
        mErrorListener = errorListener;
    }

    public void showError(String errorMessage) {
        mErrorListener.showError(errorMessage);
    }

    public void overrideError(String methodName, Class currentClass) {
        System.out.println("Метод " + methodName + " в \"" + currentClass + "\" не переопредлен");
    }
}
