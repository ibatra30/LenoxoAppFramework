package IntegrationTests.coreLogic.base;

/**
 * contains all methods which are present in AndroidLoginHelper and IOSLoginHelper.
 * Methods are abstract here and defined in specific class (AndroidLoginHelper and IOSLoginHelper)
 */
public abstract class CoreLogic {

    public abstract void verifyLoginFlow(String userName, String password, String logo)
            throws InterruptedException;

    public abstract void clearCheckoutCart()
            throws InterruptedException;

    public abstract void selectProductFromMenu(String results, String heaterProduct)
            throws InterruptedException;

    public abstract void userChangeTheStorePickup(String postalCode, String defaultDeliverTo, String defaultPickup, String miles, String storeName)
            throws InterruptedException;

    public abstract void addProductToCart(String qty, String productName, String CatNo, String modelNo, String price)
            throws InterruptedException;

    public abstract void checkOutProduct(String poNumber)
            throws InterruptedException;

}
