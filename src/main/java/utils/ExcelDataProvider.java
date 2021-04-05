package utils;

import org.testng.annotations.DataProvider;

import java.io.File;
import java.lang.reflect.Method;

import static utils.InitMethod.*;

public class ExcelDataProvider {

    @DataProvider(name = "multiSheetExcelRead")
    public static Object[][] multiSheetExcelRead(Method method) throws Exception {
        File file = new File("./src/test/resources/Excel Files/TestData.xlsx");
        String SheetName = method.getName();
        System.out.println(SheetName);
        return ExcelUtils.getTableArray(file.getAbsolutePath(), SheetName);
    }

    @DataProvider(name = "LennoxHeaterProductCheckoutTest")
    public static Object[][] excelSheetNameAsMethodName(Method method) throws Exception {
       // String excelPath = EXCEL_FOLDER + "LoginTestData.xlsx";
        File file = new File("./src/main/java/testData/testData.xlsx");
        System.out.println("Opening Excel File:" + file);
        return ExcelUtils.getTableArray(file.getAbsolutePath());
    }
}

