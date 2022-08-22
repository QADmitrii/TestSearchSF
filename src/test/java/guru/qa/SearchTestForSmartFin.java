package guru.qa;

import com.codeborne.selenide.Configuration;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SearchTestForSmartFin {

    @BeforeAll
    static void config() {
        Configuration.baseUrl = "https://shop.2canonline.com";
        Configuration.browserPosition = "0x0";
        Configuration.browserSize = "1920x1080";
    }

    @ValueSource(strings = {"Меркурий 105Ф", "2can Касса 6 Pro"})
    @ParameterizedTest(name = "Результаты поиска товара {0}")
    void smartCatalogFilter(String testValue) {
        open("/");
        $("[data-ctrl='search-btn']").click();
        $("[name='search-item']").setValue(testValue);
        $("body .card-body").should(text(testValue));

    }

    @CsvSource(value = {
            "Меркурий 105Ф, 20 490,00 ₽",
            "2can Касса 6 Pro, 26 990,00 ₽",
    })
    @ParameterizedTest(name = "Соответсвие цены {1} для товара {0}")
    void searchResultTest(String testValue, String expectedResult) {
        open("/");
        $("[data-ctrl='search-btn']").click();
        $("[name='search-item']").setValue(testValue);
        $("body .card-body").should(text(testValue)).shouldHave(text(expectedResult));

    }

    @EnumSource(Data.class)
    @ParameterizedTest(name = "Проверка на отображение товаров в каталоге {0}")
    void catalogCheckVisibleSearch(@NotNull Data data) {
        open("/");
        $("[data-ctrl='search-btn']").click();
        $("[name='search-item']").setValue(data.name());
        $("[data-wrapper='toolbar-products-list']").shouldBe(visible);

    }

}
