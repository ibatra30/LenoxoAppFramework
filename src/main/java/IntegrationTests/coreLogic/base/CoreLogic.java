package IntegrationTests.coreLogic.base;

/**
 * contains all methods which are present in AndroidLoginHelper and IOSLoginHelper.
 * Methods are abstract here and defined in specific class (AndroidLoginHelper and IOSLoginHelper)
 */
public abstract class CoreLogic {

    public abstract void verifyLoginFlow(String userName, String password, String logo, String txtUsername, String repair)
            throws InterruptedException;

    public abstract void clearCheckoutCart()
            throws InterruptedException;

    public abstract void selectProductFromProductList(String postalCode, String product, String results, String miles, String heaterProduct, String storeName)
            throws InterruptedException;

    public abstract void addProductToCart(String qty)
            throws InterruptedException;

    public abstract void checkOutProduct(String poNumber)
            throws InterruptedException;

}
