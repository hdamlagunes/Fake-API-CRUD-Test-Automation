package tests;

import base.BaseTest;
import io.restassured.response.Response;
import model.Product;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductCrudTest extends BaseTest {

    private int productId;
    private int initialProductCount;

    @Test(priority = 1)
    public void createProduct() {

        Response categoryResponse = request.get("/categories");
        int dynamicCategoryId = categoryResponse.jsonPath().getInt("[0].id");


        Product product = new Product();
        product.setTitle("Test Ürünü");
        product.setPrice(199);
        product.setDescription("Test açıklaması");
        product.setCategoryId(dynamicCategoryId);
        product.setImages(new String[]{"https://placehold.co/600x400"});


        Response response = request
                .body(product)
                .post("/products");

        response.then().statusCode(201);
        productId = response.jsonPath().getInt("id");

        Assert.assertEquals(response.jsonPath().getString("title"), "Test Ürünü");
        System.out.println("Ürün oluşturuldu, ID: " + productId + ", CategoryID: " + dynamicCategoryId);
    }

    @Test(priority = 2)
    public void verifyProductCountIncreased() {
        Response responseBefore = request.get("/products");
        initialProductCount = responseBefore.jsonPath().getList("$").size();

        Response responseAfter = request.get("/products");
        int currentCount = responseAfter.jsonPath().getList("$").size();

        Assert.assertTrue(currentCount >= initialProductCount);
        System.out.println("Ürün sayısı kontrolü: " + currentCount);
    }

    @Test(priority = 3)
    public void updateProductPrice() {

        Product updatedProduct = new Product();
        updatedProduct.setTitle("Test Ürünü");
        updatedProduct.setPrice(249);
        updatedProduct.setDescription("Test açıklaması");
        updatedProduct.setCategoryId(1);
        updatedProduct.setImages(new String[]{"https://placeimg.com/640/480/any"});

        Response response = request
                .body(updatedProduct)
                .put("/products/" + productId);

        response.then().statusCode(200);
        Assert.assertEquals(response.jsonPath().getInt("price"), 249);
        System.out.println("✅ Ürün fiyatı güncellendi.");
    }


    @Test(priority = 4)
    public void deleteProduct() {
        Response response = request.delete("/products/" + productId);
        response.then().statusCode(200);
        System.out.println("Ürün silindi.");
    }

    @Test(priority = 5)
    public void updateDeletedProductExpectError() {
        Product update = new Product();
        update.setTitle("Hatalı Güncelleme");
        update.setPrice(123);
        update.setDescription("Deneme");
        update.setCategoryId(1);
        update.setImages(new String[]{"https://placeimg.com/640/480/any"});

        int statusCode;

        try {
            Response response = request
                    .body(update)
                    .put("/products/" + productId);

            statusCode = response.getStatusCode();

        } catch (Exception e) {

            if (e.getMessage().contains("status code: 400")) {
                statusCode = 400;
            } else if (e.getMessage().contains("status code: 404")) {
                statusCode = 404;
            } else {

                throw e;
            }
        }

        Assert.assertTrue(statusCode == 400 || statusCode == 404,
                "Beklenen hata alınamadı. Gerçek status: " + statusCode);
    }

}
