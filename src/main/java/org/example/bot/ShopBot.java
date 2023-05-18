package org.example.bot;

import lombok.SneakyThrows;
import org.example.domain.model.UserState;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import static org.example.domain.model.UserState.*;

public class ShopBot extends TelegramLongPollingBot {
    ShopBotService botService = new ShopBotService();

    @Override
    public String getBotUsername() {
        return "http://t.me/g21Shop_bot";
    }

    @Override
    public String getBotToken() {
        return "5941493722:AAHbkgS2NtkO01w9c41NkZnmLGseuxcRcbI";
    }

    @SneakyThrows
    @Override

    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String chatId = message.getChatId().toString();
            String text = message.getText();

            System.out.println(message.getFrom().getFirstName() + " -> " + message.getText());

            UserState userState = botService.checkState(chatId);

            if (userState == START) {
                if (message.hasContact()) {
                    execute(botService.registerUser(chatId, message.getContact()));
                } else {
                    execute(botService.shareContact(chatId));
                }
            }
            if (userState == REGISTERED || userState == PRODUCT_LIST || userState == PRODUCT
                    || userState == BASKET || userState == ORDERS) {
                if (text.equals("\uD83D\uDCCB Categories") || text.equals("\uD83D\uDCD1 Orders") ||
                        text.equals("\uD83D\uDED2 Basket")) {
                    UserState state = botService.updateState(chatId, text);
                    execute(botService.main(chatId));
                    switch (state) {
                        case CATEGORIES -> execute(botService.categories(chatId));
                        case BASKET -> execute(botService.userBasket(chatId));
                        case ORDERS -> execute(botService.orders(chatId));
                    }
                }
            }


        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message message = callbackQuery.getMessage();
            String data = callbackQuery.getData();

            switch (data) {
                case "PC" -> execute(botService.   Products(message.getChatId(), "PC"));
                case "laptop" -> execute(botService.Products(message.getChatId(), "LAPTOP"));
                case "phone" -> execute(botService.Products(message.getChatId(), "PHONE"));
                case "tv" -> execute(botService.Products(message.getChatId(), "TV"));
                case "Iphone X" -> execute(botService.AddBasket(message.getChatId(), "Iphone X"));
                case "S20 Ultra" -> execute(botService.AddBasket(message.getChatId(), "S20 Ultra"));
                case "S23 Ultra" -> execute(botService.AddBasket(message.getChatId(), "S23 Ultra"));
                case "Iphone 14" -> execute(botService.AddBasket(message.getChatId(), "Iphone 14"));
                case "Lenovo legion gaming" ->
                        execute(botService.AddBasket(message.getChatId(), "Lenovo legion gaming"));
                case "RTX 3080 gaming" -> execute(botService.AddBasket(message.getChatId(), "RTX 3080 gaming"));
                case "Dell Precision 7920: 16GB RAM, 512GB SSD" ->
                        execute(botService.AddBasket(message.getChatId(), "Dell Precision 7920: 16GB RAM, 512GB SSD"));
                case "ROG Strix G35CZ G35CZ-XB982" ->
                        execute(botService.AddBasket(message.getChatId(), "ROG Strix G35CZ G35CZ-XB982"));
                case " HP Victus 16 Intel i5-12450H 8GB, 256GB SSD, GTX1650 4GB" ->
                        execute(botService.AddBasket(message.getChatId(), " HP Victus 16 Intel i5-12450H 8GB, 256GB SSD, GTX1650 4GB"));
                case "MacBook Pro" -> execute(botService.AddBasket(message.getChatId(), "MacBook Pro"));
                case "Asus Rog Strix G713" -> execute(botService.AddBasket(message.getChatId(), "Asus Rog Strix G713"));
                case "Lenovo V15-IGL" -> execute(botService.AddBasket(message.getChatId(), "Lenovo V15-IGL"));
                case "Samsung S95B OLED" -> execute(botService.AddBasket(message.getChatId(), "Samsung S95B OLED"));
                case "Sony A95K OLED" -> execute(botService.AddBasket(message.getChatId(), "Sony A95K OLED"));
                case "TCL 6 Series/R655 2022 QLED" ->
                        execute(botService.AddBasket(message.getChatId(), "TCL 6 Series/R655 2022 QLED"));
                case "Hisense U8H" -> execute(botService.AddBasket(message.getChatId(), "Hisense U8H"));
                case "1" -> execute(botService.Basket(message.getChatId(), 1));
                case "2" -> execute(botService.Basket(message.getChatId(), 2));
                case "3" -> execute(botService.Basket(message.getChatId(), 3));
                case "back to menu" -> execute(botService.main(message.getChatId().toString()));
                case "categories" -> execute(botService.categories(message.getChatId().toString()));
                case "products" -> execute(botService.Products(message.getChatId(), botService.type1));
                case "back" -> execute(botService.AddBasket(message.getChatId(), botService.prodName1));
                case "c9d28ef4-32cc-45f0-a8bc-b4df91ca026c" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "c9d28ef4-32cc-45f0-a8bc-b4df91ca026c"));
                case "51c02280-415b-4778-8400-6b011e28d324" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "51c02280-415b-4778-8400-6b011e28d324"
                        ));
                case "3679e201-f999-457a-a2cc-36e2f34647cb" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "3679e201-f999-457a-a2cc-36e2f34647cb"
                        ));
                case "3ce456e3-3a4f-4b3b-8ddd-6de99f9b4dc0" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "3ce456e3-3a4f-4b3b-8ddd-6de99f9b4dc0"
                        ));
                case "6135dc18-62ad-4888-a36d-e90704400eb8" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "6135dc18-62ad-4888-a36d-e90704400eb8"
                        ));
                case "06eb2d25-2139-43d0-a6b3-a5b27f737cf1" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "06eb2d25-2139-43d0-a6b3-a5b27f737cf1"
                        ));
                case "d0ba2486-840d-44fb-9e3b-3f30145bbc5c" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "d0ba2486-840d-44fb-9e3b-3f30145bbc5c"
                        ));
                case "96db492d-93eb-49b6-87a4-dd7df8bce92e" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "96db492d-93eb-49b6-87a4-dd7df8bce92e"
                        ));
                case "e4313fc1-6ee3-46da-8929-3eaf6982211c" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "e4313fc1-6ee3-46da-8929-3eaf6982211c"
                        ));
                case "e97a835c-9138-4dc5-b5db-633ef1607a1c" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "e97a835c-9138-4dc5-b5db-633ef1607a1c"
                        ));
                case "dca7a750-7b72-4ba2-baf5-61a9436eac4c" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "dca7a750-7b72-4ba2-baf5-61a9436eac4c"
                        ));
                case "ec4f590f-3de1-4abe-9e83-5fdda169880c" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "ec4f590f-3de1-4abe-9e83-5fdda169880c"
                        ));
                case "f99ca0c1-46a0-474b-941f-a4867357a3ee" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "f99ca0c1-46a0-474b-941f-a4867357a3ee"
                        ));
                case "2fc01b9c-9ab5-4064-af55-ac23cc7b0733" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "2fc01b9c-9ab5-4064-af55-ac23cc7b0733"
                        ));
                case "3a3bdfe9-64fb-464d-9ea5-56015328c775" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "3a3bdfe9-64fb-464d-9ea5-56015328c775"
                        ));
                case "91faa86e-dddb-4a15-9fa1-a8a1dd0e2d4e" ->
                        execute(botService.orderProduct(message.getChatId().toString(),
                                "91faa86e-dddb-4a15-9fa1-a8a1dd0e2d4e"
                        ));
                case "menu" -> execute(botService.main(message.getChatId().toString()));
            }
        }
    }
}
